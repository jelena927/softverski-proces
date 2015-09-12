/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.mesto.model;

import domen.Mesto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Korisnik
 */
public class ModelTabeleMesto extends AbstractTableModel {
    private List<Mesto> lm;

    public ModelTabeleMesto(List<Mesto> lm) {
        this.lm = lm;
    }

    @Override
    public int getRowCount() {
        if (lm == null) {
            return 0;
        }
        return lm.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mesto m = lm.get(rowIndex);
        switch(columnIndex) {
            case 0: return m.getPtt();
            case 1: return m.getNaziv();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "PTT";
            case 1: return "Naziv";
            default: return "";
        }
    }
}
