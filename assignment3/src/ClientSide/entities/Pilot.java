package ClientSide.entities;

import ServerSide.main.SimulPar;
import interfaces.*;

import java.rmi.RemoteException;

/**
 * Pilot thread and life cycle.
 * @author André Alves
 * @author Mariana Pinto
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
    private final DepartureAirportInterface depAir;

    /**
     * Reference to Destination Airport.
     */
    private final DestinationAirportInterface destAir;

    /**
     * Reference to Plane.
     */
    private final PlaneInterface plane;

    /**
     * number of flights.
     */
    private int nflights;


    /**
     * Reference to the General Repository Stub
     */
    private final GeneralRepositoryInterface repos;


    /**
     * Pilot Constructor.
     * Initiates a new Pilot that drives a plane
     *
     * @param name name of the pilot
     * @param depAir departure Airport.
     * @param destAir destination Airport
     * @param plane plane that is flying
     */
    public Pilot (String name, DepartureAirportInterface depAir, DestinationAirportInterface destAir, PlaneInterface plane, GeneralRepositoryInterface repos){
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
                    informPlaneReadyForBoarding();
                    break;
                case 1:
                    waitForAllInBoard();
                    break;
                case 2:
                    flyToDestinationPoint();
                    fly();
                    break;
                case 3:
                    announceArrival();
                    break;
                case 4:
                    flyToDeparturePoint();
                    fly();
                    break;
                case 5:
                    parkAtTransferGate();
                    if(SimulPar.N == getPTAL()) {
                        endOfLife = true;
                        sumUp();
                    }
                    break;
            }
        }
    }

    /**
     * Plane is flying.
     *
     * Internal operation.
     */
    private void fly() {
        try {
            sleep((long) (1 + 150 * Math.random()));
        } catch (InterruptedException e) {}
    }

    // DEPARTURE AIRPORT FUNCTIONS

    /**
     * Pilot informs hostess that the plane is ready to take off.
     */
    public void informPlaneReadyForBoarding(){
        int ret = -1;

        try {
            ret = depAir.informPlaneReadyForBoarding();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on informPlaneReadyForBoarding: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    /**
     * Pilot parks the plane at the transfer gate.
     */
    public void parkAtTransferGate(){
        ReturnType ret = null;

        try {
            ret = depAir.parkAtTransferGate();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on parkAtTransferGate: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret.getState();
        endOfLife = ret.boolState();
    }

    // PLANE

    /**
     * Pilot waits for boarding to end.
     */
    public void waitForAllInBoard(){
        int ret = -1;

        try {
            ret = plane.waitForAllInBoard();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on waitForAllInBoard: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    /**
     * Pilot flies the plane to the destination airport.
     */
    public void flyToDestinationPoint(){
        int ret = -1;

        try {
            ret = plane.flyToDestinationPoint();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on flyToDestinationPoint: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    /**
     * Pilot flies the plane to Departure airport.
     */
    public void flyToDeparturePoint(){
        ReturnType ret = null;

        try {
            ret = plane.flyToDeparturePoint();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on flyToDeparturePoint: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret.getState();
    }

    // DESTINATION AIRPORT

    /**
     * Pilot announces arrival at destination airport.
     */
    public void announceArrival(){
        int ret = -1;

        try {
            ret = destAir.announceArrival();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on announceArrival: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    // GENERAL REPOSITORY FUNCTIONS

    /**
     * Gets the number of passengers arrived at the destination airport.
     * @return number of passengers arrived at the destination airport.
     */
    public int getPTAL(){
        int ret = -1;

        try {
            ret = repos.getPTAL();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on getPTAL: " + e.getMessage ());
            System.exit(1);
        }
        return ret;
    }

    /**
     *  Close of operations.
     */
    public void sumUp(){
        try {
            repos.sumUp();
        } catch (RemoteException e) {
            System.out.println("Pilot remote exception on sumUp: " + e.getMessage ());
            System.exit(1);
        }
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
