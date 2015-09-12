/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.partner;

import domen.Adresa;
import domen.Mesto;
import domen.PoslovniPartner;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import klijent.Komunikacija;
import konstante.Operacije;
import kontroler.KontrolerKI;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontorlerKIUnosPoslovnogPartnera {

    private static PoslovniPartner poslovniPartner;

    public static void zapamtiPoslovnogPartnera(JTextField txtNaziv, JTextField txtPib,
            JTextField txtKontakt, JTextField txtUlica, JTextField txtBroj,
            JComboBox cbMesto, FmUnosPoslovnogPartnera fmUnosMesta) {

        PoslovniPartner partner = null;
        try {
            String naziv = txtNaziv.getText().trim();
            String pib = txtPib.getText().trim();
            String kontakt = txtKontakt.getText().trim();
            String ulica = txtUlica.getText().trim();
            String broj = txtBroj.getText().trim();
            
            if (naziv.isEmpty() || pib.isEmpty() || kontakt.isEmpty() || ulica.isEmpty() ||
                    broj.isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            Mesto mesto = (Mesto) cbMesto.getSelectedItem();
            Adresa adresa = new Adresa(ulica, broj, mesto);

            partner = new PoslovniPartner(poslovniPartner.getId(), pib, naziv, kontakt, adresa);

            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_POSLOVNOG_PARTNERA);
            toZahtev.setParametar(partner);

            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            String poruka = null;
            if (toOdogovor.getIzuzetak() != null) {
                poruka = ((Exception) toOdogovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(fmUnosMesta, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                poruka = (String) toOdogovor.getPoruka();
                JOptionPane.showMessageDialog(fmUnosMesta, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(fmUnosMesta, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void kreirajPoslovnogPartnera(FmUnosPoslovnogPartnera f) throws ClassNotFoundException, IOException, Exception {

        poslovniPartner = new PoslovniPartner();
        int poslovniPartnerId = KontrolerKI.generisiID(poslovniPartner);
        poslovniPartner.setId(poslovniPartnerId);

        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(konstante.Operacije.KREIRAJ_POSLOVNOG_PARTNERA);
        toZahtev.setParametar(poslovniPartner);

        Komunikacija.vratiObjekat().posalji(toZahtev);

        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        String poruka = null;
        if (toOdgovor.getIzuzetak() != null) {
            poruka = ((Exception) toOdgovor.getIzuzetak()).getMessage();
            JOptionPane.showMessageDialog(f, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
        } else {
            poruka = (String) toOdgovor.getPoruka();
            JOptionPane.showMessageDialog(f, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
