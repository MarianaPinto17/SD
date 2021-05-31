package ClientSide.stub;

import commonInfrastructures.*;
import ClientSide.entities.*;

/**
 *
 * @author Mariana Pinto
 * @author André Alves
 */
public class PlaneStub {

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
    public PlaneStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Pilot function - waits for all passengers get on board.
     */
    public void waitForAllInBoard(){
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

        outMessage = new Message(MessageType.WAIT_FOR_ALL_IN_BOARD, ((Pilot) Thread.currentThread()).getPilotState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFAIB){
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
     * Pilot function - Pilot flies to destination.
     */
    public void flyToDestinationPoint(){
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

        outMessage = new Message(MessageType.FLY_TO_DESTINATION_POINT, ((Pilot) Thread.currentThread()).getPilotState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_FTDesP){
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
     * Passenger function - passenger waits for the flight to end.
     */
    public void waitForEndOfFlight(){
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

        outMessage = new Message(MessageType.WAIT_FOR_END_OF_FLIGHT,((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFEOF){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        com.close();
    }

    /**
     * Pilot function - Pilot flies back to departure.
     */
    public void flyToDeparturePoint(){
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

        outMessage = new Message(MessageType.FLY_TO_DEPARTURE_POINT, ((Pilot) Thread.currentThread()).getPilotState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_FTDepP){
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
     * Hostess Function - if no passenger at queue or plane is full awakes pilot.
     */
    public void informPlaneReadyToTakeOff(){
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

        outMessage = new Message(MessageType.INFORM_PLANE_READY_TO_TAKEOFF, ((Hostess) Thread.currentThread()).getHostessState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_IPRTT){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT || inMessage.getState() > HostessStates.READY_TO_FLY){
            System.out.println("Invalid return hostess state!!");
            System.exit(1);
        }

        ((Hostess) Thread.currentThread()).setHostessState(inMessage.getState());

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
