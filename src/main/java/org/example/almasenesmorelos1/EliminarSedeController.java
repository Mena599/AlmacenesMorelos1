package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EliminarSedeController {

    private boolean confirmado = false;

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private Button btnsi;

    @FXML
    private Button btnno;

    @FXML
    private void onSiAction(ActionEvent event) {
        confirmado = true;
        cerrarVentana(event);
    }

    @FXML
    private void onNoAction(ActionEvent event) {
        confirmado = false;
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
