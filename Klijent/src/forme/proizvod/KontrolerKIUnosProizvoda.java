/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.proizvod;

import domen.JedMere;
import domen.Proizvod;
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
public class KontrolerKIUnosProizvoda {

    private static Proizvod proizvod;

    public static void kreirajProizvod(FmUnosProizvoda f) throws Exception {
        proizvod = new Proizvod();
        int proizvodId = KontrolerKI.generisiID(proizvod);
        proizvod.setId(proizvodId);

        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.KREIRAJ_PROIZVOD);
        toZahtev.setParametar(proizvod);
        Komunikacija.vratiObjekat().posalji(toZahtev);

        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        String poruka = null;
        if (toOdgovor.getIzuzetak() != null) {
            poruka = ((Exception) toOdgovor.getIzuzetak()).getMessage();
        } else {
            poruka = (String) toOdgovor.getPoruka();
        }

        JOptionPane.showMessageDialog(f, poruka);
    }

    static void sacuvajProizvod(JTextField txtNaziv, JTextField txtCena, 
            JComboBox cbJedMere, FmUnosProizvoda f) {
        try {
            String naziv = txtNaziv.getText();
            if (naziv.isEmpty() || txtNaziv.getText().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            double cena = 0;
            try {
                cena = Double.parseDouble(txtCena.getText());
            } catch (Exception e) {
                throw new Exception("Cena mora biti broj.");
            }
            
            if (cena <= 0) {
                throw new Exception("Cena mora biti broj veći od nule.");
            }
            
            JedMere jedMere = (JedMere) cbJedMere.getSelectedItem();

            proizvod = new Proizvod(proizvod.getId(), naziv, cena, jedMere);

            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_PROIZVOD);
            toZahtev.setParametar(proizvod);

            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdogovor.getIzuzetak() != null) {
                JOptionPane.showMessageDialog(f, toOdogovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(f, toOdogovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
