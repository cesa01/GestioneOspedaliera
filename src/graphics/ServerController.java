/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import server.Server;

import java.io.*;
import java.util.*;
import java.net.*;
import javafx.scene.control.TextArea;
import server.Connect;
import server.Manager;

/**
 * FXML Controller class
 *
 * @author Cesa
 */
public class ServerController implements Initializable {

    //Dichiaro il server e Conenct
    Server s;
    Connect c;
    Manager manager;

    @FXML private Button start;
    @FXML private Button stop;
    @FXML private TextArea console;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Passo come variabile FXML la console per la stampa degli utenti entrati.
        manager = new Manager(console);
        
        //FXML - Gestione visibilità dei tasti
        stop.setDisable(true);
    }

    //========================================
    //[BUTTON] Avvia SERVER
    //========================================
    @FXML
    private void avvia(ActionEvent event) {
        try {
            //Avvio il server classe Server.java
            s = new Server(manager);
            
            //FXML - Gestione visibilità dei tasti
            start.setDisable(true);
            stop.setDisable(false);
        } catch (IOException e) {
            System.err.println("Errore: " + e);
        }

    }
    
    //========================================
    //[BUTTON] Ferma SERVER
    //========================================
    @FXML
    private void ferma(ActionEvent event) {
        try {
            //Chiude il server
            s.close();
            
            //FXML - Gestione visibilità dei tasti
            start.setDisable(false);
            stop.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
