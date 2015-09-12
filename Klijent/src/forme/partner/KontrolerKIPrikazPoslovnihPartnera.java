/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.partner;

import domen.PoslovniPartner;
import forme.partner.model.ModelTabelePoslovniPartner;
import java.io.IOException;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIPrikazPoslovnihPartnera {

    public static List<PoslovniPartner> vratiPoslovnePartnere(JDialog d) throws IOException, ClassNotFoundException, Exception {
        TransferObjekat kto = new TransferObjekat();
        kto.setOperacija(Operacije.VRATI_SVE_POSLOVNE_PARTNERE);
        kto.setParametar(new PoslovniPartner());
        Komunikacija.vratiObjekat().posalji(kto);
        TransferObjekat sto = Komunikacija.vratiObjekat().procitaj();
        if (sto.getIzuzetak() != null) {
            throw (Exception)sto.getIzuzetak();
        }
        List<PoslovniPartner> lista = (List<PoslovniPartner>)sto.getRezultat();
        
        return lista;
    }
    
    
    public static void prikaziPoslovnePartnere(FmPrikazPartneraTabela f) throws Exception {
        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.PRETRAZI_POSLOVNE_PARTNERE);
        PoslovniPartner p = new PoslovniPartner();
        p.setNaziv(f.getTxtNaziv1().getText());
        toZahtev.setParametar(p);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        if (toOdgovor.getIzuzetak() != null) {
            throw (Exception)toOdgovor.getIzuzetak();
        }
        List<PoslovniPartner> lista = (List<PoslovniPartner>)toOdgovor.getRezultat();
        
        f.getTblPartneri().setModel(new ModelTabelePoslovniPartner(lista));
    }

    static void prikaziPodatke(PoslovniPartner poslovniPartner) throws Exception{
        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.PRIKAZI_POSLOVNOG_PARTNERA);
        toZahtev.setParametar(poslovniPartner);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        TransferObjekat toOdgovor = (TransferObjekat) Komunikacija.vratiObjekat().procitaj();
        FmUnosPoslovnogPartnera unos = new FmUnosPoslovnogPartnera(null, true);
        if (toOdgovor.getIzuzetak() != null) {
            JOptionPane.showMessageDialog(unos, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(unos, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
            
            PoslovniPartner p = (PoslovniPartner) toOdgovor.getRezultat();
            unos.popuniPodatke(p);
        }
    }
    
}
