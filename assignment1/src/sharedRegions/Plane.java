package sharedRegions;

import commonInfrastructures.PilotStates;
import entities.Pilot;

/**
 * Shared Region Plane
 */
public class Plane {
    /**
     * Pilot function - waits for all passengers get on board
     */
    public synchronized void waitForAllInBoard(){
        Pilot pi = (Pilot) Thread.currentThread();
        // Pilot is inside the plane ready for boarding
        assert(pi.getCurrentState() == PilotStates.READY_FOR_BOARDING);

        try{
            while(!isReadyToFly()){
                System.out.println("Wait for all passengers on board.");
                wait();
            }
        }catch (InterruptedException exc) {
            pi.setCurrentState(PilotStates.WAIT_FOR_BOARDING);
            System.out.println("All Passengers on board.");
        }
    }

    /**
     * Passenger function - passenger waits for the flight to end
     */
    public synchronized void waitForEndOfFlight(){

    }
}
