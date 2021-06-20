package ServerSide.main;

import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.*;
import ServerSide.objects.*;
import interfaces.*;

/**
 *    Server side of the Destination Airport of Information.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 * @author André Alves
 * @author Mariana Pinto
 */

public class DestinationAirportMain {

    /**
     *  Flag signaling the end of operations.
     */

    private static boolean end = false;

    /**
     *  Main method.
     *
     *        args[0] - port number for listening to service requests
     *        args[1] - name of the platform where is located the RMI registering service
     *        args[2] - port number where the registering service is listening to service requests
     */
    public static void main (String[] args) {
        int portNumb = -1;                                             // port number for listening to service requests
        String rmiRegHostName;                                         // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1;                                       // port number where the registering service is listening to service requests

        if (args.length != 3) {
            System.out.println("Wrong number of parameters!");
            System.exit (1);
        }

        try {
            portNumb = Integer.parseInt (args[0]);
        } catch (NumberFormatException e) {
            System.out.println("args[0] is not a number!");
            System.exit (1);
        }

        if ((portNumb < 4000) || (portNumb >= 65536)) {
            System.out.println("args[0] is not a valid port number!");
            System.exit (1);
        }

        rmiRegHostName = args[1];

        try {
            rmiRegPortNumb = Integer.parseInt (args[2]);
        } catch (NumberFormatException e) {
            System.out.println("args[2] is not a number!");
            System.exit (1);
        }

        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[2] is not a valid port number!");
            System.exit (1);
        }

        /* create and install the security manager */

        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        System.out.println("Security manager was installed!");

        /* get a remote reference to the general repository object */

        String nameEntryGeneralRepos = "GeneralRepository";            // public name of the general repository object
        GeneralRepositoryInterface reposStub = null;                        // remote reference to the general repository object
        Registry registry = null;                                      // remote reference for registration in the RMI registry service

        try {
            registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        System.out.println("RMI registry was created!");

        try {
            reposStub = (GeneralRepositoryInterface) registry.lookup (nameEntryGeneralRepos);
        } catch (RemoteException e) {
            System.out.println("GeneralRepository lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println("GeneralRepository not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        /* instantiate a destination airport object */

        DestinationAirport desAir = new DestinationAirport(reposStub);                 // Destination Airport object
        DestinationAirportInterface desAirStub = null;                          // remote reference to the Destination Airport object

        try {
            desAirStub = (DestinationAirportInterface) UnicastRemoteObject.exportObject (desAir, portNumb);
        } catch (RemoteException e) {
            System.out.println("Destination Airport stub generation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        System.out.println("Stub was generated!");

        /* register it with the general registry service */

        String nameEntryBase = "RegisterHandler";                      // public name of the object that enables the registration
        // of other remote objects
        String nameEntryObject = "DestinationAirport";                         // public name of the Destination Airport object
        Register reg = null;                                           // remote reference to the object that enables the registration
        // of other remote objects

        try {
            reg = (Register) registry.lookup (nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            reg.bind (nameEntryObject, desAirStub);
        } catch (RemoteException e) {
            System.out.println("Destination Airport registration exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (AlreadyBoundException e) {
            System.out.println("Destination Airport already bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        System.out.println("Destination Airport object was registered!");

        /* wait for the end of operations */

        System.out.println("Destination Airport is in operation!");
        try {
            while (!end) synchronized (Class.forName ("ServerSide.main.DestinationAirportMain")) {
                try {
                    (Class.forName ("ServerSide.main.DestinationAirportMain")).wait ();
                } catch (InterruptedException e) {
                    System.out.println("Destination Airport main thread was interrupted!");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type DestinationAirportMain was not found (blocking)!");
            e.printStackTrace ();
            System.exit (1);
        }

        /* server shutdown */

        boolean shutdownDone = false;                                  // flag signalling the shutdown of the Destination Airport service

        try {
            reg.unbind (nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Destination Airport deregistration exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println("Destination Airport ot bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        System.out.println("Destination Airport was deregistered!");

        try {
            shutdownDone = UnicastRemoteObject.unexportObject (desAir, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Destination Airport unexport exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        if (shutdownDone)
            System.out.println("Destination Airport was shutdown!");
    }

    /**
     *  Close of operations.
     */
    public static void shutdown ()
    {
        end = true;
        try {
            synchronized (Class.forName ("ServerSide.main.DestinationAirportMain")) {
                (Class.forName ("ServerSide.main.DestinationAirportMain")).notify ();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type DestinationAirportMain was not found (waking up)!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}
