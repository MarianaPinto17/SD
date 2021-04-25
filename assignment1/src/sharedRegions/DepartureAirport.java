package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.SimulPar;

/**
 * Shared Region Departure Airport
 */
public class DepartureAirport {
    /**
     * If a plane is ready to start boarding (controlled by the pilot).
     */
    private boolean readyForBoarding;

    /**
     * if a plane is full.
     */
    private boolean planeIsFull;

    /**
     * If a plane has a minimum of passengers checked in.
     */
    private boolean planeIsMin;

    /**
     * number of passengers at Queue.
     */
    private MemFIFO<Integer> passengersAtQueue;

    /**
     * number of passengers at the plane.
     */
    private int passengersAtPlane;

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
     * General repository of information
     * @serialField repo
     */
    private GeneralRepository repo ;

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
     * Departure Airport constructor
     * @param repo general repository of information
     */
    public DepartureAirport(GeneralRepository repo){
        nPassengers = 0;
        nCheckedPassengers = 0;
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

        this.repo = repo;
    }

    /**
     * Pilot function - says to hostess boarding can start.
     */
    public synchronized void informPlaneReadyForBoarding(){
        pi = (Pilot) Thread.currentThread();
        // pilot is at transfer gate and ready for boarding
        pi.setCurrentState(PilotStates.READY_FOR_BOARDING);
        System.out.println("Plane ready for boarding.");
        // hostess can start boarding
        setReadyForBoarding(true);
        notifyAll();
    }

    /**
     * Hostess Function - prepare to start boarding passengers on the plane.
     */
    public synchronized void prepareForPassBoarding(){
        ho = (Hostess) Thread.currentThread();
        while(!isReadyForBoarding()){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        ho.setCurrentState(HostessStates.WAIT_FOR_PASSENGER);
        repo.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        notifyAll();

    }

    /**
     * Passenger function - passenger waits in queue to board the plane.
     */
    public synchronized void waitInQueue(){
        int passengerID = ((Passenger) Thread.currentThread()).getID();
        pass[passengerID] = (Passenger) Thread.currentThread();
        pass[passengerID].setCurrentState(PassengerStates.IN_QUEUE);
        repo.setPassengerState(passengerID, PassengerStates.IN_QUEUE);
        // number of passengers in queue increases
        nPassengers++;
        repo.setInQ(nPassengers);
        try{
            passengersAtQueue.write(passengerID);
        }catch (MemException e){
            System.out.println("Insertion of customer id in FIFO failed: " +e.getMessage());
            System.exit(1);
        }
        try{
            pass[passengerID].wait();
        } catch (InterruptedException e){}

        notifyAll();
    }

    /**
     * Hostess function - if a passenger in queue checks documents, waits for passenger to show documents.
     */
    public synchronized void checkDocuments(){
        int passengerID;
        ho.setCurrentState(HostessStates.CHECK_PASSENGER);
        repo.setHostessState(HostessStates.CHECK_PASSENGER);
        try{
            passengerID = passengersAtQueue.read();
        }catch (MemException e){
            System.out.println("Retrieval of passsenger ID from wainting FIFO failed." +e.getMessage());
            passengerID = -1;
            System.exit(1);
        }

        this.checkedPass[passengerID] = true;

        notifyAll();

        while (!docShow){
            try{
                wait();
            } catch (InterruptedException e){}
        }
    }

    /**
     * Passenger function - passenger shows documents to hostess.
     */
    public synchronized void showDocuments() {
        docShow = true;

        notifyAll();

        while (!canBoard){
            try{
                wait();
            } catch (InterruptedException e){}
        }
    }

    /**
     *  Hostess function - hostess waits for passengers if plane not full and not min and passenger in queue.
     */
    public synchronized void waitForNextPassenger(){
        ho.setCurrentState(HostessStates.WAIT_FOR_PASSENGER);
        repo.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        this.canBoard = true;
        nCheckedPassengers++;

        notifyAll();

        int passInDep = (SimulPar.N - (repo.getInF() + repo.getPTAL()));

        while(passengersAtQueue.isEmpty() || nCheckedPassengers != passInDep && (passInDep < 5)){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        if((nCheckedPassengers <= 10 && nCheckedPassengers > 5) || passInDep < 5){
            informPlane = true;
        }

    }

    /**
     * Hostess Function - if no passenger at queue or plane is full awakes pilot.
     */
    public synchronized void informPlaneReadyToTakeOff(){
        while(nPassengers != 0){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        ho.setCurrentState(HostessStates.READY_TO_FLY);
        repo.setHostessState(HostessStates.READY_TO_FLY);

        repo.setReadyToFly(true);

        notifyAll();
    }

    /**
     * Hostess function - hostess informs next Flight.
     */
    public synchronized void informNextFlight(){
        try{
            while(!planeAtDeparture){
                System.out.println("Plane not at departure gate.");
                wait();
            }
        }catch (InterruptedException exc){
            System.out.println("Plane at departure gate, start boarding.");
        }
    }

    /**
     * Passenger function - passenger boards the plane
     */
    public synchronized void boardThePlane() {
        nPassengers --;
        int passengerID = -1;
        try{
            passengerID = passengersAtQueue.read();
        }catch (MemException e){
            System.out.println("read of customer id in FIFO failed: " +e.getMessage());
            System.exit(1);
        }
        pass[passengerID].setCurrentState(PassengerStates.IN_FLIGHT);
        repo.setPassengerState(passengerID, PassengerStates.IN_FLIGHT);

        repo.setInF(repo.getInF() + 1);

        notifyAll();
    }

    /**
     * Getters and setters
     */

    /**
     * checks if a plane is ready for boaring
     * @return true if is ready
     */
    public boolean isReadyForBoarding() {
        return readyForBoarding;
    }

    /**
     * sets a plane ready for boarding
     * @param readyForBoarding
     */
    public void setReadyForBoarding(boolean readyForBoarding) {
        this.readyForBoarding = readyForBoarding;
    }

    /**
     * checks if a plane is ready for flying
     * @return true if the boarding is over
     */
    public boolean isInformPlane() {
        return informPlane;
    }

    /**
     * sets the plane ready for flying
     * @param informPlane
     */
    public void setInformPlane(boolean informPlane) {
        this.informPlane = informPlane;
    }
}
