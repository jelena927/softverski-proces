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
 */
public class PretraziObjekte extends OpstaSistemskaOperacija{

    List<OpstiDomenskiObjekat> lista;
    
    public PretraziObjekte(OpstiDomenskiObjekat odo) {
        super(odo);
    }

    @Override
    public void izvrsiOperaciju() throws Exception {
        lista = dbbr.pretraziObjekte(odo);
    }

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }
}
