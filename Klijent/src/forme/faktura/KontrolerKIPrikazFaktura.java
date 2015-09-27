/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.faktura;

import domen.Faktura;
import domen.PoslovniPartner;
import forme.faktura.model.ModelTableFaktura;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIPrikazFaktura {
    
    private List<Faktura> model;
    private final FmPretragaFaktura view;

    public KontrolerKIPrikazFaktura(Frame mainView) {
        model = vratiFakture();
        view = new FmPretragaFaktura(mainView, true, model);
        view.postaviVrednosti();
    }

    private void prikaziFakture() {
        try {
            TransferObjekat<Faktura> toZahtev = new TransferObjekat<>();
            toZahtev.setOperacija(Operacije.PRETRAZI_FAKTURE);
            Faktura f = new Faktura();
            PoslovniPartner partner = (PoslovniPartner) view.getCbPartneri().getSelectedItem();
            f.setPoslovniPartner(partner);
            toZahtev.setParametar(f);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                throw (Exception) toOdgovor.getIzuzetak();
            }
            List<Faktura> lista = (List<Faktura>) toOdgovor.getRezultat();
            view.getTblFakture().setModel(new ModelTableFaktura(lista));
        } catch (Exception ex) {
            Logger.getLogger(KontrolerKIPrikazFaktura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Faktura> vratiFakture() {
        try {
            TransferObjekat<Faktura> kto = new TransferObjekat<>();
            kto.setOperacija(Operacije.VRATI_SVE_FAKTURE);
            kto.setParametar(new Faktura());
            Komunikacija.vratiObjekat().posalji(kto);
            TransferObjekat sto = Komunikacija.vratiObjekat().procitaj();
            if (sto.getIzuzetak() != null) {
                throw (Exception) sto.getIzuzetak();
            }
            List<Faktura> lista = (List<Faktura>) sto.getRezultat();
            
            return lista;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return Collections.EMPTY_LIST;
    }
    
    
    public void pokreniFormu() {
        postaviOsluskivace();
        prikaziFormu();
    }

    private void postaviOsluskivace() {
        view.getBtnPretraga1().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                prikaziFakture();
            }
        });
        
        view.getTblFakture().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (view.getTblFakture().getSelectedRow() != -1) {
                    view.getBtnPrikazi().setEnabled(true);
                }else{
                    view.getBtnPrikazi().setEnabled(false);
                }
            }
            
        });
        
        view.getBtnPrikazi().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (view.getTblFakture().getSelectedRow() == -1) {
                    return;
                }
                Faktura faktura = ((ModelTableFaktura) view.getTblFakture().getModel()).
                getFaktura(view.getTblFakture().getSelectedRow());
                prikaziPodatke(faktura);
                
                ((ModelTableFaktura) view.getTblFakture().getModel()).azurirajTabelu(vratiFakture());
                view.getBtnPrikazi().setEnabled(false);
            }
        });
    }
    
    
    private void prikaziPodatke(Faktura faktura) {
        try {
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.PRIKAZI_FAKTURU);
            toZahtev.setParametar(faktura);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = (TransferObjekat) Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
                Faktura p = (Faktura) toOdgovor.getRezultat();
                KontrolerKIUnosFakture k = new KontrolerKIUnosFakture((Frame) view.getParent(), p);
                k.pokreniFormu();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }
}
