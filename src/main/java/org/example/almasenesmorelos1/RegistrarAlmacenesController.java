package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrarAlmacenesController {

    @FXML
    private TextField txtTamaño;

    @FXML
    private TextField txtCede;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnRegistrar;

    // Esta referencia se pasará desde AlmacenesController al abrir la ventana emergente
    private ClientesController AlmacenesController;

    public void setClientesController(ClientesController controller) {
        this.AlmacenesController = controller;
    }

    @FXML
    private void OnRegistarAction(ActionEvent event) {
        try {
            // 1. Obtener los datos del formulario
            String nombre = txtTamaño.getText();
            String correo = txtCede.getText();
            String telefono = txtTelefono.getText();

            // 2. Cargar la tarjeta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TarjetasAlmacees.fxml"));
            AnchorPane tarjeta = loader.load();

            // 3. Obtener el controlador y asignar datos
            TargetasAlmacenesController controller = loader.getController();
            controller.setLblTamaño(nombre);
            controller.setLblCede(correo);
            controller.setLblTelefono(telefono);

            // 4. Agregar tarjeta al FlowPane en la vista de Clientes
            if (AlmacenesController != null) {
                AlmacenesController.getTargetasFlow().getChildren().add(tarjeta);
            }

            // 5. Cerrar esta ventana (la del formulario)
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.close();

            System.out.println("ClientesController: " + AlmacenesController);
            System.out.println("FlowPane: " + (AlmacenesController != null ? AlmacenesController.getTargetasFlow() : "null"));
            System.out.println("Nombre capturado: " + nombre);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
