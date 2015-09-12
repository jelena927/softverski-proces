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
public class Proizvod implements Serializable, OpstiDomenskiObjekat {

    private int id;
    private String naziv;
    private double cena;
    private JedMere jedinicaMere;

    public Proizvod() {
    }

    public Proizvod(int id) {
        this.id = id;
    }

    public Proizvod(int id, String naziv, double cena, JedMere jedinicaMere) {
        this.id = id;
        this.naziv = naziv;
        this.cena = cena;
        this.jedinicaMere = jedinicaMere;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public JedMere getJedinicaMere() {
        return jedinicaMere;
    }

    public void setJedinicaMere(JedMere jedinicaMere) {
        this.jedinicaMere = jedinicaMere;
    }

    @Override
    public String toString() {
        if (jedinicaMere == null) {
            return "";
        }
        return naziv + ", " + cena + ", " + jedinicaMere.toString();
    }
//    @Override
//    public String toString() {
//        return naziv;
//    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (id == ((Proizvod) obj).id) {
            return true;
        }
        return false;
    }

    @Override
    public String vratiNazivTabele() {
        return "Proizvod";
    }

    @Override
    public String vratiAtributeSaVrednostima() {
        return "proizvodId=" + id + ", naziv='" + getNaziv() + "', cena="
                + getCena() + ", jedinicaMere='" + getJedinicaMere() + "'";
    }

    @Override
    public List<OpstiDomenskiObjekat> napuni(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lo = new ArrayList<>();
        while (rs.next()) {
            int idS = rs.getInt("proizvodId");
            String nazivS = rs.getString("naziv");
            double cenaS = rs.getDouble("cena");
            String jedMereS = rs.getString("jedinicaMere");

            Proizvod proizvod = new Proizvod(idS, nazivS, cenaS, JedMere.valueOf(jedMereS));
            lo.add(proizvod);
        }
        return lo;
    }

    @Override
    public String vratiNazivKljuca() {
        return "proizvodId";
    }

    @Override
    public Object vratiVrednostKljuca() {
        return id;
    }

    @Override
    public String vratiVrednostiAtributa() {
        return "" + id + ", '" + getNaziv() + "', " + getCena() + ", '" + getJedinicaMere() + "'";
    }

    @Override
    public String vratiKriterijumPretrage() {
        return "naziv='" + naziv + "' OR naziv LIKE '" + naziv + "%'";
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
    public String vratiTabeluStavke() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OpstiDomenskiObjekat vratiJednuStavku() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setujStavke(List<OpstiDomenskiObjekat> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean vrednosnaOgranicenja() {
        if (naziv.isEmpty() || naziv == null || jedinicaMere == null || cena <= 0) {
            return false;
        }
        return true;
    }
}
