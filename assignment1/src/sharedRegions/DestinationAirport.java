package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.SimulPar;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport {


    private final Passenger[] pass;
    private Pilot pi;
    private final GeneralRepository repo;
    private boolean arrivedDestination;

    public DestinationAirport(GeneralRepository repo){
        pass = new Passenger[SimulPar.N];

        this.repo = repo;
    }




    /**
     * Pilot function - pilot announces that the plane arrived at destination
     */

    public synchronized void announceArrival(){
        Pilot pi = (Pilot) Thread.currentThread();
        pi.setCurrentState(PilotStates.DEBOARDING);
        arrivedDestination = true;

        while (repo.getInF() > 0){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

    public synchronized void leaveThePlane(){
        int passengerID = ((Passenger) Thread.currentThread()).getID();

        pass[passengerID] = (Passenger) Thread.currentThread();




    }
}
