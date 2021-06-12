package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface PlaneInterface extends Remote {

    public Message waitForAllInBoard() throws RemoteException;
    public Message flyToDestinationPoint() throws RemoteException;
    public Message waitForEndOfFlight() throws RemoteException;
    public Message flyToDeparturePoint() throws RemoteException;
    public Message informPlaneReadyToTakeOff() throws RemoteException;
    public Message shutdown() throws RemoteException;

}
