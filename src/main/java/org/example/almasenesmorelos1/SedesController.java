package org.example.almasenesmorelos1;

import javafx.collections.ListChangeListener;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.Sede;
import javafx.collections.ObservableList;
import java.io.IOException;

public class SedesController {

    @FXML private ImageView userIcon;
    @FXML private ImageView logoutIcon;
    @FXML private ImageView logoImage;

    @FXML private Button addButton;

    @FXML private VBox sedeListContainer;     // Botones laterales por sede
    @FXML private FlowPane mainCardContainer; // Tarjetas de sedes

    @FXML
    private void initialize() {
        // Asegura datos desde BD (por si llegas directo)
        try { DataStore.getInstance().refrescarSedesDesdeBD(); } catch (Throwable ignored) {}

        // Render inicial
        renderAll();

        // Reactivo: re-render completo ante cambios en la lista observable
        DataStore.getInstance().getSedes().addListener((ListChangeListener<Sede>) ch -> renderAll());
    }

    /** Repinta lista y tarjetas desde el store (estado único de la app). */
    private void renderAll() {
        if (mainCardContainer != null) mainCardContainer.getChildren().clear();
        if (sedeListContainer != null) sedeListContainer.getChildren().clear();

        for (Sede s : DataStore.getInstance().getSedes()) {
            addSedeToView(s);
        }
    }

    public void deleteSedeById(String id) {
        if (id == null) return;
        ObservableList<Sede> sedes = DataStore.getInstance().getSedes();
        boolean removed = sedes.removeIf(s -> id.equals(s.getId()));

        if (!removed) {
            showAlert(Alert.AlertType.WARNING, "No encontrada",
                    "No se encontró la sede a eliminar (id: " + id + ").");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Eliminación",
                    "La sede ha sido eliminada.");
        }
    }

    @FXML
    private void handleAddButtonAction(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/example/almasenesmorelos1/RegistrarSedes.fxml")
            );
            Parent root = fxmlLoader.load();

            // Si antes llamabas addSedeToView desde el modal, ya no es necesario.
            // El listener sobre DataStore disparará renderAll() automáticamente.

            Stage stage = new Stage();
            stage.setTitle("Registrar Nueva Sede");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar", "No se pudo cargar el formulario de registro.");
        }
    }

    /** Crea tarjeta + botón lateral para una sede (con de-duplicación). */
    public void addSedeToView(Sede sede) {
        try {
            // --- Dedupe tarjeta ---
            if (mainCardContainer != null) {
                boolean existsCard = mainCardContainer.getChildren().stream()
                        .anyMatch(n -> sede.getId().equals(n.getUserData()));
                if (existsCard) return;
            }

            // --- Tarjeta ---
            FXMLLoader cardLoader = new FXMLLoader(
                    getClass().getResource("/org/example/almasenesmorelos1/SedeCard.fxml")
            );
            VBox sedeCard = cardLoader.load();
            sedeCard.setUserData(sede.getId()); // para dedupe

            SedeCardController cardController = cardLoader.getController();
            // setea datos básicos
            cardController.setSedeData(sede.getId(), sede.getMunicipio(), sede.getTelefono());
            cardController.setSedesController(this);
            cardController.setParentContainer(mainCardContainer);

            // marca visual de ocupación
            try {
                cardController.getClass().getMethod("setEstadoOcupada", boolean.class)
                        .invoke(cardController, sede.isOcupada());
            } catch (Exception ignore) {
                if (sede.isOcupada()) {
                    sedeCard.setStyle("-fx-background-color:#ffd8d8; -fx-background-radius:12;");
                } else {
                    sedeCard.setStyle("-fx-background-color:#d8ffe0; -fx-background-radius:12;");
                }
            }

            if (mainCardContainer != null) mainCardContainer.getChildren().add(sedeCard);

            // --- Dedupe botón lateral ---
            if (sedeListContainer != null) {
                boolean existsBtn = sedeListContainer.getChildren().stream()
                        .filter(n -> n instanceof Button)
                        .map(n -> (Button) n)
                        .anyMatch(b -> sede.getId().equals(b.getUserData()));
                if (!existsBtn) {
                    Button newSedeButton = new Button(sede.getMunicipio());
                    newSedeButton.setUserData(sede.getId()); // para dedupe
                    newSedeButton.setPrefHeight(40.0);
                    newSedeButton.setPrefWidth(210.0);
                    newSedeButton.setStyle("-fx-background-color: #7d8f9e; -fx-background-radius: 5;");
                    newSedeButton.setTextFill(javafx.scene.paint.Color.WHITE);
                    newSedeButton.setOnAction(this::handleSedeButton);

                    if (sede.isOcupada()) {
                        newSedeButton.setDisable(true);
                        newSedeButton.setStyle("-fx-background-color: #b0b8bf; -fx-background-radius: 5; -fx-opacity: 0.8;");
                    }
                    sedeListContainer.getChildren().add(newSedeButton);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al crear tarjeta", "No se pudo crear la tarjeta de la sede.");
        }
    }

    @FXML
    private void handleSedeButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String sedeName = clickedButton.getText();
        showAlert(Alert.AlertType.INFORMATION, "Sede", "Has seleccionado la sede de " + sedeName);
    }

    public void removeSedeCard(Parent card) {
        if (mainCardContainer.getChildren().contains(card)) {
            mainCardContainer.getChildren().remove(card);
            showAlert(Alert.AlertType.INFORMATION, "Eliminación", "La sede ha sido eliminada.");
        }
    }

    @FXML
    private void handleUserIconAction(MouseEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Usuario", "Se hizo clic en el icono de usuario.");
    }

    @FXML
    private void handleLogoAction(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/org/example/almasenesmorelos1/InicioSuperAdmin.fxml")
            );
            Stage currentStage = (Stage) logoImage.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar Inicio.");
        }
    }

    @FXML
    private void handleLogoutAction(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/org/example/almasenesmorelos1/InicioSuperAdmin.fxml")
            );
            Stage currentStage = (Stage) logoutIcon.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar Login.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
