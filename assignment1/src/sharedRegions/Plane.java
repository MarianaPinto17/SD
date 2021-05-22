package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.*;


/**
 * Shared Region Plane
 */
public class Plane {

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

        repos.setReadyToFly(false);

        pi.setCurrentState(PilotStates.FLYING_FORWARD);
        repos.setPilotState(PilotStates.FLYING_FORWARD);

        try {
            pi.sleep ((long) (1 + 150 * Math.random ()));
        } catch (InterruptedException e) {}

        repos.setArrivedAtDest(true);
        notifyAll();

    }


    /**
     * Passenger function - passenger waits for the flight to end.
     */
    public synchronized void waitForEndOfFlight(){

        while(!repos.isArrivedAtDest()){
            try{
                wait();

            } catch (InterruptedException e){}
        }
    }



    /**
     * Pilot function - Pilot flies back to departure.
     */
    public synchronized void flyToDeparturePoint(){

        repos.setEmptyPlaneDest(false);

        pi.setCurrentState(PilotStates.FLYING_BACK);
        repos.setPilotState(PilotStates.FLYING_BACK);

        try {
            pi.sleep ((long) (1 + 150 * Math.random ()));
        } catch (InterruptedException e) {}

    }

    /**
     * Hostess Function - if no passenger at queue or plane is full awakes pilot.
     */
    public synchronized void informPlaneReadyToTakeOff(){
        ho = (Hostess) Thread.currentThread();

        ho.setCurrentState(HostessStates.READY_TO_FLY);
        System.out.println();
        repos.setHostessState(HostessStates.READY_TO_FLY);

        repos.setReadyToFly(true);

        notifyAll();
    }
}
