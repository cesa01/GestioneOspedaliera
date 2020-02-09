/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysql.ConnectionDB;

/**
 *
 * @author Cesa
 */
public class Connect extends Thread {

    //Variabili Socket & IN/OUT
    private Socket client = null;
    BufferedReader in;
    PrintStream out;

    //Inizializzo ConnectionDB
    ConnectionDB con = new ConnectionDB();

    //========================================
    //Costruttori CONNECT Class
    //========================================
    public Connect(Socket socketClient) throws SQLException, IOException {

        this.client = socketClient;

        try {
            //Inizializzo gli STREAM DI COMUNICAZIONE
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintStream(client.getOutputStream(), true);

        } catch (IOException e1) {
            //Se si verifica un errore viene chiuso il socket
            try {
                client.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        //Avvia il thread
        this.start();
    }

    //**********************************************
    //START e do-while per ricevere mess. da CLIENT
    //**********************************************
    @Override
    public void run() {
        try {

            //Ciclo infinito per ricevere messaggi da CLIENT
            String message = "";

            do {
                try {
                    message = in.readLine();
                } catch (IOException ex) {
                    System.out.println("\nERRORE| " + ex);
                    closeChannel();
                    break;
                }
                if (message != null) {
                    System.out.println("\nMessaggio da CLIENT| " + message + "\n");
                    read(message);
                }
            } while (true);

            // chiude gli stream e le connessioni
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("ERROR| " + e);
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //****************************************
    //METHOD: per leggere le QUERY
    //****************************************
    public void read(String line) throws IOException, SQLException {
        
        //Viene eseguito solo se la linea non è vuota
        if(line!=null){
            String[] stringa = line.split(";");
            
            //memorizzo data in una stringa
            String data = stringa[1];
            
            //splitto ciò che mi serve (ex.SQL,ESCI, ecc...)
            for(String azione: stringa){
                switch(azione){
                    //================/ Command: loginAdmin /================
                    case "loginAdmin":
                        //Stampo i dati ricevuti
                        System.out.println(data);
                        
                        //Ottengo la connesisone a MySQL
                        con.getConnection();
                        
                        //Se le credenziali corrispondono invia un messaggio al server: OK altrimenti ERRORE
                        if(con.loginAdmin(data)){
                            out.println("ok");
                        } else {
                            out.println("errore");
                        }
                        
//                        closeChannel();
                        break;
                    //================/ Command: loginDottore /================
                    case "loginDottore":
                        //Stampo i dati ricevuti
                        System.out.println(data);
                        
                        //Ottengo la connesisone a MySQL
                        con.getConnection();
                        
                        //Se le credenziali corrispondono invia un messaggio al server: OK altrimenti ERRORE
                        if(con.loginDottore(data)){
                            out.println("ok");
                        } else {
                            out.println("errore");
                        }
                        break;
                    //================/ Command: ESCI /================
                    case "ESCI":
                        System.err.println("Uscita...");
                        closeChannel();
                        break;
                }
            }
        } else {
            System.err.println("ERRORE: String nulla");
        }
    }

    //****************************************
    //METHOD:Inserimento del messaggio console
    //****************************************
    public Socket getSocket() {
        return client;
    }

    //****************************************
    //Chiudo il socket
    //****************************************
    public void closeChannel() {
        try {
            interrupt();
            client.close();
        } catch (IOException ex) {
            System.out.println("\nERRORE| " + ex);
        }
    }
    
    //****************************************
    //Chiudo gli stream
    //****************************************
    public void closeStream(){
        try { 
            out.close();
            in.close();
            client.close();
        } catch (IOException ex){
            System.out.println("ERRORE STREAM| " + ex);
        }
    }
    
    //****************************************
    //Inviare un MESSAGGIO
    //****************************************
    public void sendMessage(String message){
        out.println(message);
        out.flush();
    }
}
