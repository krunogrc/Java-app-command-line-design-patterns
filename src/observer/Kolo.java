/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author krunogr
 */
public class Kolo {
    
    private List<Susret> susret = new LinkedList<>();
    private int brojKola;

    public List<Susret> getSusret() {
        return susret;
    }

    public void setSusret(Susret susret) {
        this.susret.add(susret);
    }

    public int getBrojKola() {
        return brojKola;
    }

    public void setBrojKola(int brojKola) {
        this.brojKola = brojKola;
    }
    
}
