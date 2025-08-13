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
    public Button btnmirar1;
    public Button btnmirar2;
    public Button btnir;
    @FXML
    public void OnMirar1Action(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sedes.fxml"));
            Parent root = loader.load();

            // Obtener el stage actual desde el botón que se presionó
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sedes");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void OnMiarar2Action(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminSede.fxml"));
            Parent root = loader.load();

            // Obtener el stage actual desde el botón que se presionó
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Administradores de sede");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ir(ActionEvent  event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InicioAdminSede.fxml"));
        Parent root = loader.load();

        // Obtener el stage actual desde el botón que se presionó
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Administradores de sede");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }


}
