package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AlmacenesController {

    @FXML
    public Button btnAgregar;
    @FXML
    private void initialize() {
        btnAgregar.setOnAction(this::abrirFormularioAlmacen);
    }
    @FXML
    private FlowPane TargetasFlow;

    public FlowPane getTargetasFlow() {
        return TargetasFlow;
    }
    @FXML
    private void abrirFormularioAlmacen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarAlmacenes.fxml"));
            Parent root = loader.load();

            // Obtenemos el controlador de la ventana emergente
            RegistrarAlmacenesController registrarController = loader.getController();

            // Le pasamos la referencia de este mismo controlador (AlmacenesController)

            // Creamos y mostramos la ventana emergente
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Almacen");
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}