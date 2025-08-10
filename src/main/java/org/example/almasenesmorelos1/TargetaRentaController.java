package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.NumberFormat;

public class TargetaRentaController {

    @FXML private Label lblTamano;
    @FXML private Label lblUbicacion;
    @FXML private Label lblPrecio;

    private Publicacion publicacion;

    public void setData(Publicacion p, NumberFormat money) {
        this.publicacion = p;
        var a = p.getAlmacen();
        lblTamano.setText("Tamaño: " + a.getTamanoM2() + " m²");
        lblUbicacion.setText("Ubicación: " + a.getUbicacion());
        lblPrecio.setText("Renta mensual: " + money.format(p.getPrecio()));
    }

    @FXML
    private void onRentar() {
        // Aquí tu flujo de renta (confirmación/modal/etc.)
        System.out.println("Rentar clic en publicación: " + (publicacion != null ? publicacion.getId() : "null"));
    }
}
