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
public class PoslovniPartner implements Serializable, OpstiDomenskiObjekat {

    private int id;
    private String pib;
    private String naziv;
    private String kontakt;
    private Adresa adresa;

    public PoslovniPartner() {
    }

    public PoslovniPartner(int id, String pib, String naziv, String kontakt, Adresa adresa) {
        this.id = id;
        this.pib = pib;
        this.naziv = naziv;
        this.kontakt = kontakt;
        this.adresa = adresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == null || obj == null) {
            return false;
        }
        if (id == ((PoslovniPartner)obj).id) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String vratiNazivTabele() {
        return "PoslovniPartner";
    }

    @Override
    public String vratiAtributeSaVrednostima() {
        return "poslovniPartnerId=" + id + ", pib='" + getPib() + "', naziv='"
                + getNaziv() + "', kontakt='" + getKontakt() + "', ulica='" + getAdresa().getUlica()
                + "', broj='" + getAdresa().getBroj() + "', ptt=" + getAdresa().getMesto().getPtt();
    }

    @Override
    public List<OpstiDomenskiObjekat> napuni(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lo = new ArrayList<>();
        while (rs.next()) {
            int idS = rs.getInt("poslovniPartnerId");
            String pibS = rs.getString("pib");
            String nazivS = rs.getString("naziv");
            String kontaktS = rs.getString("kontakt");
            String ulica = rs.getString("ulica");
            String broj = rs.getString("broj");
            int ptt = rs.getInt("ptt");

            PoslovniPartner poslovniPartner = new PoslovniPartner(idS, pibS, nazivS, kontaktS, new Adresa(ulica, broj, new Mesto(ptt, null)));
            lo.add(poslovniPartner);
        }
        return lo;
    }

    @Override
    public String vratiNazivKljuca() {
        return "poslovniPartnerId";
    }

    @Override
    public Object vratiVrednostKljuca() {
        return id;
    }

    @Override
    public String vratiVrednostiAtributa() {
        return id + ", '" + pib + "', '" + naziv + "', '" + kontakt + "', '"
                + adresa.getUlica() + "', '" + adresa.getBroj() + "', " + adresa.getMesto().getPtt();
    }

    @Override
    public String vratiKriterijumPretrage() {
        return "naziv='" + naziv + "' OR naziv LIKE '" + naziv + "%'";
    }

    @Override
    public int vratiBrojStavki() {
        return 0;
    }

    @Override
    public OpstiDomenskiObjekat vratiStavku(int rb) {
        return null;
    }

    @Override
    public OpstiDomenskiObjekat vratiVezaniObjekat() {
        return adresa.getMesto();
    }

    @Override
    public void setVezaniObjekat(OpstiDomenskiObjekat odo) {
        adresa.setMesto((Mesto) odo);
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
        if (adresa == null || pib.isEmpty() || pib == null || naziv.isEmpty() ||
                naziv == null || kontakt.isEmpty() || kontakt == null) {
            return false;
        }
        return true;
    }
}
