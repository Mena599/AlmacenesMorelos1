package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SedesController {

    @FXML private AnchorPane rootPane;
    @FXML private ImageView userIcon;
    @FXML private ImageView logoutIcon;
    @FXML private Button cuernavacaButton;
    @FXML private Button temixcoButton;
    @FXML private Button eZapataButton;
    @FXML private Button addButton;
    @FXML private HBox contentHBox; // Agregado para referenciar el HBox con los almacenes

    @FXML
    private void initialize() {
        // Lógica de inicialización

    }

    /**
     * Maneja los clics en los botones de sede (Cuernavaca, Temixco, etc.).
     *
     */
    @FXML
    private void handleSedeButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String sedeName = clickedButton.getText();

        System.out.println("Navegando a la vista de almacenes de: " + sedeName);

        // Aquí se implementa el cambio de vista, por ejemplo, cargando la vista de "Almacenes"
        try {
            Parent almacenesView = FXMLLoader.load(getClass().getResource("AlmacenesView.fxml"));
            Scene scene = rootPane.getScene(); // Obtiene la escena actual
            scene.setRoot(almacenesView); // Reemplaza la raíz de la escena con la nueva vista

            // AlmacenesController controller = fxmlLoader.getController();
            // controller.setSede(sedeName);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error de Navegación", "No se pudo cargar la vista de Almacenes.");
        }
    }

    /**
     * Maneja el clic en el botón de "+ Añadir".
     * Abre un popup con el formulario de registro de sedes.
     */
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegistrarSedes.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Nueva Sede");
            stage.setScene(new Scene(parent));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar el formulario de registro de sedes.");
        }
    }

    /**
     * Maneja el clic en los botones de eliminar (los de la papelera).
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres eliminar este almacén?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Almacén eliminado.");
        }
    }

    /**
     * Maneja el clic en el ícono de usuario.
     * Abre un popup con el perfil del administrador.
     */
    @FXML
    private void handleUserIconAction(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PerfilUsuarioPopup.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Perfil del Usuario");
            stage.setScene(new Scene(parent));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar el popup de perfil.");
        }
    }

    /**
     * Maneja el clic en el ícono de cerrar sesión.
     * Muestra una alerta de confirmación y regresa a la pantalla de login.
     */
    @FXML
    private void handleLogoutIconAction(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Cierre de Sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Parent loginView = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = logoutIcon.getScene();
                scene.setRoot(loginView);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de login.");
            }
        }
    }

    /**
     * Método auxiliar para mostrar alertas.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}