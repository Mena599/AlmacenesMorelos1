package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class TargetasAlmacenesController {

    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblTelefono;

    // Setters para mostrar los datos en la tarjeta
    public void setLblNombre(String nombre) {
        lblNombre.setText(nombre);
    }

    public void setLblCorreo(String correo) {
        lblCorreo.setText(correo);
    }

    public void setLblTelefono(String telefono) {
        lblTelefono.setText(telefono);
    }

    // Botón para eliminar la tarjeta de su contenedor
    @FXML
    private void eliminarCliente() {
        AnchorPane tarjeta = (AnchorPane) lblNombre.getParent();
        FlowPane flowPane = (FlowPane) tarjeta.getParent();
        flowPane.getChildren().remove(tarjeta);
    }
}
