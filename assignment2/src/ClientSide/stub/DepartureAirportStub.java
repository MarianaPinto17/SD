package ClientSide.stub;

import commonInfrastructures.*;
import ClientSide.entities.*;

/**
 *
 * @author Mariana Pinto
 * @author André Alves
 */
public class DepartureAirportStub {
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
    public DepartureAirportStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Pilot function - says to hostess boarding can start.
     */
    public void informPlaneReadyForBoarding() {
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
        outMessage = new Message(MessageType.INFORM_PLANE_READY_FOR_BOARDING, ((Pilot) Thread.currentThread()).getPilotState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_IPRFB || inMessage.getState() < PilotStates.AT_TRANSFER_GATE || inMessage.getState() > PilotStates.FLYING_BACK){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());

        com.close();
    }

    /**
     * Hostess Function - prepare to start boarding passengers on the plane.
     */
    public void prepareForPassBoarding() {
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

        System.out.println("Hostess state: "+((Hostess) Thread.currentThread()).getHostessState());

        outMessage = new Message(MessageType.PREPARE_FOR_PASS_BOARDING, ((Hostess) Thread.currentThread()).getHostessState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_PFPB || inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT || inMessage.getState() > HostessStates.READY_TO_FLY) {
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Hostess) Thread.currentThread()).setHostessState(inMessage.getState());

        com.close();
    }

    /**
     * Passenger function - passenger waits in queue to board the plane.
     */
    public void waitInQueue(){
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

        outMessage = new Message(MessageType.WAIT_IN_QUEUE, ((Passenger) Thread.currentThread()).getPassengerState(), ((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WIQ){
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
     * Hostess function - if a passenger in queue checks documents, waits for passenger to show documents.
     */
    public void checkDocuments(){
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

        outMessage = new Message(MessageType.CHECK_DOCUMENTS, ((Hostess) Thread.currentThread()).getHostessState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_CD){
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
     * Passenger function - passenger shows documents to hostess.
     */
    public void showDocuments(){
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

        outMessage = new Message(MessageType.SHOW_DOCUMENTS, ((Passenger) Thread.currentThread()).getPassengerState(), ((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_SD){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        com.close();
    }

    /**
     *  Hostess function - hostess waits for passengers if plane not full and not min and passenger in queue.
     */
    public void waitForNextPassenger(){
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

        outMessage = new Message(MessageType.WAIT_FOR_NEXT_PASSENGER, ((Hostess) Thread.currentThread()).getHostessState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFNP){
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
     * Passenger function - passenger boards the plane.
     */
    public void boardThePlane(){
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

        outMessage = new Message(MessageType.BOARD_THE_PLANE,  ((Passenger) Thread.currentThread()).getPassengerState(), ((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_BTP){
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
     * Hostess function - hostess waits for the next flight of the day.
     */
    public void waitForNextFlight(){
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

        outMessage = new Message(MessageType.WAIT_FOR_NEXT_FLIGHT, ((Hostess) Thread.currentThread()).getHostessState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFNF){
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
     * Pilot function - when the pilot parks the plane at the Transfer gate.
     */
    public void parkAtTransferGate(){
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

        outMessage = new Message(MessageType.PARK_AT_TRANSFER_GATE, ((Pilot) Thread.currentThread()).getPilotState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_PATG){
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
     * Checks if a plane is ready for flying.
     * @return true if the boarding is over.
     */
    public boolean isInformPlane() {
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

        outMessage = new Message(MessageType.IS_INFORM_PLANE);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_IIP){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        boolean informPlane = inMessage.isInformPlane();

        com.close();

        return informPlane;

    }
}
