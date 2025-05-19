package model;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private String nom;
    private int age;
    private String type;
    private List<Visite> visites = new ArrayList<>();

    public Animal(String nom, int age, String type) {
        this.nom = nom;
        this.age = age;
        this.type = type;
    }

    public void ajouterVisite(Visite v) {
        visites.add(v);
    }

    // Getters et setters

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Visite> getVisites() { return visites; }
    public void setVisites(List<Visite> visites) { this.visites = visites; }
}
