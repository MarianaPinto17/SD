package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.*;


/**
 * Shared Region Plane
 */
public class Plane {

    /**
     * checks if a plane is at the destination airport.
     */

    private boolean arrivedAtDest;

    /**
     * Reference to Passenger threads.
     */

    private final Passenger[] pass;

    /**
     * Reference to pilot.
     */

    private Pilot pi;


    /**
     * Reference to hostess.
     */

    private Hostess ho;


    /**
     *  Waiting passengers in departure airport.
     */

    private MemFIFO<Integer> waitingPassenger;


    /**
     * Reference to general repository.
     */

    private final GeneralRepository repos;

    /**
     * Plane instantiation.
     * @param repos reference to general repository.
     */

    public Plane(GeneralRepository repos) {
        pass = new Passenger[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++)
                pass[i] = null;

        try {
            waitingPassenger = new MemFIFO<> (new Integer [SimulPar.N]);
        } catch (MemException e) {
            System.out.println ("Instantiation of waiting FIFO failed: " + e.getMessage ());
            waitingPassenger = null;
            System.exit (1);
        }

        arrivedAtDest = false;
        this.repos = repos;

    }

    /**
     * Pilot function - waits for all passengers get on board.
     */
    public synchronized void waitForAllInBoard(){
        pi = (Pilot) Thread.currentThread();

        // Pilot is inside the plane ready for boarding
        pi.setCurrentState(PilotStates.WAIT_FOR_BOARDING);
        repos.setPilotState(PilotStates.WAIT_FOR_BOARDING);
        try {
            pi.sleep((long) (1 + 100 * Math.random ()));
        } catch (InterruptedException e) {}
    }

    /**
     * Passenger function - passenger waits for the flight to end.
     */
    public synchronized void waitForEndOfFlight(){
        while(!arrivedAtDest){
            try{
                wait();
            } catch (InterruptedException e){}
        }
    }


    /**
     * Pilot function - Pilot flies to destination.
     */

    public synchronized void flyToDestinationPoint(){
        while (!repos.isReadyToFly()){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        pi.setCurrentState(PilotStates.FLYING_FORWARD);
        repos.setPilotState(PilotStates.FLYING_FORWARD);

        try {
            pi.sleep ((long) (1 + 100 * Math.random ()));
        } catch (InterruptedException e) {}

    }

    /**
     * Pilot function - Pilot flies back to departure.
     */
    public synchronized void flyToDeparturePoint(){


    }
    /**
     * Setters and Getters.
     */

    /**
     * Checks if a plane arrived at the destination.
     * @return true if the plane it's at the destination gate.
     */
    public boolean isArrivedAtDest() {
        return arrivedAtDest;
    }

    /**
     * Sets the current state of the plane (arrived or not).
     * @param arrivedAtDest new state of arrivedAtDest
     */
    public void setArrivedAtDest(boolean arrivedAtDest) {
        this.arrivedAtDest = arrivedAtDest;
    }
}
