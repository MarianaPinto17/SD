package ClientSide.main;

import ClientSide.entities.*;
import ClientSide.stub.*;
import ServerSide.main.*;
import commonInfrastructures.*;

/**
 *    Client side of the Sleeping Pilots (pilots).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PilotMain {
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
        int desAirPortNumb = -1;
        int genReposPortNumb = -1;

        String depAirHostName;
        String desAirHostName;
        String planeHostName;
        String genReposHostName;

        Pilot pilot;                    // array of pilot threads
        DepartureAirportStub depAir;
        DestinationAirportStub desAir;
        PlaneStub plane;
        GeneralRepositoryStub genReposStub;                                 // remote reference to the general repository


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
        desAirHostName = args[2];
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
        planeHostName = args[4];
        try
        { desAirPortNumb = Integer.parseInt (args[5]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[5] is not a number!");
            System.exit (1);
        }
        if ((desAirPortNumb < 4000) || (desAirPortNumb >= 65536))
        { System.out.println("args[5] is not a valid port number!");
            System.exit (1);
        }
        genReposHostName = args[6];
        try
        { genReposPortNumb = Integer.parseInt (args[7]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[7] is not a number!");
            System.exit (1);
        }
        if ((genReposPortNumb < 4000) || (genReposPortNumb >= 65536))
        { System.out.println("args[7] is not a valid port number!");
            System.exit (1);
        }

        /* problem initialization */

        depAir = new DepartureAirportStub(depAirHostName, depAirPortNumb);
        desAir = new DestinationAirportStub(desAirHostName, desAirPortNumb);
        plane = new PlaneStub(planeHostName, planePortNumb);
        genReposStub = new GeneralRepositoryStub (genReposHostName, genReposPortNumb);

        pilot = new Pilot("Piloto", depAir, desAir, plane, genReposStub);

        /* start of the simulation */
        pilot.start();


        /* waiting for the end of the simulation */

        System.out.println();

        while (pilot.isAlive ()) {
            Thread.yield ();
        }
        try {
            pilot.join ();
        } catch (InterruptedException e) {}
        System.out.println("The pilot has terminated.");

        System.out.println();
        genReposStub.shutdown();
    }
}
