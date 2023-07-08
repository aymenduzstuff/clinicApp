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
    int act_dent_id ;
    int temine ;
    int[] teeth ;

    public Consultation(int id, int client_id, int act_dent_id, int temine) {
        this.id = id;
        this.client_id = client_id;
        this.act_dent_id = act_dent_id;
        this.temine = temine;
    }

    public Consultation() {
    }
    
           
}
