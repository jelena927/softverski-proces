/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.proizvod;

import domen.Proizvod;
import forme.proizvod.model.ModelTabeleProizvod;
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
public class KontrolerKIPrikazProizvoda {
    
    private final List<Proizvod> model;
    private final FmPrikazProizvoda view;

    public KontrolerKIPrikazProizvoda(Frame mainView) {
        model = vratiProizvode(null);
        view = new FmPrikazProizvoda(mainView, true, model);
        view.postaviVrednosti();
    }
    
    public static List<Proizvod> vratiProizvode(Dialog dialog) {
        try {
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.VRATI_SVE_PROIZVODE);
            toZahtev.setParametar(new Proizvod());
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                throw (Exception)toOdgovor.getIzuzetak();
            }
            List<Proizvod> lista = (List<Proizvod>)toOdgovor.getRezultat();
            return lista;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return Collections.EMPTY_LIST;
    }

    private void prikaziProizvode() {
        try {
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.PRETRAZI_PROIZVODE);
            Proizvod p = new Proizvod();
            p.setNaziv(view.getTxtNaziv().getText());
            toZahtev.setParametar(p);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                throw (Exception)toOdgovor.getIzuzetak();
            }
            List<Proizvod> lista = (List<Proizvod>)toOdgovor.getRezultat();
            view.getTblProizvodi().setModel(new ModelTabeleProizvod(lista));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziPodatke(Proizvod proizvod) {
        try {
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.PRIKAZI_PROIZVOD);
            toZahtev.setParametar(proizvod);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = (TransferObjekat) Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
                Proizvod p = (Proizvod) toOdgovor.getRezultat();
                KontrolerKIUnosProizvoda k = new KontrolerKIUnosProizvoda((Frame) view.getParent(), p);
                k.pokreniFormu();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void pokreniFormu() {
        postaviOsluskivace();
        prikaziFormu();
    }

    private void postaviOsluskivace() {
        view.getBtnPretraga().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                prikaziProizvode();
            }
        });
        
        view.getBtnIzmeniProizvod().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (view.getTblProizvodi().getSelectedRow() == -1) {
                    return;
                }
                try {
                    Proizvod proizvod = ((ModelTabeleProizvod) view.getTblProizvodi().getModel()).
                            getProizvod(view.getTblProizvodi().getSelectedRow());

                    prikaziPodatke(proizvod);
                    ((ModelTabeleProizvod) view.getTblProizvodi().getModel()).azurirajTabelu(vratiProizvode(view));
                    view.getBtnIzmeniProizvod().setEnabled(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        view.getTblProizvodi().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (view.getTblProizvodi().getSelectedRow() != -1) {
                    view.getBtnIzmeniProizvod().setEnabled(true);
                }else{
                    view.getBtnIzmeniProizvod().setEnabled(false);
                }
            }
            
        });
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }
}
