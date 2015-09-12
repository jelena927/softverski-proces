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
public class VratiObjekteSaVezanimObjektom extends OpstaSistemskaOperacija{

    private List<OpstiDomenskiObjekat> lista;
    
    public VratiObjekteSaVezanimObjektom(OpstiDomenskiObjekat odo) {
        super(odo);
    }
    
    @Override
    public void izvrsiOperaciju() throws Exception {
        lista = dbbr.vratiSve(odo);
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : lista) {
            opstiDomenskiObjekat.setVezaniObjekat(dbbr.dajPodatke(opstiDomenskiObjekat.vratiVezaniObjekat()));
        }
    }

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }
}
