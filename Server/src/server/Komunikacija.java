/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import transfer.TransferObjekat;

/**
 *
 * @author student1
 */
public class Komunikacija {

    private Socket socket;

    public void pokreniServer() throws IOException, ClassNotFoundException, Exception {
        ServerSocket ss = new ServerSocket(9000);
        System.out.println("Server je pokrenut");
        while (true) {
            if (NitZaOpsluzivanjeKlijenta.brojac <= 9) {
                socket = ss.accept();
                System.out.println("Klijent se povezao");
                NitZaOpsluzivanjeKlijenta nit = new NitZaOpsluzivanjeKlijenta(socket);
                NitZaOpsluzivanjeKlijenta.brojac++;
                nit.start();
            } else {
                socket = ss.accept();
                System.out.println("Dostignut maksimalan broj klijenata.");
                NitZaOpsluzivanjeKlijenta nit = new NitZaOpsluzivanjeKlijenta(socket);
                TransferObjekat t = new TransferObjekat();
                t.setIzuzetak(new Exception("Dostignut maksimalan broj klijenata."));
                t.setPoruka("Dostignut maksimalan broj klijenata.");
                nit.posaljiOdgovor(t);
                nit = null;
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, Exception {
        Komunikacija k = new Komunikacija();
        k.pokreniServer();
    }
}
