package ServerSide.sharedRegions;
import ServerSide.main.*;
import ClientSide.entities.*;
import commonInfrastructures.*;

/**
 *  Interface to the General Repository of Information.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    General Repository and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralRepositoryInterface {

    /**
     *  Reference to the general repository.
     */

    private final GeneralRepository repos;

    /**
     *  Instantiation of an interface to the general repository.
     *
     *    @param repos reference to the general repository
     */

    public GeneralRepositoryInterface (GeneralRepository repos)
    {
        this.repos = repos;
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
            case SETNFIC:
                if (inMessage.getFilename() == null)
                    throw new MessageException ("Name of the logging file is not present!", inMessage);
                break;

            case SET_PILOT_STATE:
                if ((inMessage.getState() < PilotStates.AT_TRANSFER_GATE.value) || (inMessage.getState() > PilotStates.FLYING_BACK.value))
                    throw new MessageException ("Invalid Pilot state!", inMessage);
                break;

            case SET_HOSTESS_STATE:
                if ((inMessage.getState () < HostessStates.WAIT_FOR_FLIGHT.value) && (inMessage.getState() != HostessStates.READY_TO_FLY.value))
                    throw new MessageException ("Invalid Hostess state!", inMessage);
                break;

            case SET_PASSENGER_STATE:
                if ((inMessage.getPassId() < 0) || (inMessage.getPassId() >= SimulPar.N))
                    throw new MessageException ("Invalid passenger id!", inMessage);
                else if ((inMessage.getState() < PassengerStates.GOING_TO_AIRPORT.value) || (inMessage.getState() > PassengerStates.AT_DESTINATION.value))
                    throw new MessageException ("Invalid passenger state!", inMessage);
                break;
            case SUM_UP:
                if (inMessage.getNpassFlight() == null){
                    throw new MessageException ("Invalid message, no passengers array found", inMessage);
                }
                break;
            case SET_INF: case SET_PTAL:
                if (inMessage.getState () < 0 || inMessage.getState() > SimulPar.N)
                    throw new MessageException ("Invalid InF or PTAL value!", inMessage);
                break;
            case SHUT: case GET_INF: case GET_PTAL:  case IS_ARRIVED_AT_DEST:   // check nothing
                break;
            case SET_ARRIVED_AT_DEST: case SET_EMPTY_PLANE_DEST:
                if (inMessage.isInformPlane() != false && inMessage.isInformPlane() != true)
                    throw new MessageException ("Invalid boolean value!", inMessage);
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
            case SUM_UP:
                repos.sumUp(inMessage.getNpassFlight());
                outMessage = new Message(MessageType.DONE_SU);
            break;
            case SHUT:
                repos.shutdown();
                outMessage = new Message (MessageType.DONE_S);
                break;
            case GET_INF:
                outMessage = new Message(MessageType.DONE_GINF, repos.getInF());
                break;
            case GET_PTAL:
                outMessage = new Message(MessageType.DONE_GPTAL, repos.getPTAL());
                break;
            case SET_INF:
                repos.setInF(inMessage.getState());
                outMessage = new Message(MessageType.DONE_SINF);
                break;
            case SET_PTAL:
                repos.setPTAL(inMessage.getState());
                outMessage = new Message(MessageType.DONE_SPTAL);
                break;
            case SET_ARRIVED_AT_DEST:
                repos.setArrivedAtDest(inMessage.isInformPlane());
                outMessage = new Message(MessageType.DONE_SAAD);
                break;
            case SET_EMPTY_PLANE_DEST:
                repos.setEmptyPlaneDest(inMessage.isInformPlane());
                outMessage = new Message(MessageType.DONE_SEPD);
                break;
            case IS_ARRIVED_AT_DEST:
                outMessage = new Message(MessageType.DONE_IAAD, repos.isArrivedAtDest() );
                break;
        }

        return (outMessage);
    }
}
