package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface PlaneInterface extends Remote {

    public int waitForAllInBoard() throws RemoteException;
    public int flyToDestinationPoint() throws RemoteException;
    public void waitForEndOfFlight(int passId) throws RemoteException;
    public Message flyToDeparturePoint() throws RemoteException;
    public int informPlaneReadyToTakeOff() throws RemoteException;
    public void shutdown() throws RemoteException;

}
