package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InicioSuperAdminController {

    @FXML
    private Button btnClientes;

    @FXML
    private void abrirVentana2(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Clientes.fxml"));
            Parent root = loader.load();

            // Obtener el stage actual desde el botón que se presionó
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Clientes");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
