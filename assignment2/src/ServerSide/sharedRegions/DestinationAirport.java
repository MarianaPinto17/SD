package ServerSide.sharedRegions;

import ClientSide.entities.*;
import ServerSide.main.SimulPar;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport {

    /**
     * Reference to the passenger threads.
     */
    private final Passenger[] pass;

    /**
     * Reference to the Pilot.
     */
    private Pilot pi;

    /**
     * Reference to the General Repository.
     */
    private final GeneralRepository repos;

    /**
     * Destination Airport constructor.
     * @param repos general repository of information
     */
    public DestinationAirport(GeneralRepository repos){
        pass = new Passenger[SimulPar.N];
        this.repos = repos;
    }

    /**
     * Pilot function - pilot announces that the plane arrived at destination.
     */
    public synchronized void announceArrival(){
        pi = (Pilot) Thread.currentThread();
        pi.setCurrentState(PilotStates.DEBOARDING.value);
        repos.setPilotState(PilotStates.DEBOARDING.value);
        pi.setNpassengers(repos.getnFlights(),repos.getInF());

        notifyAll();
        while (repos.getInF() > 0){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

    /**
     * Passenger function - when the plane arrives at destination the passenger exits the plane.
     */
    public synchronized void leaveThePlane(){
        int passengerID = ((Passenger) Thread.currentThread()).getID();

        pass[passengerID] = (Passenger) Thread.currentThread();
        repos.setInF(repos.getInF() - 1);
        repos.setPTAL(repos.getPTAL() + 1);

        if(repos.getInF() == 0){
            repos.setArrivedAtDest(false);
            repos.setEmptyPlaneDest(true);
        }
        pass[passengerID].setCurrentState(PassengerStates.AT_DESTINATION);
        repos.setPassengerState(passengerID, PassengerStates.AT_DESTINATION.value);

        notifyAll();
    }
}
