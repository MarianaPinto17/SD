package ClientSide.entities;
/**
 * Pilot life states.
 * @author Mariana Pinto
 * @author André Alves
 */
public class PilotStates {
    public static final int AT_TRANSFER_GATE = 0;
    public static final int READY_FOR_BOARDING = 1;
    public static final int WAIT_FOR_BOARDING = 2;
    public static final int FLYING_FORWARD = 3;
    public static final int DEBOARDING = 4;
    public static final int FLYING_BACK = 5;

    private PilotStates() {}
}
