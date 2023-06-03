/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;

/**
 *
 * @author sts
 */
public class act {
    int id ;
    String nom_act ;
    int cout ;
    String description ;

    public act(int id, String nom_act, int cout, String description) {
        this.id = id;
        this.nom_act = nom_act;
        this.cout = cout;
        this.description = description;
    }

    public act() {
        this.id = 0;
        this.nom_act = "";
        this.cout = 10000;
        this.description = "";
    }
    
}
