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
public class KreirajNoviObjekat extends OpstaSistemskaOperacija{

    OpstiDomenskiObjekat objekat;
    
    public KreirajNoviObjekat(OpstiDomenskiObjekat odo) {
        super(odo);
    }
    
    @Override
    public void izvrsiOperaciju() throws Exception {
        dbbr.kreiraj(odo);
    }

    public OpstiDomenskiObjekat getObjekat() {
        return objekat;
    }
}
