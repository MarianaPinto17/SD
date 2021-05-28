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
    private PilotStates currentState;

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
     * number of passengers in each flight.
     */
    private int npassengers[];

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
        this.npassengers=new int[5];
        this.repos=repos;
    }

    /**
     * Pilot life cycle.
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch(currentState){
                case AT_TRANSFER_GATE:
                    depAir.informPlaneReadyForBoarding();
                    break;
                case READY_FOR_BOARDING:
                    plane.waitForAllInBoard();
                    break;
                case WAIT_FOR_BOARDING:
                    plane.flyToDestinationPoint();
                    fly();
                    break;
                case FLYING_FORWARD:
                    destAir.announceArrival();
                    break;
                case DEBOARDING:
                    plane.flyToDeparturePoint();
                    fly();
                    break;
                case FLYING_BACK:
                    depAir.parkAtTransferGate();
                    if(SimulPar.N == getPTAL()) {
                        repos.sumUp(npassengers);
                    }
                    break;
            }
        }
    }

    /**
     *
     */
    private void fly() {
        try {
            sleep((long) (1 + 150 * Math.random()));
        } catch (InterruptedException e) {}
    }
    /**
     * Get the number of passengers that already arrived at destination.
     * @return number of passengers that already arrived at destination
     */
    public int getPTAL(){
        int total=0;
        for (int i: this.npassengers) {
            total+= i;
        }
        return total;
    }

    /**
     * Get current state
     * @return the current state of a pilot
     */
    public int getPilotState(){
        return currentState.value;
    }

    /**
     * Set current state
     * @param newState new state of a pilot
     */
    public void setPilotState(int newState){
        this.currentState.setValue(newState);
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
     * Checks the number of passengers in each flight.
     * @return number of passengers in each flight.
     */
    public int[] getNpassengers() {
        return npassengers;
    }

    /**
     * sets the number of passengers in each flight.
     * @param index index of the flight
     * @param npassengers number the passenger of the flight
     */
    public void setNpassengers(int index, int npassengers) {
        this.npassengers[index] = npassengers;
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
