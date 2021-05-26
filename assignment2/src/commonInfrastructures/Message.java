package commonInfrastructures;

public class Message {
    /**
     *  Message type.
     */

    private MessageType msgType = null;

    /**
     * File Name
     */
    private String filename = null;

    /**
     * Passenger Id
     */
    private int passId = -1;

    /**
     * Number os passenger per flight
     */
    private int[] npassFlight = null;

    /**
     * if a plane is ready to take off.
     */
    private boolean informPlane = false;

    /**
     * state number (can be for Pilot, Hostess or Passenger)
     */
    private int state = -1;

    /**
     * boolean
     */
    private boolean boolState = false;
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
     * @param npassFlight number of passengers per flight
     */
    public Message(MessageType msgType, int[] npassFlight) {
        this.msgType = msgType;
        this.npassFlight = npassFlight;
    }

    /**
     * Message instantiation (form 3).
     *
     * @param msgType message type
     * @param informPlane if a plane is ready to take off.
     */
    public Message(MessageType msgType, boolean informPlane) {
        this.msgType = msgType;
        this.informPlane = informPlane;
    }

    /**
     * Messafe instantiation (form 4).
     *
     * @param msgType message type
     * @param fileName file name of log file
     */
    public Message(MessageType msgType, String fileName) {
        this.msgType = msgType;
        this.filename = fileName;
    }

    /**
     * Messafe instantiation (form 5).
     *
     * @param msgType message type
     * @param state state number (can be for Pilot, Hostess or Passenger) or passenger id
     */
    public Message(MessageType msgType, int state) {
        this.msgType = msgType;
        this.state = state;
    }

    /**
     * Messafe instantiation (form 6).
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
