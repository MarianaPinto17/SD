package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

/**
 * Plane Interface.
 * @author André Alves
 * @author Mariana Pinto
 */
public interface PlaneInterface extends Remote {

    /**
     * Pilot waits for all passengers get on board.
     * @return pilot state wait for boarding
     * @throws RemoteException
     */
    public int waitForAllInBoard() throws RemoteException;

    /**
     * Pilot flies to destination.
     * @return pilot state flying forward
     * @throws RemoteException
     */
    public int flyToDestinationPoint() throws RemoteException;

    /**
     * Passenger waits for the flight to end.
     * @param passId passenger id
     * @throws RemoteException
     */
    public void waitForEndOfFlight(int passId) throws RemoteException;

    /**
     * Pilot flies back to departure airport.
     * @return pilot state flying back
     * @throws RemoteException
     */
    public Message flyToDeparturePoint() throws RemoteException;

    /**
     * Hostess informs plane is ready to take off.
     * @return hostess state ready to fly
     * @throws RemoteException
     */
    public int informPlaneReadyToTakeOff() throws RemoteException;

    /**
     * Operation server shutdown.
     * @throws RemoteException
     */
    public void shutdown() throws RemoteException;

}
