package ClientSide.stub;

import commonInfrastructures.*;
import ClientSide.entities.*;

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

        outMessage = new Message(MessageType.INFORM_PLANE_READY_FOR_BOARDING);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_IPRFB){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getPilotState());
        // Output message instantiation
        outputMessage = new Message(MessageTypes.INFORM_PLANE_READY_FOR_BOARDING);
        outputMessage.setrAttributesSize(1);
        outputMessage.setrAttributesType(new int[]{ParameterTypes.INTEGER});
        outputMessage.setrAttributes(new Object[]{((Pilot) Thread.currentThread()).getPilotState()});
        com.writeObject(outputMessage);
        inputMessage = (Message) com.readObject(); // check receiving message

        if (inputMessage.getMessageType() != MessageTypes.RETURN || inputMessage.getrAttributesSize() != 1 || inputMessage.getrAttributesType()[0] != ParameterTypes.INTEGER) {
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setPilotState((int) inputMessage.getrAttributes()[0]);
        com.close();    // close the connection
    }
}
