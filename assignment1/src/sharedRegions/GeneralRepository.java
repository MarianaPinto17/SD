package sharedRegions;

import entities.*;
import main.*;

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
     * Arrived at destination airport
     */

    private boolean arrivedAtDest;

    /**
     * Number of flight.
     */

    private int nFlights;

    /**
     * True when Plane is empty in destination.
     */

    private boolean emptyPlaneDest;

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
        arrivedAtDest = false;
        readyToFly = false;
        nFlights = 0;
        emptyPlaneDest = false;

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
                pw.printf("%85s","Airlift - Description of the internal state");
                pw.println("\n");
                pw.println(" PT   HT   P00  P01  P02  P03  P04  P05  P06  P07  P08  P09  P10  P11  P12  P13  P14  P15  P16  P17  P18  P19  P20 InQ InF PTAL");
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

                lineStatus += String.format("%3d",InQ);
                lineStatus += String.format("%4d",InF);
                lineStatus += String.format("%4d",PTAL);

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

        if(state == PilotStates.DEBOARDING || state == PilotStates.READY_FOR_BOARDING || state == PilotStates.FLYING_BACK){
            String lineStatus;
            switch (state){
                case DEBOARDING:
                    lineStatus = "arrived.";
                    break;
                case READY_FOR_BOARDING:
                    lineStatus = "boarding started.";
                    break;
                case FLYING_BACK:
                    lineStatus = "returning.";
                    break;
                default:
                    lineStatus = "";
            }
            try {
                FileWriter fw = new FileWriter(logFileName, true);

                try (PrintWriter pw = new PrintWriter(fw)) {
                    pw.println("\nFlight "+nFlights+": "+lineStatus);

                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
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
        if (state == HostessStates.READY_TO_FLY) {
            String lineStatus = "departed with "+InF+" passengers.";
            try {
                FileWriter fw = new FileWriter(logFileName, true);

                try (PrintWriter pw = new PrintWriter(fw)) {
                    pw.println("\nFlight "+nFlights+": "+lineStatus);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reportStatus ();
    }

    public synchronized void setHostessState (HostessStates state, int id)
    {
        this.hostessState = state;
        if (state == HostessStates.CHECK_PASSENGER) {
            String lineStatus = "passenger " + id + " checked.";
            try {
                FileWriter fw = new FileWriter(logFileName, true);

                try (PrintWriter pw = new PrintWriter(fw)) {
                    pw.println("\nFlight "+nFlights+": "+lineStatus);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reportStatus ();
    }

    /**
     *
     *
     */
    public synchronized void sumUp(int[] npassFlight){
        try {
            FileWriter fw = new FileWriter(logFileName, true);

            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.println();
                for(int i=0;i<npassFlight.length;i++){
                    if(npassFlight[i]!=0){
                        pw.println("Flight "+ i + " transported "+ npassFlight[i]  +" passengers");
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
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

    /**
     * Getters and setters.
     */

    /**
     * checks if a plane is ready to fly
     * @return true if the plane is ready to fly
     */
    public boolean isReadyToFly() {
        return readyToFly;
    }

    /**
     * Sets the plane ready to fly.
     * @param readyToFly changes status of readyToFly.
     */
    public void setReadyToFly(boolean readyToFly) {
        this.readyToFly = readyToFly;
    }

    /**
     * checks the number of passengers in queue.
     * @return the number of passengers in queue.
     */
    public int getInQ() {
        return InQ;
    }

    /**
     * sets the number of passengers in queue.
     * @param inQ changes status of inQ.
     */
    public void setInQ(int inQ) {
        InQ = inQ;
    }

    /**
     * Number of passengers in flight.
     * @return number of passengers inside the plane.
     */
    public int getInF() {
        return InF;
    }

    /**
     * sets the number of passengers inside de plane.
     * @param inF changes status of inF.
     */
    public void setInF(int inF) {
        InF = inF;
    }

    /**
     * checks the number of passengers that arrived at the destination airport.
     * @return the number of passengers that arrived at the destination airport.
     */
    public int getPTAL() {
        return PTAL;
    }

    /**
     * sets the number of passengers that arrived at the destination airport.
     * @param PTAL changes status of PTAL.
     */
    public void setPTAL(int PTAL) {
        this.PTAL = PTAL;
    }

    /**
     * checks if the plane is at destination.
     * @return true if the plane is at destination.
     */
    public boolean isArrivedAtDest() {
        return arrivedAtDest;
    }

    /**
     * sets the status of the plane to Arrived at destination or not.
     * @param arrivedAtDest changes status of arrivedAtDest.
     */
    public void setArrivedAtDest(boolean arrivedAtDest) {
        this.arrivedAtDest = arrivedAtDest;
    }

    /**
     * sets the status of the plane to empty when all the passengers left the plane at the destination airport.
     * @param emptyPlaneDest changes status to emptyPlaneDest.
     */
    public void setEmptyPlaneDest(boolean emptyPlaneDest) {
        this.emptyPlaneDest = emptyPlaneDest;
    }

    public boolean isEmptyPlaneDest() {
        return emptyPlaneDest;
    }

    public void setnFlights(int nFlights) {
        this.nFlights = nFlights;
    }

    public int getnFlights() {
        return nFlights;
    }
}
