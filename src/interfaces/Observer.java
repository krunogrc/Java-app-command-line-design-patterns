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
public interface Observer {
    
    public void setSifra(String sifra);
    public void setNaziv(String naziv);
    public String getSifra();
    public String getNaziv();
    public int getUkupnoBodovi();
    public int getPozicija();
    public void setPozicija(int pozicija);
    
    public void update(String data);
}
