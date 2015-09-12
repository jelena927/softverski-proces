/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.korisnik;

import domen.Korisnik;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import klijent.Komunikacija;
import konstante.Operacije;
import kontroler.KontrolerKI;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIPrijavaKorisnika {
    
    private static Korisnik korisnik;

    static void prijaviKorisnika(FmPrijavaKorisnika aThis, JTextField txtIme, JPasswordField txtSifra) throws Exception{
        String korisnickoIme = txtIme.getText();
        String korisnickaSifra = String.valueOf(txtSifra.getPassword());
        
        if (korisnickoIme.isEmpty() || korisnickaSifra.isEmpty()) {
            throw new Exception("Sva polja su obavezna.");
        }
        
        Korisnik k = new Korisnik(korisnickoIme, korisnickaSifra);
        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.PRIJAVI_KORISNIKA);
        toZahtev.setParametar(k);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        
        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        if (toOdgovor.getIzuzetak() != null) {
            JOptionPane.showMessageDialog(aThis, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
            if (toOdgovor.getPoruka().equals("Dostignut maksimalan broj klijenata.")) {
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(aThis, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
            
            korisnik = (Korisnik) toOdgovor.getRezultat();
            aThis.setVisible(false);
        }
    }

    public static Korisnik getKorisnik() {
        return korisnik;
    }

    public static void setKorisnik(Korisnik korisnik) {
        KontrolerKIPrijavaKorisnika.korisnik = korisnik;
    }
    
}
