/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;

/**
 *
 * @author sts
 */
public class Facture {
    int id ;
    String designation ;
    int cout ;
    String description ;

    public Facture(int id, String designation, int cout, String description) {
        this.id = id;
        this.designation = designation;
        this.cout = cout;
        this.description = description;
    }

    public Facture() {
        this.id = 0;
        this.designation = "";
        this.cout = 10000;
        this.description = "";
    }
    
}
