/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.proizvod;

import domen.JedMere;
import domen.Proizvod;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import klijent.Komunikacija;
import konstante.Operacije;
import kontroler.KontrolerKI;
import konverter.KonverterTipova;
import transfer.TransferObjekat;

/**
 *
 * @author Jelena
 */
public class KontrolerKIUnosProizvoda {

    private final Proizvod model;
    private final FmUnosProizvoda view;

    public KontrolerKIUnosProizvoda(Frame mainView) {
        this.model = new Proizvod();
        this.view = new FmUnosProizvoda(mainView, true, model);
        kreirajProizvod();
        view.popuniPodatke(false);
    }

    KontrolerKIUnosProizvoda(Frame frame, Proizvod p) {
        model = p;
        view = new FmUnosProizvoda(frame, true, model);
        view.popuniPodatke(true);
    }
      
    public void pokreniFormu(){
        postaviOsluskivace();
        prikaziFormu();
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }
    
    private void kreirajProizvod() {
        try {
            int proizvodId = KontrolerKI.generisiID(model);
            model.setId(proizvodId);
            
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.KREIRAJ_PROIZVOD);
            toZahtev.setParametar(model);
            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdgovor = Komunikacija.vratiObjekat().procitaj();
            
            String poruka = toOdgovor.getPoruka();
            if (toOdgovor.getIzuzetak() != null) {
                poruka += "\n" + ((Exception)toOdgovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(view, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sacuvajProizvod() {
        try {
            model.setNaziv(KonverterTipova.konvertuj(view.getTxtNaziv(),String.class));
            if (model.getNaziv().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            double cena = 0;
            try {
                cena = KonverterTipova.konvertuj(view.getTxtCena(), Double.class);
            } catch (Exception e) {
                throw new Exception("Cena mora biti broj.");
            }
            if (cena <= 0) {
                throw new Exception("Cena mora biti broj veći od nule.");
            }
            model.setCena(cena);
            
            model.setJedinicaMere(KonverterTipova.konvertuj(view.getCbJedMere(),JedMere.class));

            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_PROIZVOD);
            toZahtev.setParametar(model);

            Komunikacija.vratiObjekat().posalji(toZahtev);
            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            
            String poruka = toOdogovor.getPoruka();
            if (toOdogovor.getIzuzetak() != null) {
                poruka += "\n" + ((Exception)toOdogovor.getIzuzetak()).getMessage();
                JOptionPane.showMessageDialog(view, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, poruka, "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postaviOsluskivace() {
        view.getBtnSacuvaj().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                sacuvajProizvod();
            }
        });
        
        view.getBtnIzmeniProizvod().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int o = JOptionPane.showConfirmDialog(view, "Da li ste sigurni da želite da izmenite proizvod?", 
                        "Potvrda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (o) {
                    case JOptionPane.YES_OPTION:
                        sacuvajProizvod();
                        break;
                    case JOptionPane.NO_OPTION:
                        view.dispose();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
