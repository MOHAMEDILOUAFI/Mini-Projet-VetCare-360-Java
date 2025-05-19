package view;

import controller.ProprietaireController;
import controller.VeterinaireController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Animal;
import model.Proprietaire;
import model.Visite;

public class ProprietaireDetailView {
    private VBox root;
    private ListView<String> animauxList;
    private ListView<String> visitesList;
    private ProprietaireController controller;
    private Proprietaire proprietaire;
    private Stage stage;

    private void modifierProprietaire() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier Propriétaire");

        TextField nom = new TextField(proprietaire.getNom());
        TextField prenom = new TextField(proprietaire.getPrenom());
        TextField adresse = new TextField(proprietaire.getAdresse());
        TextField ville = new TextField(proprietaire.getVille());
        TextField tel = new TextField(proprietaire.getTelephone());

        VBox content = new VBox(10,
                new Label("Nom :"), nom,
                new Label("Prénom :"), prenom,
                new Label("Adresse :"), adresse,
                new Label("Ville :"), ville,
                new Label("Téléphone :"), tel);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                controller.modifierProprietaire(proprietaire,
                        nom.getText(), prenom.getText(), adresse.getText(), ville.getText(), tel.getText());
                updateAnimaux();
                updateVisites(0);
            }
        });
    }
    private void supprimerProprietaire() {
        if (confirm("Supprimer ce propriétaire ?")) {
            controller.supprimerProprietaire(proprietaire);
            ProprietaireSearchView searchView = new ProprietaireSearchView(stage);
            Scene scene = new Scene(searchView.getView(), 900, 600);
            stage.setScene(scene);
        }
    }


    public ProprietaireDetailView(Stage stage, Proprietaire proprietaire, ProprietaireController controller) {
        this.stage = stage;
        this.proprietaire = proprietaire;
        this.controller = controller;

        root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");

        Label title = new Label("👤 Fiche de " + proprietaire.getPrenom() + " " + proprietaire.getNom());
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #343a40;");

        Label info = new Label("📍 " + proprietaire.getAdresse() + ", " + proprietaire.getVille()
                + " | ☎ " + proprietaire.getTelephone());
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #495057;");

        Button modifierProprio = createStyledButton("✏️ Modifier Propriétaire", "#ffc107");
        Button supprimerProprio = createStyledButton("🗑 Supprimer Propriétaire", "#dc3545");
        Button retour = createStyledButton("⬅ Retour", "#6c757d");

        modifierProprio.setOnAction(e -> modifierProprietaire());
        supprimerProprio.setOnAction(e -> supprimerProprietaire());
        retour.setOnAction(e -> {
            ProprietaireSearchView view = new ProprietaireSearchView(stage);
            Scene scene = new Scene(view.getView(), 900, 600);
            stage.setScene(scene);
        });

        HBox boxProprio = new HBox(10, modifierProprio, supprimerProprio);
        boxProprio.setAlignment(Pos.CENTER_LEFT);

        animauxList = new ListView<>();
        animauxList.setFixedCellSize(32);
        animauxList.setStyle("-fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 4; -fx-background-color: #ffffff;");

        visitesList = new ListView<>();
        visitesList.setFixedCellSize(32);
        visitesList.setStyle("-fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 4; -fx-background-color: #ffffff;");

        updateAnimaux();

        animauxList.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            int index = animauxList.getSelectionModel().getSelectedIndex();
            if (index >= 0) updateVisites(index);
        });

        Button addAnimal = createStyledButton("➕ Ajouter Animal", "#28a745");
        Button editAnimal = createStyledButton("✏️ Modifier Animal", "#ffc107");
        Button delAnimal = createStyledButton("🗑 Supprimer Animal", "#dc3545");

        addAnimal.setOnAction(e -> ajouterAnimal());
        editAnimal.setOnAction(e -> modifierAnimal());
        delAnimal.setOnAction(e -> supprimerAnimal());

        HBox btnAnimalBox = new HBox(10, addAnimal, editAnimal, delAnimal);
        btnAnimalBox.setAlignment(Pos.CENTER);

        Button addVisite = createStyledButton("➕ Ajouter Visite", "#28a745");
        Button editVisite = createStyledButton("✏️ Modifier Visite", "#ffc107");
        Button delVisite = createStyledButton("🗑 Supprimer Visite", "#dc3545");

        addVisite.setOnAction(e -> ajouterVisite());
        editVisite.setOnAction(e -> modifierVisite());
        delVisite.setOnAction(e -> supprimerVisite());

        HBox btnVisiteBox = new HBox(10, addVisite, editVisite, delVisite);
        btnVisiteBox.setAlignment(Pos.CENTER);

        VBox sectionAnimaux = new VBox(10,
                new Label("🐾 Animaux :"), animauxList, btnAnimalBox);
        VBox sectionVisites = new VBox(10,
                new Label("\uD83D\uDC68\u200D⚕\uFE0F Visites :"), visitesList, btnVisiteBox);

        root.getChildren().addAll(title, info, boxProprio, sectionAnimaux, sectionVisites, retour);
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 8; -fx-font-size: 13px; -fx-padding: 8 15;");
        return btn;
    }

    private void updateAnimaux() {
        animauxList.getItems().clear();
        for (Animal a : proprietaire.getAnimaux()) {
            animauxList.getItems().add(a.getNom() + " (" + a.getType() + ", " + a.getAge() + " ans)");
        }
    }

    private void updateVisites(int indexAnimal) {
        visitesList.getItems().clear();
        Animal animal = proprietaire.getAnimaux().get(indexAnimal);
        for (Visite v : animal.getVisites()) {
            visitesList.getItems().add(v.getDate() + " - " + v.getMotif() + " (" + v.getVeterinaire() + ")");
        }
    }


    private void ajouterAnimal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un animal");

        TextField nom = new TextField();
        TextField age = new TextField();
        TextField type = new TextField();

        VBox content = new VBox(10,
                new Label("Nom :"), nom,
                new Label("Âge :"), age,
                new Label("Type :"), type);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                try {
                    int a = Integer.parseInt(age.getText());
                    Animal animal = new Animal(nom.getText(), a, type.getText());
                    proprietaire.getAnimaux().add(animal);
                    controller.sauvegarder();
                    updateAnimaux();
                } catch (NumberFormatException ex) {
                    showError("Âge invalide !");
                }
            }
        });
    }

    private void modifierAnimal() {
        int index = animauxList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Animal animal = proprietaire.getAnimaux().get(index);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier l'animal");

            TextField nom = new TextField(animal.getNom());
            TextField age = new TextField(String.valueOf(animal.getAge()));
            TextField type = new TextField(animal.getType());

            VBox content = new VBox(10,
                    new Label("Nom :"), nom,
                    new Label("Âge :"), age,
                    new Label("Type :"), type);

            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(r -> {
                if (r == ButtonType.OK) {
                    try {
                        int a = Integer.parseInt(age.getText());
                        animal.setNom(nom.getText());
                        animal.setAge(a);
                        animal.setType(type.getText());
                        controller.sauvegarder();
                        updateAnimaux();
                    } catch (NumberFormatException ex) {
                        showError("Âge invalide !");
                    }
                }
            });
        }
    }

    private void supprimerAnimal() {
        int index = animauxList.getSelectionModel().getSelectedIndex();
        if (index >= 0 && confirm("Supprimer cet animal ?")) {
            proprietaire.getAnimaux().remove(index);
            controller.sauvegarder();
            updateAnimaux();
            visitesList.getItems().clear();
        }
    }

    private void ajouterVisite() {
        int index = animauxList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            DatePicker datePicker = new DatePicker();
            TextField motif = new TextField();

            VeterinaireController vc = new VeterinaireController();
            ComboBox<String> vetBox = new ComboBox<>();
            vetBox.getItems().addAll(vc.getNomsVeterinaires());

            Dialog<ButtonType> dialog = new Dialog<>();
            VBox content = new VBox(10,
                    new Label("Date :"), datePicker,
                    new Label("Motif :"), motif,
                    new Label("Vétérinaire :"), vetBox);

            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(r -> {
                if (r == ButtonType.OK && datePicker.getValue() != null && vetBox.getValue() != null) {
                    Visite v = new Visite(datePicker.getValue(), motif.getText(), vetBox.getValue());
                    proprietaire.getAnimaux().get(index).ajouterVisite(v);
                    controller.sauvegarder();
                    updateVisites(index);
                }
            });
        }
    }

    private void modifierVisite() {
        int a = animauxList.getSelectionModel().getSelectedIndex();
        int v = visitesList.getSelectionModel().getSelectedIndex();
        if (a >= 0 && v >= 0) {
            Animal animal = proprietaire.getAnimaux().get(a);
            Visite visite = animal.getVisites().get(v);

            DatePicker datePicker = new DatePicker(visite.getDate());
            TextField motif = new TextField(visite.getMotif());

            VeterinaireController vc = new VeterinaireController();
            ComboBox<String> vetBox = new ComboBox<>();
            vetBox.getItems().addAll(vc.getNomsVeterinaires());
            vetBox.setValue(visite.getVeterinaire());

            Dialog<ButtonType> dialog = new Dialog<>();
            VBox content = new VBox(10,
                    new Label("Date :"), datePicker,
                    new Label("Motif :"), motif,
                    new Label("Vétérinaire :"), vetBox);

            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(r -> {
                if (r == ButtonType.OK && datePicker.getValue() != null && vetBox.getValue() != null) {
                    visite.setDate(datePicker.getValue());
                    visite.setMotif(motif.getText());
                    visite.setVeterinaire(vetBox.getValue());
                    controller.sauvegarder();
                    updateVisites(a);
                }
            });
        }
    }

    private void supprimerVisite() {
        int a = animauxList.getSelectionModel().getSelectedIndex();
        int v = visitesList.getSelectionModel().getSelectedIndex();
        if (a >= 0 && v >= 0 && confirm("Supprimer cette visite ?")) {
            proprietaire.getAnimaux().get(a).getVisites().remove(v);
            controller.sauvegarder();
            updateVisites(a);
        }
    }

    private boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.showAndWait();
    }

    public VBox getView() {
        return root;
    }
}
