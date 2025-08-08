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
    private FlowPane TargetasFlow; // FlowPane para agregar tarjetas dinámicamente

    // Getter público para que otros controladores accedan al FlowPane
    public FlowPane getTargetasFlow() {
        return TargetasFlow;
    }

    @FXML
    private void initialize() {
        btnAgregar.setOnAction(this::abrirFormularioAlmacen);
    }

    /**
     * Método para abrir la ventana de registro de almacén como modal
     */
    @FXML
    private void abrirFormularioAlmacen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarAlmacenes.fxml"));
            Parent root = loader.load();

            // Obtenemos el controlador del registro
            RegistrarAlmacenesController registrarController = loader.getController();
            registrarController.setAlmacenesController(this); // Pasamos referencia

            // Abrimos la ventana modal
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Almacén");
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
