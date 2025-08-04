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

    @FXML
    public void eliminarCliente(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarCliente.fxml"));
            Parent root = loader.load();

            EliminarclienteController controller = loader.getController();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Confirmar eliminación");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            if (controller.isConfirmado()) {
                // Lógica para eliminar la tarjeta o el usuario
                ;  // Asegúrate de que este método exista
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
