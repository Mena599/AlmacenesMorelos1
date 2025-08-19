package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.AsignacionCliente;
import org.example.almasenesmorelos1.model.EstatusOperacion;

import java.time.LocalDate;

public class RegistrarClientesController {

    // === Campos que existen en tu FXML ===
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private ComboBox<String> comboOperacion;  // "Venta" | "Renta"
    @FXML private DatePicker dpFechaNacimiento;     // (usaremos como fecha de adquisición por ahora)
    @FXML private Button btnRegistrar;

    // === Estado: el almacén al que asignaremos el cliente ===
    private Almacen almacen;

    /** Llamado desde la tarjeta antes de mostrar el modal */
    public void setAlmacen(Almacen a) {
        this.almacen = a;
    }

    @FXML
    private void OnRegistarAction(ActionEvent event) {
        // 1) Validaciones básicas
        String nombre = safe(txtNombre.getText());
        String apellidos = safe(txtApellidos.getText());
        String correo = safe(txtCorreo.getText());
        String telefono = safe(txtTelefono.getText());
        String operacion = comboOperacion != null ? comboOperacion.getValue() : null;
        LocalDate fechaAdq = dpFechaNacimiento != null ? dpFechaNacimiento.getValue() : null;

        StringBuilder sb = new StringBuilder();
        if (almacen == null) sb.append("No se recibió el almacén.\n");
        if (nombre.isBlank()) sb.append("Ingresa el nombre.\n");
        if (apellidos.isBlank()) sb.append("Ingresa los apellidos.\n");
        if (correo.isBlank()) sb.append("Ingresa el correo.\n");
        if (telefono.isBlank()) sb.append("Ingresa el teléfono.\n");
        if (operacion == null) sb.append("Selecciona Venta o Renta.\n");
        if (fechaAdq == null) sb.append("Selecciona la fecha.\n");

        if (sb.length() > 0) {
            alert(Alert.AlertType.WARNING, "Datos incompletos", sb.toString());
            return;
        }

        // 2) Mapear estatus y preparar datos
        EstatusOperacion estatus = "Venta".equalsIgnoreCase(operacion)
                ? EstatusOperacion.VENTA
                : EstatusOperacion.RENTA;

        String nombreCompleto = (nombre + " " + apellidos).trim();
        LocalDate fechaExp = fechaAdq.plusMonths(12); // regla provisional

        // 3) Construir Asignación y guardar en DataStore
        AsignacionCliente asig = new AsignacionCliente(
                almacen.getId(),
                nombreCompleto,
                correo,
                telefono,
                estatus,
                fechaAdq,
                fechaExp
        );
        DataStore.getInstance().agregarAsignacion(asig);

        // 4) Cerrar modal
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        if (stage != null) stage.close();
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }

    private void alert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
