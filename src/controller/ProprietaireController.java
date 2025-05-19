package controller;

import model.Proprietaire;
import storage.FichierManager;

import java.util.List;

public class ProprietaireController {
    private List<Proprietaire> proprietaires;

    public ProprietaireController() {
        this.proprietaires = FichierManager.chargerProprietaires();
    }

    public List<Proprietaire> getProprietaires() {
        return proprietaires;
    }

    public void ajouterProprietaire(String nom, String prenom, String adresse, String ville, String telephone) {
        Proprietaire p = new Proprietaire(nom, prenom, adresse, ville, telephone);
        proprietaires.add(p);
        sauvegarder();
    }

    public void modifierProprietaire(Proprietaire p, String nom, String prenom, String adresse, String ville, String tel) {
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setAdresse(adresse);
        p.setVille(ville);
        p.setTelephone(tel);
        sauvegarder();
    }

    public void supprimerProprietaire(Proprietaire p) {
        proprietaires.remove(p);
        sauvegarder();
    }

    public void sauvegarder() {
        FichierManager.sauvegarderProprietaires(proprietaires);
    }
}
