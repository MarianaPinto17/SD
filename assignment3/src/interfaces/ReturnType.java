package interfaces;

import java.io.Serializable;


/**
 *  Data type to return both a boolean and an integer or two integers values.
 *
 *  Used in calls on remote objects.
 */

public class ReturnType implements Serializable {

    private static final long serialVersionUID = 7973215442438841515L;

    /**
     * Passenger Id.
     */
    private int passId = -1;

    /**
     * State number (can be for Pilot, Hostess or Passenger).
     */
    private int state = -1;

    /**
     * Boolean state.
     */
    private boolean boolState = false;


    /**
     * ReturnType instantiation (form 1).
     *
     * @param state state number (can be for Pilot, Hostess or Passenger)
     * @param Id number corresponding to passenger
     */
    public ReturnType(int state, int Id) {
        this.state = state;
        this.passId = Id;
    }

    /**
     * ReturnType instantiation (form 2).
     *
     * @param state state number (can be for Pilot, Hostess or Passenger)
     * @param boolState boolean value.
     */
    public ReturnType(int state, boolean boolState) {
        this.state = state;
        this.boolState = boolState;
    }

    /**
     * Getting passenger id.
     * @return passenger id
     */
    public int getPassId() {
        return passId;
    }

    /**
     * Getting the boolean variable.
     * @return boolean variable.
     */
    public boolean boolState() {
        return boolState;
    }

    /**
     * Getting number corresponding to state.
     * @return number corresponding to state
     */
    public int getState() {
        return state;
    }

}
