package ServerSide.entities;

public interface Passenger {
    /**
     * Get the passenger's ID.
     *
     * @return Passenger's ID
     */
    int getID();

    /**
     * Set the passenger's ID.
     *
     * @param id passenger's ID.
     */
    void setId(int id);

    /**
     * Get current state.
     *
     * @return the current state of a passenger
     */
    int getPassengerState();

    /**
     * Set current state.
     *
     * @param newState new state of a passenger
     */
    void setPassengerState(int newState);

    /**
     * Get end of life.
     *
     * @return true if passenger arrived at the destination airport
     */
    boolean getPaEndOfLife();

    /**
     * Set end of life state.
     *
     * @param newEndOfLife changes status of endOfLife
     */
    void setPaEndOfLife(boolean newEndOfLife);
}
