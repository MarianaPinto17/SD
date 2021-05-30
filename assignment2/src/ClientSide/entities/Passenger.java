package ClientSide.entities;

import ClientSide.stub.*;
import ClientSide.entities.*;

/**
 * Passenger thread and life cycle.
 * @author Mariana Pinto
 * @author André Alves
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
    private final DepartureAirportStub depAir;

    /**
     * Reference to Destination Airport.
     */
    private final DestinationAirportStub destAir;

    /**
     * Reference to Plane.
     */
    private final PlaneStub plane;

    /**
     * Passenger Constructor.
     * Initiates a new Passenger that flies on a plane.
     *
     * @param id Passenger's ID
     * @param depAir departure Airport.
     * @param destAir destination Airport
     * @param plane plane that is flying
     */
    public Passenger(int id, DepartureAirportStub depAir, DestinationAirportStub destAir, PlaneStub plane) {
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
                    depAir.waitInQueue();
                    break;
                case 1:
                    depAir.showDocuments();
                    depAir.boardThePlane();
                    break;
                case 2:
                    plane.waitForEndOfFlight();
                    destAir.leaveThePlane();
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
            sleep ((long) (1 + 10000 * Math.random ()));
        } catch (InterruptedException e) {}

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
