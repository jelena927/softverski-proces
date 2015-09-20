/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.faktura;

import domen.Faktura;
import domen.PoslovniPartner;
import forme.faktura.model.ModelTableFaktura;
import java.util.List;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIPrikazFaktura {

    public static void prikaziFakture(FmPretragaFaktura aThis) throws Exception {
        TransferObjekat<Faktura> toZahtev = new TransferObjekat<>();
        toZahtev.setOperacija(Operacije.PRETRAZI_FAKTURE);
        Faktura f = new Faktura();
        PoslovniPartner partner = (PoslovniPartner) aThis.getCbPartneri().getSelectedItem();
        f.setPoslovniPartner(partner);
        toZahtev.setParametar(f);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        if (toOdgovor.getIzuzetak() != null) {
            throw (Exception) toOdgovor.getIzuzetak();
        }
        List<Faktura> lista = (List<Faktura>) toOdgovor.getRezultat();

        aThis.getTblFakture().setModel(new ModelTableFaktura(lista));
    }

    public static List<Faktura> vratiFakture(FmPretragaFaktura aThis) throws Exception {
        TransferObjekat<Faktura> kto = new TransferObjekat<>();
        kto.setOperacija(Operacije.VRATI_SVE_FAKTURE);
        kto.setParametar(new Faktura());
        Komunikacija.vratiObjekat().posalji(kto);
        TransferObjekat sto = Komunikacija.vratiObjekat().procitaj();
        if (sto.getIzuzetak() != null) {
            throw (Exception) sto.getIzuzetak();
        }
        List<Faktura> lista = (List<Faktura>) sto.getRezultat();

        return lista;
    }

    public static void prikaziPodatke(Faktura faktura) throws Exception {
        TransferObjekat<Faktura> toZahtev = new TransferObjekat<>();
        toZahtev.setOperacija(Operacije.PRIKAZI_FAKTURU);
        toZahtev.setParametar(faktura);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        TransferObjekat toOdgovor = (TransferObjekat) Komunikacija.vratiObjekat().procitaj();
        FmUnosFakture unos = new FmUnosFakture(null, true);
        if (toOdgovor.getIzuzetak() != null) {
            JOptionPane.showMessageDialog(unos, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(unos, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);

            Faktura f = (Faktura) toOdgovor.getRezultat();
            unos.setFaktura(f);
            unos.popuniPodatke(f);
            if (faktura.isStornirana()) {
                unos.setStornirana();
            } else if (faktura.isObradjena()) {
                unos.setObradjena();
            } else {
                unos.setSacuvana();
            }
            unos.setVisible(true);
        }
    }
}
