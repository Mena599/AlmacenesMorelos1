package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.NumberFormat;

public class TarjetaVentaController {

    @FXML private Label lblTamano;
    @FXML private Label lblUbicacion;
    @FXML private Label lblPrecio;

    private Publicacion publicacion;

    /** Llamado por VentaController después de cargar el FXML. */
    public void setData(Publicacion p, NumberFormat money) {
        this.publicacion = p;
        Almacen a = p.getAlmacen();

        // Si "nombre" que muestras al cliente es el ID visual, puedes ponerlo en otro label/título.
        lblTamano.setText(a.getTamanoM2() + " m²");
        lblUbicacion.setText(a.getUbicacion());
        lblPrecio.setText(money.format(p.getPrecio()));
    }

    @FXML
    private void onComprar() {
        // Aquí la lógica de compra (abrir modal, confirmar, etc.)
        System.out.println("Comprar clic en publicación: " + (publicacion != null ? publicacion.getId() : "null"));
    }
}
