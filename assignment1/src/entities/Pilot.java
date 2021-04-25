package entities;

import sharedRegions.DepartureAirport;
import sharedRegions.DestinationAirport;
import sharedRegions.Plane;

/**
 * Pilot thread and life cycle
 * @author Mariana Pinto
 * @author André Alves
 */
public class Pilot extends Thread{
    /**
     * current state of the pilot
     */
    private PilotStates currentState;

    /**
     * True if the pilot doesn't have more flights
     */
    private boolean endOfLife;

    //
    /**
     * True if the pilot is asleep
     */
    private boolean asleep;

    /**
     * Reference to Departure Airport
     */

    private final DepartureAirport depAir;

    /**
     * Reference to Destination Airport
     */

    private final DestinationAirport destAir;

    /**
     * Reference to Plane
     */

    private final Plane plane;

    /**
     * Pilot Constructor.
     * Initiates a new Pilot that drives a plane
     */
    public Pilot (String name, DepartureAirport depAir, DestinationAirport destAir, Plane plane){
        super(name);
        this.depAir = depAir;
        this.destAir = destAir;
        this.plane = plane;
        this.endOfLife = false;
        this.asleep = false;
        this.currentState = PilotStates.AT_TRANSFER_GATE;
    }

    /**
     * Pilot life cycle.
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch(currentState){
                case AT_TRANSFER_GATE:
                    depAir.informPlaneReadyForBoarding();
                    break;
                case READY_FOR_BOARDING:
                    plane.waitForAllInBoard();
                    break;
                case WAIT_FOR_BOARDING:
                    plane.flyToDestinationPoint();
                    break;
                case FLYING_FORWARD:
                    destAir.announceArrival();
                    break;
                case DEBOARDING:
                    plane.flyToDeparturePoint();
                    break;
                case FLYING_BACK:
                    break;
            }
        }
    }

    /**
     * Get current state
     * @return the current state of a pilot
     */
    public PilotStates getCurrentState(){
        return currentState;
    }

    /**
     * Set current state
     * @param newState new state of a pilot
     */
    public void setCurrentState(PilotStates newState){
        this.currentState = newState;
    }

    /**
     * Get end of life
     * @return true if pilot don't have more flights
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
     * Get if the pilot is asleep
     * @return True if is asleep
     */
    public boolean getAsleep() {
        return asleep;
    }

    /**
     * Set a pilot to sleep or wakes her up
     * @param newAsleep new state of asleep
     */
    public void setAsleep(boolean newAsleep){
        this.asleep = newAsleep;
    }
}
