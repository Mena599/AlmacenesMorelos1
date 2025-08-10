package org.example.almasenesmorelos1;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.NumberFormat;

public class RentaController {

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

    }

    @FXML
    private void OnCompraAction(javafx.event.ActionEvent event) throws java.io.IOException {

    }

    @FXML
    private void OnRentaAction(javafx.event.ActionEvent event) throws java.io.IOException {

    }

    @FXML
    private void onRegresarClick(javafx.event.ActionEvent event) throws java.io.IOException {

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
}
