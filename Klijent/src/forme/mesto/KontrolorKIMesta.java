/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.mesto;

import domen.Mesto;
import java.util.List;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolorKIMesta {

    public static List<Mesto> vratiMesta() throws Exception {
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
    }
    
    
}
