package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientesController {

    @FXML
    public Button btnAgregar;

    @FXML
    private void initialize() {
        btnAgregar.setOnAction(this::abrirFormularioCliente);
    }

    public FlowPane getFlowPane() {
        return flowPaneClientes;
    }

    public void abrirFormularioCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/almasenesmorelos1/RegistrarClientes.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL); // ventana modal
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait(); // bloquea hasta que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
