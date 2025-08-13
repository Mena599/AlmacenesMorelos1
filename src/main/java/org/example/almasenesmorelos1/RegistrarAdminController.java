package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

// ➕ NUEVO
import javafx.util.StringConverter;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.Sede;

public class RegistrarAdminController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private Button btnRegistrar;

    // ➕ NUEVO: selector de sede
    @FXML private ComboBox<Sede> sedeCombo;

    // Referencia del contenedor de tarjetas (tu flujo actual)
    private AdminSedeController AdminSedeController;

    public void setAdminSedeController(AdminSedeController controller) {
        this.AdminSedeController = controller;
    }

    @FXML
    private void initialize() {
        // Cargar sedes desde el DataStore (ObservableList)
        sedeCombo.setItems(DataStore.getInstance().getSedes());

        // Mostrar el nombre de la sede
        sedeCombo.setConverter(new StringConverter<>() {
            @Override public String toString(Sede s) { return s == null ? "" : s.getNombre(); }
            @Override public Sede fromString(String s) { return null; }
        });

        sedeCombo.getItems().addListener((javafx.collections.ListChangeListener<Sede>) c -> {
            btnRegistrar.setDisable(sedeCombo.getItems().isEmpty());
        });
    }

    @FXML
    private void OnRegistarAction(ActionEvent event) {
        try {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();
            Sede sedeSeleccionada = sedeCombo.getValue();

            // Validaciones mínimas
            StringBuilder errors = new StringBuilder();
            if (nombre == null || nombre.trim().isEmpty()) errors.append("Nombre requerido.\n");
            if (correo == null || correo.trim().isEmpty()) errors.append("Correo requerido.\n");
            if (telefono == null || telefono.trim().isEmpty()) errors.append("Teléfono requerido.\n");
            if (sedeSeleccionada == null) errors.append("Selecciona una Sede.\n");

            if (errors.length() > 0) {
                // Alerta simple sin romper tu flujo (usa tus utilidades si tienes)
                javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
                a.setTitle("Datos incompletos");
                a.setHeaderText(null);
                a.setContentText(errors.toString());
                a.showAndWait();
                return;
            }

            // Cargar tarjeta como ya lo hacías
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TargetasAdminSede.fxml"));
            AnchorPane tarjeta = loader.load();

            TargetasAdminSedeController controller = loader.getController();
            controller.setLblNombre(nombre);
            controller.setLblCorreo(correo);
            controller.setLblTelefono(telefono);
            // ➕ (opcional) si tu tarjeta muestra la sede:
            // controller.setLblSede(sedeSeleccionada.getNombre());

            if (AdminSedeController != null) {
                AdminSedeController.getTargetasFlow().getChildren().add(tarjeta);
            }

            // Cerrar esta ventana
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.close();

            System.out.println("ClientesController: " + AdminSedeController);
            System.out.println("FlowPane: " + (AdminSedeController != null ? AdminSedeController.getTargetasFlow() : "null"));
            System.out.println("Nombre capturado: " + nombre + " | Sede: " + sedeSeleccionada.getNombre());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
