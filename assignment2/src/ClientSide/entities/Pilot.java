package ClientSide.entities;

import ClientSide.main.SimulPar;
import ClientSide.stub.*;

/**
 * Pilot thread and life cycle.
 * @author Mariana Pinto
 * @author André Alves
 */
public class Pilot extends Thread {

    /**
     * current state of the pilot.
     */
    private int currentState;

    /**
     * True if the pilot doesn't have more flights.
     */
    private boolean endOfLife;

    /**
     * Reference to Departure Airport.
     */
    private final DepartureAirportStub depAir;

    /**
     * Reference to Destination Airport.
     */
    private final DestinationAirportStub destAir;

    /**
     * Reference to Plane.
     */
    private final PlaneStub plane;

    /**
     * number of flights.
     */
    private int nflights;


    /**
     * Reference to the General Repository Stub
     */
    private final GeneralRepositoryStub repos;


    /**
     * Pilot Constructor.
     * Initiates a new Pilot that drives a plane
     *
     * @param name name of the pilot
     * @param depAir departure Airport.
     * @param destAir destination Airport
     * @param plane plane that is flying
     */
    public Pilot (String name, DepartureAirportStub depAir, DestinationAirportStub destAir, PlaneStub plane, GeneralRepositoryStub repos){
        super(name);
        this.depAir = depAir;
        this.destAir = destAir;
        this.plane = plane;
        this.endOfLife = false;
        this.currentState = PilotStates.AT_TRANSFER_GATE;
        this.nflights=0;
        this.repos=repos;
    }

    /**
     * Pilot life cycle.
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch(currentState){
                case 0:
                    depAir.informPlaneReadyForBoarding();
                    break;
                case 1:
                    plane.waitForAllInBoard();
                    break;
                case 2:
                    plane.flyToDestinationPoint();
                    fly();
                    break;
                case 3:
                    destAir.announceArrival();
                    break;
                case 4:
                    plane.flyToDeparturePoint();
                    fly();
                    break;
                case 5:
                    depAir.parkAtTransferGate();
                    if(SimulPar.N == repos.getPTAL()) {
                        endOfLife = true;
                        repos.sumUp();
                    }
                    break;
            }
        }
    }

    /**
     * Plane is flying.
     */
    private void fly() {
        try {
            sleep((long) (1 + 150 * Math.random()));
        } catch (InterruptedException e) {}
    }


    /**
     * Get current state
     * @return the current state of a pilot
     */
    public int getPilotState(){
        return currentState;
    }

    /**
     * Set current state
     * @param newState new state of a pilot
     */
    public void setPilotState(int newState){
        this.currentState = newState;
    }

    /**
     * Get end of life
     * @return true if pilot don't have more flights
     */
    public boolean getPiEndOfLife(){
        return endOfLife;
    }

    /**
     * Set end of life state
     * @param newEndOfLife changes status of endOfLife
     */
    public void setPiEndOfLife(boolean newEndOfLife){
        this.endOfLife = newEndOfLife;
    }

    /**
     * Checks number of flights made.
     * @return number of flights
     */
    public int getNflights() {
        return nflights;
    }

    /**
     * Sets numbers of flights made.
     * @param nflights
     */
    public void setNflights(int nflights) {
        this.nflights = nflights;
    }
}
