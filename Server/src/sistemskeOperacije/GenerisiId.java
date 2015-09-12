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
public class GenerisiId extends OpstaSistemskaOperacija{

    private int id;

    public GenerisiId(OpstiDomenskiObjekat odo) {
        super(odo);
    }
    
    @Override
    public void izvrsiOperaciju() throws Exception {
        id = dbbr.generisiId(odo);
    }

    public int getId() {
        return id;
    }
    
    
}
