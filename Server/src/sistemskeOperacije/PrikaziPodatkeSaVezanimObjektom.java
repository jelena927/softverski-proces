/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import domen.OpstiDomenskiObjekat;

/**
 *
 * @author Jelena
 */
public class PrikaziPodatkeSaVezanimObjektom extends OpstaSistemskaOperacija{

    OpstiDomenskiObjekat objekat; 
    
    public PrikaziPodatkeSaVezanimObjektom(OpstiDomenskiObjekat odo) {
        super(odo);
    }

    @Override
    public void izvrsiOperaciju() throws Exception {
        objekat = dbbr.dajPodatke(odo);
        OpstiDomenskiObjekat o = dbbr.dajPodatke(objekat.vratiVezaniObjekat());
        objekat.setVezaniObjekat(o);
    }

    public OpstiDomenskiObjekat getObjekat() {
        return objekat;
    }
    
    
}
