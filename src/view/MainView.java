package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView {
    private VBox root;

    public MainView(Stage stage) {
        root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #ffffff);");

        Text title = new Text("🐾 VetCare360");
        title.setFont(Font.font("Segoe UI", 32));
        title.setStyle("-fx-fill: #2c3e50; -fx-font-weight: bold;");

        // Image illustration
        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/animals.png").toExternalForm()));
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);

        Button btnVets = createStyledButton("Liste des Vétérinaires", "#17a2b8");
        btnVets.setOnAction(e -> {
            VeterinaireView vetView = new VeterinaireView(stage);
            Scene scene = new Scene(vetView.getView(), 800, 600);
            stage.setScene(scene);
        });

        Button btnProps = createStyledButton("Liste des Propriétaires", "#007bff");
        btnProps.setOnAction(e -> {
            ProprietaireSearchView view = new ProprietaireSearchView(stage);
            Scene scene = new Scene(view.getView(), 900, 600);
            stage.setScene(scene);
        });

        root.getChildren().addAll(title, imageView, btnVets, btnProps);
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 10 30;");
        return button;
    }

    public VBox getView() {
        return root;
    }
}
