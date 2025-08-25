package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.data.DataStore;
public class TargetasAlmacenesController {

    @FXML private Label lblId;
    @FXML private Label lblTamano;
    @FXML private Label lblUbicacion;

    @FXML private Label lblPrecioVenta;
    @FXML private Label lblPrecioRenta;

    @FXML private Button btnvr;
    @FXML private Button btnDesasignar; // <-- agregado en el FXML

    private Almacen almacen;

    // Inventario (AppStore) y asignaciones (DataStore)
    private final AppStore appStore = AppStore.getInstance();
    private final DataStore dataStore = DataStore.getInstance();

    /** Llamar inmediatamente tras cargar el FXML. */
    public void setData(Almacen a) {
        this.almacen = a;
        lblId.setText("ID: " + a.getId());
        lblTamano.setText("Tamaño: " + formatM2(a.getTamanoM2()));
        lblUbicacion.setText("Ubicación: " + a.getUbicacion());

        lblPrecioVenta.setText("$" + formatMoney(a.getPrecioVenta()));
        lblPrecioRenta.setText("$" + formatMoney(a.getPrecioRenta()));

        // Habilita/deshabilita el botón Desasignar según existan asignaciones
        actualizarEstadoBotonDesasignar();
    }

    /** Abre el modal para asignar cliente (Venta/Renta). */
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
            ctrl.setAlmacen(almacen);

            Stage modal = new Stage();
            modal.setScene(new Scene(root));
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Asignar cliente a " + almacen.getId());
            modal.showAndWait();

            // Tras cerrar, re-evaluamos si ahora hay asignación
            actualizarEstadoBotonDesasignar();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo abrir RegistrarClientes.fxml");
        }
    }

    /** Handler del botón "Desasignar" (confirmación y eliminación). */
    @FXML
    private void desasignarCliente() {
        if (almacen == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No hay almacén asociado a esta tarjeta.");
            return;
        }

        if (!existeAsignacionParaEsteAlmacen()) {
            showAlert(Alert.AlertType.INFORMATION, "Sin asignación",
                    "Este almacén no tiene clientes asignados.");
            actualizarEstadoBotonDesasignar();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Desasignar cliente");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Deseas desasignar al cliente de este almacén?");
        ButtonType si = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(si, no);

        var res = confirm.showAndWait();
        if (res.isPresent() && res.get() == si) {
            // Elimina todas las asignaciones que correspondan a este almacén
            String idAlmacen = almacen.getId();
            boolean eliminado = dataStore.getAsignaciones().removeIf(
                    a -> idAlmacen != null && idAlmacen.equalsIgnoreCase(a.getIdAlmacen())
            );

            if (eliminado) {
                showAlert(Alert.AlertType.INFORMATION, "Listo",
                        "Se desasignó el cliente del almacén.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Sin cambios",
                        "No se encontró una asignación activa para este almacén.");
            }

            // Refresca estado del botón
            actualizarEstadoBotonDesasignar();
        }
    }

    /** Habilita o deshabilita el botón según existan asignaciones para este almacén. */
    private void actualizarEstadoBotonDesasignar() {
        if (btnDesasignar == null || almacen == null) return;
        btnDesasignar.setDisable(!existeAsignacionParaEsteAlmacen());
    }

    /** ¿Hay alguna asignación en DataStore ligada a este almacén? */
    private boolean existeAsignacionParaEsteAlmacen() {
        if (almacen == null) return false;
        String id = almacen.getId();
        return dataStore.getAsignaciones().stream()
                .anyMatch(a -> id != null && id.equalsIgnoreCase(a.getIdAlmacen()));
    }

    // Helpers
    private String formatM2(double m2) {
        return (m2 % 1 == 0) ? String.format("%.0f m²", m2) : String.format("%.2f m²", m2);
    }
    private String formatMoney(double v) { return String.format("%,.2f", v); }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        var a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
