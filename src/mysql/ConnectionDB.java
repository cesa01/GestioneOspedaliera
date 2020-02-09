/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.awt.HeadlessException;
import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import javax.swing.JOptionPane;
import server.Connect;

/**
 *
 * @author Cesa
 */
public class ConnectionDB {

    //Server
    Connect C;

    //oggetto connection da ritornare
    private Connection con = null;

    //leggere i dati
    private FileReader reader;

    public ConnectionDB() throws IOException {
    }

    //=================================================================
    //metodo per ritornare l'oggetto connetion tramite getConnection
    //=================================================================
    public Connection getConnection() throws FileNotFoundException, IOException, SQLException {
        String dbSchema = null;

        MysqlDataSource dataSource = new MysqlDataSource();

        if (con == null) {
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setUser("root");
            dataSource.setPassword("");
            dataSource.setDatabaseName("ospedale");
            con = dataSource.getConnection();
            return con;
        }
        return con;
    }
    //se la connessione non è stata ancora stabilita
    //        if (con == null) {
    //            Path currentDir = Paths.get("");
    //
    //            reader = new FileReader(currentDir.toAbsolutePath() + "\\src\\mysql\\conf.dat");
    //            Properties conf = new Properties();
    //
    //            conf.load(reader);
    //
    //            MysqlDataSource dataSource = new MysqlDataSource();
    //            dataSource.setServerName(conf.getProperty("url"));
    //            dataSource.setPort(Integer.parseInt(conf.getProperty("port")));
    //            dataSource.setUser(conf.getProperty("user"));
    //            dataSource.setPassword(conf.getProperty("password"));
    //
    //            if (dbSchema == null) {
    //                dataSource.setDatabaseName(conf.getProperty("dbSchema"));
    //            }
    //
    //            try {
    //                con = dataSource.getConnection();
    //                
    //            } catch (Exception e) {
    //                System.out.println(e);
    //            }
    //        }
    //        
    //        return con;
    //    }

    //=================================================================
    //METHOD: per inviare una query
    //=================================================================
    public ResultSet excuteQuery(String data) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = con.prepareStatement(data);
        rs = ps.executeQuery();
        rs.first();

        return rs;
    }

    //=================================================================
    //LOGIN: Verifica di credenziali [ADMIN]
    //=================================================================
    public boolean loginAdmin(String data) throws SQLException, IOException {
        try {
            //Memorizzo nelle variabili le credenziali tramite una temp
            //[---utente---] --> [user/pass]
            String[] tempUser = data.split(":");
            String user = tempUser[0];
            String pass = tempUser[1];

            //Stampo le credenziali "temporanee" per la verifica
            System.out.println("\nUtente: " + user + " \nPassword: " + pass + "\n");

            //Query da inviare per verificare credenziali 
            String sql = "select username, password from admin where username='" + user + "'";

            //invio query var "sql"
            ResultSet rs = excuteQuery(sql);

            //Effettua verifica credenziali tramite il resultset
            if (pass.equals(rs.getString("password"))) {
                return true;
            } else {
//                JOptionPane.showMessageDialog(null, "User or Password wrong.")
                return false;
            }
        } catch (HeadlessException | SQLException e) {
            return false;
        }
    }
    
    //=================================================================
    //LOGIN: Verifica di credenziali [DOTTORE]
    //=================================================================
    public boolean loginDottore(String data) throws SQLException, IOException {
        try {
            //Memorizzo nelle variabili le credenziali tramite una temp
            //[---utente---] --> [user/pass]
            String[] tempUser = data.split(":");
            String user = tempUser[0];
            String pass = tempUser[1];

            //Stampo le credenziali "temporanee" per la verifica
            System.out.println("\nUtente: " + user + " \nPassword: " + pass + "\n");

            //Query da inviare per verificare credenziali 
            String sql = "select username, password from doctor where username='" + user + "'";

            //invio query var "sql"
            ResultSet rs = excuteQuery(sql);

            //Effettua verifica credenziali tramite il resultset
            if (pass.equals(rs.getString("password"))) {
                return true;
            } else {
//                JOptionPane.showMessageDialog(null, "User or Password wrong.")
                return false;
            }
        } catch (HeadlessException | SQLException e) {
            return false;
        }
    }
}
