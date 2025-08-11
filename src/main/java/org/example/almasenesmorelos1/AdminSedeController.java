package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.EventObject;

public class AdminSedeController {

        @FXML
        public Button btnAgregar;
        @FXML
        private void initialize() {
            btnAgregar.setOnAction(this::abrirFormularioAdminSede);
        }
        @FXML
        private FlowPane TargetasFlow;
        @FXML
        private ImageView imgLongOut;

        public FlowPane getTargetasFlow() {
            return TargetasFlow;
        }
        @FXML
        private void abrirFormularioAdminSede(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistarAdmins.fxml"));
                Parent root = loader.load();

                // Obtenemos el controlador de la ventana emergente
                RegistrarAdminController registrarController = loader.getController();

                // Le pasamos la referencia de este mismo controlador (ClientesController)
                registrarController.setAdminSedeController(this); // <- ¡ESTO ES CLAVE!

                // Creamos y mostramos la ventana emergente
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Registrar Cliente");
                stage.showAndWait();

            } catch (IOException e) {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmarCerrarSesion.fxml"));
            Parent root = loader.load();

            ConfirmarCerrarSesionController controller = loader.getController();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Confirmar Cerrar Sesión");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            if (controller.isConfirmado()) {
                // Usa mouseEvent en lugar de crear un evento nulo
                Stage currentStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
                currentStage.close();

                FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent loginRoot = loginLoader.load();
                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(loginRoot));
                loginStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la ventana de confirmación.");
        }
    }

}