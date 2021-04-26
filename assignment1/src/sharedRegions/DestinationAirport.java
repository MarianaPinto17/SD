package sharedRegions;

import entities.*;
import main.SimulPar;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport {

    /**
     *
     */
    private final Passenger[] pass;

    /**
     *
     */
    private Pilot pi;

    /**
     *
     */
    private final GeneralRepository repo;
    /**
     *
     */
    private boolean arrivedDestination;

    /**
     * Destination Airport constructor
     * @param repo general repository of information
     */

    public DestinationAirport(GeneralRepository repo){
        pass = new Passenger[SimulPar.N];
        this.repo = repo;
    }

}
