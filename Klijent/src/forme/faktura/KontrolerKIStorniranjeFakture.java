/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.faktura;

import domen.Faktura;
import domen.PoslovniPartner;
import domen.StavkaFakture;
import forme.faktura.model.ModelTabeleStavkaFakture;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIStorniranjeFakture {
    static void stornirajFakturu(Faktura faktura, FmUnosFakture aThis, JTextField txtBrojFakture, JTextField txtBrojOtpremnice, JTextField txtDatum, JTextField txtOsnovica, JTextField txtPdv, JTextField txtUkupnaVrednost, JTable tblStavkeFakture, JComboBox cbPoslovniPartner, JCheckBox checkObradjena, JCheckBox checkStornirana) {
        Faktura f = null;
        try {
            String brojFakture = txtBrojFakture.getText().trim();
            String brojOtpremnice = txtBrojOtpremnice.getText().trim();
            String datumS = txtDatum.getText().trim();
            
            if (brojFakture.isEmpty() || brojOtpremnice.isEmpty() || datumS.isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            Date d = null;
            try {
                d = new SimpleDateFormat("dd/MM/yyyy").parse(datumS);
            } catch (Exception e) {
                throw new Exception("Unesite datum u formatu dd/MM/yyyy");
            }
            Date datum = new java.sql.Date(d.getTime());
            double osnovica = Double.parseDouble(txtOsnovica.getText().trim());
            double pdv = Double.parseDouble(txtPdv.getText().trim());
            double ukupnaVrednost = Double.parseDouble(txtUkupnaVrednost.getText().trim());
            boolean obradjena = checkObradjena.isSelected();
            boolean stornirana = true;
            PoslovniPartner partner = (PoslovniPartner) cbPoslovniPartner.getSelectedItem();
            List<StavkaFakture> stavke = ((ModelTabeleStavkaFakture) tblStavkeFakture.getModel()).vratiStavke();

            if (stavke.isEmpty()) {
                throw new Exception("Faktura mora imati bar jednu stavku.");
            }
            
            for (StavkaFakture stavkaFakture : stavke) {
                if (stavkaFakture.getProizvod() == null) {
                    throw new Exception("Morate uneti proizvod za svaku stavku");
                }
                if (stavkaFakture.getKolicina() <= 0) {
                    throw new Exception("Količina mora biti broj veći od nule.");
                }
                if (stavkaFakture.getRabat() < 0 || stavkaFakture.getRabat() > 1) {
                    throw new Exception("Rabat mora biti broj između 0 i 1.");
                }
                if (stavkaFakture.getPdv()< 0 || stavkaFakture.getPdv() > 1) {
                    throw new Exception("PDV mora biti broj između 0 i 1.");
                }
            }
            
            f = new Faktura(faktura.getIdFakture(), brojFakture, partner, 
                    ukupnaVrednost, obradjena, stornirana, stavke, osnovica, pdv, brojOtpremnice, datum);

            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.STORNIRAJ_FAKTURU);
            toZahtev.setParametar(f);

            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            String poruka = null;
            if (toOdogovor.getIzuzetak() != null) {
                poruka = ((Exception) toOdogovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(aThis, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                poruka = (String) toOdogovor.getPoruka();
                aThis.setFaktura(f);
                aThis.setStornirana();
                JOptionPane.showMessageDialog(aThis, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(aThis, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
