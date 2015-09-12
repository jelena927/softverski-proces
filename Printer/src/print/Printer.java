/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.MultiDocPrintService;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Jelena
 */
public class Printer {
    public static void main(String[] args) {
        print();
    }
    public static void print() {
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

        PrintService service = ServiceUI.printDialog(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(), 200, 200,
                printService, defaultService, flavor, pras);
        if (service != null) {
            try {
                DocPrintJob job = service.createPrintJob();
                File f = new File("C:\\\\Users\\Jelena\\Documents\\NetBeansProjects\\proSoft\\seminarski\\Klijent\\src\\print\\text.txt");
                
                FileInputStream fis = new FileInputStream(f);
                DocAttributeSet das = new HashDocAttributeSet();
                SimpleDoc document = new SimpleDoc(fis, flavor, das);
                job.print(document, pras);
            } catch (PrintException ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
