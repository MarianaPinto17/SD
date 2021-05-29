package ClientSide.main;

import ClientSide.entities.Passenger;
import ClientSide.stub.DepartureAirportStub;
import ClientSide.stub.DestinationAirportStub;
import ClientSide.stub.PlaneStub;

/**
 *    Client side of the Sleeping Pilots (pilots).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PassengerMain {
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


        String depAirHostName;
        String planeHostName;
        String desAirHostName;

        Passenger passenger[] = new Passenger[SimulPar.N];                    // array of pilot threads
        DepartureAirportStub depAir;
        DestinationAirportStub desAir;
        PlaneStub plane;


        /* getting problem runtime parameters */

        if (args.length != 6)
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
        { desAirPortNumb = Integer.parseInt (args[3]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[3] is not a number!");
            System.exit (1);
        }
        if ((desAirPortNumb < 4000) || (desAirPortNumb >= 65536))
        { System.out.println("args[3] is not a valid port number!");
            System.exit (1);
        }
        planeHostName = args[4];
        try
        { planePortNumb = Integer.parseInt (args[5]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[5] is not a number!");
            System.exit (1);
        }
        if ((planePortNumb < 4000) || (planePortNumb >= 65536))
        { System.out.println("args[5] is not a valid port number!");
            System.exit (1);
        }

        /* problem initialization */

        depAir = new DepartureAirportStub(depAirHostName, depAirPortNumb);
        desAir = new DestinationAirportStub(desAirHostName, desAirPortNumb);
        plane = new PlaneStub(planeHostName, planePortNumb);

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
                passenger[i].join ();
            } catch (InterruptedException e) {}
            System.out.println("The passenger " + (i+1) + " has terminated.");
        }

        System.out.println();
    }
}
