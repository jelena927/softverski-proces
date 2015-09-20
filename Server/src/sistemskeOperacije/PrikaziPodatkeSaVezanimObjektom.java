/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import domen.OpstiDomenskiObjekat;

/**
 *
 * @author Jelena
 * @param <T>
 */
public class PrikaziPodatkeSaVezanimObjektom <T extends OpstiDomenskiObjekat> extends OpstaSistemskaOperacija<T>{

    T objekat; 
    
    public PrikaziPodatkeSaVezanimObjektom(T odo) {
        super(odo);
    }

    @Override
    public void izvrsiOperaciju() throws Exception {
        objekat = dbbr.dajPodatke(odo);
        OpstiDomenskiObjekat o = dbbr.dajPodatke(objekat.vratiVezaniObjekat());
        objekat.setVezaniObjekat(o);
    }

    public T getObjekat() {
        return objekat;
    }
    
    
}
