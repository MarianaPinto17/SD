package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

public interface GeneralRepositoryInterface extends Remote {

    public void initSimul(String logFileName) throws RemoteException;
    public void shutdown() throws RemoteException;
    public void setPilotState(int state) throws RemoteException;
    public void setHostessState(int state) throws RemoteException;
    public void setHostessState(int state, int id) throws RemoteException;
    public void setPassengerState(int id, int state) throws RemoteException;
    public void sumUp() throws RemoteException;
    public int getInF() throws RemoteException;
    public int getPTAL() throws RemoteException;
    public void setEmptyPlaneDest(boolean emptyPlaneDest) throws RemoteException;


}
