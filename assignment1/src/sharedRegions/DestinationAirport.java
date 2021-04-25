package sharedRegions;

import commonInfrastructures.PilotStates;
import entities.Pilot;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport {

    private boolean arrivedDestination;
    /**
     * Pilot function - pilot announces that the plane arrived at destination
     */
    public synchronized void announceArrival(){
        Pilot pi = (Pilot) Thread.currentThread();
        pi.setCurrentState(PilotStates.DEBOARDING);
        arrivedDestination = true;
    }

    public synchronized void leaveThePlane(){
        int passengerID;


    }
}
