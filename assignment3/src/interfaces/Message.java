package interfaces;

import java.io.Serializable;

/**
 *   Internal structure of the exchanged messages.
 *
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class Message implements Serializable {

    private static final long serialVersionUID = 7973215442438841515L;

    /**
     * File Name.
     */
    private String filename = null;

    /**
     * Passenger Id.
     */
    private int passId = -1;

    /**
     * State number (can be for Pilot, Hostess or Passenger).
     */
    private int state = -1;

    /**
     * Boolean state.
     */
    private boolean boolState = false;


    /**
     * Messafe instantiation (form 1).
     *
     * @param state state number (can be for Pilot, Hostess or Passenger)
     * @param Id number corresponding to passenger
     */
    public Message(int state, int Id) {
        this.state = state;
        this.passId = Id;
    }

    /**
     * Messafe instantiation (form 2).
     *
     * @param state state number (can be for Pilot, Hostess or Passenger)
     * @param boolState boolean value.
     */
    public Message(int state, boolean boolState) {
        this.state = state;
        this.boolState = boolState;
    }

    /**
     * Getting passenger id.
     * @return passenger id
     */
    public int getPassId() {
        return passId;
    }

    /**
     * Getting the boolean variable.
     * @return boolean variable.
     */
    public boolean boolState() {
        return boolState;
    }

    /**
     * Getting file name of log.
     * @return file name of logfile
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Getting number corresponding to state.
     * @return number corresponding to state
     */
    public int getState() {
        return state;
    }

}
