package ClientSide.entities;
/**
 * Hostess life states.
 * @author André Alves
 * @author Mariana Pinto
 */
public class HostessStates {
    /**
     * Hostess waits for fllight to arrive.
     */
    public static final int WAIT_FOR_FLIGHT = 0;
    /**
     * Hostess waits for passenger.
     */
    public static final int WAIT_FOR_PASSENGER = 1;
    /**
     * Hostess checks passenger.
     */
    public static final int CHECK_PASSENGER = 2;
    /**
     * Hostess says plane is ready to fly.
     */
    public static final int READY_TO_FLY = 3;

    /**
     * The class can not be instantiated.
     */
    private HostessStates() {
    }
}