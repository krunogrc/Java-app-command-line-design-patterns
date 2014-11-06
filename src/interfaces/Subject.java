/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import observer.*;

/**
 *
 * @author krunogr
 */
public interface Subject {
    public void addObserver(Observer o );
    public void removeObserver(Observer o);
    public void notifyObservers(Observer o);
}
