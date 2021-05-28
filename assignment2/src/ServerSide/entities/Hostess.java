package ServerSide.entities;

/**
 * Hostess thread and life cycle.
 * @author Mariana Pinto
 * @author André Alves
 */
public interface Hostess {

    /**
     * Get current state.
     * @return the current state of a hostess
     */
    public int getHostessState();

    /**
     * Set current state.
     * @param newState int representing new state of a hostess
     */
    public void setHostessState(int newState);

    /**
     * Get end of life.
     * @return true if Hostess don't have passengers in queue
     */
    public boolean getHEndOfLife();

    /**
     * Set end of life state of a hostess.
     * @param newEndOfLife changes status of endOfLife
     */
    public void setHEndOfLife(boolean newEndOfLife);

}