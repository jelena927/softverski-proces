/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.proizvod;

import domen.Proizvod;
import forme.proizvod.model.ModelTabeleProizvod;
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
public class KontrolerKIPrikazProizvoda {
    
    public static List<Proizvod> vratiProizvode(JDialog d) throws Exception {
        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.VRATI_SVE_PROIZVODE);
        toZahtev.setParametar(new Proizvod());
        Komunikacija.vratiObjekat().posalji(toZahtev);
        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        if (toOdgovor.getIzuzetak() != null) {
            throw (Exception)toOdgovor.getIzuzetak();
        }
        List<Proizvod> lista = (List<Proizvod>)toOdgovor.getRezultat();
        return lista;
    }

    public static void prikaziProizvode(FmPrikazProizvoda f) throws Exception {
        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.PRETRAZI_PROIZVODE);
        Proizvod p = new Proizvod();
        p.setNaziv(f.getTxtNaziv().getText());
        toZahtev.setParametar(p);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        if (toOdgovor.getIzuzetak() != null) {
            throw (Exception)toOdgovor.getIzuzetak();
        }
        List<Proizvod> lista = (List<Proizvod>)toOdgovor.getRezultat();
        f.getTblProizvodi().setModel(new ModelTabeleProizvod(lista));
    }

    static void prikaziPodatke(Proizvod proizvod) throws Exception{
        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.PRIKAZI_PROIZVOD);
        toZahtev.setParametar(proizvod);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        TransferObjekat toOdgovor = (TransferObjekat) Komunikacija.vratiObjekat().procitaj();
        FmUnosProizvoda unos = new FmUnosProizvoda(null, true);
        if (toOdgovor.getIzuzetak() != null) {
            JOptionPane.showMessageDialog(unos, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(unos, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
            
            Proizvod p = (Proizvod) toOdgovor.getRezultat();
            unos.popuniPodatke(p);
        }
    }
}
