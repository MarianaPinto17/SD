package ServerSide.sharedRegions;

import ClientSide.stub.GeneralRepositoryStub;
import ServerSide.main.PlaneMain;
import commonInfrastructures.*;
import ServerSide.entities.*;
import ServerSide.main.SimulPar;


/**
 * Shared Region Plane
 *
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
     * is plane ready to fly
     */
    private boolean readyToFly;

    /**
     *  Waiting passengers in departure airport.
     */
    private MemFIFO<Integer> waitingPassenger;

    /**
     * Reference to general repository.
     */
    private final GeneralRepositoryStub repos;

    /**
     * Arrived at destination boolean
     */
    private boolean arrivedAtDest;

    /**
     * Plane instantiation.
     * @param repos reference to general repository.
     */
    public Plane(GeneralRepositoryStub repos) {
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
        arrivedAtDest = false;
        // Pilot is inside the plane ready for boarding
        pi.setPilotState(PilotStates.WAIT_FOR_BOARDING);
        repos.setPilotState(PilotStates.WAIT_FOR_BOARDING);
    }

    /**
     * Pilot function - Pilot flies to destination.
     */
    public synchronized void flyToDestinationPoint(){
        pi = (Pilot) Thread.currentThread();
        while (!readyToFly){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        readyToFly = false;

        pi.setPilotState(PilotStates.FLYING_FORWARD);
        repos.setPilotState(PilotStates.FLYING_FORWARD);

        arrivedAtDest = true;
        notifyAll();

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
     * Pilot function - Pilot flies back to departure.
     */
    public synchronized void flyToDeparturePoint(){
        pi = (Pilot) Thread.currentThread();

        repos.setEmptyPlaneDest(false);

        pi.setPilotState(PilotStates.FLYING_BACK);
        repos.setPilotState(PilotStates.FLYING_BACK);

    }

    /**
     * Hostess Function - if no passenger at queue or plane is full awakes pilot.
     */
    public synchronized void informPlaneReadyToTakeOff(){
        ho = (Hostess) Thread.currentThread();

        ho.setHostessState(HostessStates.READY_TO_FLY);
        System.out.println();
        repos.setHostessState(HostessStates.READY_TO_FLY);

        readyToFly = true;

        notifyAll();
    }

    public void shutdown() {
        PlaneMain.waitConnection = false;
    }
}
