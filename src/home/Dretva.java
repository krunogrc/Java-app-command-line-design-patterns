/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import interfaces.Observer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import memento.Caretaker;
import memento.Originator;
import observer.Klub;
import observer.Kolo;
import observer.Susret;
import observer.TablicaPrvenstva;

/**
 *
 * @author krunogr
 */
public class Dretva {

    private int interval, kontrola, prag;
    private ScheduledFuture<?> readHandle;
    TablicaPrvenstva tp = (TablicaPrvenstva) Caretaker.getInstance().getAktivnaTablica();
    List<Observer> klubovi = tp.getKlubovi();
    int brKlubova = klubovi.size();
    int brKlubovaPomocna = 0;
    int brojac = 0, koloBr = 1;
    List<Integer> parovi; //tu se smjestaju indeksi klubova koji su izvuceni

    public Dretva(String data[]) {
        interval = Integer.parseInt(data[1]);
        kontrola = Integer.parseInt(data[2]);
        prag = Integer.parseInt(data[3]);
    }

    public int getInterval() {
        return interval;
    }

    public int getBrojac() {
        return brojac;
    }

    public void incrementBrojac() {
        this.brojac++;
    }

    public int getKontrola() {
        return kontrola;
    }

    public void setBrojac(int brojac) {
        this.brojac = brojac;
    }

    public void stopReadHandle() {
        this.readHandle.cancel(true);
    }
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void pokreni() {

        final Runnable citac = new Runnable() {
            public void run() {
                parovi = new ArrayList<>();
                Kolo kolo = new Kolo();
                tp = (TablicaPrvenstva) Caretaker.getInstance().getAktivnaTablica();
                klubovi = tp.getKlubovi();
                brKlubova = klubovi.size();
                
                brKlubovaPomocna = brKlubova;
                if (brKlubova % 2 != 0) {
                    brKlubovaPomocna = brKlubova - 1;
                }

                System.out.println("Interval " + getBrojac() + ": " + getTime());
                try {
                    for (int i = 0; i < brKlubovaPomocna / 2; i++) {
                        Random rnd = new Random();
                        int domacin = -1, gost = -1;
                        boolean regularno = false, D = false, G = false;

                        while (regularno == false) {

                            int tempDomacin = rnd.nextInt(brKlubova);
                            int tempGost = rnd.nextInt(brKlubova);
                            
                            if (!parovi.contains(tempDomacin) && D == false) {
                                domacin = tempDomacin;
                                parovi.add(domacin);
                                D = true;
                            }
                            if (!parovi.contains(tempGost) && G == false) {
                                gost = tempGost;
                                parovi.add(gost);
                                G = true;
                            }

                            if (domacin != gost && D == true && G == true) {
                                Susret s = new Susret();
                                s.setDomacin(klubovi.get(domacin).getSifra());
                                s.setDomacinNaziv(klubovi.get(domacin).getNaziv());
                                s.setGost(klubovi.get(gost).getSifra());
                                s.setGostNaziv(klubovi.get(gost).getNaziv());
                                s.setRezultat(rnd.nextInt(3) + "");
                                s.setPozicijaDomacin(domacin);
                                s.setPozicijaGost(gost);
                                kolo.setSusret(s);
                                Klub k = (Klub) klubovi.get(domacin);
                                k.setBrojKola(1 + k.getBrojKola());
                                k = (Klub) klubovi.get(gost);
                                k.setBrojKola(1 + k.getBrojKola());
                                regularno = true;
                            }
                        }
                    }
                    kolo.setBrojKola(koloBr++);
                    incrementBrojac();
                    if(brKlubovaPomocna % 2 != 0){
                        brKlubovaPomocna = pronadjiSlobodniKlub(parovi);
                    }else{
                        brKlubovaPomocna = -1;
                    }
                    
                    Originator originator = new Originator();
                    TablicaPrvenstva nova = originator.azurirajTablicu(kolo, brKlubovaPomocna);
                    nova = originator.azurirajRedoslijedKlubova(nova);
                    boolean promjena = originator.usporediTablice(nova);
                    if(promjena == true){
                        originator.arhivirajTablicu(nova);
                    }

                    TablicaPrvenstva tab = new TablicaPrvenstva();
                    tab.provjeraEfikasnosti();

                    if (getBrojac() == getKontrola()) { // ako je dosegnut prag (kontrola) odigranih kola
                        originator.provjeraStanja(prag);
                        originator.izbaciKlubove();
                        if(originator.provjeraProstaliBrojKlubova() == true){
                            stopReadHandle();
                        }
                        setBrojac(0);
                    }
                    
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        };

        readHandle = scheduler.scheduleWithFixedDelay(citac, 0, interval, TimeUnit.SECONDS);

        scheduler.schedule(new Runnable() {
            public void run() {
                readHandle.cancel(true);
            }
        }, 60 * 60, TimeUnit.SECONDS);
    }
    
    public int pronadjiSlobodniKlub(List<Integer> parovi){
        for(Observer o : klubovi){
            if(parovi.contains(klubovi.indexOf(o))){
                return klubovi.indexOf(o);
            }
        }
        return -1;
    }
    
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String time = sdf.format(cal.getTime());  
        return time;
    }
}
