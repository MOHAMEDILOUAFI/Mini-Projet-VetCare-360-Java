package view;

import controller.VeterinaireController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Veterinaire;

import java.util.List;

public class VeterinaireView {
    private VBox root;
    private VBox listContainer;
    private VeterinaireController controller;
    private int selectedIndex = -1;

    public VeterinaireView(Stage stage) {
        controller = new VeterinaireController();

        root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f4f8, #d9e2ec);");

        Label title = new Label("\uD83D\uDC68\u200D⚕\uFE0F Liste des Vétérinaires");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        listContainer = new VBox(15);
        listContainer.setAlignment(Pos.CENTER_LEFT);
        listContainer.setPadding(new Insets(20));
        listContainer.setStyle("-fx-background-color: white; -fx-border-radius: 12; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        updateList();

        Button addBtn = createStyledButton("➕ Ajouter", "#28a745");
        Button editBtn = createStyledButton("✏️ Modifier", "#ffc107");
        Button deleteBtn = createStyledButton("🗑 Supprimer", "#dc3545");
        Button backBtn = createStyledButton("⬅ Retour", "#6c757d");

        addBtn.setOnAction(e -> showAddDialog());
        editBtn.setOnAction(e -> {
            if (selectedIndex >= 0) showEditDialog(selectedIndex);
        });
        deleteBtn.setOnAction(e -> {
            if (selectedIndex >= 0 && confirm("Supprimer ce vétérinaire ?")) {
                controller.supprimerVeterinaire(selectedIndex);
                selectedIndex = -1;
                updateList();
            }
        });
        backBtn.setOnAction(e -> {
            MainView mainView = new MainView(stage);
            Scene scene = new Scene(mainView.getView(), 900, 600);
            scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            stage.setScene(scene);
        });

        HBox buttons = new HBox(15, addBtn, editBtn, deleteBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, listContainer, buttons);
    }

    private Button createStyledButton(String text, String bgColor) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + bgColor + "; " +
                "-fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; " +
                "-fx-font-size: 14px; -fx-padding: 10 20;");
        return btn;
    }

    private void showAddDialog() {
        TextInputDialog nomDialog = new TextInputDialog();
        nomDialog.setHeaderText("Nom du vétérinaire :");
        nomDialog.showAndWait().ifPresent(nom -> {
            TextInputDialog specDialog = new TextInputDialog();
            specDialog.setHeaderText("Spécialité :");
            specDialog.showAndWait().ifPresent(spe -> {
                controller.ajouterVeterinaire(nom, spe);
                updateList();
            });
        });
    }

    private void showEditDialog(int index) {
        Veterinaire selected = controller.getVeterinaires().get(index);
        TextInputDialog nomDialog = new TextInputDialog(selected.getNom());
        nomDialog.setHeaderText("Modifier le nom :");
        nomDialog.showAndWait().ifPresent(nom -> {
            TextInputDialog specDialog = new TextInputDialog(selected.getSpecialite());
            specDialog.setHeaderText("Modifier la spécialité :");
            specDialog.showAndWait().ifPresent(spe -> {
                controller.modifierVeterinaire(index, nom, spe);
                updateList();
            });
        });
    }

    private void updateList() {
        listContainer.getChildren().clear();
        List<Veterinaire> list = controller.getVeterinaires();
        for (int i = 0; i < list.size(); i++) {
            Veterinaire v = list.get(i);
            int currentIndex = i;

            HBox card = new HBox(10);
            card.setPadding(new Insets(10));
            card.setAlignment(Pos.CENTER_LEFT);
            card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label label = new Label(v.getNom() + " - " + v.getSpecialite());
            label.setStyle("-fx-font-size: 16px; -fx-text-fill: #343a40;");

            card.getChildren().add(label);
            card.setOnMouseClicked((MouseEvent event) -> {
                selectedIndex = currentIndex;
                updateList();
            });

            if (selectedIndex == i) {
                card.setStyle("-fx-background-color: #d1ecf1; -fx-border-color: #17a2b8; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");
            }

            listContainer.getChildren().add(card);
        }
    }

    private boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    public VBox getView() {
        return root;
    }
}