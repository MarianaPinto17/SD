package ClientSide.entities;

import ClientSide.stub.*;
import ClientSide.entities.*;


/**
 * Hostess thread and life cycle.
 * @author Mariana Pinto
 * @author André Alves
 */
public class Hostess extends Thread{

    /**
     * Reference to Departure Airport.
     */

    private final DepartureAirportStub depAir;

    /**
     * Reference to Plane.
     */

    private final PlaneStub plane;

    /**
     * The current state of the Hostess.
     */
    private HostessStates currentState;

    /**
     * True if hostess don't have more passengers in Queue.
     */
    private boolean endOfLife;

    /**
     * Hosstess Constructor.
     * Initiates a new Hostess that checks passengers aboard.
     * @param name hostess name.
     * @param depAir departure Airport.
     * @param plane plane that is flying
     */
    public Hostess(String name, DepartureAirportStub depAir, PlaneStub plane){
        super(name);
        this.depAir = depAir;
        this.plane = plane;
        this.endOfLife = false;
        this.currentState = HostessStates.WAIT_FOR_FLIGHT;
    }

    /**
     * Hostess life cycle.
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
     * Get current state.
     * @return the current state of a hostess
     */
    public int getHostessState(){
        return currentState.value;
    }

    /**
     * Set current state.
     * @param newState new state of a hostess
     */
    public void setHostessState(HostessStates newState){
        this.currentState = newState;
    }

    /**
     * Set current state.
     * @param newState int representing new state of a hostess
     */
    public void setHostessState(int newState){
        this.currentState.setValue(newState);
    }

    /**
     * Get end of life.
     * @return true if Hostess don't have passengers in queue
     */
    public boolean getHEndOfLife(){
        return endOfLife;
    }

    /**
     * Set end of life state of a hostess.
     * @param newEndOfLife changes status of endOfLife
     */
    public void setHEndOfLife(boolean newEndOfLife){
        this.endOfLife = newEndOfLife;
    }

}