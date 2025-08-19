package org.example.almasenesmorelos1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.AdminSede;
import org.example.almasenesmorelos1.model.Sede;
import javafx.collections.ListChangeListener;

public class RegistrarAdminController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private Button btnRegistrar;

    @FXML private ComboBox<Sede> sedeCombo;

    // Opcional: solo si lo tienes en tu FXML. Si no, déjalo como null sin problemas.
    @FXML private TextField txtUsername;

    // Lista “cache” para reponer sedes libres cuando cambie el store
    private final ObservableList<Sede> sedesLibres = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        recargarSedesLibres();

        sedeCombo.setItems(sedesLibres);
        sedeCombo.setConverter(new StringConverter<>() {
            @Override public String toString(Sede s) { return s == null ? "" : s.getNombre(); }
            @Override public Sede fromString(String s) { return null; }
        });

        btnRegistrar.setDisable(sedeCombo.getItems().isEmpty());
    }


    private void recargarSedesLibres() {
        sedesLibres.setAll(
                DataStore.getInstance().getSedes().filtered(s -> {
                    try { return !s.isOcupada(); } catch (Exception e) { return true; }
                })
        );
    }

    @FXML
    private void OnRegistarAction() {
        String nombre   = safe(txtNombre.getText());
        String correo   = safe(txtCorreo.getText());
        String telefono = safe(txtTelefono.getText());
        Sede sedeSeleccionada = sedeCombo.getValue();
        String usernameDeseado = txtUsername != null ? safe(txtUsername.getText()) : null;

        // Validaciones
        StringBuilder errors = new StringBuilder();
        if (nombre.isBlank())   errors.append("Nombre requerido.\n");
        if (correo.isBlank())   errors.append("Correo requerido.\n");
        if (telefono.isBlank()) errors.append("Teléfono requerido.\n");
        if (sedeSeleccionada == null) errors.append("Selecciona una Sede.\n");

        // Verificar que siga libre (por si cambió el estado mientras el modal estaba abierto)
        if (sedeSeleccionada != null) {
            try {
                if (sedeSeleccionada.isOcupada()) {
                    errors.append("La sede seleccionada ya está ocupada.\n");
                }
            } catch (Exception ignore) {
                // Si tu modelo aún no tiene 'ocupada', añade el campo como te pasé antes
            }
        }

        if (errors.length() > 0) {
            new Alert(Alert.AlertType.WARNING, errors.toString()).showAndWait();
            return;
        }

        // Crear el admin (datos básicos)
        AdminSede admin = new AdminSede(nombre, correo, telefono);

        // Asignar en DataStore: bloquea sede, setea sedeId, crea username/password únicos
        boolean ok = DataStore.getInstance().asignarSedeAAdmin(sedeSeleccionada, admin, usernameDeseado);
        if (!ok) {
            new Alert(Alert.AlertType.ERROR, "No se pudo asignar la sede (ya ocupada o usuario existente).").showAndWait();
            // Refrescar la lista por si cambió el estado
            recargarSedesLibres();
            return;
        }

        // Mostrar credenciales
        new Alert(Alert.AlertType.INFORMATION,
                "Admin asignado correctamente.\n\nUsuario: " + admin.getUsername() +
                        "\nContraseña: " + admin.getPassword() +
                        "\nSede: " + admin.getSedeId()
        ).showAndWait();

        // Refrescar sedes libres del combo (ésta ya no debe aparecer)
        recargarSedesLibres();

        // Cerrar modal
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        if (stage != null) stage.close();
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }
}
