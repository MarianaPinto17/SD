package ServerSide.objects;

import commonInfrastructures.*;
import ClientSide.entities.*;
import ServerSide.main.*;
import interfaces.*;
import java.rmi.*;

/**
 * Shared Region Departure Airport.
 * @author André Alves
 * @author Mariana Pinto
 */
public class DepartureAirport implements DepartureAirportInterface {
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
     * number of passengers in flight.
     */
    private int InF;

    /**
     * number of passengers at destinations airport.
     */
    private int PTAL;

    /**
     * number of passengers in departure airport that didn't checked.
     */
    private int passengersInDepartureNotChecked;

    /**
     * General repossitory of information
     * @serialField repos
     */
    private GeneralRepositoryInterface repos ;

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
     * Departure Airport constructor.
     *
     * @param repos general repository of information
     */
    public DepartureAirport(GeneralRepositoryInterface repos){
        readyForBoarding = false;
        InF = 0;
        PTAL = 0;
        passengersInDepartureNotChecked = 0;

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
    @Override
    public synchronized int informPlaneReadyForBoarding() throws RemoteException {
        // pilot is at transfer gate and ready for boarding
        repos.setPilotState(PilotStates.READY_FOR_BOARDING);
        System.out.println("Plane ready for boarding.");

        // hostess can start boarding
        readyForBoarding = true;
        notifyAll();

        return PilotStates.READY_FOR_BOARDING;
    }

    /**
     * Hostess Function - prepare to start boarding passengers on the plane.
     */
    @Override
    public synchronized Message prepareForPassBoarding() throws RemoteException {

        if(SimulPar.N == PTAL){
            return new Message(ClientSide.entities.HostessStates.WAIT_FOR_FLIGHT, true);
        }
        while(!readyForBoarding){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        InF = 0;
        repos.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        waitPassengers = true;

        notifyAll();

        return new Message(HostessStates.WAIT_FOR_PASSENGER,false);

    }

    /**
     * Passenger function - passenger waits in queue to board the plane.
     */
    @Override
    public synchronized Message waitInQueue(int passID) throws RemoteException{
        while(!waitPassengers){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        passengersInDepartureNotChecked++;
        repos.setPassengerState(PassengerStates.IN_QUEUE, passID);
        // number of passengers in queue increases

        try{
            passengersAtQueue.write(passID);
        }catch (MemException e){
            System.out.println("Insertion of customer id in FIFO failed: " +e.getMessage());
            System.exit(1);
        }
        notifyAll();

        return new Message(PassengerStates.IN_QUEUE, passID);
    }

    /**
     * Hostess function - if a passenger in queue checks documents, waits for passenger to show documents.
     */
    @Override
    public synchronized int checkDocuments() throws RemoteException{
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

        repos.setHostessState(HostessStates.CHECK_PASSENGER, checkDocID);

        this.checkedPass[checkDocID] = true;

        notifyAll();

        while (!docShow){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        docShow = false;

        return HostessStates.CHECK_PASSENGER;
    }

    /**
     * Passenger function - passenger shows documents to hostess.
     */
    @Override
    public synchronized void showDocuments(int passId) throws RemoteException {
        while (!this.checkedPass[passId]){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        docShow = true;
        passengersInDepartureNotChecked--;

        if(!(InF < 10 && !informPlane))
            notifyAll();
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
    @Override
    public synchronized int waitForNextPassenger() throws RemoteException {
        repos.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        this.canBoard = true;


        boolean readyToFly = InF == 10 || InF >= 5 & passengersAtQueue.isEmpty() || PTAL > SimulPar.N - 5;

        notifyAll();

        while(passengersAtQueue.isEmpty() && !(InF == 10 || InF >= 5 & passengersAtQueue.isEmpty() || PTAL > SimulPar.N - 5)){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        if(readyToFly){
            informPlane = true;
            planeAtDeparture = false;
        }

        return HostessStates.WAIT_FOR_PASSENGER;

    }

    /**
     * Passenger function - passenger boards the plane.
     */
    @Override
    public synchronized Message boardThePlane(int passId) throws RemoteException {
        InF++;
        repos.setPassengerState(PassengerStates.IN_FLIGHT, passId);

        notifyAll();

        return new Message(PassengerStates.IN_FLIGHT, passId);
    }

    /**
     * Hostess function - hostess waits for the next flight of the day.
     */
    @Override
    public synchronized int waitForNextFlight() throws RemoteException {
        repos.setHostessState(HostessStates.WAIT_FOR_FLIGHT);

        PTAL += InF;
        InF = 0;

        informPlane = false;
        while(!planeAtDeparture){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        notifyAll();

        return HostessStates.WAIT_FOR_FLIGHT;
    }

    /**
     * Pilot function - when the pilot parks the plane at the Transfer gate.
     */
    @Override
    public synchronized Message parkAtTransferGate() throws RemoteException {
        planeAtDeparture = true;
        boolean PilotEndOfLife = false;

        repos.setPilotState(PilotStates.AT_TRANSFER_GATE);

        if(SimulPar.N == PTAL){
            PilotEndOfLife = true;
        }

        notifyAll();

        return new Message(PilotStates.AT_TRANSFER_GATE, PilotEndOfLife);

    }


    /**
     *   Operation server shutdown.
     *
     *   New operation.
     *
     *      @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                              service fails
     */
    @Override
    public void shutdown() throws RemoteException {
        DepartureAirportMain.shutdown();
        notifyAll();
    }

    /**
     * Getters and setters.
     */

    /**
     * Checks if a plane is ready for flying.
     * @return true if the boarding is over.
     */
    public boolean isInformPlane() {
        return informPlane;
    }

}
