package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.Sede;

import java.io.IOException;

public class SedesController {

    @FXML
    private ImageView userIcon;

    @FXML
    private ImageView logoutIcon;

    @FXML
    private ImageView logoImage; // Asegúrate de que tu logo tenga este fx:id

    @FXML
    private Button addButton;

    @FXML
    private VBox sedeListContainer; // Contenedor para los botones de sedes

    @FXML
    private FlowPane mainCardContainer; // Contenedor para las tarjetas de sedes

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegistrarSedes.fxml"));
            Parent root = fxmlLoader.load();

            RegistrarSedesController registrarController = fxmlLoader.getController();
            registrarController.setSedesController(this);

            Stage stage = new Stage();
            stage.setTitle("Registrar Nueva Sede");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar formulario", "No se pudo cargar el formulario de registro.");
        }
    }

    public void addSedeToView(String idSede, String municipio, String idAdmi, String telefono, String fechaRegistro) {
        try {
            FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("SedeCard.fxml"));
            VBox sedeCard = cardLoader.load();

            SedeCardController cardController = cardLoader.getController();
            cardController.setSedeData(idSede, municipio, telefono);
            cardController.setSedesController(this);
            cardController.setParentContainer(mainCardContainer);

            mainCardContainer.getChildren().add(sedeCard);

            Button newSedeButton = new Button(municipio);
            newSedeButton.setPrefHeight(40.0);
            newSedeButton.setPrefWidth(210.0);
            newSedeButton.setStyle("-fx-background-color: #7d8f9e; -fx-background-radius: 5;");
            newSedeButton.setTextFill(javafx.scene.paint.Color.WHITE);
            newSedeButton.setFont(new javafx.scene.text.Font(18.0));
            VBox.setMargin(newSedeButton, new javafx.geometry.Insets(10.0, 20.0, 0, 20.0));
            newSedeButton.setOnAction(this::handleSedeButton);

            sedeListContainer.getChildren().add(newSedeButton);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al crear tarjeta", "No se pudo crear la tarjeta de la sede.");
        }
    }

    @FXML
    private void handleSedeButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String sedeName = clickedButton.getText();
        showAlert(Alert.AlertType.INFORMATION, "Navegación", "Has seleccionado la sede de " + sedeName);
    }

    public void removeSedeCard(Parent card) {
        if (mainCardContainer.getChildren().contains(card)) {
            mainCardContainer.getChildren().remove(card);
            showAlert(Alert.AlertType.INFORMATION, "Eliminación Exitosa", "La sede ha sido eliminada.");
        }
    }

    @FXML
    private void handleUserIconAction(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Información de Usuario", "Se ha hecho clic en el icono de usuario.");
    }

    // Nuevo método para manejar el clic en el logo
    @FXML
    private void handleLogoAction(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("InicioSuperAdmin.fxml"));
            Stage currentStage = (Stage) logoImage.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de Inicio.");
        }
    }

    // Nuevo método para manejar el clic en el ícono de cerrar sesión
    @FXML
    private void handleLogoutAction(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("InicioSuperAdmin.fxml"));
            Stage currentStage = (Stage) logoutIcon.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de Login.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void initialize() {
        // 1) Limpia contenedores para evitar duplicados al reentrar
        if (mainCardContainer != null) mainCardContainer.getChildren().clear();
        if (sedeListContainer != null) sedeListContainer.getChildren().clear();

        // 2) Repinta todo desde el DataStore
        for (Sede s : DataStore.getInstance().getSedes()) {
            // Reusa tu método existente para crear tarjeta + botón lateral
            addSedeToView(
                    s.getId(),
                    s.getMunicipio(),
                    s.getIdAdmin(),
                    s.getTelefono(),
                    s.getFechaRegistro()
            );
        }
    }

}