/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import transfer.TransferObjekat;

/**
 *
 * @author student1
 */
public class Komunikacija {
    Socket socket; 
    private static Komunikacija k;

    private Komunikacija() throws UnknownHostException, IOException, Exception {
        socket = new Socket("127.0.0.1", 9000);
        System.out.println("Uspostavljam komunikaciju.");
    }
    
    public static Komunikacija vratiObjekat() throws UnknownHostException, IOException, Exception {
        if (k == null) {
            k = new Komunikacija();
        }
        return k;
    }
    
    public void posalji(TransferObjekat to) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(to);
    }
    
    public TransferObjekat procitaj() throws IOException, ClassNotFoundException {
        ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
        TransferObjekat toOdgovor = (TransferObjekat) inSocket.readObject();
        return toOdgovor;
    }

    public void ispisiKraj() {
        System.out.println("Komunikacija prekinuta.");
    }
    
    
    
}
