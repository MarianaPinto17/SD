package ClientSide.entities;

import ClientSide.stub.*;
import ClientSide.entities.*;


/**
 * Hostess thread and life cycle
 * @author Mariana Pinto
 * @author André Alves
 */
public class Hostess extends Thread{

    /**
     * Reference to Departure Airport
     */

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
     * The current state of the Hostess
     */
    private HostessStates currentState;

    /**
     * True if hostess don't have more passengers in Queue
     */
    private boolean endOfLife;

    /**
     * True if the hostess is asleep
     */
    private boolean asleep;

    /**
     * Hosstess Constructor.
     * Initiates a new Hostess that checks passengers aboard
     * @param name hostess name.
     * @param depAir departure Airport.
     * @param destAir destination Airport
     * @param plane plane that is flying
     */
    public Hostess(String name, DepartureAirportStub depAir, DestinationAirportStub destAir, PlaneStub plane){
        super(name);
        this.depAir = depAir;
        this.destAir = destAir;
        this.plane = plane;
        this.endOfLife = false;
        this.asleep = false;
        this.currentState = HostessStates.WAIT_FOR_FLIGHT;
    }

    /**
     * Hostess life cycle
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch (currentState){
                case WAIT_FOR_FLIGHT:
                    depAir.prepareForPassBoarding();
                    break;
                case WAIT_FOR_PASSENGER:
                    if(depAir.isInformPlane())
                        plane.informPlaneReadyToTakeOff();
                    else
                        depAir.checkDocuments();
                    break;
                case CHECK_PASSENGER:
                    depAir.waitForNextPassenger();
                    break;
                case READY_TO_FLY:
                    depAir.waitForNextFlight();
                    break;
            }
        }
    }

    /**
     * Get current state
     * @return the current state of a hostess
     */
    public int getCurrentState(){
        return currentState.value;
    }

    /**
     * Set current state
     * @param newState new state of a hostess
     */
    public void setCurrentState(HostessStates newState){
        this.currentState = newState;
    }

    /**
     * Set current state
     * @param newState int representing new state of a hostess
     */
    public void setCurrentState(int newState){
        this.currentState.setValue(newState);
    }

    /**
     * Get end of life
     * @return true if Hostess don't have passengers in queue
     */
    public boolean getEndOfLife(){
        return endOfLife;
    }

    /**
     * Set end of life state of a hostess
     * @param newEndOfLife changes status of endOfLife
     */
    public void setEndOfLife(boolean newEndOfLife){
        this.endOfLife = newEndOfLife;
    }

    /**
     * Get if the hostess is asleep
     * @return True if is asleep
     */
    public boolean getAsleep() {
        return asleep;
    }

    /**
     * Set a hostess to sleep or wakes her up
     * @param newAsleep new state of asleep
     */
    public void setAsleep(boolean newAsleep){
        this.asleep = newAsleep;
    }

}