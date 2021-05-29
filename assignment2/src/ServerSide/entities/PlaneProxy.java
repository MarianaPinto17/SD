package ServerSide.entities;

import ServerSide.sharedRegions.PlaneInterface;
import commonInfrastructures.Message;
import commonInfrastructures.MessageException;
import commonInfrastructures.ServerCom;

/**
 *  Service provider agent for access to the Barber Shop.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PlaneProxy extends Thread implements Pilot, Hostess, Passenger {
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

    private PlaneInterface planeInt;

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
     * End of life of the hostess
     */
    private boolean hEndOfLife;
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
     *     @param planeInt interface to the barber shop
     */

    public PlaneProxy(ServerCom sconi, PlaneInterface planeInt)
    {
        super ("PlaneProxy_" + PlaneProxy.getProxyId ());
        this.sconi = sconi;
        this.planeInt = planeInt;
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
        { cl = Class.forName ("ServerSide.entities.PlaneProxy");
        }
        catch (ClassNotFoundException e)
        { System.out.println("Data type PlaneProxy was not found!");
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
     * Get current state.
     *
     * @return the current state of a hostess
     */
    @Override
    public int getHostessState() {
        return hostessState;
    }

    /**
     * Set current state.
     *
     * @param newState int representing new state of a hostess
     */
    @Override
    public void setHostessState(int newState) {
        hostessState = newState;
    }

    /**
     * Get end of life
     *
     * @return true if hostess don't have more flights
     */
    @Override
    public boolean getHEndOfLife() {
        return hEndOfLife;
    }

    /**
     * Set end of life state
     *
     * @param newEndOfLife changes status of hEndOfLife
     */
    @Override
    public void setHEndOfLife(boolean newEndOfLife) {
        hEndOfLife = newEndOfLife;
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
        { outMessage = planeInt.processAndReply (inMessage);         // process it
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
