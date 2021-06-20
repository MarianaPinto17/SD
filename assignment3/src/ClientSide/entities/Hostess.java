package ClientSide.entities;

import java.rmi.*;
import commonInfrastructures.*;
import interfaces.*;


/**
 * Hostess thread and life cycle.
 * @author André Alves
 * @author Mariana Pinto
 */
public class Hostess extends Thread{

    /**
     * Reference to Departure Airport.
     */
    private final DepartureAirportInterface depAir;

    /**
     * Reference to Plane.
     */
    private final PlaneInterface plane;

    /**
     * The current state of the Hostess.
     */
    private int currentState;

    /**
     * True if hostess don't have more passengers in Queue.
     */
    private boolean endOfLife;

    /**
     * Hosstess Constructor.
     * Initiates a new Hostess that checks passengers aboard.
     * @param name hostess name.
     * @param depAir departure Airport.
     * @param plane plane that is flying
     */
    public Hostess(String name, DepartureAirportInterface depAir, PlaneInterface plane){
        super(name);
        this.depAir = depAir;
        this.plane = plane;
        this.endOfLife = false;
        this.currentState = HostessStates.WAIT_FOR_FLIGHT;
    }

    /**
     * Hostess life cycle.
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch (currentState){
                case 0:
                    endOfLife = prepareForPassBoarding();
                    if(endOfLife) shutdown();
                    break;
                case 1:
                    if(isInformPlane())
                        informPlaneReadyToTakeOff();
                    else
                        checkDocuments();
                    break;
                case 2:
                    waitForNextPassenger();
                    break;
                case 3:
                    waitForNextFlight();
                    break;
            }
        }
    }

    // DEPARTURE AIRPORT FUNCTIONS

    /**
     * Hostess waiting for boarding to start.
     * @return hostess state
     */
    public boolean prepareForPassBoarding(){
        Message ret = null;                            // return value

        try { 
            ret = depAir.prepareForPassBoarding();
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on prepareForPassBoarding: " + e.getMessage ());
            System.exit (1);
        }
        currentState = ret.getState ();
        return ret.boolState();
    }

    /**
     * Hostess checks documents of passengers.
     */
    public void checkDocuments(){
        int ret = -1;

        try {
            ret = depAir.checkDocuments();
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on checkDocuments: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    /**
     * Hostess waits for next passenger in line.
     */
    public void waitForNextPassenger(){
        int ret = -1;

        try {
            ret = depAir.waitForNextPassenger();
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on waitForNextPassenger: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    /**
     * Hostess waits for the next flight.
     */
    public void waitForNextFlight(){
        int ret = -1;

        try {
            ret = depAir.waitForNextFlight();
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on waitForNextFlight: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    /**
     *
     * @return
     */
    public boolean isInformPlane(){
        boolean ret = false;

        try {
            ret = depAir.isInformPlane();
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on isInformPlane: " + e.getMessage ());
            System.exit(1);
        }
        return ret;
    }

    /**
     * Hostess shutdown.
     */
    public void shutdown(){
        try {
            depAir.waitForNextFlight();
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on shutdown: " + e.getMessage ());
            System.exit(1);
        }
    }

    // PLANE FUNCTIONS

    /**
     * Hostess informs that the plane is ready to take of.
     */
    public void informPlaneReadyToTakeOff(){
        int ret = -1;

        try {
            ret = plane.informPlaneReadyToTakeOff();
        } catch (RemoteException e) {
            System.out.println("Hostess remote exception on informPlaneReadyToTakeOff: " + e.getMessage ());
            System.exit(1);
        }
        currentState = ret;
    }

    /**
     * Get current state.
     * @return the current state of a hostess
     */
    public int getHostessState(){
        return currentState;
    }

    /**
     * Set current state.
     * @param newState new state of a hostess
     */
    public void setHostessState(int newState){
        this.currentState = newState;
    }

}