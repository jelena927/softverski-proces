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
public class VratiSveObjekte <T extends OpstiDomenskiObjekat> extends OpstaSistemskaOperacija{

    private List<? extends OpstiDomenskiObjekat> lista;
    
    public VratiSveObjekte(T odo) {
        super(odo);
    }
    
    @Override
    public void izvrsiOperaciju() throws Exception {
        lista = dbbr.vratiSve(odo);
    }

    public List<? extends OpstiDomenskiObjekat> getLista() {
        return lista;
    }
    
    
}
