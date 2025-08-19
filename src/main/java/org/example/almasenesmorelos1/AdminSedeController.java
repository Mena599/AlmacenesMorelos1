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

public class AdminSedeController {

    @FXML public Button btnAgregar;
    @FXML private FlowPane TargetasFlow;
    @FXML private ImageView imgLongOut;

    @FXML
    private void initialize() {
        // 1) Render inicial
        TargetasFlow.getChildren().clear();
        DataStore.getInstance().getAdmins()
                .forEach(a -> TargetasFlow.getChildren().add(createAdminCard(a)));

        // 2) Reactivo
        DataStore.getInstance().getAdmins().addListener((ListChangeListener<AdminSede>) change -> {
            TargetasFlow.getChildren().setAll(
                    DataStore.getInstance().getAdmins().stream()
                            .map(this::createAdminCard)
                            .toList()
            );
        });

        // 3) Botón para abrir modal
        btnAgregar.setOnAction(e -> abrirFormularioAdminSede());
    }

    /** Carga la tarjeta de admin y setea datos. */
    private Node createAdminCard(AdminSede a) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/almasenesmorelos1/TargetasAdminSede.fxml")
            );
            Node card = loader.load();

            TargetasAdminSedeController ctrl = loader.getController();
            ctrl.setLblNombre(a.getNombre());
            ctrl.setLblCorreo(a.getCorreo());
            ctrl.setLblTelefono(a.getTelefono());
            // Si tienes sede/estado:
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
