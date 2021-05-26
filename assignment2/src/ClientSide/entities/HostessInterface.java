package ClientSide.entities;

/**
 * Hostess thread and life cycle.
 * @author Mariana Pinto
 * @author André Alves
 */
public interface HostessInterface {

    /**
     * Get current state.
     * @return the current state of a hostess
     */
    public int getHostessState();

    /**
     * Set current state.
     * @param newState new state of a hostess
     */
    public void setHostessState(HostessStates newState);

    /**
     * Set current state.
     * @param newState int representing new state of a hostess
     */
    public void setHostessState(int newState);

    /**
     * Get end of life.
     * @return true if Hostess don't have passengers in queue
     */
    public boolean getEndOfLife();

    /**
     * Set end of life state of a hostess.
     * @param newEndOfLife changes status of endOfLife
     */
    public void setEndOfLife(boolean newEndOfLife);

    /**
     * Get if the hostess is asleep.
     * @return True if is asleep
     */
    public boolean getAsleep();

    /**
     * Set a hostess to sleep or wakes her up.
     * @param newAsleep new state of asleep
     */
    public void setAsleep(boolean newAsleep);

}