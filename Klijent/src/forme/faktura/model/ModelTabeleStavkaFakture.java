/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.faktura.model;

import domen.Faktura;
import domen.Proizvod;
import domen.StavkaFakture;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author student1
 */
public class ModelTabeleStavkaFakture extends AbstractTableModel {

    Faktura faktura;
    String[] columnNames = new String[]{"RB", "Proizvod", "Kolicina", "Rabat", 
        "Iznos rabata", "Poreska osnovica", "PDV", "Iznos PDV-a", "Vrednost sa PDV-om"};

    public ModelTabeleStavkaFakture(Faktura faktura) {
        this.faktura = faktura;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return faktura.getStavke() == null ? 0 : faktura.getStavke().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class ;
            case 1:
                return Proizvod.class;
            case 2:
                return Double.class;
            case 3:
                return Double.class;
            case 4:
                return Double.class;
            case 5:
                return Double.class;
            case 6:
                return Double.class;
            case 7:
                return Double.class;
            case 8:
                return Double.class;
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaFakture stavka = faktura.getStavke().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return stavka.getRb();
            case 1:
                return stavka.getProizvod();
            case 2:
                return stavka.getKolicina();
            case 3:
                return stavka.getRabat();
            case 4:
                return stavka.getIznosRabata();
            case 5:
                return stavka.getPoreskaOsnovica();
            case 6:
                return stavka.getPdv();
            case 7:
                return stavka.getIznosPdva();
            case 8:
                return stavka.getVrednostSaPdvom();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        StavkaFakture so = faktura.getStavke().get(rowIndex);
        switch (columnIndex) {
            case 0:
                so.setRb(Integer.parseInt(aValue.toString()));
                break;
            case 1:
                so.setProizvod((Proizvod) aValue);
                so.setIznosRabata(so.getProizvod().getCena() * so.getKolicina() * so.getRabat());
                so.setPoreskaOsnovica(so.getKolicina() * so.getProizvod().getCena() - so.getIznosRabata());
                so.setIznosPdva(so.getPoreskaOsnovica() * so.getPdv());
                so.setVrednostSaPdvom(so.getPoreskaOsnovica() + so.getIznosPdva());
                fireTableDataChanged();
                break;
            case 2:
                so.setKolicina(Double.parseDouble(aValue.toString()));
                so.setIznosRabata(so.getProizvod().getCena() * so.getKolicina() * so.getRabat());
                so.setPoreskaOsnovica(so.getKolicina() * so.getProizvod().getCena() - so.getIznosRabata());
                so.setIznosPdva(so.getPoreskaOsnovica() * so.getPdv());
                so.setVrednostSaPdvom(so.getPoreskaOsnovica() + so.getIznosPdva());
                fireTableDataChanged();
                break;
            case 3:
                so.setRabat(Double.parseDouble(aValue.toString()));
                so.setIznosRabata(so.getProizvod().getCena() * so.getKolicina() * so.getRabat());
                so.setPoreskaOsnovica(so.getKolicina() * so.getProizvod().getCena() - so.getIznosRabata());
                so.setIznosPdva(so.getPoreskaOsnovica() * so.getPdv());
                so.setVrednostSaPdvom(so.getPoreskaOsnovica() + so.getIznosPdva());
                fireTableDataChanged();
                break;
            case 6:
                so.setPdv(Double.parseDouble(aValue.toString()));
                so.setIznosPdva(so.getPoreskaOsnovica() * so.getPdv());
                so.setVrednostSaPdvom(so.getPoreskaOsnovica() + so.getIznosPdva());
                fireTableDataChanged();
                break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 6) {
            return true;
        }
        return false;
    }

    public void dodajStavkuFakture() throws Exception {
        faktura.dodajPraznuStavku();
        fireTableDataChanged();
    }

    public Faktura vratiFakturu() {
        return faktura;
    }

    public String vratiUkupnuVrednostFakture(JTextField txtOsnovica, JTextField txtPDV) {
        return String.valueOf(Double.parseDouble(txtOsnovica.getText()) + Double.parseDouble(txtPDV.getText()));
    }

    public String vratiPDV() {
        double suma = 0;
        for (StavkaFakture stavkaFakture : faktura.getStavke()) {
            suma += stavkaFakture.getIznosPdva();
        }
        return String.valueOf(suma);
    }

    public String vratiPoreskuOsnovicu() {
        double suma = 0;
        for (StavkaFakture stavkaFakture : faktura.getStavke()) {
            suma += stavkaFakture.getPoreskaOsnovica();
        }
        return String.valueOf(suma);
    }

    public void obrisiStavku(int selectedRow) {
        faktura.getStavke().remove(selectedRow);
        fireTableDataChanged();
        azurirajRB();
        fireTableDataChanged();
    }

    private void azurirajRB() {
        int i = 1;
        for (StavkaFakture stavkaFakture : faktura.getStavke()) {
            stavkaFakture.setRb(i);
            i++;
        }
    }
    
    public List<StavkaFakture> vratiStavke(){
        return faktura.getStavke();
    }
    
}
