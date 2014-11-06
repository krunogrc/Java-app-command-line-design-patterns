/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import observer.Klub;
import observer.Klub.Status;

/**
 *
 * @author krunogr
 */
public interface State {
    
    public void stanjeNatjecatelj(Klub klub);
    public void stanjeNormalno(Klub klub);
    public void stanjeSlab(Klub klub);
    public State getState(Klub klub);
    
    public Status getStatus();
}
