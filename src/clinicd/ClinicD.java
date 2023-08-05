/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clinicd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author aymen
 */
public class ClinicD {

  
    public static void main(String[] args) throws Exception  {
        
        Properties properties = new Properties();

        try (InputStream fis = ClinicD.class.getResourceAsStream("/config/config.properties")) {
            properties.load(fis);
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            
            DBconnection dBConnection = new DBconnection(dbUrl, dbUsername, dbPassword);

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            login adminLogin = new login(dBConnection);
            adminLogin.setVisible(true);
            // Use the properties as needed in your program
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "couldn't connect to config file");
        }
        
        //DBconnection dBConnection = new DBconnection("jdbc:mysql://localhost:3306/clinicdatabase" ,"clinicdatabase" , "aymenhhh");
       
        
        //mainFrame mainTest = new mainFrame(dBConnection , "aymen");
        //mainTest.setVisible(true);
    }
    
}
