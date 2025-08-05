package org.example.almasenesmorelos1;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class RegistrarSedesController {

    @FXML
    private Button cuernavacaButton;

    @FXML
    private Button temixcoButton;

    @FXML
    private Button eZapataButton;

    @FXML
    private ImageView userIcon;

    @FXML
    private ImageView logoutIcon;

    @FXML
    private Button addButton;

    @FXML
    private void handleRegistrarButtonAction() {

    }

    // Asumiremos que los botones de eliminar se manejan de forma genérica
    // Si tuvieras que interactuar con ellos dinámicamente, se usaría otra estrategia.

    // Este método se llamará al hacer clic en los botones de sede del panel lateral
    @FXML
    private void handleSedeButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String sedeName = clickedButton.getText();

        showAlert(Alert.AlertType.INFORMATION, "Navegación", "Has seleccionado la sede de " + sedeName);

        // Aquí podrías agregar la lógica para cargar los datos de la sede seleccionada
        // Por ejemplo: cargar una lista de almacenes de Cuernavaca.
    }

    // Este método se llamará al hacer clic en los botones de eliminar
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        // En una aplicación real, obtendrías el objeto de la sede
        // que deseas eliminar y confirmarías la acción.
        showAlert(Alert.AlertType.CONFIRMATION, "Confirmar eliminación", "¿Estás seguro de que quieres eliminar esta sede?");

        // Aquí iría la lógica para eliminar el elemento de la base de datos o de la lista.
    }

    // Este método se llama al hacer clic en el icono de usuario
    @FXML
    private void handleUserIconAction(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Información de Usuario", "Se ha hecho clic en el icono de usuario. Aquí se podría mostrar un perfil.");

        // Lógica para abrir una ventana de perfil o mostrar información del usuario.
    }

    // Este método se llama al hacer clic en el icono de cerrar sesión
    @FXML
    private void handleLogoutIconAction(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Cerrar Sesión", "Has cerrado la sesión. Redirigiendo a la pantalla de login.");

        // Aquí se implementaría la lógica de logout, por ejemplo,
        // cerrando la ventana actual y abriendo la ventana de login.
        // ((Node) event.getSource()).getScene().getWindow().hide(); // Ocultar la ventana actual
        // Lógica para abrir el FXML de login...
    }

    // Este método se llama al hacer clic en el botón de "+ Añadir"
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Añadir Sede", "Se ha hecho clic en el botón de añadir. Aquí se podría abrir un formulario de registro para una nueva sede.");

        // Lógica para abrir el formulario de registro de sede (Sede.fxml).
        // Sería muy similar a cómo abriste la vista de "Sedes" desde el Login.
    }

    // Método auxiliar para mostrar alertas, para mantener el código DRY (Don't Repeat Yourself)
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
