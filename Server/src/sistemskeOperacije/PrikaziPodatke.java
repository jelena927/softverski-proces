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
public class PrikaziPodatke extends OpstaSistemskaOperacija{
    OpstiDomenskiObjekat objekat;

    public PrikaziPodatke(OpstiDomenskiObjekat odo) {
        super(odo);
    }

    @Override
    public void izvrsiOperaciju() throws Exception {
        objekat = dbbr.dajPodatke(odo);
    }

    public OpstiDomenskiObjekat getObjekat() {
        return objekat;
    }
}
