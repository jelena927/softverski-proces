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
public class PretraziObjekteSaVezanimObjektom <T extends OpstiDomenskiObjekat> extends OpstaSistemskaOperacija{

    List<? extends OpstiDomenskiObjekat> lista;
    
    public PretraziObjekteSaVezanimObjektom(T odo) {
        super(odo);
    }

    @Override
    public void izvrsiOperaciju() throws Exception {
        lista = dbbr.pretraziObjekte(odo);
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : lista) {
            opstiDomenskiObjekat.setVezaniObjekat(dbbr.dajPodatke(opstiDomenskiObjekat.vratiVezaniObjekat()));
        }
    }

    public List<? extends OpstiDomenskiObjekat> getLista() {
        return lista;
    }
}
