package ClientSide.entities;
/**
 * Passenger life states.
 * @author André Alves
 * @author Mariana Pinto
 */

public class PassengerStates {
    /**
     * Passenger goes to airport.
     */
    public static final int GOING_TO_AIRPORT = 0;
    /**
     * Passenger waits in queue.
     */
    public static final int IN_QUEUE = 1;
    /**
     * Passenger is in flight.
     */
    public static final int IN_FLIGHT = 2;
    /**
     * Passenger arrives at destination airport.
     */
    public static final int AT_DESTINATION = 3;
    /**
     * The class can not be instantiated.
     */
    private PassengerStates() {
    }
}
