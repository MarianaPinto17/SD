package sharedRegions;

<<<<<<< HEAD
import commonInfrastructures.PilotStates;
import entities.Pilot;
=======
import commonInfrastructures.*;
import entities.*;
import main.*;
>>>>>>> 59cc258da1d0349efb9c5d4568887ff510bbcba4

/**
 * Shared Region Plane
 */
public class Plane {

    /**
     *  Number of passengers waiting to enter plane.
     */

    private int nPassengersWait;

    /**
     * Reference to Passenger threads.
     */

    private final Passenger[] pass;


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
        nPassengersWait = 0;

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
     * Pilot function - waits for all passengers get on board
     */
    public synchronized void waitForAllInBoard(){
        Pilot pi = (Pilot) Thread.currentThread();
        // Pilot is inside the plane ready for boarding
        assert(pi.getCurrentState() == PilotStates.READY_FOR_BOARDING);

        try{
            while(!isReadyToFly()){
                System.out.println("Wait for all passengers on board.");
                wait();
            }
        }catch (InterruptedException exc) {
            pi.setCurrentState(PilotStates.WAIT_FOR_BOARDING);
            System.out.println("All Passengers on board.");
        }
    }

    /**
     * Passenger function - passenger waits for the flight to end
     */
    public synchronized void waitForEndOfFlight(){
<<<<<<< HEAD
=======

    }


    /**
     * Pilot function - Pilot flies to destination.
     */

    public synchronized void flyToDestinationPoint(){

    }

    /**
     * Pilot function - Pilot flies back to departure.
     */

    public synchronized void flyToDeparturePoint(){
>>>>>>> 59cc258da1d0349efb9c5d4568887ff510bbcba4

    }
}
