package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TargetasClienteController {

    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblID;

    public void setLblNombre(String lblNombre) {
        this.lblNombre.setText(lblNombre);
    }
    public void setLblCorreo(String lblCorreo) {
        this.lblCorreo.setText(lblCorreo);
    }
    public void setLblID(String lblID) {
        this.lblID.setText(lblID);
    }

}
