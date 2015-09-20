/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import domen.OpstiDomenskiObjekat;
import java.util.List;

/**
 *
 * @author Jelena
 * @param <T>
 */
public class PrikaziPodatkeSaVezanimObjektomIStavkama <T extends OpstiDomenskiObjekat> extends OpstaSistemskaOperacija<T>{

    T objekat;
    
    public PrikaziPodatkeSaVezanimObjektomIStavkama(T odo) {
        super(odo);
    }

    @Override
    public void izvrsiOperaciju() throws Exception {
        objekat = dbbr.dajPodatke(odo);
        objekat.setVezaniObjekat(dbbr.dajPodatke(odo.vratiVezaniObjekat()));
        List<T> lista = dbbr.vratiSlabeObjekte(odo);
        for (T opstiDomenskiObjekat : lista) {
            opstiDomenskiObjekat.setVezaniObjekat(dbbr.dajPodatke(opstiDomenskiObjekat.vratiVezaniObjekat()));
        }
        objekat.setujStavke(lista);
    }

    public T getObjekat() {
        return objekat;
    }
}
