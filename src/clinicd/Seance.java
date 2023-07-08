/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;

import java.time.LocalDate;

/**
 *
 * @author sts
 */
public class Seance {
    int id_cons;
    int id;
    String notes;
    int montant;

    public Seance(int id_cons, int id, String notes, int montant) {
        this.id_cons = id_cons;
        this.id = id;
        this.notes = notes;
        this.montant = montant;
    }
    
    

    public Seance() {
        
    }
    
    
}
