package ServerSide.objects;

import java.rmi.*;
import commonInfrastructures.*;
import ClientSide.entities.*;
import ServerSide.main.*;
import interfaces.*;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport implements DestinationAirportInterface {

    /**
     * Reference to the passenger threads.
     */
    private final Passenger[] pass;

    /**
     * Reference to the Pilot.
     */
    private Pilot pi;

    /**
     * Reference to the General Repository.
     */
    private final GeneralRepositoryInterface repos;

    /**
     * Destination Airport constructor.
     * @param repos general repository of information
     */
    public DestinationAirport(GeneralRepositoryInterface repos){
        pass = new Passenger[SimulPar.N];
        this.repos = repos;
    }

    /**
     * Pilot function - pilot announces that the plane arrived at destination.
     */
    @Override
    public synchronized int announceArrival() throws RemoteException {
        pi = (Pilot) Thread.currentThread();
        pi.setPilotState(PilotStates.DEBOARDING);
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
        int passengerID = ((Passenger) Thread.currentThread()).getID();

        pass[passId] = (Passenger) Thread.currentThread();

        if(repos.getInF() == 0){
            repos.setEmptyPlaneDest(true);
        }
        pass[passId].setPassengerState(PassengerStates.AT_DESTINATION);
        repos.setPassengerState(PassengerStates.AT_DESTINATION, passId);

        notifyAll();

        return new Message(PassengerStates.AT_DESTINATION, passId);
    }

    @Override
    public void shutdown() throws RemoteException {
        DestinationAirportMain.shutdown();
        notifyAll();
    }
}
