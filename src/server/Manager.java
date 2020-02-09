/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.sql.*;
import javafx.scene.control.TextArea;
import mysql.ConnectionDB;

/**
 *
 * @author Cesa
 */
public class Manager {

    //Area di testo comune
    protected TextArea message;

    //========================================
    //COSTRUTTORE: Manager
    //========================================
    public Manager(TextArea message) {
        this.message = message;
    }

    //****************************************
    //METHOD:Inserimento del messaggio console
    //****************************************
    public synchronized void appendMessage(String msg) {
        message.appendText(msg);
    }

    //****************************************
    //METHOD:Initialize
    //****************************************
    public void initialize(Socket socket) throws IOException, SQLException{
        Connect channel = new Connect(socket);;
        
        appendMessage(channel.getSocket().getInetAddress() + " - si è connesso al Server!\n");
        
        
//        con.getConnection();
//        if(con.getConnection().isClosed()){
//            System.err.println("\nConnessione al Database non riuscita.\n");
//        } else {
//            System.out.println("\nConnessione al Database riuscita.\n");
//        }
    }
}
