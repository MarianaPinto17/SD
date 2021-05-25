package ClientSide.entities;

import ClientSide.stub.*;

/**
 * Passenger thread and life cycle
 * @author Mariana Pinto
 * @author André Alves
 */
public class Passenger extends Thread{
    /**
     * The Passenger's ID
     */
    private int id;

    /**
     * The current state of a passenger
     */
    private PassengerStates currentState;

    /**
     * True if passenger arrived to the destination
     */
    private boolean endOfLife;

    /**
     * checks if passenger is asleep
     */
    private boolean asleep;

    /**
     * Reference to Departure Airport
     */

    /**
     * Documents checked
     * True if documents are checked
     */
    private boolean docChecked;

    private final DepartureAirportStub depAir;

    /**
     * Reference to Destination Airport
     */

    private final DestinationAirportStub destAir;

    /**
     * Reference to Plane
     */

    private final PlaneStub plane;

    /**
     * Passenger Constructor.
     * Initiates a new Passenger that flies on a plane
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
        this.asleep = false;
        this.depAir = depAir;
        this.destAir = destAir;
        this.plane = plane;
        this.docChecked = false;
    }

    /**
     * Passenger life cycle
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch(currentState){
                case GOING_TO_AIRPORT:
                    travelToAirport();
                    depAir.waitInQueue();
                    break;
                case IN_QUEUE:
                    depAir.showDocuments();
                    depAir.boardThePlane();
                    break;
                case IN_FLIGHT:
                    plane.waitForEndOfFlight();
                    destAir.leaveThePlane();
                    break;
                case AT_DESTINATION:
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
            sleep ((long) (1 + 200 * Math.random ()));
        } catch (InterruptedException e) {}

    }

    /**
     * Get the passenger's ID
     * @return Passenger's ID
     */
    public int getID(){
        return id;
    }

    /**
     * Get current state
     * @return the current state of a passenger
     */
    public PassengerStates getCurrentState(){
        return currentState;
    }

    /**
     * Set current state
     * @param newState new state of a passenger
     */
    public void setCurrentState(PassengerStates newState){
        this.currentState = newState;
    }

    /**
     * Set current state
     * @param newState int representing the new state of a passenger
     */
    public void setCurrentState(int newState){
        this.currentState.setValue(newState);
    }

    /**
     * Get end of life
     * @return true if passenger arrived at the destination airport
     */
    public boolean getEndOfLife(){
        return endOfLife;
    }

    /**
     * Set end of life state
     * @param newEndOfLife changes status of endOfLife
     */
    public void setEndOfLife(boolean newEndOfLife){
        this.endOfLife = newEndOfLife;
    }

    /**
     * Get if the passenger is asleep
     * @return True if is asleep
     */
    public boolean getAsleep() {
        return asleep;
    }

    /**
     * Set a passenger to sleep or wakes her up
     * @param newAsleep new state of asleep
     */
    public void setAsleep(boolean newAsleep){
        this.asleep = newAsleep;
    }
}
