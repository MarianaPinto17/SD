package ClientSide.main;

import ClientSide.entities.*;
import ServerSide.main.SimulPar;
import interfaces.*;
import java.rmi.*;
import java.rmi.registry.*;

/**
 *    Client side of the Sleeping Pilots (pilots).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 * @author André Alves
 * @author Mariana Pinto
 */

public class PassengerMain {
    /**
     *  Main method.
     *
     *    @param args runtime arguments
     *        args[0] - name of the platform where is located RMI registering service
     *        args[1] - port number where the registering service is listening to service requests
     */

    public static void main (String [] args) {

        String rmiRegHostName;                                         // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1;                                       // port number where the registering service is listening to service requests

        /* getting problem runtime parameters */

        if (args.length != 2) {
            System.out.println("Wrong number of parameters!");
            System.exit(1);
        }
        rmiRegHostName = args[0];
        try {
            rmiRegPortNumb = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("args[1] is not a number!");
            System.exit(1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[1] is not a valid port number!");
            System.exit(1);
        }

        /* problem initialization */

        String nameEntryDepartureAirport = "DepartureAirport";                     // public name of the departure airport object
        DepartureAirportInterface depAir = null;
        String nameEntryPlane = "Plane";                     // public name of the plane object
        PlaneInterface plane = null;
        String nameEntryDestinationAirport = "DestinationAirport";                     // public name of the Destination Airport object
        DestinationAirportInterface desAir = null;
        Registry registry = null;                                      // remote reference for registration in the RMI registry service
        Passenger passenger[] = new Passenger[SimulPar.N];                    // array of pilot threads

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            depAir = (DepartureAirportInterface) registry.lookup(nameEntryDepartureAirport);
        } catch (RemoteException e) {
            System.out.println("DepartureAirport lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("DepartureAirport not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            desAir = (DestinationAirportInterface) registry.lookup(nameEntryDestinationAirport);
        } catch (RemoteException e) {
            System.out.println("GeneralRepos lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("GeneralRepos not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            plane = (PlaneInterface) registry.lookup(nameEntryPlane);
        } catch (RemoteException e) {
            System.out.println("DepartureAirport lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("DepartureAirport not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        for (int i = 0; i < SimulPar.N; i++) {
            passenger[i] = new Passenger(i, depAir, desAir, plane);
        }

        /* start of the simulation */
        for (int i = 0; i < SimulPar.N; i++)
            passenger[i].start();


        /* waiting for the end of the simulation */

        System.out.println();

        System.out.println();
        for (int i = 0; i < SimulPar.N; i++) {
            try {
                passenger[i].join();
            } catch (InterruptedException e) {
            }
            System.out.println("The passenger " + (i + 1) + " has terminated.");
        }

        System.out.println();
        try {
            depAir.shutdown();
        } catch (RemoteException e) {
            System.out.println("Passenger generator remote exception on General Repository shutdown: " + e.getMessage());
            System.exit(1);
        }

        try {
            desAir.shutdown();
        } catch (RemoteException e) {
            System.out.println("Passenger generator remote exception on Destination Airport shutdown: " + e.getMessage());
            System.exit(1);
        }

        try {
            plane.shutdown();
        } catch (RemoteException e) {
            System.out.println("Passenger generator remote exception on Plane shutdown: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * The class can not be instantiated.
     */
    private PassengerMain(){

    }
}
