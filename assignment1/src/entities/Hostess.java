package entities;

import commonInfrastructures.HostessStates;
/**
 * Hostess thread and life cycle
 * @author Mariana Pinto
 * @author André Alves
 */
public class Hostess extends Thread{
    // checks the current state of a Hostess
    private HostessStates currentState;
    // checks if hostess don't have more flights
    private boolean endOfLife;

    /**
     * Hosstess Constructor.
     * Initiates a new Hostess
     */
    public Hostess(){
        this.endOfLife = false;
    }

    /**
     * Hostess life cycle
     */
    @Override
    public void run(){
        while(!endOfLife){
            switch (currentState){
                case
            }
        }
    }
}
