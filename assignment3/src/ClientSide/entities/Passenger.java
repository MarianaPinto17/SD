package ClientSide.entities;

import commonInfrastructures.Message;
import interfaces.*;
import ClientSide.entities.*;

import java.rmi.RemoteException;

/**
 * Passenger thread and life cycle.
 * @author André Alves
 * @author Mariana Pinto
 */
public class Passenger extends Thread {
    /**
     * The Passenger's ID.
     */
    private int id;

    /**
     * The current state of a passenger.
     */
    private int currentState;

    /**
     * True if passenger arrived to the destination.
     */
    private boolean endOfLife;

    /**
     * Documents checked.
     * True if documents are checked.
     */
    private boolean docChecked;

    /**
     * Reference to Departure Airport.
     */
    private final DepartureAirportInterface depAir;

    /**
     * Reference to Destination Airport.
     */
    private final DestinationAirportInterface destAir;

    /**
     * Reference to Plane.
     */
    private final PlaneInterface plane;

    /**
     * Passenger Constructor.
     * Initiates a new Passenger that flies on a plane.
     *
     * @param id Passenger's ID
     * @param depAir departure Airport.
     * @param destAir destination Airport
     * @param plane plane that is flying
     */
    public Passenger(int id, DepartureAirportInterface depAir, DestinationAirportInterface destAir, PlaneInterface plane) {
        this.id = id;
        this.currentState = PassengerStates.GOING_TO_AIRPORT;
        this.endOfLife = false;
        this.depAir = depAir;
        this.destAir = destAir;
        this.plane = plane;
        this.docChecked = false;
    }

    /**
     * Passenger life cycle.
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch(currentState){
                case 0:
                    travelToAirport();
                    waitInQueue();
                    break;
                case 1:
                    showDocuments();
                    boardThePlane();
                    break;
                case 2:
                    waitForEndOfFlight();
                    leaveThePlane();
                    break;
                case 3:
                    endOfLife = true;
                    break;
            }
        }
    }


    /**
     *  Travel to Airport.
     *
     *  Internal operation.
     */

    private void travelToAirport()
    {
        try {
            sleep ((long) (1 + 8000 * Math.random ()));
        } catch (InterruptedException e) {}

    }


    // DEPARTURE AIRPORT FUNCTIONS

    /**
     * Passenger waits in queue.
     */
    public void waitInQueue(){
        Message ret = null;

        try {
            ret = depAir.waitInQueue(id);
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on waitInQueue: " + e.getMessage ());
            System.exit(1);
        }

        id = ret.getPassId();
        currentState = ret.getState();
    }

    /**
     * Passenger shows documents to hostess.
     */
    public void showDocuments(){
        try {
            depAir.showDocuments(id);
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on showDocuments: " + e.getMessage ());
            System.exit(1);
        }
    }

    /**
     * Passenger boards the plane.
     */
    public void boardThePlane(){
        Message ret = null;

        try {
            ret = depAir.boardThePlane(id);
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on boardThePlane: " + e.getMessage ());
            System.exit(1);
        }

        id = ret.getPassId();
        currentState = ret.getState();
    }

    // PLANE FUNCTIONS

    /**
     * Passenger waits for the flight to end.
     */
    public void waitForEndOfFlight(){
        try {
            plane.waitForEndOfFlight(id);
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on waitForEndOfFlight: " + e.getMessage ());
            System.exit(1);
        }
    }

    // DESTINATION AIRPORT FUNCTIONS

    /**
     * Passenger leaves the plane when arriving to destination airport.
     */
    public void leaveThePlane(){
        Message ret = null;

        try {
            ret = destAir.leaveThePlane(id);
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on leaveThePlane: " + e.getMessage ());
            System.exit(1);
        }

        id = ret.getPassId();
        currentState = ret.getState();
    }

    /**
     * Get the passenger's ID.
     * @return Passenger's ID
     */
    public int getID(){
        return id;
    }

    /**
     * Set the passenger's ID.
     * @param id passenger's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get current state.
     * @return the current state of a passenger
     */
    public int getPassengerState(){
        return currentState;
    }

    /**
     * Set current state.
     * @param newState new state of a passenger
     */
    public void setPassengerState(int newState){
        this.currentState = newState;
    }

    /**
     * Get end of life.
     * @return true if passenger arrived at the destination airport
     */
    public boolean getPaEndOfLife(){
        return endOfLife;
    }

    /**
     * Set end of life state.
     * @param newEndOfLife changes status of endOfLife
     */
    public void setPaEndOfLife(boolean newEndOfLife){
        this.endOfLife = newEndOfLife;
    }
}
