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
public class StanjeNatjecatelj implements State {

    private Klub.Status status;

    @Override
    public void stanjeNatjecatelj(Klub klub) {
        setStatus(Klub.Status.NATJECATELJ);
    }

    @Override
    public void stanjeNormalno(Klub klub) {
    }

    @Override
    public void stanjeSlab(Klub klub) {
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
