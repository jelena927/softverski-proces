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
public class PretraziObjekteSaVezanimObjektom <T extends OpstiDomenskiObjekat> extends OpstaSistemskaOperacija<T>{

    List<T> lista;
    
    public PretraziObjekteSaVezanimObjektom(T odo) {
        super(odo);
    }

    @Override
    public void izvrsiOperaciju() throws Exception {
        lista = dbbr.pretraziObjekte(odo);
        for (T opstiDomenskiObjekat : lista) {
            opstiDomenskiObjekat.setVezaniObjekat(dbbr.dajPodatke(opstiDomenskiObjekat.vratiVezaniObjekat()));
        }
    }

    public List<T> getLista() {
        return lista;
    }
}
