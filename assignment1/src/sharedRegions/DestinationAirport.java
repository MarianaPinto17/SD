package sharedRegions;

import commonInfrastructures.*;
import entities.*;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport {

    /**
     * if the plane arrived at the destination gate
     */
    private boolean arrivedDestination;
    /**
     * Pilot function - pilot announces that the plane arrived at destination
     */
    public synchronized void announceArrival(){
        Pilot pi = (Pilot) Thread.currentThread();
        pi.setCurrentState(PilotStates.DEBOARDING);
        arrivedDestination = true;
    }

    /**
     * Passenger function - passenger arrived at the destination and leaves the plane
     */
    public synchronized void leaveThePlane(){
        int passengerID;

    }
}
