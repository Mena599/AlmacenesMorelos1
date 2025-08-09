package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TargetasAlmacenesController {

    @FXML private ImageView imgCliente; // si tu FXML usa otro id, cámbialo

    @FXML private Label lblTamano;      // "Tamaño: ... m²"
    @FXML private Label lblSede;      // "Sede: ..."
    @FXML private Label lblId;    // "Precio: ..."
    @FXML private Button btnEliminar;

    private Almacen almacen;
    private final AppStore store = AppStore.getInstance();

    /** Llamar inmediatamente tras cargar el FXML. */
    public void setData(Almacen a, String precioTexto) {
        this.almacen = a;
        lblTamano.setText("Tamaño: " + a.getTamanoM2() + " m²");
        lblSede.setText("Sede: " + a.getSedeId());
        lblId.setText("Precio: " + (precioTexto == null ? "—" : precioTexto));
    }

    @FXML
    private void eliminarCliente() { // si tu FXML usa otro nombre en onAction, sincroniza
        if (almacen != null) {
            store.removeAlmacen(almacen); // se elimina de Inventario y publicaciones
        }
    }
}
