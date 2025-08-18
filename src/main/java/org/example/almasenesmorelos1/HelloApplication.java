package org.example.almasenesmorelos1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cambia el nombre del FXML según lo que quieras abrir primero
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));

        // Opción A: Ajuste automático al contenido
        Scene scene = new Scene(fxmlLoader.load());
        stage.sizeToScene(); // Ajusta ventana al tamaño del FXML

        // Si quieres tamaño fijo, usa esta línea en lugar de las dos de arriba:
        // Scene scene = new Scene(fxmlLoader.load(), 1000, 600);

        stage.setTitle("Almacenes Morelos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
