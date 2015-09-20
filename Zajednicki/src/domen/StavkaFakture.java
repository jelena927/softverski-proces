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
public class StavkaFakture implements Serializable, OpstiDomenskiObjekat {
    //private Faktura faktura;

    private int rb;
    private double kolicina;
    private double rabat;
    private double iznosRabata;
    private double poreskaOsnovica;
    private double pdv;
    private double iznosPdva;
    private double vrednostSaPdvom;
    private Proizvod proizvod;

    public StavkaFakture() {
        pdv = 0.20;
    }

    public StavkaFakture(int rb, double kolicina, double rabat, double iznosRabata, double poreskaOsnovica, double pdv, double iznosPdva, double vrednostSaPdvom, Proizvod proizvod) {
        this.rb = rb;
        this.kolicina = kolicina;
        this.rabat = rabat;
        this.iznosRabata = iznosRabata;
        this.poreskaOsnovica = poreskaOsnovica;
        this.pdv = pdv;
        this.iznosPdva = iznosPdva;
        this.vrednostSaPdvom = vrednostSaPdvom;
        this.proizvod = proizvod;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public double getRabat() {
        return rabat;
    }

    public void setRabat(double rabat) {
        this.rabat = rabat;
    }

    public double getIznosRabata() {
        return iznosRabata;
    }

    public void setIznosRabata(double iznosRabata) {
        this.iznosRabata = iznosRabata;
    }

    public double getPoreskaOsnovica() {
        return poreskaOsnovica;
    }

    public void setPoreskaOsnovica(double poreskaOsnovica) {
        this.poreskaOsnovica = poreskaOsnovica;
    }

    public double getPdv() {
        return pdv;
    }

    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

    public double getIznosPdva() {
        return iznosPdva;
    }

    public void setIznosPdva(double iznosPdva) {
        this.iznosPdva = iznosPdva;
    }

    public double getVrednostSaPdvom() {
        return vrednostSaPdvom;
    }

    public void setVrednostSaPdvom(double vrednostSaPdvom) {
        this.vrednostSaPdvom = vrednostSaPdvom;
    }

    @Override
    public String vratiNazivTabele() {
        return "StavkaFakture";
    }

    @Override
    public String vratiAtributeSaVrednostima() {
        return "rb=" + rb + ", kolicina=" + getKolicina() + ", rabat="
                + getRabat() + ", iznosRabata=" + getIznosRabata() + ", poreskaOsnovica=" + getPoreskaOsnovica()
                + ", pdv=" + getPdv() + ", iznosPdva=" + getIznosPdva() + ", vrednostSaPdvom=" + getVrednostSaPdvom()
                + ", proizvodId=" + getProizvod().getId();
    }

    @Override
    public List<OpstiDomenskiObjekat> napuni(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lo = new ArrayList<>();
        while (rs.next()) {
            int rbS = rs.getInt("rb");
            double kolicinaS = rs.getDouble("kolicina");
            double rabatS = rs.getDouble("rabat");
            double iznosRabataS = rs.getDouble("iznosRabata");
            double poreskaOsnovicaS = rs.getDouble("poreskaOsnovica");
            double pdvS = rs.getDouble("pdv");
            double iznosPdvaS = rs.getDouble("iznosPdva");
            double vrednostSaPdvomS = rs.getDouble("vrednostSaPdvom");
            int proizvodIdS = rs.getInt("proizvodId");

            StavkaFakture stavkaFakture = new StavkaFakture(rbS, kolicinaS, rabatS,
                    iznosRabataS, poreskaOsnovicaS, pdvS, iznosPdvaS, vrednostSaPdvomS,
                    new Proizvod(proizvodIdS));
            lo.add(stavkaFakture);
        }
        return lo;
    }

    @Override
    public String vratiNazivKljuca() {
        return "rb";
    }

    @Override
    public Object vratiVrednostKljuca() {
        return rb;
    }

    @Override
    public String vratiVrednostiAtributa() {
        return "" + rb + ", " + getKolicina() + ", " + getRabat() + ", " + getIznosRabata()
                + ", " + getPoreskaOsnovica() + ", " + getPdv() + ", " + getIznosPdva()
                + ", " + getVrednostSaPdvom() + ", " + getProizvod().getId();
    }

    @Override
    public String vratiKriterijumPretrage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return proizvod;
    }

    @Override
    public void setVezaniObjekat(OpstiDomenskiObjekat odo) {
        proizvod = (Proizvod) odo;
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
        if (rb <= 0 || kolicina <= 0 || rabat <= 0 || rabat > 1 || pdv <= 0 || pdv > 1 
            || proizvod == null){
            return false;
        }
        if (iznosRabata != kolicina * proizvod.getCena() * rabat) {
            return false;
        }
        if (poreskaOsnovica != kolicina * proizvod.getCena() - iznosRabata) {
            return false;
        }
        if (iznosPdva != poreskaOsnovica * pdv) {
            return false;
        }
        if (vrednostSaPdvom != poreskaOsnovica + iznosPdva) {
            return false;
        }
        return true;
    }
}
