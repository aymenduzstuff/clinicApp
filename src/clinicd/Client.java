/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;

import java.time.LocalDate;


public class Client {

    public int id;
    public String nom ;
    public String prenom ;
    public String adresse ;
    public String numero_tel ;
    public int age ;
    public LocalDate date_nais ;
    public String notes ;

    public Client(int id, String nom, String prenom, String adresse, String numero_tel, int age, LocalDate date_nais, String notes) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numero_tel = numero_tel;
        this.age = age;
        this.date_nais = date_nais;
        this.notes = notes;
    }
    
    


    Client() {
        this.id = 0;
        this.nom = "";
        this.prenom = "";
        this.adresse = "";
        this.numero_tel = "";
        this.age = 0;
        this.date_nais = LocalDate.now() ;
        this.notes = "";
    }
    
}
