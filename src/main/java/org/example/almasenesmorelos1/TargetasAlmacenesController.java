package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class TargetasAlmacenesController {

    @FXML private Label lblTamaño;
    @FXML private Label lblCede;
    @FXML private Label lblTelefono;
    @FXML private Button btnEliminar;
    public void setLblTamaño(String lblTamaño) {
        this.lblTamaño.setText(lblTamaño);
    }
    public void setLblCede(String lblCede) {
        this.lblCede.setText(lblCede);
    }
    public void setLblTelefono(String lblTelefono) {
        this.lblTelefono.setText(lblTelefono);
    }
    public void setBtnEliminar(Button btnEliminar) { this .btnEliminar = btnEliminar; }

    @FXML
    public void eliminarAlmacen(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarAlmacen.fxml"));
            Parent root = loader.load();


            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Confirmar eliminación");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();


    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
