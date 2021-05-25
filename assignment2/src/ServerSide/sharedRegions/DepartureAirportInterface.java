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
            case INFORM_PLANE_READY_FOR_BOARDING:
            case PREPARE_FOR_PASS_BOARDING:
            case WAIT_IN_QUEUE:
            case CHECK_DOCUMENTS:
            case SHOW_DOCUMENTS:
            case WAIT_FOR_NEXT_PASSENGER:
            case BOARD_THE_PLANE:
            case WAIT_FOR_NEXT_FLIGHT:
            case PARK_AT_TRANSFER_GATE:
            case IS_INFORM_PLANE:
                break;
            default:
                throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ()) {
            case SETNFIC:
                repos.initSimul(inMessage.getFilename());
                outMessage = new Message (MessageType.DONE_NFIC);
                break;
            case SET_PILOT_STATE:

                repos.setPilotState(inMessage.getState());
                outMessage = new Message (MessageType.DONE_SPiS);
                break;

            case SET_HOSTESS_STATE:
                if(inMessage.getPassId() >= 0)
                    repos.setHostessState(inMessage.getState(), inMessage.getPassId());
                else
                    repos.setHostessState(inMessage.getState());
                outMessage = new Message (MessageType.DONE_SHS);
                break;
            case SET_PASSENGER_STATE:
                repos.setPassengerState(inMessage.getPassId(), inMessage.getState());
                outMessage = new Message (MessageType.DONE_SPaS);
                break;
            case SHUT:
                repos.shutdown ();
                outMessage = new Message (MessageType.DONE_S);
                break;

            case SHOW_DOCUMENTS:
                break;
            case WAIT_FOR_NEXT_PASSENGER:
                break;
            case BOARD_THE_PLANE:
                break;
            case WAIT_FOR_NEXT_FLIGHT:
                break;
            case PARK_AT_TRANSFER_GATE:
                break;
            case IS_INFORM_PLANE:
                break;
        }

        return (outMessage);
    }
}

