package ServerSide.entities;

public interface Pilot {
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
    boolean getPiEndOfLife();

    /**
     * Set end of life state
     *
     * @param newEndOfLife changes status of endOfLife
     */
    void setPiEndOfLife(boolean newEndOfLife);

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
