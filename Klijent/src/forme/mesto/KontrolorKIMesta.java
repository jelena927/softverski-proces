/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.mesto;

import domen.Mesto;
import java.awt.Dialog;
import java.awt.Frame;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolorKIMesta {

    private final List<Mesto> model;
    private final FmPrikazMesta view;

    public KontrolorKIMesta(Frame mainView) {
        model = vratiMesta(null);
        view = new FmPrikazMesta(mainView, true, model);
        view.postaviVrednosti();
    }
    
    public static List<Mesto> vratiMesta(Dialog dialog){
        try {
            TransferObjekat kto = new TransferObjekat();
            kto.setOperacija(Operacije.VRATI_SVA_MESTA);
            kto.setParametar(new Mesto());
            Komunikacija.vratiObjekat().posalji(kto);
            TransferObjekat sto = Komunikacija.vratiObjekat().procitaj();
            if (sto.getIzuzetak() != null) {
                throw (Exception)sto.getIzuzetak();
            }
            List<Mesto> mesta = (List<Mesto>)sto.getRezultat();
            return mesta;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return Collections.EMPTY_LIST;
    }

    public void pokreniFormu() {
        view.setVisible(true);
    }
    
    
}
