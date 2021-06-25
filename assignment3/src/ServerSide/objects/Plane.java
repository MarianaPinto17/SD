package ServerSide.objects;

import commonInfrastructures.*;
import ClientSide.entities.*;
import ServerSide.main.*;
import interfaces.*;
import java.rmi.*;


/**
 * Shared Region Plane.
 * @author André Alves
 * @author Mariana Pinto
 *
 */
public class Plane implements PlaneInterface {

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
        arrivedAtDest = false;
        // Pilot is inside the plane ready for boarding
        repos.setPilotState(PilotStates.WAIT_FOR_BOARDING);

        return PilotStates.WAIT_FOR_BOARDING;
    }

    /**
     * Pilot function - Pilot flies to destination.
     */
    @Override
    public synchronized int flyToDestinationPoint() throws RemoteException {
        while (!readyToFly){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        readyToFly = false;

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
    public synchronized ReturnType flyToDeparturePoint() throws RemoteException {

        repos.setEmptyPlaneDest(false);

        repos.setPilotState(PilotStates.FLYING_BACK);

        return new ReturnType(PilotStates.FLYING_BACK, false);

    }

    /**
     * Hostess Function - if no passenger at queue or plane is full awakes pilot.
     */
    @Override
    public synchronized int informPlaneReadyToTakeOff() throws RemoteException {
        System.out.println();
        repos.setHostessState(HostessStates.READY_TO_FLY);

        readyToFly = true;

        notifyAll();

        return HostessStates.READY_TO_FLY;
    }

    /**
     * Operation server shutdown.
     * @throws RemoteException
     */
    @Override
    public void shutdown() throws RemoteException {
        PlaneMain.shutdown();
    }
}
