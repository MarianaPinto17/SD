package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface DepartureAirportInterface extends Remote {
    public Message informPlaneReadyForBoarding() throws RemoteException;
    public Message prepareForPassBoarding() throws RemoteException;
    public Message waitInQueue() throws RemoteException;
    public Message checkDocuments() throws RemoteException;
    public Message showDocuments() throws RemoteException;
    public Message waitForNextPassenger() throws RemoteException;
    public Message boardThePlane() throws RemoteException;
    public Message waitForNextFlight() throws RemoteException;
    public Message parkAtTransferGate() throws RemoteException;
    public Message shutdown() throws RemoteException;
    public Message isInformPlane() throws RemoteException;
}
