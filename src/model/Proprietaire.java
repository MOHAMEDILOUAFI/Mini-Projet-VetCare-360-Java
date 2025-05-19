package model;

import java.util.ArrayList;
import java.util.List;

public class Proprietaire {
    private String nom;
    private String prenom;
    private String adresse;
    private String ville;
    private String telephone;
    private List<Animal> animaux;

    public Proprietaire(String nom, String prenom, String adresse, String ville, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
        this.telephone = telephone;
        this.animaux = new ArrayList<>();
    }

    // Getters et setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public List<Animal> getAnimaux() { return animaux; }
}
