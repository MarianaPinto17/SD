package ClientSide.main;

import ClientSide.entities.*;
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

public class HostessMain {
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
            System.exit (1);
        }
        rmiRegHostName = args[0];
        try {
            rmiRegPortNumb = Integer.parseInt (args[1]);
        } catch (NumberFormatException e) {
            System.out.println("args[1] is not a number!");
            System.exit (1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[1] is not a valid port number!");
            System.exit (1);
        }

        /* problem initialization */

        String nameEntryDepartureAirport = "DepartureAirport";                     // public name of the departure airport object
        DepartureAirportInterface depAir = null;
        String nameEntryPlane = "Plane";                     // public name of the plane object
        PlaneInterface plane = null;
        Registry registry = null;                                      // remote reference for registration in the RMI registry service
        Hostess hostess;                    // array of barber threads

        try {
            registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            depAir = (DepartureAirportInterface) registry.lookup(nameEntryDepartureAirport);
        } catch (RemoteException e) {
            System.out.println("DepartureAirport lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println("DepartureAirport not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            plane = (PlaneInterface) registry.lookup(nameEntryPlane);
        } catch (RemoteException e) {
            System.out.println("Plane lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println("Plane not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        hostess = new Hostess("Hospedeira", depAir, plane);

        /* start of the simulation */
        hostess.start();

        /* waiting for the end of the simulation */

        try {
            hostess.join ();
        } catch (InterruptedException e) {}
        System.out.println("The Hostess has terminated.");

    }

    /**
     * The class can not be instantiated.
     */
    private HostessMain(){

    }
}
