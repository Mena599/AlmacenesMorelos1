package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class InicioAdminSedeController {

    @FXML private AnchorPane rootPane;
    @FXML private Button verAlmacenesButton;
    @FXML private Button verClientesButton;
    @FXML private ImageView userIcon;
    @FXML private ImageView logoutIcon;
    @FXML private Button btnalmacenes;
    @FXML private Button btnClientes;
    @FXML public Button ir;
    @FXML
    private void initialize() {
        // Método que se ejecuta al cargar el FXML.
        // Se puede usar para inicializar datos o componentes.
    }

    @FXML
    private void iralmacenes(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Almacenes.fxml"));
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
    private void irclientes(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Clientes.fxml"));
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
    private void handleUserIconAction(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PerfilUsuarioPopup.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Perfil del Usuario");
            stage.setScene(new Scene(parent));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar el popup de perfil.");
        }
    }

    @FXML
    private void irinicio(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
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
    private void handleLogoutIconAction(MouseEvent event) {
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}