/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.faktura;

import domen.Faktura;
import domen.PoslovniPartner;
import domen.StavkaFakture;
import forme.faktura.model.ModelTabeleStavkaFakture;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import klijent.Komunikacija;
import konstante.Operacije;
import kontroler.KontrolerKI;
import konverter.KonverterTipova;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIUnosFakture {

    private final Faktura model;
    private final FmUnosFakture view;

    public KontrolerKIUnosFakture(Frame  mainView) {
        this.model = new Faktura();
        this.view = new FmUnosFakture(mainView, true, model);
        kreirajFakturu();
        view.popuniPodatke(false);
    }

    KontrolerKIUnosFakture(Frame mainView, Faktura faktura) {
        this.model = faktura;
        this.view = new FmUnosFakture(mainView, true, model);
        view.popuniPodatke(true);
    }
    
    public void pokreniFormu(){
        postaviOsluskivace();
        prikaziFormu();
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }
    
    private void kreirajFakturu() {
        try {
            int id = KontrolerKI.generisiID(model);
            model.setIdFakture(id);
            
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(konstante.Operacije.KREIRAJ_FAKTURU);
            toZahtev.setParametar(model);
            
            Komunikacija.vratiObjekat().posalji(toZahtev);
            
            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            String poruka = null;
            if (toOdgovor.getIzuzetak() != null) {
                poruka = ((Exception) toOdgovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(view, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                poruka = (String) toOdgovor.getPoruka();
                JOptionPane.showMessageDialog(view, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void zapamtiFakturu(int operacija) {
        try {
            model.setBrojFakture(KonverterTipova.konvertuj(view.getTxtBrojFakture(), String.class));
            model.setBrojOtpremnice(KonverterTipova.konvertuj(view.getTxtBrojOtpremnice(), String.class));
            model.setBrojFakture(KonverterTipova.konvertuj(view.getTxtBrojFakture(), String.class));
            
            if (model.getBrojFakture().isEmpty() || model.getBrojOtpremnice().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            String datumS = null;
            Date d = null;
            try {
                datumS = KonverterTipova.konvertuj(view.getTxtDatum(), String.class);
                d = new SimpleDateFormat("dd/MM/yyyy").parse(datumS);
            } catch (Exception e) {
                throw new Exception("Unesite datum u formatu dd/MM/yyyy");
            }
            model.setDatum(new java.sql.Date(d.getTime()));
            
            model.setObradjena(view.getCheckObradjena().isSelected());
            model.setStornirana(view.getCheckStornirana().isSelected());
            model.setPoslovniPartner(KonverterTipova.konvertuj(view.getCbPoslovniPartner(), PoslovniPartner.class));
            
            List<StavkaFakture> stavke = ((ModelTabeleStavkaFakture) view.getTblStavkeFakture().getModel()).vratiStavke();

            if (stavke.isEmpty()) {
                throw new Exception("Faktura mora imati bar jednu stavku.");
            }
            
            for (StavkaFakture stavkaFakture : stavke) {
                if (stavkaFakture.getProizvod() == null) {
                    throw new Exception("Morate uneti proizvod za svaku stavku");
                }
                if (stavkaFakture.getKolicina() <= 0) {
                    throw new Exception("Količina mora biti broj veći od nule.");
                }
                if (stavkaFakture.getRabat() < 0 || stavkaFakture.getRabat() > 1) {
                    throw new Exception("Rabat mora biti broj između 0 i 1.");
                }
                if (stavkaFakture.getPdv()< 0 || stavkaFakture.getPdv() > 1) {
                    throw new Exception("PDV mora biti broj između 0 i 1.");
                }
            }
            model.setStavke(stavke);
            
            model.setOsnovica(KonverterTipova.konvertuj(view.getTxtOsnovica(), Double.class));
            model.setPdv(KonverterTipova.konvertuj(view.getTxtPdv(), Double.class));
            model.setUkupnaVrednost(KonverterTipova.konvertuj(view.getTxtUkupnaVrednost(), Double.class));

            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(operacija);
            toZahtev.setParametar(model);

            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            String poruka = null;
            if (toOdogovor.getIzuzetak() != null) {
                poruka = ((Exception) toOdogovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(view, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                poruka = (String) toOdogovor.getPoruka();
                view.setSacuvana();
                JOptionPane.showMessageDialog(view, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postaviOsluskivace() {
        view.getBtnDodajStavku().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ((ModelTabeleStavkaFakture) view.getTblStavkeFakture().getModel()).dodajStavkuFakture();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        view.getTblStavkeFakture().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (view.getTblStavkeFakture().getModel() instanceof DefaultTableModel) {
                    return;
                }
                try{
                    view.getTxtOsnovica().setText(((ModelTabeleStavkaFakture) view.getTblStavkeFakture().
                            getModel()).vratiPoreskuOsnovicu());
                    view.getTxtPdv().setText(((ModelTabeleStavkaFakture) view.getTblStavkeFakture().
                            getModel()).vratiPDV());
                    view.getTxtUkupnaVrednost().setText(((ModelTabeleStavkaFakture) view.getTblStavkeFakture().
                            getModel()).vratiUkupnuVrednostFakture(view.getTxtOsnovica(), view.getTxtPdv()));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        
        view.getBtnSacuvaj().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                zapamtiFakturu(Operacije.SACUVAJ_FAKTURU);
            }
        });
        
        view.getBtnIzmeni().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                zapamtiFakturu(Operacije.SACUVAJ_FAKTURU);
            }
        });
        
        view.getTblStavkeFakture().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (view.getTblStavkeFakture().getModel() instanceof DefaultTableModel) {
                    return;
                }
                try{
                view.getTxtOsnovica().setText(((ModelTabeleStavkaFakture) view.getTblStavkeFakture().
                        getModel()).vratiPoreskuOsnovicu());
                view.getTxtPdv().setText(((ModelTabeleStavkaFakture) view.getTblStavkeFakture().
                        getModel()).vratiPDV());
                view.getTxtUkupnaVrednost().setText(((ModelTabeleStavkaFakture) view.getTblStavkeFakture().
                        getModel()).vratiUkupnuVrednostFakture(view.getTxtOsnovica(), view.getTxtPdv()));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        
        view.getBtnObrisiStavku().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (view.getTblStavkeFakture().getSelectedRow() == -1) {
                    return;
                }
                ((ModelTabeleStavkaFakture) view.getTblStavkeFakture().getModel()).obrisiStavku(view.getTblStavkeFakture().getSelectedRow());
            }
        });
        
        view.getBtnObradi().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                view.getCheckObradjena().setSelected(true);
                zapamtiFakturu(Operacije.OBRADI_FAKTURU);
                view.setObradjena();
            }
        });
        
        view.getBtnStorniraj().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                view.getCheckStornirana().setSelected(true);
                zapamtiFakturu(Operacije.STORNIRAJ_FAKTURU);
                view.setStornirana();
            }
        });
    }
}
