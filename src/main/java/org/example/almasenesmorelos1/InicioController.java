package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;

public class InicioController {
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnCompra;
    @FXML
    private Button btnRenta;

    @FXML
    private Button btnMirar;
    @FXML
    private Button btnMirar2;
     @FXML
     private Button btnRegresar;
    @FXML
    private void onInicioAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Compra.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnCompra.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Compra");

    }
    @FXML
    private void OnCompraAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Compra.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnCompra.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Compra");
    }
    @FXML
    private void OnRentaAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Renta.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnRenta.getScene().getWindow(); // obtener ventana actual
        stage.setScene(scene);
        stage.setTitle("Renta");
    }

    //Estos son de los bloques de compra y renta

    @FXML
    private void OnMirarAction() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Venta.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnMirar.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Venta");
    }

    @FXML
    private void OnMirar2Action()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Renta.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnMirar2.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Renta");
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void handleLogoutIconAction(javafx.scene.input.MouseEvent mouseEvent) {

        try {
            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();

            // Cargar la ventana de inicio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
            Parent root = loader.load();

            Stage inicioStage = new Stage();
            inicioStage.setScene(new Scene(root));
            inicioStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la página de inicio");
        }

    }

}
