package sharedRegions;

import entities.*;
import main.*;

import java.io.IOException;
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
     * Current state of the pilot.
     */

    private PilotStates pilotState;

    /**
     * Current state of the hostess.
     */

    private HostessStates hostessState;

    /**
     * Current state of each passenger (in array).
     */

    private final PassengerStates[] passengerStates;

    /**
     * Number of passengers presently forming a queue to board the plane.
     */

    private int InQ;

    /**
     * Number of passengers in the plane.
     */

    private int InF;

    /**
     * Number of passengers that have already arrived at their destination
     */

    private int PTAL;


    /**
     * Ready to fly
     */
    private boolean readyToFly;

    /**
     *
     */
    public GeneralRepository(String logFileName){
        if ((logFileName == null) || Objects.equals (logFileName, ""))
            this.logFileName = "logger";
        else this.logFileName = logFileName;
        pilotState = PilotStates.AT_TRANSFER_GATE;
        hostessState = HostessStates.WAIT_FOR_FLIGHT;
        passengerStates = new PassengerStates[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++)
            passengerStates[i] = PassengerStates.GOING_TO_AIRPORT;
        InQ = 0;
        InF = 0;
        PTAL = 0;
        readyToFly = false;

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
                    case WAIT_FOR_FLIGHT:
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

    /**
     *   Set Pilot state.
     *
     *     @param state pilot state
     */

    public synchronized void setPilotState (PilotStates state)
    {
        this.pilotState = state;
        reportStatus ();
    }

    /**
     *   Set Hostess state.
     *
     *     @param state hostess state
     */

    public synchronized void setHostessState (HostessStates state)
    {
        this.hostessState = state;
        reportStatus ();
    }
    /**
     *   Set Passenger state.
     *
     *     @param id passenger id
     *     @param state passenger state
     */

    public synchronized void setPassengerState (int id, PassengerStates state)
    {
        this.passengerStates[id] = state;
        reportStatus ();
    }

    public boolean isReadyToFly() {
        return readyToFly;
    }

    public void setReadyToFly(boolean readyToFly) {
        this.readyToFly = readyToFly;
    }

    public int getInQ() {
        return InQ;
    }

    public void setInQ(int inQ) {
        InQ = inQ;
    }

    public int getInF() {
        return InF;
    }

    public void setInF(int inF) {
        InF = inF;
    }

    public int getPTAL() {
        return PTAL;
    }

    public void setPTAL(int PTAL) {
        this.PTAL = PTAL;
    }
}
