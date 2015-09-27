/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.partner;

import domen.PoslovniPartner;
import forme.partner.model.ModelTabelePoslovniPartner;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIPrikazPoslovnihPartnera {
    
    private final List<PoslovniPartner> model;
    private final FmPrikazPartneraTabela view;

    public KontrolerKIPrikazPoslovnihPartnera(Frame mainView) {
        model = vratiPoslovnePartnere(null);
        view = new FmPrikazPartneraTabela(mainView, true, model);
        view.postaviVrednosti();
    }
    
    public void pokreniFormu(){
        postaviOsluskivace();
        prikaziFormu();
    }

    public static List<PoslovniPartner> vratiPoslovnePartnere(Dialog dialog) {
        try {
            TransferObjekat kto = new TransferObjekat();
            kto.setOperacija(Operacije.VRATI_SVE_POSLOVNE_PARTNERE);
            kto.setParametar(new PoslovniPartner());
            Komunikacija.vratiObjekat().posalji(kto);
            TransferObjekat sto = Komunikacija.vratiObjekat().procitaj();
            if (sto.getIzuzetak() != null) {
                throw (Exception)sto.getIzuzetak();
            }
            List<PoslovniPartner> lista = (List<PoslovniPartner>)sto.getRezultat();
            return lista;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return Collections.EMPTY_LIST;
    }
    
    
    private void prikaziPoslovnePartnere(){
        try {
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.PRETRAZI_POSLOVNE_PARTNERE);
            PoslovniPartner p = new PoslovniPartner();
            p.setNaziv(view.getTxtNaziv1().getText());
            toZahtev.setParametar(p);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                throw (Exception)toOdgovor.getIzuzetak();
            }
            List<PoslovniPartner> lista = (List<PoslovniPartner>)toOdgovor.getRezultat();
            view.getTblPartneri().setModel(new ModelTabelePoslovniPartner(lista));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziPodatke(PoslovniPartner poslovniPartner){
        try {
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.PRIKAZI_POSLOVNOG_PARTNERA);
            toZahtev.setParametar(poslovniPartner);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = (TransferObjekat) Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
                PoslovniPartner p = (PoslovniPartner) toOdgovor.getRezultat();
                KontorlerKIUnosPoslovnogPartnera k = new KontorlerKIUnosPoslovnogPartnera((Frame) view.getParent(), p);
                k.pokreniFormu();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void postaviOsluskivace() {
        view.getBtnPretraga1().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                prikaziPoslovnePartnere();
            }
        });
        
        view.getBtnIzmeni().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (view.getTblPartneri().getSelectedRow() == -1) {
                    return;
                }
                try {
                    PoslovniPartner poslovniPartner = ((ModelTabelePoslovniPartner) view.getTblPartneri().getModel()).
                            getPoslovniPartner(view.getTblPartneri().getSelectedRow());

                    prikaziPodatke(poslovniPartner);
                    prikaziPoslovnePartnere();
                    view.getBtnIzmeni().setEnabled(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        view.getTblPartneri().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (view.getTblPartneri().getSelectedRow() != -1) {
                    view.getBtnIzmeni().setEnabled(true);
                }else{
                    view.getBtnIzmeni().setEnabled(false);
                }
            }
        });
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }
    
}
