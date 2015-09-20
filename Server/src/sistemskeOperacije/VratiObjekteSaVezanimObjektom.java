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
public class VratiObjekteSaVezanimObjektom <T extends OpstiDomenskiObjekat> extends OpstaSistemskaOperacija{

    private List<? extends OpstiDomenskiObjekat> lista;
    
    public VratiObjekteSaVezanimObjektom(T odo) {
        super(odo);
    }
    
    @Override
    public void izvrsiOperaciju() throws Exception {
        lista = dbbr.vratiSve(odo);
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : lista) {
            opstiDomenskiObjekat.setVezaniObjekat(dbbr.dajPodatke(opstiDomenskiObjekat.vratiVezaniObjekat()));
        }
    }

    public List<? extends OpstiDomenskiObjekat> getLista() {
        return lista;
    }
}
