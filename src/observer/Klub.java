/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import interfaces.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import state.StanjeNatjecatelj;
import state.StanjeNormalno;
import state.StanjeSlab;
import interfaces.State;

/**
 *
 * @author krunogr
 */
public class Klub implements Observer {

    public enum Status {

        NATJECATELJ(1), NORMALAN(2), SLABI(3);
        int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }  
    };
    
    private String sifraKluba;
    private String nazivKluba;
    private List<Integer> mjestaNaTablici = new LinkedList<>();
    private List<Klub.Status> stanja = new ArrayList<>();
    private boolean ispao;
    private int pozicija, ukupnoBodovi = 0, ponavljanjeStanja = 0, prethodnoMjesto;
    private int brojKola = 1;
    private double efikasnost = 0.0;
    private State state;

    @Override
    public void update(String data) {
        System.out.println("Promjena" + data);
    }

    @Override
    public void setSifra(String sifra) {
        this.sifraKluba = sifra;
    }

    @Override
    public void setNaziv(String naziv) {
        this.nazivKluba = naziv;
    }

    @Override
    public String getSifra() {
        return sifraKluba;
    }

    @Override
    public String getNaziv() {
        return nazivKluba;
    }

    public boolean isIspao() {
        return ispao;
    }

    private void setIspao(boolean ispao) {
        this.ispao = ispao;
    }

    public int getPozicija() {
        return pozicija;
    }

    public void setPozicija(int pozicija) {
        this.pozicija = pozicija;
    }

    public int getUkupnoBodovi() {
        return ukupnoBodovi;
    }

    public void setUkupnoBodovi(int bodovi) {
        this.ukupnoBodovi += bodovi;
    }

    public List<Integer> getMjestaNaTablici() {
        return mjestaNaTablici;
    }

    public void setMjestaNaTablici(Integer mjesto) {
        this.mjestaNaTablici.add(mjesto);
    }

    public double getEfikasnost() {
        return efikasnost;
    }

    public void setEfikasnost(double efikasnost) {
        this.efikasnost = efikasnost;
    }

    public int getBrojKola() {
        return brojKola;
    }

    public void setBrojKola(int broj) {
        this.brojKola = broj;
    }

    public List<Klub.Status> getStanja() {
        return stanja;
    }

    public void setStanja(Klub.Status stanje) {
        this.stanja.add(stanje);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getPrethodnoMjesto() {
        return prethodnoMjesto;
    }

    public void setPrethodnoMjesto(int prethodnoMjesto) {
        this.prethodnoMjesto = prethodnoMjesto;
    }
    
    public void provjeraStanja(int prag) {
        if(this.getPozicija() < getPrethodnoMjesto()){
            this.stanjeSlab();
            ponavljanjeStanja++;
        }else if(this.getPozicija() > getPrethodnoMjesto()){
            this.stanjeNormalno();
        }        
        if(ponavljanjeStanja >= prag){
            this.setIspao(true);
        }      
    }
    
    public void stanjeNatjecatelj(){
        state = new StanjeNatjecatelj();
        state.stanjeNatjecatelj(this);
    }
    
    public void stanjeNormalno(){
        state = new StanjeNormalno();
        state.stanjeNormalno(this);
    }
    
    public void stanjeSlab(){
        state = new StanjeSlab();
        state.stanjeSlab(this);
    }
    
    public void ispisPodataka(){
        System.err.println("Ispis podataka: " + this.getNaziv() + " (" + this.getSifra()+ ") "
                + " Efikasnost: " + getEfikasnost()
                + " Ukupno bodovi " + getUkupnoBodovi());
    }

    public int getPonavljanjeStanja() {
        return ponavljanjeStanja;
    }

    public void setPonavljanjeStanja(int ponavljanjeStanja) {
        this.ponavljanjeStanja = ponavljanjeStanja;
    }
    
    
}
