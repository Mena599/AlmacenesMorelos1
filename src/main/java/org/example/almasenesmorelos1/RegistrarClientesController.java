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
    @FXML private DatePicker dpFechaNacimiento;     // usado como fecha de adquisición
    @FXML private Button btnRegistrar;

    // === NUEVO: labels de precios y precio aplicado ===
    @FXML private Label lblPrecioVenta;
    @FXML private Label lblPrecioRenta;
    @FXML private Label lblPrecioAplicado;

    // === Estado: el almacén al que asignaremos el cliente ===
    private Almacen almacen;

    /** Llamado desde la tarjeta antes de mostrar el modal */
    public void setAlmacen(Almacen a) {
        this.almacen = a;

        // Pinta precios del almacén en la UI
        if (lblPrecioVenta != null) {
            lblPrecioVenta.setText("$" + formatMoney(a.getPrecioVenta()));
        }
        if (lblPrecioRenta != null) {
            lblPrecioRenta.setText("$" + formatMoney(a.getPrecioRenta()));
        }

        // Si ya hay selección, actualiza el precio aplicado; si no, deja en blanco
        actualizarPrecioAplicado();
    }

    @FXML
    private void initialize() {
        // Asegurar valores en combo si no vienen del FXML
        if (comboOperacion != null && (comboOperacion.getItems() == null || comboOperacion.getItems().isEmpty())) {
            comboOperacion.getItems().setAll("Venta", "Renta");
        }

        // Selección por defecto y listener para actualizar precio aplicado
        if (comboOperacion != null) {
            if (comboOperacion.getValue() == null) {
                comboOperacion.getSelectionModel().select("Renta"); // por defecto
            }
            comboOperacion.valueProperty().addListener((obs, oldVal, newVal) -> actualizarPrecioAplicado());
        }
    }

    private void actualizarPrecioAplicado() {
        if (almacen == null || lblPrecioAplicado == null || comboOperacion == null) return;

        String op = comboOperacion.getValue();
        if ("Venta".equalsIgnoreCase(op)) {
            lblPrecioAplicado.setText("$" + formatMoney(almacen.getPrecioVenta()));
        } else if ("Renta".equalsIgnoreCase(op)) {
            lblPrecioAplicado.setText("$" + formatMoney(almacen.getPrecioRenta()));
        } else {
            lblPrecioAplicado.setText("—");
        }
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

        // 2) Mapear estatus
        EstatusOperacion estatus = "Venta".equalsIgnoreCase(operacion)
                ? EstatusOperacion.VENTA
                : EstatusOperacion.RENTA;

        String nombreCompleto = (nombre + " " + apellidos).trim();
        LocalDate fechaExp = fechaAdq.plusMonths(12); // regla provisional

        // 3) (OPCIONAL) Calcular precio aplicado (útil guardar en la asignación)
        double precioAplicado = "Venta".equalsIgnoreCase(operacion)
                ? almacen.getPrecioVenta()
                : almacen.getPrecioRenta();

        // 4) Construir Asignación y guardar en DataStore
        //    Si no quieres guardar precioAplicado en el modelo, usa tu constructor actual;
        //    si quieres guardarlo, ver apartado 3) abajo para ampliar el modelo.
        AsignacionCliente asig = new AsignacionCliente(
                almacen.getId(),
                nombreCompleto,
                correo,
                telefono,
                estatus,
                fechaAdq,
                fechaExp,
                precioAplicado  // <--- nuevo
        );

        DataStore.getInstance().agregarAsignacion(asig);

        // 5) Cerrar modal
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        if (stage != null) stage.close();
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }
    private String formatMoney(double v) { return String.format("%,.2f", v); }

    private void alert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
