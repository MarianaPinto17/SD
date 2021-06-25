package interfaces;

import java.rmi.*;

/**
 * Departure Airport Interface.
 * @author André Alves
 * @author Mariana Pinto
 */
public interface DepartureAirportInterface extends Remote {
    /**
     * Pilot informs plane is ready to start boarding.
     * @return pilot state ready for baording
     * @throws RemoteException
     */
    public int informPlaneReadyForBoarding() throws RemoteException;

    /**
     * Hostess prepare to start boarding passengers on the plane.
     * @return hostess state wait for passenger
     * @throws RemoteException
     */
    public ReturnType prepareForPassBoarding() throws RemoteException;

    /**
     * Passenger waits in queue to board the plane.
     * @param passId passenger ID
     * @return passenger state in queue
     * @throws RemoteException
     */
    public ReturnType waitInQueue(int passId) throws RemoteException;

    /**
     * Hostess checking passengers documents.
     * @return hostess state check passenger
     * @throws RemoteException
     */
    public int checkDocuments() throws RemoteException;

    /**
     * Passenger shows documents to hostess.
     * @param passId
     * @throws RemoteException
     */
    public void showDocuments(int passId) throws RemoteException;

    /**
     * Hostess waits for next passenger.
     * @return hostess state wait for next passenger
     * @throws RemoteException
     */
    public int waitForNextPassenger() throws RemoteException;

    /**
     * Passenger boards the plane.
     * @param passId passenger ID
     * @return passenger state in flight
     * @throws RemoteException
     */
    public ReturnType boardThePlane(int passId) throws RemoteException;

    /**
     * Hostess waits for the next flight of the day.
     * @return hostess state wait for flight.
     * @throws RemoteException
     */
    public int waitForNextFlight() throws RemoteException;

    /**
     * Pilot parks the plane at transfer Gate.
     * @return pilot state at transfer gate.
     * @throws RemoteException
     */
    public ReturnType parkAtTransferGate() throws RemoteException;

    /**
     * Operation server shutdown.
     * @throws RemoteException
     */
    public void shutdown() throws RemoteException;

    /**
     * Checks if a plane is ready to fly.
     * @return boolean if a plane is ready to take off.
     * @throws RemoteException
     */
    public boolean isInformPlane() throws RemoteException;
}
