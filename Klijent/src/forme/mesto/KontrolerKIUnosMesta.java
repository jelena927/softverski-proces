/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.mesto;

import domen.Mesto;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author student1
 */
public class KontrolerKIUnosMesta {
    
    public void unosMesta(FmUnosMesta panelUnosMesta, JTextField jtxtPtt, JTextField jtxtNaziv){
        try {
            int ptt = 0;
            String naziv = jtxtNaziv.getText().trim();
            
            if (naziv.isEmpty() || jtxtPtt.getText().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            try {
                ptt = Integer.parseInt(jtxtPtt.getText().trim());
            } catch (Exception e) {
                throw new Exception("Poštanski broj sadrži samo cifre.");
            }
            
            Mesto mesto = new Mesto(ptt, naziv);
            
            //formiranje paketa
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_MESTO);
            toZahtev.setParametar(mesto);
            
            //slanje zahteva
            Komunikacija.vratiObjekat().posalji(toZahtev);
            
            //prijem odgovora
            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            String poruka = null;
            if (toOdogovor.getIzuzetak() != null) {
                poruka = ((Exception)toOdogovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(panelUnosMesta, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                poruka = (String) toOdogovor.getPoruka();
                panelUnosMesta.obrisiPolja();
                JOptionPane.showMessageDialog(panelUnosMesta, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panelUnosMesta, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
