/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.OpstiDomenskiObjekat;
import forme.glavna.FmGlavna;
import java.io.IOException;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKI {

    public static int generisiID(OpstiDomenskiObjekat odo) throws IOException, ClassNotFoundException, Exception {
        int broj = 0;
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(konstante.Operacije.GENERISI_ID);
            toZahtev.setParametar(odo);
            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                throw (Exception)toOdgovor.getIzuzetak();
            }
            broj = (int) toOdgovor.getRezultat();
        
        return broj;
    }

    public static void prekiniKomunikaciju(FmGlavna f) throws Exception{
        TransferObjekat toZahtev = new TransferObjekat();
        toZahtev.setOperacija(Operacije.PREKINI_KOMUNIKACIJU);
        Komunikacija.vratiObjekat().posalji(toZahtev);
        
        TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
        if (toOdgovor.getIzuzetak() != null) {
            throw (Exception)toOdgovor.getIzuzetak();
        }
        Komunikacija.vratiObjekat().ispisiKraj();
    }
}
