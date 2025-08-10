package org.example.almasenesmorelos1;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.NumberFormat;

public class VentaController {
    @FXML public Button btnInicio;
    @FXML public Button btnCompra;
    @FXML public Button btnRenta;
    @FXML public Button btnRegresar;
    @FXML public FlowPane flowpaneventa;

    private final AppStore store = AppStore.getInstance();
    private final NumberFormat money = NumberFormat.getCurrencyInstance();

    @FXML
    private void initialize() {
        render(); // pinta lo que ya haya
        // Cuando se agregue una publicación de VENTA, re-render
        store.getPublicacionesVenta().addListener((ListChangeListener<Publicacion>) c -> render());
    }

    private void render() {
        flowpaneventa.getChildren().setAll(
                store.getPublicacionesVenta().stream()
                        .map(this::crearTarjetaVenta)
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



    private Node crearTarjetaVenta(Publicacion p) {
        try {
            var url = getClass().getResource("/org/example/almasenesmorelos1/TarjetaVenta.fxml");
            if (url == null) throw new IllegalStateException("Falta TarjetasVenta.fxml en resources/org/example/almasenesmorelos1/");
            FXMLLoader fx = new FXMLLoader(url);
            Node card = fx.load();

            TarjetaVentaController ctrl = fx.getController();
            ctrl.setData(p, money); // ← pasa los datos a la tarjeta

            return card;
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback simple si algo falla cargando la tarjeta
            var a = p.getAlmacen();
            VBox fallback = new VBox(6);
            fallback.setStyle("-fx-background-color: #F7F9FF; -fx-background-radius: 12; -fx-padding: 12;");
            fallback.getChildren().addAll(
                    new Label("VENTA • " + a.getNombre()),
                    new Label("Tamaño: " + a.getTamanoM2() + " m²"),
                    new Label("Ubicación: " + a.getUbicacion()),
                    new Label("Precio: " + money.format(p.getPrecio()))
            );
            fallback.setPrefWidth(240);
            return fallback;
        }
    }
}
