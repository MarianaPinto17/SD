package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

/**
 * Destination Airport Interface.
 * @author André Alves
 * @author Mariana Pinto
 */
public interface DestinationAirportInterface extends Remote {
    /**
     * Pilot announces that the plane arrived at destination.
     * @return pilot state deboarding
     * @throws RemoteException
     */
    public int announceArrival() throws RemoteException;

    /**
     * Passenger leaves the plane when arriving the destination airport.
     * @param passId passenger ID
     * @return passenger state at destination
     * @throws RemoteException
     */
    public Message leaveThePlane(int passId) throws RemoteException;

    /**
     * Operation server shutdown.
     * @throws RemoteException
     */
    public void shutdown() throws RemoteException;

}
