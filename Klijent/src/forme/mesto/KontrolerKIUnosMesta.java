/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.mesto;

import domen.Mesto;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import konverter.KonverterTipova;
import transfer.TransferObjekat;

/**
 *
 * @author student1
 */
public class KontrolerKIUnosMesta {
    
    private final Mesto model;
    private final FmUnosMesta view;

    public KontrolerKIUnosMesta(Frame mainFrame) {
        this.model = new Mesto();
        this.view = new FmUnosMesta(mainFrame, true, model);
    }
      
    public void pokreniFormu(){
        postaviOsluskivace();
        prikaziFormu();
    }
    
    private void unosMesta(){
        try {
            model.setNaziv(KonverterTipova.konvertuj(view.getJtxtNaziv(), String.class));
            
            if (model.getNaziv().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            int ptt = 0;
            try {
                ptt = KonverterTipova.konvertuj(view.getJtxtPtt(), Integer.class);
            } catch (Exception e) {
                throw new Exception("Poštanski broj sadrži samo cifre.");
            }
            model.setPtt(ptt);
            
            //formiranje paketa
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_MESTO);
            toZahtev.setParametar(model);
            
            //slanje zahteva
            Komunikacija.vratiObjekat().posalji(toZahtev);
            
            //prijem odgovora
            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            String poruka = null;
            if (toOdogovor.getIzuzetak() != null) {
                poruka = ((Exception)toOdogovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(view, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                poruka = (String) toOdogovor.getPoruka();
                view.obrisiPolja();
                JOptionPane.showMessageDialog(view, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }

    private void postaviOsluskivace() {
        view.getJbtnSnimi().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                unosMesta();
            }
        });
    }
}
