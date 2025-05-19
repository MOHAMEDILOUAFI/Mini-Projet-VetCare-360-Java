package view;

import controller.ProprietaireController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Proprietaire;

import java.util.List;
import java.util.stream.Collectors;

public class ProprietaireSearchView {
    private VBox root;
    private VBox listContainer;
    private TextField searchField;
    private ProprietaireController controller;
    private int selectedIndex = -1;
    private Stage stage;

    public ProprietaireSearchView(Stage stage) {
        this.stage = stage;
        this.controller = new ProprietaireController();

        root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e2e6ea);");

        Label title = new Label("👤 Recherche des Propriétaires");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        searchField = new TextField();
        searchField.setPromptText("Rechercher par nom ou prénom...");
        searchField.setPrefWidth(400);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateList(newVal));

        listContainer = new VBox(15);
        listContainer.setAlignment(Pos.CENTER_LEFT);
        listContainer.setPadding(new Insets(20));
        listContainer.setStyle("-fx-background-color: white; -fx-border-radius: 12; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        updateList("");

        Button addBtn = createStyledButton("➕ Ajouter", "#28a745");
        Button editBtn = createStyledButton("✏️ Modifier", "#ffc107");
        Button detailBtn = createStyledButton("📋 Détails", "#17a2b8");
        Button backBtn = createStyledButton("⬅ Retour", "#6c757d");

        addBtn.setOnAction(e -> showAddDialog());
        editBtn.setOnAction(e -> {
            if (selectedIndex >= 0) showEditDialog(selectedIndex);
        });
        detailBtn.setOnAction(e -> {
            if (selectedIndex >= 0) {
                Proprietaire selected = controller.getProprietaires().get(selectedIndex);
                ProprietaireDetailView detailView = new ProprietaireDetailView(stage, selected, controller);
                Scene detailScene = new Scene(detailView.getView(), 900, 600);
                stage.setScene(detailScene);
            }
        });
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(stage);
            Scene scene = new Scene(mainView.getView(), 900, 600);
            stage.setScene(scene);
        });

        HBox buttons = new HBox(15, addBtn, editBtn, detailBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, searchField, listContainer, buttons);
    }

    private Button createStyledButton(String text, String bgColor) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + bgColor + "; " +
                "-fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; " +
                "-fx-font-size: 14px; -fx-padding: 10 20;");
        return btn;
    }

    private void updateList(String filter) {
        listContainer.getChildren().clear();
        List<Proprietaire> proprietaires = controller.getProprietaires().stream()
                .filter(p -> (p.getNom() + " " + p.getPrenom()).toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());

        for (int i = 0; i < proprietaires.size(); i++) {
            Proprietaire p = proprietaires.get(i);
            int currentIndex = i;

            HBox card = new HBox(10);
            card.setPadding(new Insets(10));
            card.setAlignment(Pos.CENTER_LEFT);
            card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label label = new Label(p.getPrenom() + " " + p.getNom() + " - " + p.getVille());
            label.setStyle("-fx-font-size: 16px; -fx-text-fill: #343a40;");

            card.getChildren().add(label);
            card.setOnMouseClicked((MouseEvent event) -> {
                selectedIndex = currentIndex;
                updateList(filter);
            });

            if (selectedIndex == i) {
                card.setStyle("-fx-background-color: #fce8cf; -fx-border-color: #fd7e14; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");
            }

            listContainer.getChildren().add(card);
        }
    }

    private void showAddDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un Propriétaire");

        TextField nom = new TextField();
        TextField prenom = new TextField();
        TextField adresse = new TextField();
        TextField ville = new TextField();
        TextField tel = new TextField();

        VBox content = new VBox(10,
                new Label("Nom :"), nom,
                new Label("Prénom :"), prenom,
                new Label("Adresse :"), adresse,
                new Label("Ville :"), ville,
                new Label("Téléphone :"), tel
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                controller.ajouterProprietaire(nom.getText(), prenom.getText(), adresse.getText(), ville.getText(), tel.getText());
                updateList("");
            }
        });
    }

    private void showEditDialog(int index) {
        Proprietaire p = controller.getProprietaires().get(index);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier Propriétaire");

        TextField nom = new TextField(p.getNom());
        TextField prenom = new TextField(p.getPrenom());
        TextField adresse = new TextField(p.getAdresse());
        TextField ville = new TextField(p.getVille());
        TextField tel = new TextField(p.getTelephone());

        VBox content = new VBox(10,
                new Label("Nom :"), nom,
                new Label("Prénom :"), prenom,
                new Label("Adresse :"), adresse,
                new Label("Ville :"), ville,
                new Label("Téléphone :"), tel
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                controller.modifierProprietaire(p, nom.getText(), prenom.getText(), adresse.getText(), ville.getText(), tel.getText());
                updateList("");
            }
        });
    }

    public VBox getView() {
        return root;
    }
}