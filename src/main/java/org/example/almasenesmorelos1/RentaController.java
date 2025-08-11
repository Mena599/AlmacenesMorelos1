package org.example.almasenesmorelos1;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.NumberFormat;

public class RentaController {
    @FXML public Button btnInicio;
    @FXML public Button btnCompra;
    @FXML public Button btnRenta;
    @FXML private FlowPane flowpaneRenta;

    private final AppStore store = AppStore.getInstance();
    private final NumberFormat money = NumberFormat.getCurrencyInstance();

    @FXML
    private void initialize() {
        render();
        store.getPublicacionesRenta().addListener((ListChangeListener<Publicacion>) c -> render());
    }

    private void render() {
        flowpaneRenta.getChildren().setAll(
                store.getPublicacionesRenta().stream()
                        .map(this::crearTarjetaRenta)
                        .toList()
        );
    }

    @FXML
    private void OnInicioAction(javafx.event.ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnCompra.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("inicio");
    }

    @FXML
    private void OnCompraAction(javafx.event.ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Venta.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnCompra.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Compra");
    }

    @FXML
    private void OnRentaAction(javafx.event.ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Renta.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnRenta.getScene().getWindow(); // obtener ventana actual
        stage.setScene(scene);
        stage.setTitle("Renta");
    }
    private Node crearTarjetaRenta(Publicacion p) {
        try {
            var url = getClass().getResource("/org/example/almasenesmorelos1/TargetaRenta.fxml"); // usa tu nombre exacto
            if (url == null) throw new IllegalStateException("Falta TargetaRenta.fxml en resources/org/example/almasenesmorelos1/");
            FXMLLoader fx = new FXMLLoader(url);
            Node card = fx.load();

            TargetaRentaController ctrl = fx.getController(); // usa el nombre real de tu controller
            ctrl.setData(p, money);

            return card;
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback por si tu FXML aún no está listo
            var a = p.getAlmacen();
            VBox fallback = new VBox(6);
            fallback.setStyle("-fx-background-color: #FFF8F0; -fx-background-radius: 12; -fx-padding: 12;");
            fallback.getChildren().addAll(
                    new Label("RENTA • " + a.getNombre()),
                    new Label("Tamaño: " + a.getTamanoM2() + " m²"),
                    new Label("Ubicación: " + a.getUbicacion()),
                    new Label("Renta mensual: " + money.format(p.getPrecio()))
            );
            fallback.setPrefWidth(240);
            return fallback;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleLogoutIconAction(javafx.scene.input.MouseEvent mouseEvent) {

            try {
                // Cerrar la ventana actual
                Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                currentStage.close();

                // Cargar la ventana de inicio
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
                Parent root = loader.load();

                Stage inicioStage = new Stage();
                inicioStage.setScene(new Scene(root));
                inicioStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la página de inicio");
            }

    }

}

