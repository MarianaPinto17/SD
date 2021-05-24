package ClientSide.entities;
/**
 * Passenger life states
 * @author Mariana Pinto
 * @author André Alves
 */

public enum PassengerStates {
    GOING_TO_AIRPORT(0),
    IN_QUEUE(1),
    IN_FLIGHT(2),
    AT_DESTINATION(3);

    public int value;

    PassengerStates(int value) {
        this.value = value;
    }

    public void setValue(int value){
        this.value = value;
    }
}
