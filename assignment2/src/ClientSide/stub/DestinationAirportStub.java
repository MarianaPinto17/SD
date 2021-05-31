package ClientSide.stub;

import commonInfrastructures.*;
import ClientSide.entities.*;

/**
 *
 * @author Mariana Pinto
 * @author André Alves
 */
public class DestinationAirportStub {
    /**
     *  Name of the platform where is located the Departure Airport server.
     */
    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */
    private int serverPortNumb;

    /**
     *   Instantiation of a stub to the Departure Airport.
     *
     *     @param serverHostName name of the platform where is located the Departure Airport server
     *     @param serverPortNumb port number for listening to service requests
     */
    public DestinationAirportStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Pilot function - pilot announces that the plane arrived at destination.
     */
    public void announceArrival(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.ANNOUNCE_ARRIVAL, ((Pilot) Thread.currentThread()).getPilotState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_AA){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getState() < PilotStates.AT_TRANSFER_GATE || inMessage.getState() > PilotStates.FLYING_BACK){
            System.out.println("Invalid return pilot state!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());

        com.close();
    }

    /**
     * Passenger function - when the plane arrives at destination the passenger exits the plane.
     */
    public void leaveThePlane(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.LEAVE_THE_PLANE, ((Passenger) Thread.currentThread()).getPassengerState(), ((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_LTP){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getPassId() != ((Passenger) Thread.currentThread()).getID()){
            System.out.println("Invalid return passenger id!!");
            System.exit(1);
        }
        if (inMessage.getState() < PassengerStates.GOING_TO_AIRPORT || inMessage.getState() > PassengerStates.AT_DESTINATION){
            System.out.println("Invalid return passenger state!!");
            System.exit(1);
        }

        ((Passenger) Thread.currentThread()).setPassengerState(inMessage.getState());

        com.close();
    }

    /**
     * Shutdown message to remote object.
     */
    public void shutdown() {
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.SHUTDOWN);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_SHUTDOWN){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        com.close();
    }
}
