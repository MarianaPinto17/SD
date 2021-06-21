package ServerSide.objects;

import java.rmi.*;
import commonInfrastructures.*;
import ClientSide.entities.*;
import ServerSide.main.*;
import interfaces.*;

/**
 * Shared Region Destination Airport.
 * @author André Alves
 * @author Mariana Pinto
 */
public class DestinationAirport implements DestinationAirportInterface {
    /**
     * Reference to the General Repository.
     */
    private final GeneralRepositoryInterface repos;

    /**
     * Destination Airport constructor.
     * @param repos general repository of information
     */
    public DestinationAirport(GeneralRepositoryInterface repos){
        this.repos = repos;
    }

    /**
     * Pilot function - pilot announces that the plane arrived at destination.
     */
    @Override
    public synchronized int announceArrival() throws RemoteException {
        repos.setPilotState(PilotStates.DEBOARDING);

        notifyAll();
        while (repos.getInF() > 0){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        return PilotStates.DEBOARDING;
    }

    /**
     * Passenger function - when the plane arrives at destination the passenger exits the plane.
     */
    @Override
    public synchronized Message leaveThePlane(int passId) throws RemoteException {
        if(repos.getInF() == 0){
            repos.setEmptyPlaneDest(true);
        }
        repos.setPassengerState(PassengerStates.AT_DESTINATION, passId);

        notifyAll();

        return new Message(PassengerStates.AT_DESTINATION, passId);
    }

    /**
     * Operation server shutdown.
     * @throws RemoteException
     */
    @Override
    public void shutdown() throws RemoteException {
        DestinationAirportMain.shutdown();
        notifyAll();
    }
}
