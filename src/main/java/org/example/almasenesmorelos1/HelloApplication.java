package org.example.almasenesmorelos1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< Updated upstream

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
=======
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());  // Sin tamaño fijo para que se ajuste al contenido
>>>>>>> Stashed changes
        stage.setTitle("Almacenes Morelos");
        stage.setScene(scene);
        stage.sizeToScene();  // Ajusta tamaño ventana al contenido
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
