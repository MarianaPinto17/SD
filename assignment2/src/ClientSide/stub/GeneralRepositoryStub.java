package ClientSide.stub;

import ClientSide.entities.Hostess;
import ClientSide.entities.HostessStates;
import ClientSide.entities.PassengerStates;
import ClientSide.entities.PilotStates;
import commonInfrastructures.*;

/**
 *
 * @author Mariana Pinto
 * @author André Alves
 */
public class GeneralRepositoryStub {

    /**
     * Name of the computational system where the server is located.
     */
    private final String serverHostName;

    /**
     * Number of the listening port at the computational system where the server
     * is located.
     */
    private final int serverPortNumb;

    /**
     * Instantiation of a remote reference
     *
     * @param hostName name of the computational system where the server is
     * located
     * @param portNumb number of the listening port at the computational system
     * where the server is located
     */
    public GeneralRepositoryStub(String hostName, int portNumb) {
        serverHostName = hostName;
        serverPortNumb = portNumb;
    }

    /**
     *   Operation initialization of the simulation.
     *
     *     @param fileName logging file name
     */

    public void initSimul (String fileName)
    {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SETNFIC, fileName);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_NFIC)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Pilot state.
     *
     * @param state pilot state
     */
    public void setPilotState(int state){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_PILOT_STATE, state);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SPiS) {
            System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Hostess state.
     *
     * @param state hostess state
     */
    public void setHostessState(int state){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_HOSTESS_STATE, state);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SHS)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Hostess state and prints to log the passenger who checked.
     *
     * @param state hostess state
     * @param pid passenger id
     */
    public void setHostessState(int state, int pid){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_HOSTESS_STATE, state, pid);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SHS)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Passenger state.
     *
     * @param state passenger state
     * @param pid passenger id
     */
    public void setPassengerState(int state, int pid){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_PASSENGER_STATE, state, pid);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SPaS)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    public void sumUp(int[] npassengers){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SUM_UP, npassengers);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SU) {
            System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }


    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public void shutdown ()
    {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SHUT);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_S)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    public int getInF() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.GET_INF);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_GINF)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        if (inMessage.getState() < 0){
            System.out.println("Error getting INF!");
        }
        com.close ();

        return inMessage.getState();
    }

    public int getPTAL() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.GET_PTAL);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_GPTAL)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        System.out.println("get PTAL: "+inMessage.getState());
        if (inMessage.getState() < 0){
            System.out.println("Error getting PTAL!");
        }
        com.close ();

        return inMessage.getState();
    }

    public void setInF(int inf) {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SET_INF, inf);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SINF)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    public void setPTAL(int ptal) {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        System.out.println("[REPOS STUB] PTAL: "+ptal);
        outMessage = new Message (MessageType.SET_PTAL, ptal);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SPTAL)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    public void setArrivedAtDest(boolean b) {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SET_ARRIVED_AT_DEST, b);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SAAD)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    public boolean isArrivedAtDest() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.IS_ARRIVED_AT_DEST);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_IAAD)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        if (!inMessage.isInformPlane() && inMessage.isInformPlane()){
            System.out.println("Error getting IsArrivedAtDest!");
        }
        com.close ();

        return inMessage.isInformPlane();
    }

    public void setEmptyPlaneDest(boolean b) {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SET_EMPTY_PLANE_DEST, b);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SEPD)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }
}
