package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.Sede;
public class RegistrarSedesController {

    @FXML private TextField idSedeField;
    @FXML private TextField municipioField;
    @FXML private PasswordField idAdmiField;
    @FXML private PasswordField confirmarIdAdmiField;
    @FXML private TextField telefonoField;
    @FXML private TextField diaField;
    @FXML private TextField mesField;
    @FXML private TextField anioField;
    @FXML private CheckBox terminosCheckBox;
    @FXML private Button registrarButton;

    // Referencia al controlador de la ventana principal
    private SedesController sedesController;

    // Método para establecer la referencia del SedesController
    public void setSedesController(SedesController sedesController) {
        this.sedesController = sedesController;
    }

    @FXML
    private void handleRegistrarButtonAction() {
        if (validateInput()) {
            // Lógica para registrar la sede en la base de datos
            String idSede = idSedeField.getText();
            String municipio = municipioField.getText();
            String idAdmi = idAdmiField.getText();
            String telefono = telefonoField.getText();
            String fechaRegistro = diaField.getText() + "/" + mesField.getText() + "/" + anioField.getText();

            DataStore.getInstance().agregarSede(
                    new Sede(idSede, municipio, idAdmi, telefono, fechaRegistro)
            );

            // Llama al método del SedesController para agregar la nueva sede a la vista
            if (sedesController != null) {
                sedesController.addSedeToView(idSede, municipio, idAdmi, telefono, fechaRegistro);
            }

            showAlert(Alert.AlertType.INFORMATION, "Registro Exitoso", "La nueva sede ha sido registrada correctamente.");

            // Cerrar la ventana del formulario
            Stage stage = (Stage) registrarButton.getScene().getWindow();
            stage.close();

        } else {
            // La validación falló, se muestra la alerta correspondiente
        }
    }

    // ... métodos validateInput() y showAlert() sin cambios ...
    private boolean validateInput() {
        String errorMessage = "";

        if (idSedeField.getText() == null || idSedeField.getText().trim().isEmpty()) {
            errorMessage += "El campo 'ID Sede' no puede estar vacío.\n";
        }
        if (municipioField.getText() == null || municipioField.getText().trim().isEmpty()) {
            errorMessage += "El campo 'Municipio' no puede estar vacío.\n";
        }
        if (idAdmiField.getText() == null || idAdmiField.getText().trim().isEmpty()) {
            errorMessage += "El campo 'Admi-Sede' no puede estar vacío.\n";
        }
        if (confirmarIdAdmiField.getText() == null || confirmarIdAdmiField.getText().trim().isEmpty()) {
            errorMessage += "El campo 'Confirmar ID Admin' no puede estar vacío.\n";
        }
        if (!idAdmiField.getText().equals(confirmarIdAdmiField.getText())) {
            errorMessage += "Los ID de administrador no coinciden.\n";
        }
        if (telefonoField.getText() == null || telefonoField.getText().trim().isEmpty()) {
            errorMessage += "El campo 'Número Telefónico' no puede estar vacío.\n";
        }
        if (!terminosCheckBox.isSelected()) {
            errorMessage += "Debes aceptar los términos y condiciones.\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de Validación", errorMessage);
            return false;
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