package sharedRegions;

import commonInfrastructures.HostessStates;
import commonInfrastructures.PassengerStates;
import commonInfrastructures.PilotStates;
import entities.Hostess;
import entities.Passenger;
import entities.Pilot;

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
    private int passengersAtQueue;

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
     * Departure Airport constructor
     * @param repo general repository of information
     */
    public DepartureAirport(GeneralRepository repo){
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
        Passenger pa = (Passenger) Thread.currentThread();
        //checks if passenger is going to aiport
        assert(pa.getCurrentState() == PassengerStates.GOING_TO_AIRPORT);
        // passenger is waiting in queue
        pa.setCurrentState(PassengerStates.IN_QUEUE);
    }

    /**
     * Passenger function - passenger shows documents to hostess
     */
    public synchronized void showDocuments() {
        Passenger pa = (Passenger) Thread.currentThread();
        // checks if passenger is in queue
        assert(pa.getCurrentState() == PassengerStates.IN_QUEUE);
        if(passengersAtQueue>0){
            //get id of passenger
            pa.getID();

        }
        try{
            while(){
                wait();
            }
        }catch (InterruptedException exc){
            System.out.println("Documents checked.");
        }
    }

    /**
     * Hostess function - if a passenger in queue checks documents, waits for passenger to show documents
     */
    public synchronized void checkDocuments(){
        Hostess ho = (Hostess) Thread.currentThread();
        assert(ho.getCurrentState() == HostessStates.WAIT_FOR_PASSENGER);
        try{
            while(){
                System.out.println("Waiting for Documents.");
                wait();
            }
        }catch (InterruptedException exc){
            ho.setCurrentState(HostessStates.CHECK_PASSENGER);
            System.out.println("Checking documents.");
        }
    }

    /**
     *  Hostess function - hostess waits for passengers if plane not full and not min and passenger in queue
     */
    public synchronized void waitForNextPassenger(){
        try{
            while(passengersAtQueue>0 && ( || !planeIsMin)){
                System.out.println("Waits for passenger");
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
            while(!planeIsFull && !readyToFly && passengersAtQueue>0){
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
