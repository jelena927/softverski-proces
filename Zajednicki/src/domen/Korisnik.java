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
 * @author student1
 */
public class Korisnik implements Serializable, OpstiDomenskiObjekat{

    private int id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String korisnickaSifra;
    private String email;

    public Korisnik() {
    }

    public Korisnik(String korisnickoIme, String korisnickaSifra) {
        this.korisnickoIme = korisnickoIme;
        this.korisnickaSifra = korisnickaSifra;
    }

    public Korisnik(int id, String ime, String prezime, String korisnickoIme, String korisnickaSifra, String email) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.korisnickaSifra = korisnickaSifra;
        this.email = email;
    }
    
    @Override
    public String vratiNazivTabele() {
        return "Korisnik";
    }

    @Override
    public String vratiAtributeSaVrednostima() {
        return "id=" + id + ", ime='" + ime + "', prezime='"
                + prezime + "', korisnickoIme='" + korisnickoIme + "', korisnickaSifra='" + korisnickaSifra
                + "', email='" + email + "'";
    }

    @Override
    public List<OpstiDomenskiObjekat> napuni(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lo = new ArrayList<>();
        while (rs.next()) {
            int idS = rs.getInt("id");
            String imeS = rs.getString("ime");
            String prezimeS = rs.getString("prezime");
            String korisnickoImeS = rs.getString("korisnickoIme");
            String korisnickaSifraS = rs.getString("korisnickaSifra");
            String emailS = rs.getString("email");

            Korisnik k = new Korisnik(idS, imeS, prezimeS, korisnickoImeS, korisnickaSifraS, emailS);
            lo.add(k);
        }
        return lo;
    }

    @Override
    public String vratiNazivKljuca() {
        return "id";
    }

    @Override
    public Object vratiVrednostKljuca() {
        return id;
    }

    @Override
    public String vratiVrednostiAtributa() {
        return id + ", '" + ime + "', '" + prezime + "', '" + korisnickoIme + "', '"
                + korisnickaSifra + "', '" + email + "'";
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
        return null;
    }

    @Override
    public void setVezaniObjekat(OpstiDomenskiObjekat odo) {
    }

    @Override
    public String vratiKriterijumPretrage() {
        return "korisnickoIme='" + korisnickoIme + "' AND korisnickaSifra='" + korisnickaSifra + "'";
    }

    @Override
    public String vratiTabeluStavke() {
        return null;
    }

    @Override
    public OpstiDomenskiObjekat vratiJednuStavku() {
        return null;
    }

    @Override
    public <T extends OpstiDomenskiObjekat> void setujStavke(List<T> lista) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getKorisnickaSifra() {
        return korisnickaSifra;
    }

    public void setKorisnickaSifra(String korisnickaSifra) {
        this.korisnickaSifra = korisnickaSifra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean vrednosnaOgranicenja() {
        return !(ime.isEmpty() || ime == null || prezime.isEmpty() || prezime == null
                || email.isEmpty() || email == null || korisnickoIme.isEmpty() ||
                korisnickoIme == null || korisnickaSifra.isEmpty() ||
                korisnickaSifra == null);
    }
    
}
