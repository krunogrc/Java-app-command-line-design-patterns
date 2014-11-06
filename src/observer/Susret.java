/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

/**
 *
 * @author krunogr
 */
public class Susret {
    
    private String domacin, gost, domacinNaziv, gostNaziv;
    private String rezultat;
    private int pozicijaDomacin, pozicijaGost;
    
    public enum Rezultat {

        NERJEŠENO(0), DOMAĆIN(1), GOST(2);
        int value;

        private Rezultat(int value) {
            this.value = value;
        }
    };
    
    public enum Bodovi {

        NERJEŠENO(1), POBJEDA(3), PORAZ(0);
        int value;

        private Bodovi(int value) {
            this.value = value;
        }
    };

    public String getDomacin() {
        return domacin;
    }

    public void setDomacin(String domacin) {
        this.domacin = domacin;
    }

    public String getGost() {
        return gost;
    }

    public void setGost(String gost) {
        this.gost = gost;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }

    public String getDomacinNaziv() {
        return domacinNaziv;
    }

    public void setDomacinNaziv(String domacinNaziv) {
        this.domacinNaziv = domacinNaziv;
    }

    public String getGostNaziv() {
        return gostNaziv;
    }

    public void setGostNaziv(String gostNaziv) {
        this.gostNaziv = gostNaziv;
    }

    public int getPozicijaDomacin() {
        return pozicijaDomacin;
    }

    public void setPozicijaDomacin(int pozicijaDomacin) {
        this.pozicijaDomacin = pozicijaDomacin;
    }

    public int getPozicijaGost() {
        return pozicijaGost;
    }

    public void setPozicijaGost(int pozicijaGost) {
        this.pozicijaGost = pozicijaGost;
    }
    
}
