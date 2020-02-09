/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cesa
 */
public class Server extends Thread {

    //Porta del server
    int port = 1337;

    ServerSocket server;
    Manager manager;

    //========================================
    //Main SERVER
    //========================================

    //========================================
    //Constructor SERVER
    //========================================
    public Server(Manager manager) throws IOException {
        this.port = port;
        this.manager = manager;
        
        //Inizializzo il server
        server = new ServerSocket(port);
        System.out.println("Il Server è hostato sulla porta: " + port);
        
        //Avvio il thread
        this.start();
    }

    //========================================
    //INITIALIZE
    //========================================
    @Override
    public void run() {
        Socket client = null;

        try {
            do {
                //Accetto il client
                client = server.accept();
                
                //passo il client alla classe MANAGER
                manager.initialize(client);
                
                //imposto un timer di 2s
                Thread.sleep(2000);
                
            } while (true);
        } catch (IOException e) {
            System.err.println("Errore| " + e);
        } catch (SQLException ex) {
            System.out.println("Errore SQL| "+ ex);
        } catch (InterruptedException e1){
            System.out.println("Errore Thread| " + e1);
        }
    }

    //========================================
    //CLOSE
    //========================================
    public void close() throws IOException {
        if (isAlive()) {
            interrupt();
            server.close();
        }
    }
}
