/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;

/**
 *
 * @author sts
 */
public class Consultation {
    int id ;
    int client_id ;
    boolean temine ;
    String teeth ;
    int rest ;
    int cout ;
    int nbrSeances ;
    int payed;

    public Consultation(int id, int client_id, boolean temine, int rest, String dents, int cout, int nbrSeances , int payed) {
        this.id = id;
        this.client_id = client_id;
        this.temine = temine;
        this.rest = rest ;
        this.teeth = dents ;
        this.cout = cout ;
        this.nbrSeances = nbrSeances  ;
        this.payed = payed  ;
    }

    public Consultation() {
        this.id = -1 ;
    }
    
           
}
