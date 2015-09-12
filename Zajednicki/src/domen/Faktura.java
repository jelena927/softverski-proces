/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jelena
 */
public class Faktura implements Serializable, OpstiDomenskiObjekat {

    private int idFakture;
    private String brojFakture;
    private PoslovniPartner poslovniPartner;
    private double ukupnaVrednost;
    private boolean obradjena;
    private boolean stornirana;
    private List<StavkaFakture> stavke;
    private double osnovica;
    private double pdv;
    private String brojOtpremnice;
    private Date datum;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Faktura() {
        stavke = new ArrayList<>();
    }

    public Faktura(int idFakture, String brojFakture, PoslovniPartner poslovniPartnerId, double ukupnaVrednost, boolean obradjena, boolean stornirana, List<StavkaFakture> stavke, double osnovica, double pdv, String brojOtpremnice, Date datum) {
        this.idFakture = idFakture;
        this.brojFakture = brojFakture;
        this.poslovniPartner = poslovniPartnerId;
        this.ukupnaVrednost = ukupnaVrednost;
        this.obradjena = obradjena;
        this.stornirana = stornirana;
        this.stavke = stavke;
        this.osnovica = osnovica;
        this.pdv = pdv;
        this.brojOtpremnice = brojOtpremnice;
        this.datum = datum;
    }

    public String getBrojFakture() {
        return brojFakture;
    }

    public void setBrojFakture(String brojFakture) {
        this.brojFakture = brojFakture;
    }

    public PoslovniPartner getPoslovniPartner() {
        return poslovniPartner;
    }

    public void setPoslovniPartner(PoslovniPartner poslovniPartnerId) {
        this.poslovniPartner = poslovniPartnerId;
    }

    public double getUkupnaVrednost() {
        return ukupnaVrednost;
    }

    public void setUkupnaVrednost(double ukupnaVrednost) {
        this.ukupnaVrednost = ukupnaVrednost;
    }

    public boolean isObradjena() {
        return obradjena;
    }

    public void setObradjena(boolean obradjena) {
        this.obradjena = obradjena;
    }

    public boolean isStornirana() {
        return stornirana;
    }

    public void setStornirana(boolean stornirana) {
        this.stornirana = stornirana;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Faktura other = (Faktura) obj;
        if ((this.brojFakture == null) ? (other.brojFakture != null) : !this.brojFakture.equals(other.brojFakture)) {
            return false;
        }
        return true;
    }

    public List<StavkaFakture> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaFakture> stavke) {
        this.stavke = stavke;
    }

    public double getOsnovica() {
        return osnovica;
    }

    public void setOsnovica(double osnovica) {
        this.osnovica = osnovica;
    }

    public double getPdv() {
        return pdv;
    }

    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

    public String getBrojOtpremnice() {
        return brojOtpremnice;
    }

    public void setBrojOtpremnice(String brojOtpremnice) {
        this.brojOtpremnice = brojOtpremnice;
    }

    @Override
    public String vratiNazivKljuca() {
        return "idFakture";
    }

    @Override
    public Object vratiVrednostKljuca() {
        return idFakture;
    }

    @Override
    public String vratiNazivTabele() {
        return "Faktura";
    }

    @Override
    public String vratiAtributeSaVrednostima() {
        return "idFakture=" + idFakture + ", brojFakture='" + brojFakture + "', brojOtpremnice='" + getBrojOtpremnice() + "', datum='"
                + getDatum() + "', osnovica=" + getOsnovica() + ", ukupnoPdv=" + getPdv() + ", ukupnaVrednost="
                + getUkupnaVrednost() + ", obradjena=" + isObradjena() + ", stornirana=" + isStornirana()
                + ", poslovniPartnerId=" + getPoslovniPartner().getId();
    }

    @Override
    public List<OpstiDomenskiObjekat> napuni(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lo = new ArrayList<>();
        while (rs.next()) {
            int idFaktureS = rs.getInt("idFakture");
            String brojFaktureS = rs.getString("brojFakture");
            String brojOtpremniceS = rs.getString("brojOtpremnice");
            Date datumS = rs.getDate("datum");
            double osnovicaS = rs.getDouble("osnovica");
            double pdvS = rs.getDouble("ukupnoPdv");
            double ukupnoS = rs.getDouble("ukupnaVrednost");
            boolean obradjenaS = rs.getBoolean("obradjena");
            boolean storniranaS = rs.getBoolean("stornirana");
            int poslovniPartnerIdS = rs.getInt("poslovniPartnerId");

            Faktura faktura = new Faktura(idFaktureS, brojFaktureS, new PoslovniPartner(poslovniPartnerIdS, null, null, null, null),
                    ukupnoS, obradjenaS, storniranaS, new ArrayList<StavkaFakture>(),
                    osnovicaS, pdvS, brojOtpremniceS, datumS);
            lo.add(faktura);
        }
        return lo;
    }

    @Override
    public String vratiVrednostiAtributa() {
        return idFakture + ",'" + brojFakture + "', '" + getBrojOtpremnice() + "', '" + getDatum() + "', "
                + getOsnovica() + ", " + getPdv() + ", " + getUkupnaVrednost() + ", "
                + isObradjena() + ", " + isStornirana() + ", " + getPoslovniPartner();
    }

    @Override
    public String vratiKriterijumPretrage() {
        return "poslovniPartnerId=" + poslovniPartner.getId();
    }

    public void dodajPraznuStavku() {
        int rbStavke = stavke.size() + 1;
        StavkaFakture s = new StavkaFakture(rbStavke, 0, 0, 0, 0, 0.20, 0, 0, new Proizvod());
        stavke.add(s);
    }

    @Override
    public int vratiBrojStavki() {
        return stavke.size();
    }

    @Override
    public OpstiDomenskiObjekat vratiStavku(int rb) {
        return stavke.get(rb);
    }

    @Override
    public OpstiDomenskiObjekat vratiVezaniObjekat() {
        return poslovniPartner;
    }

    @Override
    public void setVezaniObjekat(OpstiDomenskiObjekat odo) {
        poslovniPartner = (PoslovniPartner) odo;
    }

    public int getIdFakture() {
        return idFakture;
    }

    public void setIdFakture(int idFakture) {
        this.idFakture = idFakture;
    }

    @Override
    public String vratiTabeluStavke() {
        return "StavkaFakture";
    }

    @Override
    public OpstiDomenskiObjekat vratiJednuStavku() {
        return new StavkaFakture();
    }

    
    @Override
    public void setujStavke(List<OpstiDomenskiObjekat> lista) {
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : lista) {
            this.stavke.add((StavkaFakture) opstiDomenskiObjekat);
        }
    }

    @Override
    public boolean vrednosnaOgranicenja() {
        if (brojFakture.isEmpty() || brojFakture == null || datum == null ||
            poslovniPartner == null || brojOtpremnice.isEmpty() || brojOtpremnice == null) {
            return false;
        }
        double osnovicaZaProveru = 0;
        double pdvZaProveru = 0;
        double ukupnoZaProveru = 0;
        for (StavkaFakture stavkaFakture : stavke) {
            osnovicaZaProveru += stavkaFakture.getPoreskaOsnovica();
            pdvZaProveru += stavkaFakture.getIznosPdva();
        }
        ukupnoZaProveru = osnovicaZaProveru + pdvZaProveru;
        if (osnovica != osnovicaZaProveru || pdv != pdvZaProveru || ukupnaVrednost != ukupnoZaProveru) {
            return false;
        }
        return true;
    }
}
