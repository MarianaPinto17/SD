package entities;

import commonInfrastructures.*;

/**
 * Pilot thread and life cycle
 * @author Mariana Pinto
 * @author André Alves
 */
public class Pilot extends Thread{
    // checks the current state of a pilot
    private PilotStates currentState;
    // checks if pilot doesn't have more flights
    private boolean endOfLife;
    // checks if the pilot is asleep
    private boolean asleep;

    public Pilot (){
        this.endOfLife = false;
        this.asleep = false;
    }

    /**
     * Pilot life cycle.
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch(currentState){
                case AT_TRANSFER_GATE:
                    if() {
                        endOfLife = True;
                    }else{
                        currentState = PilotStates.READY_FOR_BOARDING;
                    }
                    break;
                case READY_FOR_BOARDING:
                    break;
                case WAIT_FOR_BOARDING:
                    break;
                case FLYING_FORWARD:
                    break;
                case DEBOARDING:
                    break;
                case FLYING_BACK:
                    break;
            }
        }
    }
}
