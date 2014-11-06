/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import interfaces.Subject;
import java.util.LinkedList;
import java.util.List;
import observer.Kolo;

/**
 *
 * @author krunogr
 */
public class Caretaker {
    
    private List<Subject> arhiva = new LinkedList<>();
    private List<Kolo> kola = new LinkedList<>();
    private static Subject aktivnaTablica;

    public List<Subject> getArhiva() {
        return arhiva;
    }
    
    private static volatile Caretaker INSTANCE;
    // Private constructor suppresses generation of a (public) default 

    private Caretaker() {}
    
    public static Caretaker getInstance() {
    if (INSTANCE == null)
        synchronized(Caretaker.class) {
    if (INSTANCE == null)
        INSTANCE = new Caretaker();
    }
        return INSTANCE;
    }
    
    public void addSubject(Subject s) {
        arhiva.add(s);
    }
    
    public Subject getSubject(int index) {
        return arhiva.get(index);
    }
    
    public Subject getLastSubject() {
        return arhiva.get(arhiva.size()-1);
    }

    public List<Kolo> getKola() {
        return kola;
    }

    public void setKola(Kolo kolo) {
        this.kola.add(kolo);
    }

    public Subject getAktivnaTablica() {
        return aktivnaTablica;
    }

    public void setAktivnaTablica(Subject aktivnaTablica) {
        Caretaker.aktivnaTablica = aktivnaTablica;
    }
    
    
}
