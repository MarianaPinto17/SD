package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.*;

import java.io.IOException;
import java.nio.file.*;
import java.io.*;

import java.util.Objects;

/**
 *  General Repository.
 *
 *    It is responsible to keep the visible internal state of the problem and to provide means for it
 *    to be printed in the logging file.
 *    It is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are no internal synchronization points.
 */


public class GeneralRepository {
    /**
     *  Name of the logging file.
     */

    private final String logFileName;

    /**
     *  Number of iterations of the customer life cycle.
     */

    private final int nIter;

    private final PilotStates pilotState;
    private final HostessStates hostessState;
    private final PassengerStates[] passengerStates;

    private int InQ;  //number of passengers presently forming a queue to board the plane
    private int InF;  //number of passengers in the plane
    private int PTAL; //number of passengers that have already arrived at their destination

    private int N_PASSENGERS;

    /**
     *
     */
    public GeneralRepository(String logFileName, int nIter){
        if ((logFileName == null) || Objects.equals (logFileName, ""))
            this.logFileName = "logger";
        else this.logFileName = logFileName;
        this.nIter = nIter;
        pilotState = PilotStates.AT_TRANSFER_GATE;
        hostessState = HostessStates.WAIT_FOR_NEXT_FLIGHT;
        passengerStates = new PassengerStates[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++)
            passengerStates[i] = PassengerStates.GOING_TO_AIRPORT;
        InQ = 0;
        InF = 0;
        PTAL = 0;

        reportInitialStatus();
    }

    /**
     *  Write the header to the logging file.
     *
     *  The barbers are sleeping and the customers are carrying out normal duties.
     *  Internal operation.
     */

    private void reportInitialStatus () {
        try {
            FileWriter fw = new FileWriter(logFileName, false);

            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.print("Airlift - Description of the internal state");
                pw.println();
                pw.println("PT HT P00 P01 P02 P03 P04 P05 P06 P07 P08 P09 P10 P11 P12 P13 P14 P15 P16 P17 P18 P19 P20 InQ InF PTAL");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        reportStatus ();
    }

    private void reportStatus(){
        String lineStatus = "";
        try {
            FileWriter fw = new FileWriter(logFileName, true);

            try (PrintWriter pw = new PrintWriter(fw)) {
                switch (pilotState) {
                    case AT_TRANSFER_GATE:
                        lineStatus += "ATRG ";
                        break;
                    case READY_FOR_BOARDING:
                        lineStatus += "RDFB ";
                        break;
                    case WAIT_FOR_BOARDING:
                        lineStatus += "WTFB ";
                        break;
                    case FLYING_FORWARD:
                        lineStatus += "FLFW ";
                        break;
                    case DEBOARDING:
                        lineStatus += "DRPP ";
                        break;
                    case FLYING_BACK:
                        lineStatus += "FLBK ";
                        break;
                }

                switch (hostessState){
                    case READY_TO_FLY:
                        lineStatus += "RDTF ";
                        break;
                    case CHECK_PASSENGER:
                        lineStatus += "CKPS ";
                        break;
                    case WAIT_FOR_PASSENGER:
                        lineStatus += "WTPS ";
                        break;
                    case WAIT_FOR_NEXT_FLIGHT:
                        lineStatus += "WTFL ";
                        break;
                }

                for (int i = 0; i < SimulPar.N; i++) {
                    switch (passengerStates[i]){
                        case IN_QUEUE:
                            lineStatus += "INQE ";
                            break;
                        case IN_FLIGHT:
                            lineStatus += "INFL ";
                            break;
                        case AT_DESTINATION:
                            lineStatus += "ATDS ";
                            break;
                        case GOING_TO_AIRPORT:
                            lineStatus += "GTAP ";
                            break;
                    }
                }

                lineStatus += String.valueOf(InQ) + " ";
                lineStatus += String.valueOf(InF) + " ";
                lineStatus += String.valueOf(PTAL);

                pw.println(lineStatus);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
