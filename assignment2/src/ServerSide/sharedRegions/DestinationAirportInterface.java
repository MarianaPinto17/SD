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

public class DestinationAirportInterface {
    /**
     *  Reference to the departure Airport.
     */

    private final DestinationAirport desAir;

    /**
     *  Instantiation of an interface to the departure Airport.
     *
     *    @param desAir reference to the departure Airport
     */

    public DestinationAirportInterface (DestinationAirport desAir)
    {
        this.desAir = desAir;
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
            case ANNOUNCE_ARRIVAL:
                if ((inMessage.getState() < PilotStates.AT_TRANSFER_GATE) || (inMessage.getState() > PilotStates.FLYING_BACK))
                    throw new MessageException ("Invalid Pilot state!", inMessage);
                break;
            case LEAVE_THE_PLANE:
                if ((inMessage.getState() < PassengerStates.GOING_TO_AIRPORT) || (inMessage.getState() > PassengerStates.AT_DESTINATION))
                    throw new MessageException ("Invalid Passenger state!", inMessage);
                if ((inMessage.getPassId() < 0) || (inMessage.getPassId() >= SimulPar.N))
                    throw new MessageException ("Invalid Passenger id!", inMessage);
                break;
            default:
                throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ()) {
            case ANNOUNCE_ARRIVAL:
                ((Pilot) Thread.currentThread()).setPilotState(inMessage.getState());
                desAir.announceArrival();
                outMessage = new Message(MessageType.DONE_AA, ((Pilot) Thread.currentThread()).getPilotState());
                break;
            case LEAVE_THE_PLANE:
                ((Passenger) Thread.currentThread()).setId(inMessage.getPassId());
                ((Passenger) Thread.currentThread()).setPassengerState(inMessage.getState());
                desAir.leaveThePlane();
                outMessage = new Message(MessageType.DONE_LTP, ((Passenger) Thread.currentThread()).getPassengerState(), ((Passenger) Thread.currentThread()).getID());
                break;
        }

        return (outMessage);
    }
}

