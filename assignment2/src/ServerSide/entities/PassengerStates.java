package ServerSide.entities;
/**
 * Passenger life states.
 * @author Mariana Pinto
 * @author André Alves
 */

public class PassengerStates {
    public static final int GOING_TO_AIRPORT = 0;
    public static final int IN_QUEUE = 1;
    public static final int IN_FLIGHT = 2;
    public static final int AT_DESTINATION = 3;


    /**
     * The class can not be instantiated.
     */
    private PassengerStates() {
    }
}
