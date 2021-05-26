package ServerSide.sharedRegions;

import ServerSide.main.*;
import ClientSide.entities.*;
import commonInfrastructures.*;

/**
 *  Interface to the Plane
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Plane and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PlaneInterface {
    /**
     *  Reference to the Plane.
     */

    private final Plane plane;

    /**
     *  Instantiation of an interface to the Plane.
     *
     *    @param plane reference to the Plane
     */

    public PlaneInterface (Plane plane)
    {
        this.plane = plane;
    }

    /**
     *  Processing of the incoming messages.
     *
     *  Validation, execution of the corresponding method and generation of the outgoing message.
     *
     *    @param inMessage service request
     *    @return service reply
     *    @throws MessageException if the incoming message is not valid
     */

    public Message processAndReply (Message inMessage) throws MessageException
    {
        Message outMessage = null;                                     // mensagem de resposta

        /* validation of the incoming message */

        switch (inMessage.getMsgType ()) {
            case WAIT_FOR_ALL_IN_BOARD: case FLY_TO_DESTINATION_POINT: case FLY_TO_DEPARTURE_POINT:
                if ((inMessage.getState() < PilotStates.AT_TRANSFER_GATE.value) || (inMessage.getState() > PilotStates.FLYING_BACK.value))
                    throw new MessageException ("Invalid Pilot state!", inMessage);
                break;
            case INFORM_PLANE_READY_TO_TAKEOFF:
                if ((inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT.value) || (inMessage.getState() > HostessStates.READY_TO_FLY.value))
                    throw new MessageException ("Invalid Hostess state!", inMessage);
                break;
            case WAIT_FOR_END_OF_FLIGHT:
                if ((inMessage.getState() < PassengerStates.GOING_TO_AIRPORT.value) || (inMessage.getState() > PassengerStates.AT_DESTINATION.value))
                    throw new MessageException ("Invalid Passenger state!", inMessage);
                if ((inMessage.getPassId() < 0) || (inMessage.getPassId() >= SimulPar.N))
                    throw new MessageException ("Invalid Passenger id!", inMessage);
                break;
            default:
                throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ()) {
            case WAIT_FOR_ALL_IN_BOARD:
                ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());
                plane.waitForAllInBoard();
                outMessage = new Message(MessageType.DONE_WFAIB, ((Pilot) Thread.currentThread()).getPilotState());
                break;
            case FLY_TO_DESTINATION_POINT:
                ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());
                plane.flyToDestinationPoint();
                outMessage = new Message(MessageType.DONE_FTDesP, ((Pilot) Thread.currentThread()).getPilotState());
                break;
            case FLY_TO_DEPARTURE_POINT:
                ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());
                plane.flyToDeparturePoint();
                outMessage = new Message(MessageType.DONE_FTDepP, ((Pilot) Thread.currentThread()).getPilotState());
                break;
            case INFORM_PLANE_READY_TO_TAKEOFF:
                ((Hostess) Thread.currentThread()).setHostessState(inMessage.getState());
                plane.informPlaneReadyToTakeOff();
                outMessage = new Message(MessageType.DONE_IPRTT, ((Hostess) Thread.currentThread()).getHostessState());
                break;
            case WAIT_FOR_END_OF_FLIGHT:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setPassengerState(inMessage.getState());
                plane.waitForEndOfFlight();
                outMessage = new Message(MessageType.DONE_WFEOF);
                break;
        }

        return (outMessage);
    }
}

