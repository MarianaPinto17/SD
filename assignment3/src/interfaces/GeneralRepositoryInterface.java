package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface GeneralRepositoryInterface extends Remote {

    public Message initSimul(String logFileName) throws RemoteException;
    public Message shutdown() throws RemoteException;
    public Message reportInitialStatus() throws RemoteException;
    public Message reportStatus() throws RemoteException;
    public Message setPilotState(int state) throws RemoteException;
    public Message setHostessState(int state) throws RemoteException;
    public Message setHostessState(int state, int id) throws RemoteException;
    public Message setPassengerState(int id, int state) throws RemoteException;
    public Message sumUp() throws RemoteException;
    public Message getInF() throws RemoteException;
    public Message getPTAL() throws RemoteException;
    public Message setEmptyPlaneDest(boolean emptyPlaneDest) throws RemoteException;


}
