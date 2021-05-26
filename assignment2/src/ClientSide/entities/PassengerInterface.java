package ClientSide.entities;

public interface PassengerInterface {
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
    void setPassengerState(PassengerStates newState);

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
    boolean getEndOfLife();

    /**
     * Set end of life state.
     *
     * @param newEndOfLife changes status of endOfLife
     */
    void setEndOfLife(boolean newEndOfLife);

    /**
     * Get if the passenger is asleep.
     *
     * @return True if is asleep
     */
    boolean getAsleep();

    /**
     * Set a passenger to sleep or wakes her up.
     *
     * @param newAsleep new state of asleep
     */
    void setAsleep(boolean newAsleep);
}
