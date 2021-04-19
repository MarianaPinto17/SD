package sharedRegions;

/**
 * Shared Region Departure Airport
 */
public class DepartureAirport {
    private boolean readyToFly;

    /**
     * Pilot function - waits for all passengers get on board
     */
    public synchronized void waitForAllInBoard(){
        while(!readyToFly){

        }
    }
    /**
     * Hostess Function - if no passenger at queue or plane is full awakes pilot
     */
    public synchronized void informPlaneReadyToTakeOff(){

    }
    /**
     * Hostess function - hostess informs next Flight
     */
    public synchronized void informNextFlight(){

    }

    /**
     *  Hostess function - hostess waits for passengers if plane not full and not min and passenger in queue
     */
    public synchronized void waitForNextPassenger(){

    }

    /**
     * Hostess function - if a passenger in queue checks documents
     */
    public synchronized void checkDocuments(){

    }

    /**
     * Passenger function - passenger waits in queue to board the plane
     */
    public synchronized void waitInQueue(){

    }

    /**
     * Passenger function - passenger shows documents to hostess
     */
    public synchronized void showDocuments() {

    }
}
