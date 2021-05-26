package ClientSide.main;

import ClientSide.entities.Hostess;
import ClientSide.entities.Pilot;
import ClientSide.stub.DepartureAirportStub;
import ClientSide.stub.DestinationAirportStub;
import ClientSide.stub.GeneralRepositoryStub;
import ClientSide.stub.PlaneStub;

/**
 *    Client side of the Sleeping Pilots (pilots).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class HostessMain {
    /**
     *  Main method.
     *
     *    @param args runtime arguments
     *        args[0] - name of the platform where is located the pilot shop server
     *        args[1] - port nunber for listening to service requests
     *        args[2] - name of the platform where is located the general repository server
     *        args[3] - port nunber for listening to service requests
     */

    public static void main (String [] args)
    {
        int depAirPortNumb = -1;
        int planePortNumb = -1;

        String depAirHostName;
        String planeHostName;

        Hostess hostess;                    // array of pilot threads
        DepartureAirportStub depAir;
        PlaneStub plane;


        /* getting problem runtime parameters */

        if (args.length != 4)
        { System.out.println("Wrong number of parameters!");
            System.exit (1);
        }
        depAirHostName = args[0];
        try
        { depAirPortNumb = Integer.parseInt (args[1]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[1] is not a number!");
            System.exit (1);
        }
        if ((depAirPortNumb < 4000) || (depAirPortNumb >= 65536))
        { System.out.println("args[1] is not a valid port number!");
            System.exit (1);
        }
        planeHostName = args[2];
        try
        { planePortNumb = Integer.parseInt (args[3]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[3] is not a number!");
            System.exit (1);
        }
        if ((planePortNumb < 4000) || (planePortNumb >= 65536))
        { System.out.println("args[3] is not a valid port number!");
            System.exit (1);
        }

        /* problem initialization */

        depAir = new DepartureAirportStub(depAirHostName, depAirPortNumb);
        plane = new PlaneStub(planeHostName, planePortNumb);

        hostess = new Hostess("Hospedeira", depAir, plane);

        /* start of the simulation */
        hostess.start();


        /* waiting for the end of the simulation */

        System.out.println();

        while (hostess.isAlive ()) {
            Thread.yield ();
        }
        try {
            hostess.join ();
        } catch (InterruptedException e) {}
        System.out.println("The pilot has terminated.");

        System.out.println();
    }
}
