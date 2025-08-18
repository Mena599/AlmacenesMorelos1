package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Optional;

public class InicioSuperAdminController {

    @FXML
    public Button btnmirar1;
    public Button btnmirar2;
    public Button btnir;
    public ImageView logoutIcon;
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
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleLogoutIconAction(javafx.scene.input.MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Cierre de Sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Lógica para regresar a la vista de login
                Parent loginView = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = logoutIcon.getScene();
                scene.setRoot(loginView);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de login.");
            }
        }

    }
}
