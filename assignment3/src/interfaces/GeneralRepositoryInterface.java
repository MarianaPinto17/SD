package interfaces;

import java.rmi.*;
import commonInfrastructures.*;

/**
 * General Repository Interface.
 * @author André Alves
 * @author Mariana Pinto
 */
public interface GeneralRepositoryInterface extends Remote {
    /**
     * Operation initialization of simulation.
     * @param logFileName name of the logging file
     * @throws RemoteException
     */
    public void initSimul(String logFileName) throws RemoteException;

    /**
     * Operation server shutdown.
     * @throws RemoteException
     */
    public void shutdown() throws RemoteException;

    /**
     * Set Pilot state.
     * @param state pilot state
     * @throws RemoteException
     */
    public void setPilotState(int state) throws RemoteException;

    /**
     * Set Hostess state.
     * @param state hostess state
     * @throws RemoteException
     */
    public void setHostessState(int state) throws RemoteException;

    /**
     * Set hostess State when the passenger is checked.
     * @param state hostess state
     * @param id passenger id
     * @throws RemoteException
     */
    public void setHostessState(int state, int id) throws RemoteException;

    /**
     * Set passenger State.
     * @param id passenger id
     * @param state passenger state
     * @throws RemoteException
     */
    public void setPassengerState(int id, int state) throws RemoteException;

    /**
     * Close of operations.
     * @throws RemoteException
     */
    public void sumUp() throws RemoteException;

    /**
     * Get the number of passengers in flight.
     * @return number of passengers in flight
     * @throws RemoteException
     */
    public int getInF() throws RemoteException;

    /**
     * Gets the number of passengers arrived at the destination airport.
     * @return number of passengers arrived at the destination airport
     * @throws RemoteException
     */
    public int getPTAL() throws RemoteException;

    /**
     * Sets the status of the plane to empty when all the passengers left the plane at the destination airport.
     * @param emptyPlaneDest changes status to emptyPlaneDest
     * @throws RemoteException
     */
    public void setEmptyPlaneDest(boolean emptyPlaneDest) throws RemoteException;


}
