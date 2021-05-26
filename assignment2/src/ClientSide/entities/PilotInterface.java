package ClientSide.entities;

public interface PilotInterface {
    /**
     * Get the number of passengers that already arrived at destination.
     *
     * @return number of passengers that already arrived at destination
     */
    int getPTAL();

    /**
     * Get current state
     *
     * @return the current state of a pilot
     */
    int getPilotState();

    /**
     * Set current state
     *
     * @param newState new state of a pilot
     */
    void setPilotState(int newState);

    /**
     * Get end of life
     *
     * @return true if pilot don't have more flights
     */
    boolean getEndOfLife();

    /**
     * Set end of life state
     *
     * @param newEndOfLife changes status of endOfLife
     */
    void setEndOfLife(boolean newEndOfLife);

    /**
     * Get if the pilot is asleep
     *
     * @return True if is asleep
     */
    boolean getAsleep();

    /**
     * Set a pilot to sleep or wakes her up
     *
     * @param newAsleep new state of asleep
     */
    void setAsleep(boolean newAsleep);

    /**
     * Checks the number of passengers in each flight.
     *
     * @return number of passengers in each flight.
     */
    int[] getNpassengers();

    /**
     * sets the number of passengers in each flight.
     *
     * @param index       index of the flight
     * @param npassengers number the passenger of the flight
     */
    void setNpassengers(int index, int npassengers);

    /**
     * Checks number of flights made.
     *
     * @return number of flights
     */
    int getNflights();

    /**
     * Sets numbers of flights made.
     *
     * @param nflights
     */
    void setNflights(int nflights);
}
