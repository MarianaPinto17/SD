package ServerSide.sharedRegions;

import ServerSide.entities.*;
import ServerSide.main.*;
import commonInfrastructures.*;

/**
 *  Interface to the departure Airport
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    departure Airport and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class DepartureAirportInterface {
    /**
     *  Reference to the departure Airport.
     */

    private final DepartureAirport depAir;

    /**
     *  Instantiation of an interface to the departure Airport.
     *
     *    @param depAir reference to the departure Airport
     */

    public DepartureAirportInterface (DepartureAirport depAir)
    {
        this.depAir = depAir;
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
            case INFORM_PLANE_READY_FOR_BOARDING: case PARK_AT_TRANSFER_GATE:
                if ((inMessage.getState() < PilotStates.AT_TRANSFER_GATE) || (inMessage.getState() > PilotStates.FLYING_BACK))
                    throw new MessageException ("Invalid Pilot state!", inMessage);
                break;
            case PREPARE_FOR_PASS_BOARDING: case CHECK_DOCUMENTS: case WAIT_FOR_NEXT_PASSENGER: case WAIT_FOR_NEXT_FLIGHT:
                if ((inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT) || (inMessage.getState() > HostessStates.READY_TO_FLY))
                    throw new MessageException ("Invalid Hostess state!", inMessage);
                break;
            case WAIT_IN_QUEUE: case SHOW_DOCUMENTS: case BOARD_THE_PLANE:
                if ((inMessage.getState() < PassengerStates.GOING_TO_AIRPORT) || (inMessage.getState() > PassengerStates.AT_DESTINATION))
                    throw new MessageException ("Invalid Passenger state!", inMessage);
                if ((inMessage.getPassId() < 0) || (inMessage.getPassId() >= SimulPar.N))
                    throw new MessageException ("Invalid Passenger id!", inMessage);
                break;
            case IS_INFORM_PLANE:
                break;
            default:
                throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ()) {
            case INFORM_PLANE_READY_FOR_BOARDING:
                ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());
                depAir.informPlaneReadyForBoarding();
                outMessage = new Message(MessageType.DONE_IPRFB, ((Pilot) Thread.currentThread()).getPilotState());
                break;
            case PREPARE_FOR_PASS_BOARDING:
                ((Hostess) Thread.currentThread()).setHostessState(inMessage.getState());
                boolean endOfLife = depAir.prepareForPassBoarding();
                outMessage = new Message(MessageType.DONE_PFPB, ((Hostess) Thread.currentThread()).getHostessState(), endOfLife);
                break;
            case WAIT_IN_QUEUE:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setPassengerState(inMessage.getState());
                depAir.waitInQueue();
                outMessage = new Message(MessageType.DONE_WIQ, ((Passenger) Thread.currentThread()).getPassengerState(), ((Passenger) Thread.currentThread()).getID());
                break;
            case CHECK_DOCUMENTS:
                ((Hostess) Thread.currentThread()).setHostessState(inMessage.getState());
                depAir.checkDocuments();
                outMessage = new Message (MessageType.DONE_CD, ((Hostess) Thread.currentThread()).getHostessState());
                break;
            case SHOW_DOCUMENTS:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setPassengerState(inMessage.getState());
                depAir.showDocuments();
                outMessage = new Message(MessageType.DONE_SD);
                break;
            case WAIT_FOR_NEXT_PASSENGER:
                ((Hostess) Thread.currentThread()).setHostessState(inMessage.getState());
                depAir.waitForNextPassenger();
                outMessage = new Message (MessageType.DONE_WFNP, ((Hostess) Thread.currentThread()).getHostessState());
                break;
            case BOARD_THE_PLANE:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setPassengerState(inMessage.getState());
                depAir.boardThePlane();
                outMessage = new Message(MessageType.DONE_BTP, ((Passenger) Thread.currentThread()).getPassengerState(), ((Passenger) Thread.currentThread()).getID());
                break;
            case WAIT_FOR_NEXT_FLIGHT:
                ((Hostess) Thread.currentThread()).setHostessState(inMessage.getState());
                depAir.waitForNextFlight();
                outMessage = new Message (MessageType.DONE_WFNF, ((Hostess) Thread.currentThread()).getHostessState());
                break;
            case PARK_AT_TRANSFER_GATE:
                ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());
                depAir.parkAtTransferGate();
                outMessage = new Message(MessageType.DONE_PATG, ((Pilot) Thread.currentThread()).getPilotState());
                break;
            case IS_INFORM_PLANE:
                outMessage = new Message(MessageType.DONE_IIP, depAir.isInformPlane());
                break;
            case SHUTDOWN:
                PlaneMain.waitConnection = false;
                DestinationAirportMain.waitConnection = false;
                DepartureAirportMain.waitConnection = false;
                outMessage = new Message(MessageType.DONE_SHUTDOWN);
                break;
        }

        return (outMessage);
    }
}

