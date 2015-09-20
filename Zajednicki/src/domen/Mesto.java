/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jelena
 */
public class Mesto implements Serializable, OpstiDomenskiObjekat{
    private int ptt;
    private String naziv;

    public Mesto(int ptt, String naziv) {
        this.ptt = ptt;
        this.naziv = naziv;
    }

    public Mesto() {
    }
    
    public int getPtt() {
        return ptt;
    }

    public void setPtt(int ptt) {
        this.ptt = ptt;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return ptt + ", " + naziv; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (ptt == ((Mesto)obj).getPtt()) {
            return true;
        }
        return false;
    }

    @Override
    public String vratiNazivTabele() {
        return "Mesto";
    }

    @Override
    public String vratiAtributeSaVrednostima() {
        return "ptt= " + ptt + ", naziv='" + getNaziv() + "'";
    }

    @Override
    public List<OpstiDomenskiObjekat> napuni(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lo = new ArrayList<>();
        while (rs.next()) {
            int pttS = rs.getInt("ptt");
            String nazivS = rs.getString("naziv");
            
            Mesto mesto = new Mesto(pttS, nazivS);
            lo.add(mesto);
        }
        return lo;
    }

    @Override
    public String vratiNazivKljuca() {
        return "ptt";
    }

    @Override
    public Object vratiVrednostKljuca() {
        return ptt;
    }

    @Override
    public String vratiVrednostiAtributa() {
        return ptt + ", '" + naziv + "'";
    }

    @Override
    public int vratiBrojStavki() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OpstiDomenskiObjekat vratiStavku(int rb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OpstiDomenskiObjekat vratiVezaniObjekat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVezaniObjekat(OpstiDomenskiObjekat odo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vratiKriterijumPretrage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vratiTabeluStavke() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OpstiDomenskiObjekat vratiJednuStavku() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T extends OpstiDomenskiObjekat> void setujStavke(List<T> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean vrednosnaOgranicenja() {
        if (ptt == 0 || naziv.isEmpty() || naziv == null) {
            return false;
        }
        return true;
    }
    
    
}
