/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author student1
 */
public interface OpstiDomenskiObjekat {

    public String vratiNazivTabele();

    public String vratiAtributeSaVrednostima();

    public List<OpstiDomenskiObjekat> napuni(ResultSet rs) throws Exception;
    
    public String vratiNazivKljuca();
    
    public Object vratiVrednostKljuca();
    
    public String vratiVrednostiAtributa();
    
    public String vratiKriterijumPretrage();
    
    public int vratiBrojStavki();
    
    public OpstiDomenskiObjekat vratiStavku(int rb);
    
    public OpstiDomenskiObjekat vratiVezaniObjekat();
    
    public void setVezaniObjekat(OpstiDomenskiObjekat odo);
    
    public String vratiTabeluStavke();
    
    public OpstiDomenskiObjekat vratiJednuStavku();
    
    public void setujStavke(List<OpstiDomenskiObjekat> lista);

    public boolean vrednosnaOgranicenja();
}
