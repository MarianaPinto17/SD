package entities;

import commonInfrastructures.HostessStates;
import commonInfrastructures.PassengerStates;

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
     * Passenger Constructor.
     * Initiates a new Passenger that flies on a plane
     */
    public Passenger(int id) {
        this.id = id;
        this.currentState = PassengerStates.GOING_TO_AIRPORT;
        this.endOfLife = false;
        this.asleep = false;
    }

    /**
     * Passenger life cycle
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch(currentState){
                case GOING_TO_AIRPORT:
                    break;
                case IN_QUEUE:
                    break;
                case IN_FLIGHT:
                    break;
                case AT_DESTINATION:
                    break;
            }
        }
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
