/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clinicd;

/**
 *
 * @author aymen
 */
public class ClinicD {

  
    public static void main(String[] args) {
        DBconnection dBConnection = new DBconnection("jdbc:mysql://localhost:3306/clinicdatabase" ,"clinicdatabase" , "aymenhhh");
        
        login adminLogin = new login(dBConnection);
        adminLogin.setVisible(true);
        
        //mainFrame mainTest = new mainFrame(dBConnection , "aymen");
        //mainTest.setVisible(true);
    }
    
}
