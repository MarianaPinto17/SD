package entities;

import commonInfrastructures.HostessStates;
/**
 * Hostess thread and life cycle
 * @author Mariana Pinto
 * @author André Alves
 */
public class Hostess extends Thread{
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
     */
    public Hostess(){
        this.endOfLife = false;
        this.asleep = false;
        this.currentState = HostessStates.WAIT_FOR_NEXT_FLIGHT;
    }

    /**
     * Hostess life cycle
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch (currentState){
                case WAIT_FOR_NEXT_FLIGHT:
                    break;
                case WAIT_FOR_PASSENGER:
                    break;
                case CHECK_PASSENGER:
                    break;
                case READY_TO_FLY:
                    break;
            }
        }
    }

    /**
     * Get current state
     * @return the current state of a hostess
     */
    public HostessStates getCurrentState(){
        return currentState;
    }

    /**
     * Set current state
     * @param newState new state of a hostess
     */
    public void setCurrentState(HostessStates newState){
        this.currentState = newState;
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
