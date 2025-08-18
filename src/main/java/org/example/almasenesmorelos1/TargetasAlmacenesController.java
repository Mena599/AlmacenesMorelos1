package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.model.AsignacionCliente;

public class TargetasAlmacenesController {

    @FXML private Label lblId;         // Ej: "ID: ALM-001"
    @FXML private Label lblTamano;     // Ej: "Tamaño: 60 m²"
    @FXML private Label lblUbicacion;  // Ej: "Ubicación: ... "
    @FXML private Label lblPrecio;     // Ej: "Precio: $1,000.00"
    @FXML private Button btnvr;

    private Almacen almacen;
    private final AppStore store = AppStore.getInstance();

    /** Llamar inmediatamente tras cargar el FXML. */
    public void setData(Almacen a) {
        this.almacen = a;
        lblId.setText("ID: " + a.getId());
        lblTamano.setText("Tamaño: " + a.getTamanoM2() + " m²");
        lblUbicacion.setText("Ubicación: " + a.getUbicacion());
        lblPrecio.setText("Precio: $" + formatMoney(a.getPrecio()));
    }

    public void ventarenta() {
        if (almacen == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No hay almacén asociado a esta tarjeta.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/almasenesmorelos1/RegistrarClientes.fxml")
            );
            Parent root = loader.load();

            RegistrarClientesController ctrl = loader.getController();
            ctrl.setAlmacen(almacen); // <- muy importante

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Asignar cliente a " + almacen.getId());
            modal.showAndWait();

            // (Opcional) refrescar tarjeta si cambió estado del almacén
            setData(almacen);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo abrir AsignarCliente.fxml");
        }
    }

    // Helpers
    private String formatM2(double m2) {
        return (m2 % 1 == 0) ? String.format("%.0f m²", m2) : String.format("%.2f m²", m2);
    }
    private String formatMoney(double v) {
        return String.format("%,.2f", v);
    }
    private String safe(String s) { return s == null ? "" : s; }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        var a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

}
