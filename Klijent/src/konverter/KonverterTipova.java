/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class KonverterTipova {

    public static <T> T konvertuj(JTextField kon_u, Class<T> clazz) {
        if(clazz.equals(String.class))
            return clazz.cast(kon_u.getText().trim());
        try {
            Method m = clazz.getMethod("valueOf", String.class);
            return clazz.cast(m.invoke(null, kon_u.getText().trim()));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(KonverterTipova.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(KonverterTipova.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(KonverterTipova.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static <T> T konvertuj(JFormattedTextField kon_u, Class<T> clazz) {
        if(clazz.equals(String.class))
            return clazz.cast(kon_u.getText().trim());
        try {
            Method m = clazz.getMethod("valueOf", String.class);
            return clazz.cast(m.invoke(kon_u.getText().trim()));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(KonverterTipova.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(KonverterTipova.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(KonverterTipova.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean konvertuj(JCheckBox kon_u, boolean kon_i) {
        return kon_u.isSelected();
    }
    
    public static <T> T konvertuj(JComboBox kon_u, Class<T> clazz) {
        return clazz.cast(kon_u.getSelectedItem());
    }

    public static <T> void konvertuj(T kon_u, JTextField kon_i) {
        kon_i.setText(String.valueOf(kon_u));
    }

}
