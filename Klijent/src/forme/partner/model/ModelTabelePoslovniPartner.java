/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.partner.model;

import domen.PoslovniPartner;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jelena
 */
public class ModelTabelePoslovniPartner extends AbstractTableModel {
    private final List<PoslovniPartner> lista;

    public ModelTabelePoslovniPartner(List<PoslovniPartner> lista) {
        this.lista = lista;
    }
    
    @Override
    public int getRowCount() {
        if (lista != null) {
            return lista.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PoslovniPartner p = lista.get(rowIndex);
        if (columnIndex == 0) return p.getNaziv();
        if (columnIndex == 1) return p.getPib();
        if (columnIndex == 2) return p.getKontakt();
        if (columnIndex == 3) return p.getAdresa();
        return "n/a";
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Naziv";
            case 1: return "PIB";
            case 2: return "Kontakt";
            case 3: return "Adresa";
            default: return "n/a";
        }
    }

    public PoslovniPartner getPoslovniPartner(int selectedRow) {
        return lista.get(selectedRow);
    }
}
