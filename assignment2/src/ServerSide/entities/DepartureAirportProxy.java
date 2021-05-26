package ServerSide.entities;

import ServerSide.sharedRegions.*;
import ClientSide.entities.*;
import commonInfrastructures.*;

/**
 *  Service provider agent for access to the Barber Shop.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DepartureAirportProxy extends Thread implements PilotInterface, HostessInterface, PassengerInterface {
    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    /**
     *  Communication channel.
     */

    private ServerCom sconi;

    /**
     *  Interface to the Departure Airport.
     */

    private DepartureAirportInterface depAirInt;

    /**
     *  Passenger identification.
     */

    private int passId;

    /**
     *  Passenger state.
     */

    private int passState;

    /**
     *  Pilot state.
     */

    private int pilotState;

    /**
     *  Hostess state.
     */

    private int hostessState;

    /**
     *  Instantiation of a client proxy.
     *
     *     @param sconi communication channel
     *     @param depAirInt interface to the barber shop
     */

    public DepartureAirportProxy (ServerCom sconi, DepartureAirportInterface depAirInt)
    {
        super ("BarberShopProxy_" + DepartureAirportProxy.getProxyId ());
        this.sconi = sconi;
        this.depAirInt = depAirInt;
    }

    /**
     *  Generation of the instantiation identifier.
     *
     *     @return instantiation identifier
     */

    private static int getProxyId ()
    {
        Class<?> cl = null;                                            // representation of the BarberShopClientProxy object in JVM
        int proxyId;                                                   // instantiation identifier

        try
        { cl = Class.forName ("ServerSide.entities.DepartureAirportProxy");
        }
        catch (ClassNotFoundException e)
        { System.out.println("Data type DepartureAirportProxy was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }

    /**
     * Get the passenger's ID.
     *
     * @return Passenger's ID
     */
    public int getID() {
        return 0;
    }

    /**
     * Set the passenger's ID.
     *
     * @param id passenger's ID.
     */
    public void setId(int id) {

    }

    /**
     * Get current state.
     *
     * @return the current state of a passenger
     */
    @Override
    public int getPassengerState() {
        return 0;
    }

    /**
     * Set current state.
     *
     * @param newState new state of a passenger
     */
    @Override
    public void setPassengerState(PassengerStates newState) {

    }

    /**
     * Set current state.
     *
     * @param newState new state of a passenger
     */
    @Override
    public void setPassengerState(int newState) {

    }

    /**
     * Get the number of passengers that already arrived at destination.
     *
     * @return number of passengers that already arrived at destination
     */
    public int getPTAL() {
        return 0;
    }

    /**
     * Get current state
     *
     * @return the current state of a pilot
     */
    @Override
    public int getPilotState() {
        return 0;
    }

    /**
     * Set current state
     *
     * @param newState new state of a pilot
     */
    @Override
    public void setPilotState(int newState) {

    }

    /**
     * Get current state.
     *
     * @return the current state of a hostess
     */
    @Override
    public int getHostessState() {
        return 0;
    }

    /**
     * Set current state.
     *
     * @param newState new state of a hostess
     */
    @Override
    public void setHostessState(HostessStates newState) {

    }

    /**
     * Set current state.
     *
     * @param newState int representing new state of a hostess
     */
    @Override
    public void setHostessState(int newState) {

    }

    /**
     * Get end of life
     *
     * @return true if pilot don't have more flights
     */
    public boolean getEndOfLife() {
        return false;
    }

    /**
     * Set end of life state
     *
     * @param newEndOfLife changes status of endOfLife
     */
    public void setEndOfLife(boolean newEndOfLife) {

    }

    /**
     * Get if the pilot is asleep
     *
     * @return True if is asleep
     */
    public boolean getAsleep() {
        return false;
    }

    /**
     * Set a pilot to sleep or wakes her up
     *
     * @param newAsleep new state of asleep
     */
    public void setAsleep(boolean newAsleep) {

    }

    /**
     * Checks the number of passengers in each flight.
     *
     * @return number of passengers in each flight.
     */
    public int[] getNpassengers() {
        return new int[0];
    }

    /**
     * sets the number of passengers in each flight.
     *
     * @param index       index of the flight
     * @param npassengers number the passenger of the flight
     */
    public void setNpassengers(int index, int npassengers) {

    }

    /**
     * Checks number of flights made.
     *
     * @return number of flights
     */
    public int getNflights() {
        return 0;
    }

    /**
     * Sets numbers of flights made.
     *
     * @param nflights
     */
    public void setNflights(int nflights) {

    }

    /**
     *  Life cycle of the service provider agent.
     */

    @Override
    public void run ()
    {
        Message inMessage = null,                                      // service request
                outMessage = null;                                     // service reply

        /* service providing */

        inMessage = (Message) sconi.readObject ();                     // get service request
        try
        { outMessage = depAirInt.processAndReply (inMessage);         // process it
        }
        catch (MessageException e)
        { System.out.println("Thread " + getName () + ": " + e.getMessage () + "!");
            System.out.println(e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
    }
}
}
