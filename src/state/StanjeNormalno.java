/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import interfaces.State;
import observer.Klub;
import observer.Klub.Status;

/**
 *
 * @author krunogr
 */
public class StanjeNormalno implements State{
    

    private Status status;
    
    @Override
    public void stanjeNatjecatelj(Klub klub) {       
    }

    @Override
    public void stanjeNormalno(Klub klub) {
         setStatus(Klub.Status.NORMALAN);
    }

    @Override
    public void stanjeSlab(Klub klub) {
    }
    
    @Override
    public State getState(Klub klub){
        return klub.getState();
    }
    
    private void setStatus(Status status){
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status; 
    }
    
    
}
