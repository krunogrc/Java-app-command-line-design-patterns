/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import interfaces.State;
import observer.Klub;

/**
 *
 * @author krunogr
 */
public class StanjeSlab implements State{

    private Klub.Status status;

    @Override
    public void stanjeNatjecatelj(Klub klub) {
    }

    @Override
    public void stanjeNormalno(Klub klub) {
    }

    @Override
    public void stanjeSlab(Klub klub) {
        setStatus(Klub.Status.SLABI);
    }

    @Override
    public State getState(Klub klub) {
        return klub.getState();
    }

    private void setStatus(Klub.Status status) {
        this.status = status;
    }

    @Override
    public Klub.Status getStatus() {
        return status;
    }
    
}
