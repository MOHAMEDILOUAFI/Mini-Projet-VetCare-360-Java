package model;

public class Veterinaire {
    private String nom;
    private String specialite;

    public Veterinaire(String nom, String specialite) {
        this.nom = nom;
        this.specialite = specialite;
    }

    public String getNom() {
        return nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return nom + " - " + specialite;
    }
}
