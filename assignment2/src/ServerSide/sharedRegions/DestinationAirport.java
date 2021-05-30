package ServerSide.sharedRegions;

import ServerSide.entities.*;
import ClientSide.stub.*;
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
    private final GeneralRepositoryStub repos;

    /**
     * Destination Airport constructor.
     * @param repos general repository of information
     */
    public DestinationAirport(GeneralRepositoryStub repos){
        pass = new Passenger[SimulPar.N];
        this.repos = repos;
    }

    /**
     * Pilot function - pilot announces that the plane arrived at destination.
     */
    public synchronized void announceArrival(){
        pi = (Pilot) Thread.currentThread();
        pi.setPilotState(PilotStates.DEBOARDING);
        repos.setPilotState(PilotStates.DEBOARDING);
        System.out.println("[ Dest Air - Pilot ] InF: "+repos.getInF());

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

        if(repos.getInF() == 0){
            repos.setEmptyPlaneDest(true);
        }
        pass[passengerID].setPassengerState(PassengerStates.AT_DESTINATION);
        repos.setPassengerState(PassengerStates.AT_DESTINATION, passengerID);

        notifyAll();
    }
}
