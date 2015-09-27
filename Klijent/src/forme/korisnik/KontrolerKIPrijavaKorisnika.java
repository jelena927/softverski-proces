/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.korisnik;

import domen.Korisnik;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import konverter.KonverterTipova;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIPrijavaKorisnika {
    
    private static Korisnik korisnik;
    private final FmPrijavaKorisnika view;

    public KontrolerKIPrijavaKorisnika(Frame mainView) {
        korisnik = null;
        view = new FmPrijavaKorisnika(mainView, true);
    }

    private void prijaviKorisnika() {
        try {
            korisnik = new Korisnik();
            korisnik.setKorisnickoIme(KonverterTipova.konvertuj(view.getTxtIme(), String.class));
            korisnik.setKorisnickaSifra(String.valueOf(view.getTxtSifra().getPassword()));
            
            if (korisnik.getKorisnickoIme().isEmpty() || korisnik.getKorisnickaSifra().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.PRIJAVI_KORISNIKA);
            toZahtev.setParametar(korisnik);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            
            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            if (toOdgovor.getIzuzetak() != null) {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
                if (toOdgovor.getPoruka().equals("Dostignut maksimalan broj klijenata.")) {
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(view, toOdgovor.getPoruka(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
                korisnik = (Korisnik) toOdgovor.getRezultat();
                view.setVisible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Korisnik getKorisnik() {
        return korisnik;
    }

    public static void setKorisnik(Korisnik korisnik) {
        KontrolerKIPrijavaKorisnika.korisnik = korisnik;
    }

      
    public void pokreniFormu(){
        postaviOsluskivace();
        prikaziFormu();
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }

    private void postaviOsluskivace() {
        view.getBtnPrijava().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                prijaviKorisnika();
            }
        });
        
        view.getBtnPrijava().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                    prijaviKorisnika();
                }
            }
            
        }); 
    }
    
}
