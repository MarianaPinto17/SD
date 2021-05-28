package ServerSide.entities;

import ServerSide.sharedRegions.*;
import commonInfrastructures.*;

/**
 *  Service provider agent for access to the Barber Shop.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DestinationAirportProxy extends Thread implements Pilot, Passenger {
    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    /**
     *  Communication channel.
     */

    private ServerCom sconi;

    /**
     *  Interface to the Destination Airport.
     */

    private DestinationAirportInterface desAirInt;

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
     * End of life of the pilot
     */
    private boolean PiEndOfLife;
    /**
     * End of life of the passenger
     */
    private boolean PaEndOfLife;

    /**
     * number of passengers in each flight.
     */
    private final int[] npassengers = new int[5];

    /**
     * Number of flights at the moment.
     */
    private int nflights;

    /**
     *  Instantiation of a client proxy.
     *
     *     @param sconi communication channel
     *     @param desAirInt interface to the barber shop
     */

    public DestinationAirportProxy (ServerCom sconi, DestinationAirportInterface desAirInt)
    {
        super ("BarberShopProxy_" + DestinationAirportProxy.getProxyId ());
        this.sconi = sconi;
        this.desAirInt = desAirInt;
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
        { cl = Class.forName ("ServerSide.entities.DestinationAirportProxy");
        }
        catch (ClassNotFoundException e)
        { System.out.println("Data type DestinationAirportProxy was not found!");
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
        return passId;
    }

    /**
     * Set the passenger's ID.
     *
     * @param id passenger's ID.
     */
    public void setId(int id) {
        passId=id;
    }

    /**
     * Get current state.
     *
     * @return the current state of a passenger
     */
    @Override
    public int getPassengerState() {
        return passState;
    }

    /**
     * Set current state.
     *
     * @param newState new state of a passenger
     */
    @Override
    public void setPassengerState(int newState) {
        passState = newState;
    }

    /**
     * Get current state
     *
     * @return the current state of a pilot
     */
    @Override
    public int getPilotState() {
        return pilotState;
    }

    /**
     * Set current state
     *
     * @param newState new state of a pilot
     */
    @Override
    public void setPilotState(int newState) {
        pilotState = newState;
    }

    /**
     * Get end of life
     *
     * @return true if hostess don't have more flights
     */
    @Override
    public boolean getPiEndOfLife() {
        return PiEndOfLife;
    }

    /**
     * Set end of life state
     *
     * @param newEndOfLife changes status of hEndOfLife
     */
    @Override
    public void setPiEndOfLife(boolean newEndOfLife) {
        PiEndOfLife = newEndOfLife;
    }

    /**
     * Get end of life
     *
     * @return true if hostess don't have more flights
     */
    @Override
    public boolean getPaEndOfLife() {
        return PaEndOfLife;
    }

    /**
     * Set end of life state
     *
     * @param newEndOfLife changes status of hEndOfLife
     */
    @Override
    public void setPaEndOfLife(boolean newEndOfLife) {
        PaEndOfLife = newEndOfLife;
    }

    /**
     * Checks the number of passengers in each flight.
     *
     * @return number of passengers in each flight.
     */
    public int[] getNpassengers() {
        return npassengers;
    }

    /**
     * sets the number of passengers in each flight.
     *
     * @param index       index of the flight
     * @param npassengers number the passenger of the flight
     */
    public void setNpassengers(int index, int npassengers) {
        this.npassengers[index] = npassengers;
    }

    /**
     * Checks number of flights made.
     *
     * @return number of flights
     */
    public int getNflights() {
        return nflights;
    }

    /**
     * Sets numbers of flights made.
     *
     * @param nflights
     */
    public void setNflights(int nflights) {
        this.nflights = nflights;
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
        { outMessage = desAirInt.processAndReply (inMessage);         // process it
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
