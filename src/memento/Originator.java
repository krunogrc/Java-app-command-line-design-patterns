/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import interfaces.Observer;
import interfaces.Subject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import observer.Klub;
import observer.Kolo;
import observer.Susret;
import observer.TablicaPrvenstva;

/**
 *
 * @author krunogr
 */
public class Originator {

    private Subject tablica;
    private Caretaker cart = Caretaker.getInstance();

    public Subject getTablica() {
        return tablica;
    }

    public void setTablica(Subject tablica) {
        this.tablica = tablica;
    }

    public void kreirajTablicuPrvenstva(String data) {
        tablica = new TablicaPrvenstva();
        int brojac = 0;
        int brojacPoz = 1;
        String previous = "";
        try {
            List<String> dat = readFile(data);
            for (String st : dat) {
                Klub klub = new Klub();
                String sifra = st.substring(0, 5).trim();
                String naziv = st.substring(5).trim();

                if (previous.equals(sifra)) {
                    System.out.println("Greška, jedan klub može imati samo jedan zapis! ");
                    break;
                }
                klub.setSifra(sifra);
                klub.setNaziv(naziv);
                klub.setPozicija(brojacPoz++);
                klub.setMjestaNaTablici(brojacPoz);
                klub.stanjeNatjecatelj();
                tablica.addObserver(klub);
                previous = sifra;
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public TablicaPrvenstva azurirajTablicu(Kolo kolo, int pauzirao) {
        //TO DO 
        cart.setKola(kolo);
        TablicaPrvenstva tab = (TablicaPrvenstva) cart.getAktivnaTablica();
        TablicaPrvenstva nova = new TablicaPrvenstva();
        List<Susret> susreti = kolo.getSusret();
        for (Susret s : susreti) {
            String rez = s.getRezultat();
            Klub kl = new Klub(), kl2 = new Klub(), temp = null, temp2 = null;
            if (rez.equals("1")) {
                temp = (Klub) tab.getKlubovi().get(s.getPozicijaDomacin());
                kl.setUkupnoBodovi(3 + temp.getUkupnoBodovi());
                temp2 = (Klub) tab.getKlubovi().get(s.getPozicijaGost());
                kl2.setUkupnoBodovi(0 + temp2.getUkupnoBodovi());
            } else if (rez.equals("2")) {
                temp2 = (Klub) tab.getKlubovi().get(s.getPozicijaGost());
                kl2.setUkupnoBodovi(3 + temp2.getUkupnoBodovi());
                temp = (Klub) tab.getKlubovi().get(s.getPozicijaDomacin());
                kl.setUkupnoBodovi(0 + temp.getUkupnoBodovi());
            } else if (rez.equals("0")) {
                temp = (Klub) tab.getKlubovi().get(s.getPozicijaDomacin());
                kl.setUkupnoBodovi(1 + temp.getUkupnoBodovi());
                temp2 = (Klub) tab.getKlubovi().get(s.getPozicijaGost());
                kl2.setUkupnoBodovi(1 + temp2.getUkupnoBodovi());
            }
            kl.setSifra(s.getDomacin());
            kl.setNaziv(s.getDomacinNaziv());
            kl2.setSifra(s.getGost());
            kl2.setNaziv(s.getGostNaziv());
            kl.setBrojKola(temp.getBrojKola());
            kl2.setBrojKola(temp2.getBrojKola());
            kl.setEfikasnost(temp.getEfikasnost());
            kl2.setEfikasnost(temp2.getEfikasnost());
            kl.setPozicija(temp.getPozicija());
            kl2.setPozicija(temp2.getPozicija());
            kl.setPrethodnoMjesto(temp.getPrethodnoMjesto());
            kl2.setPrethodnoMjesto(temp2.getPrethodnoMjesto());
            kl.setState(temp.getState());
            kl2.setState(temp2.getState());
            kl.setPonavljanjeStanja(temp.getPonavljanjeStanja());
            kl2.setPonavljanjeStanja(temp2.getPonavljanjeStanja());
            nova.addObserver(kl);
            nova.addObserver(kl2);
        }
        if (pauzirao != -1) {
            nova.addObserver(tab.getKlubovi().get(pauzirao));
        }
        return nova;
    }

    public TablicaPrvenstva azurirajRedoslijedKlubova(TablicaPrvenstva nova) {
        List<Observer> klubovi = nova.getKlubovi();
        Klub min = null, temp = null;
        int m = 0, poz = 1;
        for (int i = 0; i < klubovi.size() - 1; i++) {
            min = (Klub) klubovi.get(i);
            m = i;
            for (int j = i + 1; j < klubovi.size(); j++) {
                temp = (Klub) klubovi.get(j);
                if (temp.getUkupnoBodovi() > min.getUkupnoBodovi()) {
                    m = j;
                }
            }
            Collections.swap(klubovi, i, m);
        }

        for (int i = 0; i < klubovi.size() - 1; i++) {
            int j = i + 1;
            if (klubovi.get(i).getUkupnoBodovi() > klubovi.get(j).getUkupnoBodovi()) {
                Klub k = (Klub) klubovi.get(i);
                k.setPrethodnoMjesto(k.getPozicija());
                k.setPozicija(poz);
                k = (Klub) klubovi.get(j);
                k.setPrethodnoMjesto(k.getPozicija());
                klubovi.get(j).setPozicija(++poz);
            } else if (klubovi.get(i).getUkupnoBodovi() == klubovi.get(j).getUkupnoBodovi()) {
                Klub k = (Klub) klubovi.get(i);
                k.setPrethodnoMjesto(k.getPozicija());
                k.setPozicija(poz);
                k = (Klub) klubovi.get(j);
                k.setPrethodnoMjesto(k.getPozicija());
                k.setPozicija(poz);
            }
        }
        return nova;
    }

    public void provjeraStanja(int prag) {
        TablicaPrvenstva tp = (TablicaPrvenstva) cart.getAktivnaTablica();
        List<Observer> obs = tp.getKlubovi();
        for (Observer o : obs) {
            Klub kl = (Klub) o;
            kl.provjeraStanja(prag);
        }
    }

    public void spremiListu(Subject s) {
        cart.addSubject(s);
    }

    public TablicaPrvenstva dohvatiListu(int index) {
        return (TablicaPrvenstva) cart.getSubject(index - 1);
    }

    public Subject dohvatiAktivnuListu() {
        return tablica = cart.getAktivnaTablica();
    }

    public void postaviObservere() {
        TablicaPrvenstva t = (TablicaPrvenstva) cart.getAktivnaTablica();
        List<Observer> o = t.getKlubovi();
        for (Observer ob : o) {
            tablica.addObserver(ob);
        }
    }

    public void ispisiArhiviraneListe() {
        List<Subject> tl = cart.getArhiva();
        Iterator i = tl.iterator();
        int br = 1;
        System.out.println("\nArhivirane liste:");
        while (i.hasNext()) {
            TablicaPrvenstva t = (TablicaPrvenstva) i.next();
            System.out.println(+br++ + ".tablica");
        }
        System.out.println("Unesite broj tablice:");
    }

    public void brojArhiviranihListi() {
        System.out.println("Broj listi: " + cart.getArhiva().size());
    }

    public void ispisListePremaBroju(int index) {
        try {
            TablicaPrvenstva l = dohvatiListu(index);
            Iterator i = l.getKlubovi().iterator();
            int br = 1;
            while (i.hasNext()) {
                Klub k = (Klub) i.next();
                System.out.println(k.getPozicija() + ". " + k.getNaziv() + " " + k.getUkupnoBodovi());
            }
        } catch (Exception e) {
            System.out.println("\nLista ne postoji!");
        }
    }

    public void ispisKlubova() {
        try {
            TablicaPrvenstva l = dohvatiListu(1);
            Iterator i = l.getKlubovi().iterator();
            int br = 1;
            while (i.hasNext()) {
                Klub k = (Klub) i.next();
                System.out.println(k.getPozicija() + " " + k.getNaziv());
            }
        } catch (Exception e) {
            System.out.println("Došlo je do pogreške");
        }
    }

    public void ispisRezultataKluba(int index) {

        Susret s = new Susret();
        int brojKola = cart.getKola().size();
        int brojSusretaPoKolu = cart.getKola().get(0).getSusret().size();
        TablicaPrvenstva tp = (TablicaPrvenstva) Caretaker.getInstance().getAktivnaTablica();
        tp = (TablicaPrvenstva) Caretaker.getInstance().getAktivnaTablica();
        List<Observer> klubovi = tp.getKlubovi();
        int brKlubova = klubovi.size();

        if (index > klubovi.size()) {
            System.out.println("Upisali ste kriv vrijednost");
        }

        for (int i = 0; i < brojKola; i++) {
            for (Susret j : cart.getKola().get(i).getSusret()) {
                if (index == Integer.parseInt(j.getDomacin())
                        || index == Integer.parseInt(j.getGost())) {
                    String rezultat;
                    if ((j.getRezultat()).contains("0")) {
                        rezultat = "NERJEŠENO";
                    } else if ((j.getRezultat()).contains("1")) {
                        rezultat = "POBJEDA DOMAĆINA";
                    } else {
                        rezultat = "POBJEDA GOSTA";
                    }
                    System.out.println(j.getDomacinNaziv() + " - "
                            + j.getGostNaziv() + ": "
                            + rezultat);


                }
            }
        }

    }

    public void ukupanBrojKola() {
        System.out.println("Unesite jedan broj u rangu 1 - " + cart.getKola().size()
                + " kako biste vidjeli rezultate određenog kola");

    }

    public void ispisRezultataKola(int index) {
        index--;
        if (index > cart.getKola().size()) {
            System.out.println("Upisali ste krivu vrijednost");
        }
        for (Susret susret : cart.getKola().get(index).getSusret()) {
            String rezultat;
            if ((susret.getRezultat()).contains("0")) {
                rezultat = "NERJEŠENO";
            } else if ((susret.getRezultat()).contains("1")) {
                rezultat = "POBJEDA DOMAĆINA";
            } else {
                rezultat = "POBJEDA GOSTA";
            }
            System.out.println(susret.getDomacinNaziv() + " - " + susret.getGostNaziv() + ": " + rezultat);
        }
    }

    public void izbaciKlubove() {
        TablicaPrvenstva tab = (TablicaPrvenstva) cart.getAktivnaTablica();
        List<Observer> klubovi = tab.getKlubovi();
        Iterator iterator = klubovi.iterator();
        while (iterator.hasNext()) {
            Klub klub = (Klub) iterator.next();
            if (klub.isIspao()) {
                System.err.println("Klub: " + klub.getNaziv() + ", više nije natjecatelj");
                klub.ispisPodataka();
                iterator.remove();
                iterator = klubovi.iterator();
            }
        }
    }

    public boolean provjeraProstaliBrojKlubova() {
        TablicaPrvenstva tab = (TablicaPrvenstva) cart.getAktivnaTablica();
        List<Observer> klubovi = tab.getKlubovi();
        if (klubovi.size() <= 2) {
            return true;
        }
        return false;
    }

    public boolean usporediTablice(TablicaPrvenstva nova) {
        TablicaPrvenstva trenutna = (TablicaPrvenstva) cart.getAktivnaTablica();
        List<Observer> klubovi = trenutna.getKlubovi();
        for (int i = 0; i < klubovi.size(); i++) {
            if (!(klubovi.get(i).getNaziv().equals(nova.getKlubovi().get(i).getNaziv()))) {
                return true;
            }
        }
        return false;
    }

    public void arhivirajTablicu(TablicaPrvenstva nova) {
        cart.addSubject(cart.getAktivnaTablica());
        cart.setAktivnaTablica(nova);
    }

    public static List<String> readFile(String fileName) throws IOException {
        List<String> data = new ArrayList<String>();
        String str = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "ISO-8859-2"));
        while ((str = in.readLine()) != null) {
            if (str == null) {
                break;
            }
            data.add(str);
        }
        in.close();
        return data;
    }
}
