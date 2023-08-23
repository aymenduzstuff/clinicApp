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
    int temine ;
    int[] teeth ;
    int cost ;

    public Consultation(int id, int client_id, int temine , int cost) {
        this.id = id;
        this.client_id = client_id;
        this.temine = temine;
        this.cost = cost ;
    }

    public Consultation() {
    }
    
           
}
