/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import memento.Caretaker;
import memento.Originator;

/**
 *
 * @author krunogr
 */
public class Aplikacija {

    public static void main(String[] args) {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        int num = 1;
//        String[] args = {"podaci.txt", "3", "3", "4"};
        Caretaker cart = Caretaker.getInstance();
        Originator origin = new Originator();
        origin.kreirajTablicuPrvenstva(args[0]);
        cart.setAktivnaTablica(origin.getTablica());

        Dretva dretva = new Dretva(args);
        dretva.pokreni();

        while (num != 0) {

            System.out.println("\n Odaberi operaciju: ");
            System.out.println("1. Prekid rada dretve");
            System.out.println("0. Izlaz\n");

            try {
                line = input.readLine();
                num = Integer.parseInt(line);

            } catch (Exception e) {
                e.getMessage();
            }

            switch (num) {

                case 1:
                    dretva.stopReadHandle();
                    Originator orig = new Originator();

                    while (num != 0) {

                        System.out.println("\n Odaberi operaciju: ");
                        System.out.println("1. Ispis broja arhiviranih tablica prvenstva");
                        System.out.println("2. Ispis tablice prvenstva prema broju");
                        System.out.println("3. Ispis svih rezultata nekog kluba");
                        System.out.println("4. Ispis rezultata određenog kola");
                        System.out.println("0. Izlaz");

                        try {
                            line = input.readLine();
                            num = Integer.parseInt(line);
                        } catch (Exception e) {
                            e.getMessage();
                        }

                        switch (num) {

                            case 1:
                                orig.brojArhiviranihListi();
                                break;

                            case 2:
                                orig.ispisiArhiviraneListe();
                                try {
                                    line = input.readLine();
                                    num = Integer.parseInt(line);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                orig.ispisListePremaBroju(num);
                                break;
                            case 3:
                                System.out.println("Unesite broj kluba za koji želite pogledati rezultate");
                                orig.ispisKlubova();
                                try {
                                    line = input.readLine();
                                    num = Integer.parseInt(line);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                orig.ispisRezultataKluba(num);
                                break;
                            case 4:
                                orig.ukupanBrojKola();
                                try {
                                    line = input.readLine();
                                    num = Integer.parseInt(line);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                orig.ispisRezultataKola(num);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        System.exit(0);

    }
}
