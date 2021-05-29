package ServerSide.entities;
/**
 * Hostess life states
 * @author Mariana Pinto
 * @author André Alves
 */
public class HostessStates {
    public static final int WAIT_FOR_FLIGHT = 0;
    public static final int WAIT_FOR_PASSENGER = 1;
    public static final int CHECK_PASSENGER = 2;
    public static final int READY_TO_FLY = 3;

    /**
     * The class can not be instantiated.
     */
    private HostessStates() {
    }
}