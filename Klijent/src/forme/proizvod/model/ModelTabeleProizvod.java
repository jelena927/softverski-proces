/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.proizvod.model;

import domen.Proizvod;
import forme.proizvod.FmPrikazProizvoda;
import forme.proizvod.KontrolerKIPrikazProizvoda;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jelena
 */
public class ModelTabeleProizvod extends AbstractTableModel {

    private List<Proizvod> lista;

    public ModelTabeleProizvod(List<Proizvod> lista) {
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
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Proizvod p = lista.get(rowIndex);
        if (columnIndex == 0) {
            return p.getNaziv();
        }
        if (columnIndex == 1) {
            return p.getCena();
        }
        if (columnIndex == 2) {
            return p.getJedinicaMere();
        }
        return "n/a";
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Naziv";
            case 1:
                return "Cena";
            case 2:
                return "Jed. mere";
            default:
                return "n/a";
        }
    }

    public Proizvod getProizvod(int rowIndex) {
        return lista.get(rowIndex);
    }

    public void azurirajTabelu(Proizvod p, FmPrikazProizvoda f) throws Exception {
        lista.removeAll(lista);
        KontrolerKIPrikazProizvoda.prikaziProizvode(f);
        fireTableDataChanged();
    }
}
