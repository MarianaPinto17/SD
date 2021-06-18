package ServerSide.objects;

import commonInfrastructures.*;
import ClientSide.entities.*;
import ServerSide.main.*;
import interfaces.*;
import java.rmi.*;


/**
 * Shared Region Plane
 *
 */
public class Plane implements PlaneInterface {

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
    private final GeneralRepositoryInterface repos;

    /**
     * Arrived at destination boolean
     */
    private boolean arrivedAtDest;

    /**
     * Plane instantiation.
     * @param repos reference to general repository.
     */
    public Plane(GeneralRepositoryInterface repos) {
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
    @Override
    public synchronized int waitForAllInBoard() throws RemoteException {
        pi = (Pilot) Thread.currentThread();
        arrivedAtDest = false;
        // Pilot is inside the plane ready for boarding
        pi.setPilotState(PilotStates.WAIT_FOR_BOARDING);
        repos.setPilotState(PilotStates.WAIT_FOR_BOARDING);

        return PilotStates.WAIT_FOR_BOARDING;
    }

    /**
     * Pilot function - Pilot flies to destination.
     */
    @Override
    public synchronized int flyToDestinationPoint() throws RemoteException {
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

        return PilotStates.FLYING_FORWARD;

    }


    /**
     * Passenger function - passenger waits for the flight to end.
     */
    @Override
    public synchronized void waitForEndOfFlight(int passId) throws RemoteException {

        while(!arrivedAtDest){
            try{
                wait();

            } catch (InterruptedException e){}
        }
    }



    /**
     * Pilot function - Pilot flies back to departure.
     */
    @Override
    public synchronized Message flyToDeparturePoint() throws RemoteException {
        pi = (Pilot) Thread.currentThread();

        repos.setEmptyPlaneDest(false);

        pi.setPilotState(PilotStates.FLYING_BACK);
        repos.setPilotState(PilotStates.FLYING_BACK);

        return new Message(PilotStates.FLYING_BACK, false);

    }

    /**
     * Hostess Function - if no passenger at queue or plane is full awakes pilot.
     */
    @Override
    public synchronized int informPlaneReadyToTakeOff() throws RemoteException {
        ho = (Hostess) Thread.currentThread();

        ho.setHostessState(HostessStates.READY_TO_FLY);
        System.out.println();
        repos.setHostessState(HostessStates.READY_TO_FLY);

        readyToFly = true;

        notifyAll();

        return HostessStates.READY_TO_FLY;
    }

    @Override
    public void shutdown() throws RemoteException {
        PlaneMain.shutdown();
        notifyAll();
    }
}
