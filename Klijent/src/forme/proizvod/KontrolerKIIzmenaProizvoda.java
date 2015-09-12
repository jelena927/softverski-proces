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
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIIzmenaProizvoda {

    public static void izmeniProizvod(Proizvod proizvod, JTextField txtNaziv, 
            JTextField txtCena, JComboBox cbJedMere, FmUnosProizvoda aThis) {
        try {        
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_PROIZVOD);

            String naziv = txtNaziv.getText();
            
            if (naziv.isEmpty() || txtNaziv.getText().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            double cena;
            try {
                cena = Double.parseDouble(txtCena.getText());
            } catch (Exception e) {
                throw new Exception("Cena mora biti broj.");
            }
            
            if (cena <= 0) {
                throw new Exception("Cena mora biti broj veći od nule.");
            }
            
            JedMere jm = (JedMere) cbJedMere.getSelectedItem();
            Proizvod p = new Proizvod(proizvod.getId(), naziv, cena, jm);

            toZahtev.setParametar(p);
            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                JOptionPane.showMessageDialog(aThis, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(aThis, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(aThis, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
