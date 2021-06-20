package ClientSide.entities;
/**
 * Pilot life states.
 * @author André Alves
 * @author Mariana Pinto
 */
public class PilotStates {
    /**
     * Pilot is at transfer gate.
     */
    public static final int AT_TRANSFER_GATE = 0;
    /**
     * Pilot says plane is ready for boarding.
     */
    public static final int READY_FOR_BOARDING = 1;
    /**
     * Pilot waits for boarding to finish.
     */
    public static final int WAIT_FOR_BOARDING = 2;
    /**
     * Pilot is flying the plane.
     */
    public static final int FLYING_FORWARD = 3;
    /**
     * Pilot waits for deboarding to finish.
     */
    public static final int DEBOARDING = 4;
    /**
     * Pilot flies the plane back to departure airport.
     */
    public static final int FLYING_BACK = 5;

    /**
     * The class can not be instantiated.
     */
    private PilotStates() {}
}
