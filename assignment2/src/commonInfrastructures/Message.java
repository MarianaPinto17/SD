package commonInfrastructures;

import ServerSide.entities.HostessStates;
import ServerSide.entities.PassengerStates;
import ServerSide.entities.PilotStates;

public class Message {
    /**
     *  Message type.
     */

    private MessageType msgType;

    /**
     * File Name
     */
    private String filename;

    /**
     * Pilot State
     */
    private PilotStates pilotState;

    /**
     * Hostess State
     */
    private HostessStates hostessState;

    /**
     * Passenger State
     */
    private PassengerStates passengerState;

    /**
     * Passenger Id
     */
    private int passId;

    /**
     * Number os passenger per flight
     */
    private int[] npassFlight;

    /**
     * if a plane is ready to take off.
     */
    private boolean informPlane;

    /**
     * state number (can be for Pilot, Hostess or Passenger)
     */
    private int state;

    /**
     *  Message instantiation (form 1).
     *
     *  @param msgType message type
     */

    public Message(MessageType msgType) {
        this.msgType = msgType;
    }


    /**
     * Message instantiation (form 2).
     *
     * @param msgType message type
     * @param pilotState pilot state
     */
    public Message(MessageType msgType, PilotStates pilotState) {
        this.msgType = msgType;
        this.pilotState = pilotState;
    }

    /**
     * Message instantiation (form 3).
     *
     * @param msgType message type
     * @param hostessState hostess state
     */
    public Message(MessageType msgType, HostessStates hostessState) {
        this.msgType = msgType;
        this.hostessState = hostessState;
    }

    /**
     * Message instantiation (form 4).
     *
     * @param msgType message type
     * @param hostessState hostess state
     * @param passId passenger id
     */
    public Message(MessageType msgType, HostessStates hostessState, int passId) {
        this.msgType = msgType;
        this.hostessState = hostessState;
        this.passId = passId;
    }

    /**
     * Message instantiation (form 5).
     *
     * @param msgType message state
     * @param passengerState passenger state
     * @param passId passenger id
     */
    public Message(MessageType msgType, PassengerStates passengerState, int passId) {
        this.msgType = msgType;
        this.passengerState = passengerState;
        this.passId = passId;
    }

    /**
     * Message instantiation (form 6).
     *
     * @param msgType message type
     * @param npassFlight number of passengers per flight
     */
    public Message(MessageType msgType, int[] npassFlight) {
        this.msgType = msgType;
        this.npassFlight = npassFlight;
    }

    /**
     * Message instantiation (form 7).
     *
     * @param msgType message type
     * @param informPlane if a plane is ready to take off.
     */
    public Message(MessageType msgType, boolean informPlane) {
        this.msgType = msgType;
        this.informPlane = informPlane;
    }

    /**
     * Messafe instantiation (form 8).
     *
     * @param msgType message type
     * @param fileName file name of log file
     */
    public Message(MessageType msgType, String fileName) {
        this.msgType = msgType;
        this.filename = fileName;
    }

    /**
     * Messafe instantiation (form 9).
     *
     * @param msgType message type
     * @param state state number (can be for Pilot, Hostess or Passenger)
     */
    public Message(MessageType msgType, int state) {
        this.msgType = msgType;
        this.state = state;
    }

    /**
     * Messafe instantiation (form 10).
     *
     * @param msgType message type
     * @param state state number (can be for Pilot, Hostess or Passenger)
     * @param Id number corresponding to passenger
     */
    public Message(MessageType msgType, int state, int Id) {
        this.msgType = msgType;
        this.state = state;
        this.passId = Id;
    }

    /**
     * Getting MessageType
     * @return message type
     */
    public MessageType getMsgType() {
        return msgType;
    }

    /**
     * Getting Pilot State
     * @return pilot state
     */
    public int getPilotState() {
        return pilotState.value;
    }

    /**
     * Getting Hostess State
     * @return hostess state
     */
    public int getHostessState() {
        return hostessState.value;
    }

    /**
     * Getting Passenger state
     * @return
     */
    public int getPassengerState() {
        return passengerState.value;
    }

    /**
     * Getting passenger id
     * @return passenger id
     */
    public int getPassId() {
        return passId;
    }

    /**
     * Getting Number of Passengers per flight
     * @return Number of Passengers per flight
     */
    public int[] getNpassFlight() {
        return npassFlight;
    }

    /**
     * Getting if a plane is ready to take off.
     * @return if a plane is ready to take off.
     */
    public boolean isInformPlane() {
        return informPlane;
    }

    /**
     * Getting file name of log
     * @return file name of logfile
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Getting number corresponding to state
     * @return number corresponding to state
     */
    public int getState() {
        return state;
    }
}
