/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;


public class Admin {
    int id ;
    String name ;
    String pass ; 
    int[] authorities = new int[5]  ;

    public Admin(int id, String name , int[] rights) {
        this.id = id;
        this.name = name;
        this.pass = "" ;
        this.authorities = rights ;
    }

    public Admin(int id, String name,int[] rights ,  String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.authorities = rights ;
    }
    

    public Admin() {
    }
    
}
