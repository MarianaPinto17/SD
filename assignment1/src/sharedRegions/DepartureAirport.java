package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.SimulPar;

import java.beans.IntrospectionException;

/**
 * Shared Region Departure Airport
 */
public class DepartureAirport {
    /**
     * If a plane is ready to start boarding (controlled by the pilot)
     */
    private boolean readyForBoarding;

    /**
     * if a plane is ready to Fly
     */
    private boolean readyToFly;

    /**
     * if a plane is full
     */
    private boolean planeIsFull;

    /**
     * If a plane has a minimum of passengers checked in
     */
    private boolean planeIsMin;

    /**
     * number of passengers at Queue
     */
    private MemFIFO<Integer> passengersAtQueue;

    /**
     * number of passengers at the plane
     */
    private int passengersAtPlane;

    /**
     * plane at departure gate
     */
    private boolean planeAtDeparture;

    /**
     * number of passengers in queue
     */
    private int nPassengers;

    /**
     * General repository of information
     * @serialField repo
     */
    private GeneralRepository repo ;

    /**
     * Reference to customer threads
     */
    private final Passenger [] pass;

    /**
     * Departure Airport constructor
     * @param repo general repository of information
     */
    public DepartureAirport(GeneralRepository repo){
        nPassengers = 0;
        pass = new Passenger[SimulPar.N];

        try{
            passengersAtQueue = new MemFIFO<>(new Integer[])
        }catch (MemException e){

        }
        this.repo = repo;
    }

    /**
     * Pilot function - says to hostess boarding can start
     */
    public synchronized void informPlaneReadyForBoarding(){
        Pilot pi = (Pilot) Thread.currentThread();
        // pilot is at transfer gate
        assert(pi.getCurrentState() == PilotStates.AT_TRANSFER_GATE);
        // pilot is at transfer gate and ready for boarding
        pi.setCurrentState(PilotStates.READY_FOR_BOARDING);
        System.out.println("Plane ready for boarding.");
        // hostess can start boarding
        setReadyForBoarding(true);
    }

    /**
     * Hostess Function - prepare to start boarding passengers on the plane
     */
    public synchronized void prepareForPassBoarding(){
        Hostess ho = (Hostess) Thread.currentThread();
        // hostess is waiting for a flight
        assert(ho.getCurrentState() == HostessStates.WAIT_FOR_FLIGHT);
        try{
            while(!isReadyForBoarding()) {
                wait();
            }
        }catch (InterruptedException exc){
            ho.setCurrentState(HostessStates.WAIT_FOR_PASSENGER);
            System.out.print("Boarding Starting.");
        }
    }

    /**
     * Passenger function - passenger waits in queue to board the plane
     */
    public synchronized void waitInQueue(){
        int passengerID;
        passengerID =((Passenger) Thread.currentThread()).getID();
        pass[passengerID] = (Passenger) Thread.currentThread();
        // passenger is waiting in queue
        pass[passengerID].setCurrentState(PassengerStates.IN_QUEUE);
        // number of passengers in queue increases
        nPassengers++;
        try{
            passengersAtQueue.write(passengerID);
        }catch (MemException e){
            System.out.println("Insertion of customer id in FIFO failed: " +e.getMessage());
            System.exit(1);
        }
        notifyAll();
    }

    /**
     * Hostess function - if a passenger in queue checks documents, waits for passenger to show documents
     */
    public synchronized int checkDocuments(){
        int passengerID;
        Hostess ho = (Hostess) Thread.currentThread();
        assert(ho.getCurrentState() == HostessStates.WAIT_FOR_PASSENGER);
        ho.setCurrentState(HostessStates.CHECK_PASSENGER);
        try{
            passengerID = passengersAtQueue.read();
            if((passengerID<0) || (passengerID>=SimulPar.N)){
                throw new MemException("Illegal passenger ID");
            }
        }catch (MemException e){
            System.out.println("Retrieval of passsenger ID from wainting FIFO failed." +e.getMessage());
            passengerID = -1;
            System.exit(1);
        }
        return passengerID;
        //pass[passengerID].setCurrentState(PassengerStates.);
    }

    /**
     * Passenger function - passenger shows documents to hostess
     */
    public synchronized void showDocuments(int passengerID) {
        Hostess ho = (Hostess) Thread.currentThread();
        ho.setCurrentState(HostessStates.CHECK_PASSENGER);
        pass[passengerID].setCurrentState(PassengerStates.IN_FLIGHT);
        nPassengers--;
        notifyAll();
    }

    /**
     *  Hostess function - hostess waits for passengers if plane not full and not min and passenger in queue
     */
    public synchronized void waitForNextPassenger(){
        Hostess ho = (Hostess) Thread.currentThread();
        try{
            while((!isPlaneIsFull() || !isPlaneIsMin())){
                System.out.println("Waits for passenger");
                ho.setCurrentState(HostessStates.WAIT_FOR_PASSENGER);
                wait();
            }
        }catch(InterruptedException exc){
            System.out.println("Next passenger arrived");
        }
    }

    /**
     * Hostess Function - if no passenger at queue or plane is full awakes pilot
     */
    public synchronized void informPlaneReadyToTakeOff(){
        try{
            while(!planeIsFull && !planeIsMin && nPassengers>0){
                System.out.println("Passengers boarding");
                wait();
            }
        }catch(InterruptedException exc){
            if(!planeIsFull){
                System.out.println("All passengers on board, ready to departure.");
                readyToFly = true;
            }else{
                System.out.println("Plane is full, ready to departure");
                readyToFly = true;
            }
        }
    }

    /**
     * Hostess function - hostess informs next Flight
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
     * Getters and setters
     */
    public boolean isReadyToFly() {
        return readyToFly;
    }

    public void setReadyToFly(boolean readyToFly) {
        this.readyToFly = readyToFly;
    }

    public boolean isPlaneIsFull() {
        return planeIsFull;
    }

    public void setPlaneIsFull(boolean planeIsFull) {
        this.planeIsFull = planeIsFull;
    }

    public boolean isPlaneIsMin() {
        return planeIsMin;
    }

    public void setPlaneIsMin(boolean planeIsMin) {
        this.planeIsMin = planeIsMin;
    }

    public int getPassengersAtQueue() {
        return passengersAtQueue;
    }

    public void setPassengersAtQueue(int passengersAtQueue) {
        this.passengersAtQueue = passengersAtQueue;
    }

    public int getPassengersAtPlane() {
        return passengersAtPlane;
    }

    public void setPassengersAtPlane(int passengersAtPlane) {
        this.passengersAtPlane = passengersAtPlane;
    }

    public boolean isPlaneAtDeparture() {
        return planeAtDeparture;
    }

    public void setPlaneAtDeparture(boolean planeAtDeparture) {
        this.planeAtDeparture = planeAtDeparture;
    }

    public boolean isReadyForBoarding() {
        return readyForBoarding;
    }

    public void setReadyForBoarding(boolean readyForBoarding) {
        this.readyForBoarding = readyForBoarding;
    }

    public int getnPassengers() {
        return nPassengers;
    }

    public void setnPassengers(int nPassengers) {
        this.nPassengers = nPassengers;
    }
}
