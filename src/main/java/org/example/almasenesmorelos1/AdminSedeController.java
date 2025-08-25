package org.example.almasenesmorelos1;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.AdminSede;
import org.example.almasenesmorelos1.model.Sede;

public class AdminSedeController {

    @FXML public Button btnAgregar;
    @FXML private FlowPane TargetasFlow;
    @FXML private ImageView imgLongOut;

    @FXML
    private void initialize() {
        // Asegura que SEDES estén cargadas desde BD
        try { DataStore.getInstance().refrescarSedesDesdeBD(); } catch (Throwable ignored) {}

        // Render inicial (derivado de SEDES con ID_ADMIN asignado)
        renderFromSedes();

        // Reactivo: si cambian sedes (p.ej. asignas un admin), re-render
        DataStore.getInstance().getSedes().addListener((ListChangeListener<Sede>) change -> renderFromSedes());

        // Botón para abrir modal
        btnAgregar.setOnAction(e -> abrirFormularioAdminSede());
    }

    /** Reconstruye tarjetas de admins a partir de las sedes que tienen ID_ADMIN. */
    private void renderFromSedes() {
        TargetasFlow.getChildren().clear();

        for (Sede s : DataStore.getInstance().getSedes()) {
            String u = s.getIdAdmin();
            if (u != null && !u.isBlank()) {
                // No tenemos nombre/correo/tel persistidos; mostramos username como nombre
                AdminSede admin = new AdminSede(u, "", "");
                admin.setUsername(u);
                admin.setPassword(u);       // demo
                admin.setSedeId(s.getId());
                TargetasFlow.getChildren().add(createAdminCard(admin));
            }
        }
    }

    /** Carga la tarjeta de admin y setea datos. */
    private Node createAdminCard(AdminSede a) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/almasenesmorelos1/TargetasAdminSede.fxml")
            );
            Node card = loader.load();

            TargetasAdminSedeController ctrl = loader.getController();
            // Mostramos username como "nombre" visible si no tenemos otro dato
            ctrl.setLblNombre(a.getUsername() != null && !a.getUsername().isBlank() ? a.getUsername() : a.getNombre());
            ctrl.setLblCorreo(a.getCorreo() == null ? "" : a.getCorreo());
            ctrl.setLblTelefono(a.getTelefono() == null ? "" : a.getTelefono());
            // Si tu tarjeta tiene label de sede:
            // ctrl.setLblSede(a.getSedeId());

            return card;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new javafx.scene.control.Label("Error cargando tarjeta");
        }
    }

    @FXML
    private void abrirFormularioAdminSede() {
        try {
            FXMLLoader fx = new FXMLLoader(
                    getClass().getResource("/org/example/almasenesmorelos1/RegistarAdmins.fxml")
            );
            Parent root = fx.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Admin de Sede");
            stage.showAndWait();

            // Al cerrar, si se asignó admin, DataStore.getSedes() cambiará y se re-renderiza solo
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo abrir el formulario.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    @FXML
    public void handleLogoutIconAction(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/org/example/almasenesmorelos1/InicioSuperAdmin.fxml")
            );
            Stage currentStage = (Stage) imgLongOut.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de Login.");
        }
    }
}
