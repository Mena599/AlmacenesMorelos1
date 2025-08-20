package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.Sede;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RegistrarSedesController {

    @FXML private TextField idSedeField;
    @FXML private TextField municipioField;
    @FXML private TextField telefonoField;
    @FXML private TextField diaField;
    @FXML private TextField mesField;
    @FXML private TextField anioField;
    @FXML private CheckBox terminosCheckBox;
    @FXML private Button registrarButton;

    // Referencia al controlador de la ventana principal
    private SedesController sedesController;

    // Contador para el número de sedes
    private static int sedeCounter = 1;

    // Método para establecer la referencia del SedesController
    public void setSedesController(SedesController sedesController) {
        this.sedesController = sedesController;
    }

    @FXML
    public void initialize() {
        // Al abrir el formulario, generar y mostrar el ID de la sede
        idSedeField.setText(generateSedeId());
    }

    @FXML
    private void handleRegistrarButtonAction() {
        if (validateInput()) {
            // Obtener el ID de la sede ya generado
            String idSede = idSedeField.getText();
            String municipio = municipioField.getText();
            String telefono = telefonoField.getText();
            String fechaRegistro = diaField.getText() + "/" + mesField.getText() + "/" + anioField.getText();
            Sede nueva = new Sede(idSede, municipio, "", telefono, fechaRegistro); // ID Admi se ha eliminado

            // si tu modelo ya tiene 'ocupada', asegúrate que inicie libre:
            nueva.setOcupada(false);

            DataStore.getInstance().agregarSede(nueva);

            // Agregar la tarjeta a la vista usando el método que ahora acepta Sede
            if (sedesController != null) {
                sedesController.addSedeToView(nueva);
            }

            showAlert(Alert.AlertType.INFORMATION, "Registro Exitoso", "La nueva sede ha sido registrada correctamente.");

            // Cerrar la ventana del formulario
            Stage stage = (Stage) registrarButton.getScene().getWindow();
            stage.close();

            // Incrementar el contador de sedes
            sedeCounter++;
        } else {
            // La validación falló, se muestra la alerta correspondiente
        }
    }

    private String generateSedeId() {
        // Obtener la fecha actual
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String fechaRegistro = dateFormat.format(now); // Formato ddMMyy

        // Generar 4 números aleatorios
        Random random = new Random();
        int randomNumbers = random.nextInt(10000); // Genera un número entre 0 y 9999

        // Formatear el ID de la sede
        return "Sed" + sedeCounter + "-" + fechaRegistro + "-" + String.format("%04d", randomNumbers);
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (municipioField.getText() == null || municipioField.getText().trim().isEmpty()) {
            errorMessage += "El campo 'Municipio' no puede estar vacío.\n";
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

