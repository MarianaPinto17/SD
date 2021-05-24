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
    public HostessStates getHostessState() {
        return hostessState;
    }

    /**
     * Getting Passenger state
     * @return
     */
    public PassengerStates getPassengerState() {
        return passengerState;
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
}
