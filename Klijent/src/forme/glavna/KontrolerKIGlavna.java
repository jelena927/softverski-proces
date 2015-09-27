/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.glavna;

import forme.faktura.KontrolerKIPrikazFaktura;
import forme.faktura.KontrolerKIUnosFakture;
import forme.korisnik.KontrolerKIPrijavaKorisnika;
import forme.mesto.KontrolerKIUnosMesta;
import forme.mesto.KontrolorKIMesta;
import forme.partner.KontorlerKIUnosPoslovnogPartnera;
import forme.partner.KontrolerKIPrikazPoslovnihPartnera;
import forme.proizvod.KontrolerKIPrikazProizvoda;
import forme.proizvod.KontrolerKIUnosProizvoda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import kontroler.KontrolerKI;

/**
 *
 * @author admin
 */
public class KontrolerKIGlavna {
    
    private final FmGlavna view;

    public KontrolerKIGlavna() {
        view = new FmGlavna();
        postaviOsluskivace();
        prikaziFormu();
    }

    private void postaviOsluskivace() {
        view.getBtnOdjava().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                prijavaKorisnika();
                view.setVisible(true);
            }
        });
        
        view.getMiUnosNoveFakture().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontrolerKIUnosFakture k = new KontrolerKIUnosFakture(view);
                k.pokreniFormu();
            }
        });
        
        view.getMiPretragaFaktura().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontrolerKIPrikazFaktura k = new KontrolerKIPrikazFaktura(view);
                k.pokreniFormu();
            }
        });
        
        view.getMiUnosPoslovnihPartnera().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontorlerKIUnosPoslovnogPartnera k = new KontorlerKIUnosPoslovnogPartnera(view);
                k.pokreniFormu();
            }
        });
        
        view.getMiPrikazPoslovnihPartnera().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontrolerKIPrikazPoslovnihPartnera k = new KontrolerKIPrikazPoslovnihPartnera(view);
                k.pokreniFormu();
            }
        });
        
        view.getMiUnosNovogProizvoda().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontrolerKIUnosProizvoda k = new KontrolerKIUnosProizvoda(view);
                k.pokreniFormu();
            }
        });
        
        view.getMiPrikazProizvoda().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontrolerKIPrikazProizvoda k = new KontrolerKIPrikazProizvoda(view);
                k.pokreniFormu();
            }
        });
        
        view.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    KontrolerKI.prekiniKomunikaciju(view);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });
        
        view.getMiUnosNovogMesta().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontrolerKIUnosMesta k = new KontrolerKIUnosMesta(view);
                k.pokreniFormu();
            }
        });
        
        view.getMiPrikazMesta().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                KontrolorKIMesta k = new KontrolorKIMesta(view);
                k.pokreniFormu();
            }
        });
    }

    private void prikaziFormu() {
        prijavaKorisnika();
        view.setVisible(true);
    }
    
    private void prijavaKorisnika() {
        view.getLblPoruka().setVisible(false);
        view.getBtnOdjava().setVisible(false);
        KontrolerKIPrijavaKorisnika k = new KontrolerKIPrijavaKorisnika(view);
        k.pokreniFormu();
        if (KontrolerKIPrijavaKorisnika.getKorisnik() != null) {
            view.getLblPoruka().setText("Ulogovani ste kao: " + KontrolerKIPrijavaKorisnika.getKorisnik().getIme()
                    + " " + KontrolerKIPrijavaKorisnika.getKorisnik().getPrezime());
            view.getLblPoruka().setVisible(true);
            view.getBtnOdjava().setVisible(true);
        } else {
            try {
                KontrolerKI.prekiniKomunikaciju(view);
                System.exit(0);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        KontrolerKIGlavna kIGlavna = new KontrolerKIGlavna();
    }
}
