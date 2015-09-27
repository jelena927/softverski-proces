/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.faktura.model;

import domen.Faktura;
import domen.PoslovniPartner;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jelena
 */
public class ModelTableFaktura extends AbstractTableModel {
    private List<Faktura> lista;
    String[] columnNames = new String[]{"Broj fakture", "Datum", "Poslovni partner", "Ukupna vrednost", "Obrađena", 
        "Stornirana"};

    public ModelTableFaktura(List<Faktura> lista) {
        this.lista = lista;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class ;
            case 1:
                return Date.class;
            case 2:
                return PoslovniPartner.class;
            case 3:
                return Double.class;
            case 4:
                return Boolean.class;
            case 5:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Faktura faktura = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return faktura.getBrojFakture();
            case 1:
                return faktura.getDatum();
            case 2:
                return faktura.getPoslovniPartner();
            case 3:
                return faktura.getUkupnaVrednost();
            case 4:
                return faktura.isObradjena();
            case 5:
                return faktura.isStornirana();
            default:
                return null;
        }
    }

    public Faktura getFaktura(int selectedRow) {
        return lista.get(selectedRow);
    }
    
    public void azurirajTabelu(List<Faktura> noviModel) {
        lista.removeAll(lista);
        lista.addAll(noviModel);
        fireTableDataChanged();
    }
}
