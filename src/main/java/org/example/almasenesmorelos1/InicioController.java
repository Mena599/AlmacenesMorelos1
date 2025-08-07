package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnCompra;
    @FXML
    private Button btnRenta;

    @FXML
    private Button btnMirar;
    @FXML
    private Button btnMirar2;
     @FXML
     private Button btnRegresar;
    @FXML
    private void onInicioAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Compra.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnCompra.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Compra");

    }
    @FXML
    private void OnCompraAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Compra.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnCompra.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Compra");
    }
    @FXML
    private void OnRentaAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Renta.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnRenta.getScene().getWindow(); // obtener ventana actual
        stage.setScene(scene);
        stage.setTitle("Renta");
    }

    //Estos son de los bloques de compra y renta

    @FXML
    private void OnMirarAction() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Compra.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnMirar.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Compra");
    }

    @FXML
    private void OnMirar2Action()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Renta.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnMirar2.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Renta");
    }

 //Menu superior
    @FXML
    private void onRegresarClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnInicio.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("inicio");
    }

}
