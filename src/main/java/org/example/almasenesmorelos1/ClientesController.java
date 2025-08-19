package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.AsignacionCliente;

import java.util.EventObject;

public class ClientesController {

    @FXML
    private ImageView imgLongOu;

    @FXML private TableView<AsignacionCliente> tablaClientes;
    @FXML private TableColumn<AsignacionCliente, String> colIdAlmacen;
    @FXML private TableColumn<AsignacionCliente, String> colNombre;
    @FXML private TableColumn<AsignacionCliente, String> colCorreo;
    @FXML private TableColumn<AsignacionCliente, String> colTelefono;
    @FXML private TableColumn<AsignacionCliente, String> colEstatus;
    @FXML private TableColumn<AsignacionCliente, String> colFechaAdq;
    @FXML private TableColumn<AsignacionCliente, String> colFechaExp;

    @FXML
    private void initialize() {
        // Mapeo columnas con propiedades del modelo
        colIdAlmacen.setCellValueFactory(new PropertyValueFactory<>("idAlmacen"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        colFechaAdq.setCellValueFactory(new PropertyValueFactory<>("fechaAdquisicion"));
        colFechaExp.setCellValueFactory(new PropertyValueFactory<>("fechaExpiracion"));

        // Conectar con los datos del DataStore
        tablaClientes.setItems(DataStore.getInstance().getAsignaciones());
    }
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public void handleLogoutIconAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/org/example/almasenesmorelos1/InicioAdminSede.fxml")
            );

            // Toma la ventana desde el nodo que disparó el evento
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de InicioAdminSede.");
        }

    }
}
