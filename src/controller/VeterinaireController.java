package controller;

import model.Veterinaire;
import storage.VeterinaireManager;

import java.util.List;
import java.util.stream.Collectors;

public class VeterinaireController {
    private List<Veterinaire> veterinaireList;

    public VeterinaireController() {
        this.veterinaireList = VeterinaireManager.chargerVeterinaires();
    }

    public List<Veterinaire> getVeterinaires() {
        return veterinaireList;
    }

    public List<String> getNomsVeterinaires() {
        return veterinaireList.stream()
                .map(Veterinaire::getNom)
                .collect(Collectors.toList());
    }

    public void ajouterVeterinaire(String nom, String specialite) {
        veterinaireList.add(new Veterinaire(nom, specialite));
        VeterinaireManager.sauvegarderVeterinaires(veterinaireList);
    }

    public void modifierVeterinaire(int index, String nom, String specialite) {
        if (index >= 0 && index < veterinaireList.size()) {
            Veterinaire v = veterinaireList.get(index);
            v.setNom(nom);
            v.setSpecialite(specialite);
            VeterinaireManager.sauvegarderVeterinaires(veterinaireList);
        }
    }

    public void supprimerVeterinaire(int index) {
        if (index >= 0 && index < veterinaireList.size()) {
            veterinaireList.remove(index);
            VeterinaireManager.sauvegarderVeterinaires(veterinaireList);
        }
    }
}
