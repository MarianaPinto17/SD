package ServerSide.entities;
/**
 * Hostess life states
 * @author Mariana Pinto
 * @author André Alves
 */
public enum HostessStates {
    WAIT_FOR_FLIGHT(0),
    WAIT_FOR_PASSENGER(1),
    CHECK_PASSENGER(2),
    READY_TO_FLY(3);

    public int value;

    HostessStates(int value) {
        this.value = value;
    }

    public void setValue(int value){
        this.value = value;
    }
}
