package main;

import entities.*;
import sharedRegions.*;

import java.nio.file.*;

/**
 *
 * @author André Alves
 * @author Mariana Pinto
 */
public class AirLift {

    /**
     *    Main method.
     *
     *    @param args runtime arguments
     */
    public static void main (String[] args){

        Pilot pilot;                                            // Pilot thread
        Hostess hostess;                                        // Hostess thread
        Passenger[] passengers = new Passenger[SimulPar.N];     // Array of Passenger threads

        DepartureAirport departureAirport;                      // reference to DepartureAirport
        DestinationAirport destinationAirport;                  // reference to DestinationAirport
        Plane plane;                                            // reference to Plane

        GeneralRepository repos;                                // reference to General Repository

        String fileName;                                        // logging file name
        char opt;                                               // selected option
        boolean success;                                        // end of operation flag

        String filename_args = "log.txt";
        if (args.length >= 1)
            filename_args = args[0];


        System.out.println ("\n" + "      Problem of Air Lift\n");
        do {
            System.out.println ("Logging file name? ");
            fileName = filename_args; //System.console().readLine();
            if( Files.exists(Path.of(fileName))) {
                do{
                    System.out.print ("There is already a file with this name. Delete it (y - yes; n - no)? ");
                    opt = 'y';//System.console().readLine().charAt(0);
                } while ((opt != 'y') && (opt != 'n'));
                success = opt == 'y';
            } else success = true;
        } while (!success);

        /* problem initialization */

        repos = new GeneralRepository (fileName);
        departureAirport = new DepartureAirport(repos);
        destinationAirport = new DestinationAirport(repos);
        plane = new Plane(repos);

        for (int i = 0; i < SimulPar.N; i++) {
            passengers[i] = new Passenger(i, departureAirport, destinationAirport, plane);
        }
        hostess = new Hostess("Hospedeira", departureAirport, destinationAirport, plane);
        pilot = new Pilot("Piloto", departureAirport, destinationAirport, plane, repos);


        /* START OF SIMULATION */
        pilot.start();
        hostess.start();
        for (int i = 0; i < SimulPar.N; i++) {
            passengers[i].start();
        }



        /* WAITING FOR END OF SIMULATION */

        System.out.println();
        for (int i = 0; i < SimulPar.N; i++) {
            try {
                passengers[i].join();
            } catch (InterruptedException e){}

            System.out.println("The Passenger "+(i+1)+" has terminated.");
        }

        System.out.println();
        while (pilot.isAlive()){
            pilot.interrupt();
            Thread.yield();
        }
        try{
            pilot.join();
        } catch (InterruptedException e){}

        System.out.println("The pilot has terminated");

        System.out.println();
        while (hostess.isAlive()){
            hostess.interrupt();
            Thread.yield();
        }
        try{
            hostess.join();
        } catch (InterruptedException e){}

        System.out.println("The hostess has terminated");
        System.out.println();
    }
}
