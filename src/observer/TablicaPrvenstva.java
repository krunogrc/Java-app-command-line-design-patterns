/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import interfaces.Observer;
import interfaces.Subject;
import java.util.LinkedList;
import java.util.List;
import memento.Caretaker;

/**
 *
 * @author krunogr
 */
public class TablicaPrvenstva implements Subject {

    private List<Observer> klubovi = new LinkedList<>();

    public List<Observer> getKlubovi() {
        return klubovi;
    }

    public void setKlubovi(List<Observer> klubovi) {
        this.klubovi = klubovi;
    }

    @Override
    public void addObserver(Observer o) {
        klubovi.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers(Observer o) {
        Klub klub = (Klub) o;
        o.update(" - " + klub.getNaziv() + " je promijenio efikasnost na: " + klub.getEfikasnost() + "");
    }

    public void provjeraEfikasnosti() {
        Caretaker cart = Caretaker.getInstance();
        TablicaPrvenstva tp = (TablicaPrvenstva) cart.getAktivnaTablica();
        List<Observer> clubs = tp.getKlubovi();
        for (Observer o : clubs) {
            Klub kl = (Klub) o;
            double e = kl.getUkupnoBodovi();
            double a = kl.getBrojKola();
            float fl = (float) (e/a);
            String d= String.valueOf(fl).substring(0, 3);
            double db = Double.parseDouble(d);
            if (db != kl.getEfikasnost()) {
                kl.setEfikasnost(db);
                notifyObservers(kl);
            }
        }
    }
}
