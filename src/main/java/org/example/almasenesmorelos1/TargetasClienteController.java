package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TargetasClienteController {

    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblTelefono;
    @FXML private Button btnEliminar;
    public void setLblNombre(String lblNombre) {
        this.lblNombre.setText(lblNombre);
    }
    public void setLblCorreo(String lblCorreo) {
        this.lblCorreo.setText(lblCorreo);
    }
    public void setLblTelefono(String lblTelefono) {
        this.lblTelefono.setText(lblTelefono);
    }
    public void setBtnEliminar(Button btnEliminar) { this .btnEliminar = btnEliminar; }

    public void  OnEliminarAction(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TargetasCliente.fxml"));
    }
}
