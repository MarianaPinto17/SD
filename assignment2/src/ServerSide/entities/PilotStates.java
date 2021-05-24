package ServerSide.entities;
/**
 * Pilot life states
 * @author Mariana Pinto
 * @author André Alves
 */
public enum PilotStates {
    AT_TRANSFER_GATE(0),
    READY_FOR_BOARDING(1),
    WAIT_FOR_BOARDING(2),
    FLYING_FORWARD(3),
    DEBOARDING(4),
    FLYING_BACK(5);


    public int value;

    PilotStates(int value) {
        this.value = value;
    }

    public void setValue(int value){
        this.value = value;
    }
}
