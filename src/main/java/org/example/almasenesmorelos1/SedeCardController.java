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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SedeCardController {

    @FXML private VBox rootCard;
    @FXML private Label municipioLabel;
    @FXML private Label telefonoLabel;
    @FXML private Button deleteButton;
    @FXML private Label estadoLabel;

    private SedesController sedesController;
    private FlowPane parentContainer;

    // 🔹 Nuevo: conservar el id para poder eliminar en el store
    private String sedeId;

    public void setSedesController(SedesController sedesController) {
        this.sedesController = sedesController;
    }

    public void setParentContainer(FlowPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    // ⬇️ Guarda el id y setea textos
    public void setSedeData(String idSede, String municipio, String telefono) {
        this.sedeId = idSede;
        municipioLabel.setText(municipio);
        telefonoLabel.setText("Teléfono: " + telefono);
    }

    public void setEstadoOcupada(boolean ocupada) {
        if (estadoLabel != null) {
            estadoLabel.setText(ocupada ? "Ocupada" : "Libre");
        }
        rootCard.setStyle(ocupada
                ? "-fx-background-color: #ffd6d6; -fx-background-radius: 10;"
                : "-fx-background-color: #d6ffd6; -fx-background-radius: 10;");
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        try {
            // ⚠️ Usa ruta absoluta para evitar problemas de carga
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/almasenesmorelos1/EliminarSede.fxml"));
            Parent root = loader.load();

            EliminarSedeController controller = loader.getController();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Confirmar eliminación");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            if (controller.isConfirmado()) {
                // ✅ Elimina en el store a través del SedesController
                if (sedesController != null && sedeId != null) {
                    sedesController.deleteSedeById(sedeId);
                } else if (parentContainer != null) {
                    // Fallback (solo quita la tarjeta actual si no hay controller/id)
                    parentContainer.getChildren().remove(rootCard);
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
