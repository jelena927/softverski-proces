/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import baza.DatabaseBroker;
import domen.OpstiDomenskiObjekat;

/**
 *
 * @author student1
 * @param <T>
 */
public abstract class OpstaSistemskaOperacija <T extends OpstiDomenskiObjekat>{
    protected DatabaseBroker dbbr;
    protected T odo;

    public OpstaSistemskaOperacija(T odo) {
        dbbr = new DatabaseBroker();
        this.odo = odo;
    }
    
    public void izvrsenjeSO() throws Exception{
        otvoriBazu();
        try{
            izvrsiOperaciju();
            potvrdi();
        }catch(Exception e){
            odbaci();
            throw e;
        }
        zatvoriBazu();
    }

    private void otvoriBazu() throws Exception {
        dbbr.ucitajDriver();
        dbbr.otvoriKonekciju();
    }

    public abstract void izvrsiOperaciju() throws Exception;

    private void potvrdi() throws Exception {
        dbbr.commitTransakcije();
    }

    private void odbaci() throws Exception {
        dbbr.rollbackTransakcije();
    }

    private void zatvoriBazu() throws Exception {
        dbbr.zatvoriKonekciju();
    }
}
