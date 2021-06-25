package ServerSide.objects;

import ClientSide.entities.*;
import ServerSide.main.*;
import interfaces.GeneralRepositoryInterface;

import java.rmi.*;
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
 * @author André Alves
 * @author Mariana Pinto
 */


public class GeneralRepository implements GeneralRepositoryInterface {
    /**
     *  Name of the logging file.
     */

    private String logFileName;

    /**
     * Current state of the pilot.
     */

    private int pilotState;

    /**
     * Current state of the hostess.
     */

    private int hostessState;

    /**
     * Current state of each passenger (in array).
     */

    private final int[] passengerStates;

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
     * number of passengers in each flight.
     */
    private int npassengers[];

    /**
     * General Repository constructor.
     */
    public GeneralRepository(){
        this.logFileName = "log.txt";
        pilotState = PilotStates.AT_TRANSFER_GATE;
        hostessState = HostessStates.WAIT_FOR_FLIGHT;
        passengerStates = new int[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++)
            passengerStates[i] = PassengerStates.GOING_TO_AIRPORT;
        InQ = 0;
        InF = 0;
        PTAL = 0;
        arrivedAtDest = false;
        nFlights = 0;
        emptyPlaneDest = false;
        this.npassengers=new int[7];

        reportInitialStatus();
    }

    /**
     *   Operation initialization of simulation.
     *
     *     @param logFileName name of the logging file
     */

    @Override
    public synchronized void initSimul (String logFileName) throws RemoteException {
        if (!Objects.equals (logFileName, ""))
            this.logFileName = logFileName;
        reportInitialStatus ();
    }

    /**
     *   Operation server shutdown.
     */

    @Override
    public synchronized void shutdown () throws RemoteException {
        GeneralRepositoryMain.shutdown();
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

    /**
     * Write the status of the entities to the logging file.
     *
     * Internal Operation.
     */
    private synchronized void reportStatus(){
        String lineStatus = "";
        try {
            FileWriter fw = new FileWriter(logFileName, true);

            try (PrintWriter pw = new PrintWriter(fw)) {
                switch (pilotState) {
                    case 0:
                        lineStatus += "ATRG ";
                        break;
                    case 1:
                        lineStatus += "RDFB ";
                        break;
                    case 2:
                        lineStatus += "WTFB ";
                        break;
                    case 3:
                        lineStatus += "FLFW ";
                        break;
                    case 4:
                        lineStatus += "DRPP ";
                        break;
                    case 5:
                        lineStatus += "FLBK ";
                        break;
                    default:
                        lineStatus += "ERRO ";
                        break;
                }

                switch (hostessState){
                    case 3:
                        lineStatus += "RDTF ";
                        break;
                    case 2:
                        lineStatus += "CKPS ";
                        break;
                    case 1:
                        lineStatus += "WTPS ";
                        break;
                    case 0:
                        lineStatus += "WTFL ";
                        break;
                    default:
                        lineStatus += "ERRO ";
                        break;
                }

                for (int i = 0; i < SimulPar.N; i++) {
                    switch (passengerStates[i]){
                        case 1:
                            lineStatus += "INQE ";
                            break;
                        case 2:
                            lineStatus += "INFL ";
                            break;
                        case 3:
                            lineStatus += "ATDS ";
                            break;
                        case 0:
                            lineStatus += "GTAP ";
                            break;
                        default:
                            lineStatus += "ERRO ";
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

    @Override
    public synchronized void setPilotState (int state) throws RemoteException {
        this.pilotState = state;

        if(state == PilotStates.DEBOARDING || state == PilotStates.READY_FOR_BOARDING || state == PilotStates.FLYING_BACK){
            String lineStatus;
            switch (pilotState){
                case 4:
                    lineStatus = "arrived.";
                    break;
                case 1:
                    lineStatus = "boarding started.";
                    nFlights++;
                    break;
                case 5:
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

    @Override
    public synchronized void setHostessState (int state) throws RemoteException {
        this.hostessState = state;
        if (state == HostessStates.READY_TO_FLY) {
            String lineStatus = "departed with "+InF+" passengers.";
            setNpassengers(nFlights, InF);
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
     * Set hostess State when the passenger is checked.
     * @param state hostess state
     * @param id id passenger
     * @throws RemoteException
     */
    @Override
    public synchronized void setHostessState (int state, int id) throws RemoteException {
        this.hostessState = state;
        if (state == HostessStates.CHECK_PASSENGER) {
            InQ -= 1;
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
     *   Set Passenger state.
     *
     *     @param id passenger id
     *     @param state passenger state
     */

    @Override
    public synchronized void setPassengerState (int id, int state) throws RemoteException {
        this.passengerStates[id] = state;
        switch (passengerStates[id]) {
            case 1:
                InQ += 1;
                break;
            case 2:
                InF += 1;
                break;
            case 3:
                InF -= 1;
                PTAL += 1;
                break;
        }
        reportStatus ();
    }

    /**
     * Close of operations.
     * @throws RemoteException
     */
    @Override
    public synchronized void sumUp() throws RemoteException {
        try {
            FileWriter fw = new FileWriter(logFileName, true);

            try (PrintWriter pw = new PrintWriter(fw)) {
                for (int i = 0; i < npassengers.length; i++) {
                    if (npassengers[i] != 0) {
                        pw.print("\nFlight " + (i+1) + " transported " + npassengers[i] + " passengers");
                    }
                }
                pw.println(".");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getters and setters.
     */

    /**
     * Number of passengers in flight.
     * @return number of passengers inside the plane.
     */
    @Override
    public int getInF() throws RemoteException {
        return InF;
    }

    /**
     * Checks the number of passengers that arrived at the destination airport.
     * @return the number of passengers that arrived at the destination airport.
     */
    @Override
    public int getPTAL() throws RemoteException {
        return PTAL;
    }


    /**
     * Sets the status of the plane to empty when all the passengers left the plane at the destination airport.
     * @param emptyPlaneDest changes status to emptyPlaneDest.
     */
    @Override
    public void setEmptyPlaneDest(boolean emptyPlaneDest) throws RemoteException {
        this.emptyPlaneDest = emptyPlaneDest;
    }

    /**
     * Checks if plane is empty at the destination airport.
     * @return plane status at the destination airport.
     */
    public boolean isEmptyPlaneDest() {
        return emptyPlaneDest;
    }

    /**
     * Set the number of flights occurring or occurred.
     * @param nFlights number of flights
     */
    public void setnFlights(int nFlights) {
        this.nFlights = nFlights;
    }

    /**
     * Get the number of flights occurring or occurred.
     * @return number of flights occurrng or occurred
     */
    public int getnFlights() {
        return nFlights;
    }

    /**
     * sets the number of passengers in each flight.
     * @param index index of the flight
     * @param npassengers number the passenger of the flight
     */
    public void setNpassengers(int index, int npassengers) {
        this.npassengers[index-1] = npassengers;
    }


}
