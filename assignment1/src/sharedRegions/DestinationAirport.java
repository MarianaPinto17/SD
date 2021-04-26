package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.SimulPar;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport {


    private final Passenger[] pass;
    private Pilot pi;
    private final GeneralRepository repo;
    private boolean arrivedDestination;

    public DestinationAirport(GeneralRepository repo){
        pass = new Passenger[SimulPar.N];

        this.repo = repo;
    }

}
