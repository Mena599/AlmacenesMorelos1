package org.example.almasenesmorelos1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    // Cambia este nombre si quieres arrancar con otra vista (por ejemplo "Login.fxml")
    private static final String FXML_INICIAL = "Almacenes.fxml";
    // private static final String FXML_INICIAL = "Login.fxml";

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(FXML_INICIAL));
            Parent root = loader.load();
            Scene scene = new Scene(root); // sin tamaño fijo, usa el del FXML
            stage.setTitle("Almacenes Morelos");
            stage.setScene(scene);
            stage.sizeToScene(); // ajusta al contenido del FXML
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al iniciar");
            alert.setHeaderText("No se pudo cargar el FXML inicial");
            alert.setContentText("Verifica la ruta: /org/example/almasenesmorelos1/" + FXML_INICIAL);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
