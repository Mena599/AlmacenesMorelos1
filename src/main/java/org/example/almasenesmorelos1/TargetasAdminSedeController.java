package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
public class TargetasAdminSedeController {
        @FXML private Label lblNombre;
        @FXML private Label lblCorreo;
        @FXML private Label lblTelefono;
        @FXML private Button btnEliminar;
        @FXML private AnchorPane rootPane; // <-- este es el nodo raíz de la tarjeta

        public void setLblNombre(String lblNombre) {
            this.lblNombre.setText(lblNombre);
        }

        public void setLblCorreo(String lblCorreo) {
            this.lblCorreo.setText(lblCorreo);
        }

        public void setLblTelefono(String lblTelefono) {
            this.lblTelefono.setText(lblTelefono);
        }

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
                    // Se elimina la tarjeta (AnchorPane) del FlowPane padre
                    Pane parent = (Pane) rootPane.getParent();
                    parent.getChildren().remove(rootPane);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
