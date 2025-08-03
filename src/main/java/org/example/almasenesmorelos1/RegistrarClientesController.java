package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrarClientesController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtID;

    @FXML
    private Button btnRegistrar;

    // Esta referencia se pasará desde ClientesController al abrir la ventana emergente
    private ClientesController clientesController;

    public void setClientesController(ClientesController controller) {
        this.clientesController = controller;
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        try {
            // 1. Obtener los datos del formulario
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String id = txtID.getText();

            // 2. Cargar la tarjeta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Targeta.fxml"));
            AnchorPane tarjeta = loader.load();

            // 3. Obtener el controlador y asignar datos
            TargetasCliente controller = loader.getController();
            controller.setNombre(nombre);
            controller.setCorreo(correo);
            controller.setId(id);

            // 4. Agregar tarjeta al FlowPane en la vista de Clientes
            if (clientesController != null) {
                clientesController.getFlowPane().getChildren().add(tarjeta);
            }

            // 5. Cerrar esta ventana (la del formulario)
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
