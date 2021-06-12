package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface DepartureAirportInterface extends Remote {
    public int informPlaneReadyForBoarding() throws RemoteException;
    public Message prepareForPassBoarding() throws RemoteException;
    public Message waitInQueue(int passId) throws RemoteException;
    public int checkDocuments() throws RemoteException;
    public void showDocuments(int passId) throws RemoteException;
    public int waitForNextPassenger() throws RemoteException;
    public Message boardThePlane(int passId) throws RemoteException;
    public int waitForNextFlight() throws RemoteException;
    public Message parkAtTransferGate() throws RemoteException;
    public void shutdown() throws RemoteException;
    public boolean isInformPlane() throws RemoteException;
}
