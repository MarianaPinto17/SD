package ServerSide.main;

import ClientSide.stub.GeneralRepositoryStub;
import ServerSide.entities.DestinationAirportProxy;
import ServerSide.sharedRegions.*;
import commonInfrastructures.ServerCom;

/**
 *    Server side of the General Repository of Information.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DestinationAirportMain {
    /**
     *  Flag signaling the service is active.
     */

    public static boolean waitConnection;

    /**
     *  Main method.
     *
     *    @param args runtime arguments
     *        args[0] - port nunber for listening to service requests
     *        args[1] - name of the platform where is located the server for the general repository
     *        args[2] - port nunber where the server for the general repository is listening to service requests
     */

    public static void main (String [] args)
    {
        DestinationAirport desAir;                                              // desarture airport (service to be rendered)
        DestinationAirportInterface desAirInter;                                // interface to the desarture airport
        GeneralRepositoryStub reposStub;                                    // stub to the general repository
        ServerCom scon, sconi;                                         // communication channels
        int portNumb = -1;                                             // port number for listening to service requests
        String reposServerName;                                        // name of the platform where is located the server for the general repository
        int reposPortNumb = -1;                                        // port nunber where the server for the general repository is listening to service requests

        if (args.length != 3)
        { System.out.println("Wrong number of parameters!");
            System.exit (1);
        }
        try
        { portNumb = Integer.parseInt (args[0]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[0] is not a number!");
            System.exit (1);
        }
        if ((portNumb < 4000) || (portNumb >= 65536))
        { System.out.println("args[0] is not a valid port number!");
            System.exit (1);
        }
        reposServerName = args[1];
        try
        { reposPortNumb = Integer.parseInt (args[2]);
        }
        catch (NumberFormatException e)
        { System.out.println("args[2] is not a number!");
            System.exit (1);
        }
        if ((reposPortNumb < 4000) || (reposPortNumb >= 65536))
        { System.out.println("args[2] is not a valid port number!");
            System.exit (1);
        }

        /* service is established */

        reposStub = new GeneralRepositoryStub (reposServerName, reposPortNumb);       // communication to the general repository is instantiated
        desAir = new DestinationAirport(reposStub);                                      // service is instantiated
        desAirInter = new DestinationAirportInterface(desAir);                            // interface to the service is instantiated
        scon = new ServerCom (portNumb);                                         // listening channel at the public port is established
        scon.start ();
        System.out.println("Service is established!");
        System.out.println("Server is listening for service requests.");

        /* service request processing */

        DestinationAirportProxy cliProxy;                                // service provider agent

        waitConnection = true;
        while (waitConnection) {
            sconi = scon.accept ();                                    // enter listening procedure
            cliProxy = new DestinationAirportProxy (sconi, desAirInter);    // start a service provider agent to address
            cliProxy.start ();                                         //   the request of service
        }
        scon.end ();                                                   // operations termination
        System.out.println("Server was shutdown.");
    }
}
