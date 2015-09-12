/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.partner;

import domen.Adresa;
import domen.Mesto;
import domen.PoslovniPartner;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIIzmenaPoslovnogPartnera {

    public static void izmeniPoslovnogPartnera(PoslovniPartner partner, JTextField txtNaziv, JTextField txtPib, JTextField txtKontakt, JTextField txtUlica, JTextField txtBroj, JComboBox cbMesto, FmUnosPoslovnogPartnera aThis) {
        try {        
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_POSLOVNOG_PARTNERA);

            String naziv = txtNaziv.getText();
            String pib = txtPib.getText();
            String kontakt = txtKontakt.getText();
            String ulica = txtUlica.getText();
            String broj = txtBroj.getText();
            
            if (naziv.isEmpty() || pib.isEmpty() || kontakt.isEmpty() || ulica.isEmpty() ||
                    broj.isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            Mesto mesto = (Mesto) cbMesto.getSelectedItem();
            
            PoslovniPartner p = new PoslovniPartner(partner.getId(), pib, naziv, kontakt, new Adresa(ulica, broj, mesto));

            toZahtev.setParametar(p);
            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                JOptionPane.showMessageDialog(aThis, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(aThis, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
                //aThis.dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(aThis, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
