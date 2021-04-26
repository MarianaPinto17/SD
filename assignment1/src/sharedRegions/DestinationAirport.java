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
        repo.setArrivedAtDest(true);

        notifyAll();

        while (repo.getInF() > 0){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

    public synchronized void leaveThePlane(){
        int passengerID = ((Passenger) Thread.currentThread()).getID();

        pass[passengerID] = (Passenger) Thread.currentThread();

        repo.setInF(repo.getInF() - 1);
        repo.setPTAL(repo.getPTAL() + 1);

        if(repo.getInF() == 0){
            repo.setEmptyPlaneDest(true);
        }


        pass[passengerID].setCurrentState(PassengerStates.AT_DESTINATION);
        repo.setPassengerState(passengerID, PassengerStates.AT_DESTINATION);

        notifyAll();
    }
}
