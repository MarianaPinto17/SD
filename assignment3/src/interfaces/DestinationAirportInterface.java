package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface DestinationAirportInterface extends Remote {

    public Message announceArrival() throws RemoteException;
    public Message leaveThePlane() throws RemoteException;
    public Message shutdown() throws RemoteException;

}
