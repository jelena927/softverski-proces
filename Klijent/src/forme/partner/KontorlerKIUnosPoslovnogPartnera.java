/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.partner;

import domen.Mesto;
import domen.PoslovniPartner;
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
public class KontorlerKIUnosPoslovnogPartnera {

    private final PoslovniPartner model;
    private final FmUnosPoslovnogPartnera view;

    public KontorlerKIUnosPoslovnogPartnera(Frame mainView){
        model = new PoslovniPartner();
        view = new FmUnosPoslovnogPartnera(mainView, true, model);
        kreirajPoslovnogPartnera();
        view.postaviVrednosti(false);
    }    
    
    public KontorlerKIUnosPoslovnogPartnera(Frame mainView, PoslovniPartner poslovniPartner){
        model = poslovniPartner;
        view = new FmUnosPoslovnogPartnera(mainView, true, model);
        view.postaviVrednosti(true);
    }    
    
    public void pokreniFormu(){
        postaviOsluskivace();
        prikaziFormu();
    }

    private void prikaziFormu() {
        view.setVisible(true);
    }
    
    private void kreirajPoslovnogPartnera(){
        try {
            int poslovniPartnerId = KontrolerKI.generisiID(model);
            model.setId(poslovniPartnerId);
            
            TransferObjekat toZahtev = new TransferObjekat();
            toZahtev.setOperacija(konstante.Operacije.KREIRAJ_POSLOVNOG_PARTNERA);
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
    
    private void zapamtiPoslovnogPartnera() {
        try {
            model.setNaziv(KonverterTipova.konvertuj(view.getTxtNaziv(), String.class));
            model.setPib(KonverterTipova.konvertuj(view.getTxtPib(), String.class));
            model.setKontakt(KonverterTipova.konvertuj(view.getTxtKontakt(), String.class));
            model.getAdresa().setUlica(KonverterTipova.konvertuj(view.getTxtUlica(), String.class));
            model.getAdresa().setBroj(KonverterTipova.konvertuj(view.getTxtBroj(), String.class));
            if(view.getCbMesto().getSelectedIndex() != -1)
                model.getAdresa().setMesto(KonverterTipova.konvertuj(view.getCbMesto(), Mesto.class));
            
            if (model.getNaziv().isEmpty() || model.getPib().isEmpty() || model.getKontakt().isEmpty() || 
                    model.getAdresa().getUlica().isEmpty() || model.getAdresa().getBroj().isEmpty()) {
                throw new Exception("Sva polja su obavezna.");
            }
            
            TransferObjekat<PoslovniPartner> toZahtev = new TransferObjekat();
            toZahtev.setOperacija(Operacije.SACUVAJ_POSLOVNOG_PARTNERA);
            toZahtev.setParametar(model);

            Komunikacija.vratiObjekat().posalji(toZahtev);

            TransferObjekat toOdogovor = Komunikacija.vratiObjekat().procitaj();
            String poruka = toOdogovor.getPoruka();;
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

    public PoslovniPartner getModel() {
        return model;
    }

    public FmUnosPoslovnogPartnera getView() {
        return view;
    }

    private void postaviOsluskivace() {
        view.getBtnSnimi().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                zapamtiPoslovnogPartnera();
            }
        });
        
        view.getBtnIzmeni().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int o = JOptionPane.showConfirmDialog(view, "Da li ste sigurni da želite da izmenite poslovnog partnera?", 
                        "Potvrda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (o) {
                    case JOptionPane.YES_OPTION:
                        zapamtiPoslovnogPartnera();
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
