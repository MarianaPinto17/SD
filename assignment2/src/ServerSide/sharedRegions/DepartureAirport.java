package ServerSide.sharedRegions;

import ClientSide.stub.GeneralRepositoryStub;
import commonInfrastructures.*;
import ServerSide.entities.*;
import ServerSide.main.*;

/**
 * Shared Region Departure Airport
 */
public class DepartureAirport {
    /**
     * If a plane is ready to start boarding (controlled by the pilot).
     */
    private boolean readyForBoarding;

    /**
     * number of passengers at Queue.
     */
    private MemFIFO<Integer> passengersAtQueue;

    /**
     * plane at departure gate.
     */
    private boolean planeAtDeparture;

    /**
     * number of passengers in queue.
     */
    private int nPassengers;

    /**
     * number of checked passengers in queue.
     */
    private int nCheckedPassengers;

    /**
     * number of checked passengers in total.
     */
    private int nCheckedPassengersTotal;

    /**
     * number of passengers in flight.
     */
    private int InF;

    /**
     * number of passengers at destinations airport.
     */
    private int PTAL;

    /**
     * number of passengers in Departure airport.
     */
    private int passengersInDeparture;

    /**
     * number of passengers in departure airport that didn't checked.
     */
    private int passengersInDepartureNotChecked;

    /**
     * number of passengers that already checked.
     */
    private int passengersChecked;

    /**
     * number of passengers that checked or are in later process..
     */
    private int passengersCheckedOrAtDest;

    /**
     * number os passengers who have not yet arrived.
     */
    private int passengerNotArrived;


    /**
     * General repossitory of information
     * @serialField repos
     */
    private GeneralRepositoryStub repos ;

    /**
     * Reference to passenger threads.
     */
    private final Passenger [] pass;

    /**
     * Reference to pilot.
     */
    private Pilot pi;

    /**
     * Reference to hostess.
     */
    private Hostess ho;

    /**
     * array of passengers who checked documents.
     */
    private boolean [] checkedPass;

    /**
     * if a passenger showed the documents to a hostess.
     */
    private boolean docShow;

    /**
     * if a passenger can board a plane.
     */
    private boolean canBoard;

    /**
     * if a plane is ready to take off.
     */
    private boolean informPlane;

    /**
     * false if hostess is WAIT_FOR_FLIGHT
     */
    private boolean waitPassengers;

    /**
     * Departure Airport constructor
     * @param repos general repository of information
     */
    public DepartureAirport(GeneralRepositoryStub repos){
        readyForBoarding = false;
        InF = 0;
        PTAL = 0;
        passengersInDeparture = 0;
        passengersInDepartureNotChecked = 0;
        passengersChecked = 0;
        passengersCheckedOrAtDest = 0;
        passengerNotArrived = SimulPar.N;

        pass = new Passenger[SimulPar.N];

        try{
            passengersAtQueue = new MemFIFO<>(new Integer[SimulPar.N]);
        }catch (MemException e){
            System.out.println ("Instantiation of waiting FIFO failed: " + e.getMessage ());
            passengersAtQueue = null;
            System.exit (1);
        }

        this.checkedPass = new boolean[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++) {
            this.checkedPass[i] = false;
        }

        docShow = false;
        canBoard = false;
        informPlane = false;
        waitPassengers = false;

        planeAtDeparture = true;

        this.repos = repos;
    }

    /**
     * Pilot function - says to hostess boarding can start.
     */
    public synchronized void informPlaneReadyForBoarding(){
        pi = (Pilot) Thread.currentThread();
        // pilot is at transfer gate and ready for boarding
        pi.setNflights(pi.getNflights()+1);
        pi.setPilotState(PilotStates.READY_FOR_BOARDING);
        repos.setPilotState(PilotStates.READY_FOR_BOARDING);
        System.out.println("Plane ready for boarding.");

        // hostess can start boarding
        readyForBoarding = true;
        notifyAll();
    }

    /**
     * Hostess Function - prepare to start boarding passengers on the plane.
     */
    public synchronized boolean prepareForPassBoarding(){
        ho = (Hostess) Thread.currentThread();

        if(SimulPar.N == nCheckedPassengersTotal){
            ho.setHEndOfLife(true);
            return true;
        }
        while(!readyForBoarding){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        InF = 0;
        ho.setHostessState(HostessStates.WAIT_FOR_PASSENGER);
        repos.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        waitPassengers = true;

        notifyAll();

        return false;

    }

    /**
     * Passenger function - passenger waits in queue to board the plane.
     */
    public synchronized void waitInQueue(){
        while(!waitPassengers){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        int passengerID = ((Passenger) Thread.currentThread()).getID();
        pass[passengerID] = (Passenger) Thread.currentThread();
        passengerNotArrived--;
        passengersInDeparture++;
        passengersInDepartureNotChecked++;
        pass[passengerID].setPassengerState(PassengerStates.IN_QUEUE);
        repos.setPassengerState(PassengerStates.IN_QUEUE, passengerID);
        // number of passengers in queue increases

        try{
            passengersAtQueue.write(passengerID);
        }catch (MemException e){
            System.out.println("Insertion of customer id in FIFO failed: " +e.getMessage());
            System.exit(1);
        }
        notifyAll();
    }

    /**
     * Hostess function - if a passenger in queue checks documents, waits for passenger to show documents.
     */
    public synchronized void checkDocuments(){
        ho = (Hostess) Thread.currentThread();
        while(passengersInDepartureNotChecked == 0){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        int checkDocID;
        try{
            checkDocID = passengersAtQueue.read();
        }catch (MemException e){
            System.out.println("Retrieval of passsenger ID from wainting FIFO failed." +e.getMessage());
            checkDocID = -1;
            System.exit(1);
        }

        ho.setHostessState(HostessStates.CHECK_PASSENGER);
        repos.setHostessState(HostessStates.CHECK_PASSENGER, checkDocID);

        this.checkedPass[checkDocID] = true;

        notifyAll();

        while (!docShow){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        docShow = false;
    }

    /**
     * Passenger function - passenger shows documents to hostess.
     */
    public synchronized void showDocuments() {
        int passengerID = ((Passenger) Thread.currentThread()).getID();
        pass[passengerID] = (Passenger) Thread.currentThread();
        while (!this.checkedPass[passengerID]){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        docShow = true;
        passengersChecked++;
        passengersCheckedOrAtDest++;
        passengersInDepartureNotChecked--;
        notifyAll();

//        while (!canBoard){
        System.out.println("[Pass] Plane at Departure? "+ planeAtDeparture);
        while (!(InF < 10 && !informPlane)){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        canBoard = false;
    }

    /**
     *  Hostess function - hostess waits for passengers if plane not full and not min and passenger in queue.
     */
    public synchronized void waitForNextPassenger(){
        ho = (Hostess) Thread.currentThread();
        ho.setHostessState(HostessStates.WAIT_FOR_PASSENGER);
        repos.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        this.canBoard = true;


        boolean readyToFly = InF == 10 || InF >= 5 & passengersAtQueue.isEmpty() || PTAL > SimulPar.N - 5;

        notifyAll();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("A QUEUE está vazia? "+passengersAtQueue.isEmpty());
        System.out.println("Numero de passangers que estão no departure airport: "+ passengersInDeparture);
        System.out.println("Numero de passangers que estão no departure airport mas ainda não fizeram check: "+ passengersInDepartureNotChecked);
        System.out.println("Numero de passangers que estão checked: "+ passengersChecked);
        System.out.println("Numero de passangers que estão checked ou já bazaram: "+ passengersCheckedOrAtDest);
        System.out.println("Número de passangers que ainda não chegaram ao DepAir: "+ passengerNotArrived);
        System.out.println("IN FLIGHT (InF): "+InF);
        System.out.println("IN FLIGHT (PTAL): "+PTAL);
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Vou dormir? "+ (passengersAtQueue.isEmpty() && !readyToFly));
        System.out.println("VOU VOAR? "+ ((InF == 10 || InF >= 5 & passengersAtQueue.isEmpty() || PTAL > SimulPar.N - 5)));
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");

        while(passengersAtQueue.isEmpty() && !(InF == 10 || InF >= 5 & passengersAtQueue.isEmpty() || PTAL > SimulPar.N - 5)){
//        while(){
            try{
                wait();
            } catch (InterruptedException e){}
        }

//        if((nCheckedPassengers >= 5 && passengersAtQueue.isEmpty()) || nCheckedPassengers == 10 || passInDep < 5){
        if(readyToFly){
            informPlane = true;
            planeAtDeparture = false;
            System.out.println("Flyyyyyyyyyyy");
        }

    }

    /**
     * Passenger function - passenger boards the plane.
     */
    public synchronized void boardThePlane() {
        int passengerID = ((Passenger) Thread.currentThread()).getID();
        pass[passengerID] = (Passenger) Thread.currentThread();

        InF++;
        pass[passengerID].setPassengerState(PassengerStates.IN_FLIGHT);
        repos.setPassengerState(PassengerStates.IN_FLIGHT, passengerID);

        notifyAll();
    }

    /**
     * Hostess function - hostess waits for the next flight of the day.
     */
    public synchronized void waitForNextFlight() {
        ho = (Hostess) Thread.currentThread();
        ho.setHostessState(HostessStates.WAIT_FOR_FLIGHT);
        repos.setHostessState(HostessStates.WAIT_FOR_FLIGHT);

        passengersInDeparture -= InF;
        passengersChecked -= InF;
        PTAL += InF;
        InF = 0;

        informPlane = false;
        while(!planeAtDeparture){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        notifyAll();

    }

    /**
     * Pilot function - when the pilot parks the plane at the Transfer gate.
     */
    public synchronized void parkAtTransferGate() {
        pi = (Pilot) Thread.currentThread();
        planeAtDeparture = true;

        pi.setPilotState(PilotStates.AT_TRANSFER_GATE);
        repos.setPilotState(PilotStates.AT_TRANSFER_GATE);

        if(SimulPar.N == nCheckedPassengersTotal){
            pi.setPiEndOfLife(true);

        }

        notifyAll();

    }

    /**
     * Getters and setters.
     */

//    /**
//     * Checks if a plane is ready for boarding.
//     * @return true if is ready.
//     */
//    public boolean isReadyForBoarding() {
//        return readyForBoarding;
//    }
//
//    /**
//     * Sets a plane ready for boarding.
//     * @param readyForBoarding changes status of readyForBoarding.
//     */
//    public void setReadyForBoarding(boolean readyForBoarding) {
//        this.readyForBoarding = readyForBoarding;
//    }

    /**
     * Checks if a plane is ready for flying.
     * @return true if the boarding is over.
     */
    public boolean isInformPlane() {
        return informPlane;
    }

    /**
     * Sets the plane ready for flying.
     * @param informPlane changes status of informPlane.
     */
    public void setInformPlane(boolean informPlane) {
        this.informPlane = informPlane;
    }
}
