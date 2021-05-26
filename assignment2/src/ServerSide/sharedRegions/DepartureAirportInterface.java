package ServerSide.sharedRegions;
import ServerSide.main.*;
import ServerSide.entities.*;
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
                if ((inMessage.getState() < PilotStates.AT_TRANSFER_GATE.value) || (inMessage.getState() > PilotStates.FLYING_BACK.value))
                    throw new MessageException ("Invalid Pilot state!", inMessage);
                break;
            case PREPARE_FOR_PASS_BOARDING: case CHECK_DOCUMENTS: case WAIT_FOR_NEXT_PASSENGER: case WAIT_FOR_NEXT_FLIGHT:
                if ((inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT.value) || (inMessage.getState() > HostessStates.READY_TO_FLY.value))
                    throw new MessageException ("Invalid Hostess state!", inMessage);
                break;
            case WAIT_IN_QUEUE: case SHOW_DOCUMENTS: case BOARD_THE_PLANE:
                if ((inMessage.getState() < PassengerStates.GOING_TO_AIRPORT.value) || (inMessage.getState() > PassengerStates.AT_DESTINATION.value))
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
                ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.informPlaneReadyForBoarding();
                outMessage = new Message(MessageType.DONE_IPRFB, ((Pilot) Thread.currentThread()).getCurrentState());
                break;
            case PREPARE_FOR_PASS_BOARDING:
                ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.prepareForPassBoarding();
                outMessage = new Message(MessageType.DONE_PFPB, ((Hostess) Thread.currentThread()).getCurrentState());
                break;
            case WAIT_IN_QUEUE:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.waitInQueue();
                outMessage = new Message(MessageType.DONE_WIQ, ((Passenger) Thread.currentThread()).getCurrentState(), ((Passenger) Thread.currentThread()).getID());
                break;
            case CHECK_DOCUMENTS:
                ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.checkDocuments();
                outMessage = new Message (MessageType.DONE_CD, ((Hostess) Thread.currentThread()).getCurrentState());
                break;
            case SHOW_DOCUMENTS:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.showDocuments();
                outMessage = new Message(MessageType.DONE_SD);
                break;
            case WAIT_FOR_NEXT_PASSENGER:
                ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.waitForNextPassenger();
                outMessage = new Message (MessageType.DONE_WFNP, ((Hostess) Thread.currentThread()).getCurrentState());
                break;
            case BOARD_THE_PLANE:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.boardThePlane();
                outMessage = new Message(MessageType.DONE_BTP, ((Passenger) Thread.currentThread()).getCurrentState(), ((Passenger) Thread.currentThread()).getID());
                break;
            case WAIT_FOR_NEXT_FLIGHT:
                ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.waitForNextFlight();
                outMessage = new Message (MessageType.DONE_WFNF, ((Hostess) Thread.currentThread()).getCurrentState());
                break;
            case PARK_AT_TRANSFER_GATE:
                ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getState());
                depAir.parkAtTransferGate();
                outMessage = new Message(MessageType.DONE_PATG, ((Pilot) Thread.currentThread()).getCurrentState());
                break;
            case IS_INFORM_PLANE:
                break;
        }

        return (outMessage);
    }
}

