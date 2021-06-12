package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface DestinationAirportInterface extends Remote {

    public int announceArrival() throws RemoteException;
    public Message leaveThePlane(int passId) throws RemoteException;
    public void shutdown() throws RemoteException;

}
