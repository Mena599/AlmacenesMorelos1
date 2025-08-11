package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SedeCardController {

    @FXML
    private VBox rootCard;

    @FXML
    private Label municipioLabel;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Button deleteButton;

    private SedesController sedesController;
    private FlowPane parentContainer; // Cambia HBox a FlowPane

    public void setSedesController(SedesController sedesController) {
        this.sedesController = sedesController;
    }

    public void setParentContainer(FlowPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setSedeData(String idSede, String municipio, String telefono) {
        municipioLabel.setText(municipio);
        telefonoLabel.setText("Teléfono: " + telefono);
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarSede.fxml"));
            Parent root = loader.load();

            EliminarSedeController controller = loader.getController();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Confirmar eliminación");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            if (controller.isConfirmado()) {
                if (parentContainer != null) {
                    parentContainer.getChildren().remove(rootCard);
                    showAlert(Alert.AlertType.INFORMATION, "Eliminación Exitosa", "La sede ha sido eliminada.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la ventana de confirmación.");
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
