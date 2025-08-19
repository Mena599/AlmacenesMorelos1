package org.example.almasenesmorelos1;

import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
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
// NUEVO
import org.example.almasenesmorelos1.model.SessionManager;
import javafx.collections.transformation.FilteredList; // NUEVO
import java.time.format.DateTimeFormatter;

public class ClientesController {

    @FXML private ImageView imgLongOu;

    @FXML private TableView<AsignacionCliente> tablaClientes;
    @FXML private TableColumn<AsignacionCliente, String> colIdAlmacen;
    @FXML private TableColumn<AsignacionCliente, String> colNombre;
    @FXML private TableColumn<AsignacionCliente, String> colCorreo;
    @FXML private TableColumn<AsignacionCliente, String> colTelefono;
    @FXML private TableColumn<AsignacionCliente, String> colEstatus;
    @FXML private TableColumn<AsignacionCliente, String> colFechaAdq;
    @FXML private TableColumn<AsignacionCliente, String> colFechaExp;

    // NUEVO: filtrado y utilidades
    private FilteredList<AsignacionCliente> filtradas;
    private final AppStore appStore = AppStore.getInstance();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        // --- Mapeo columnas base ---
        colIdAlmacen.setCellValueFactory(new PropertyValueFactory<>("idAlmacen"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        // Fechas: convertir a String seguro (por si son LocalDate o null)
        colFechaAdq.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(
                        cd.getValue().getFechaAdquisicion() == null
                                ? ""
                                : cd.getValue().getFechaAdquisicion().format(fmt)
                )
        );
        colFechaExp.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(
                        cd.getValue().getFechaExpiracion() == null
                                ? ""
                                : cd.getValue().getFechaExpiracion().format(fmt)
                )
        );

        // --- FILTRADO POR SEDE ---
        // Sede del admin logueado (SessionManager)
        String sedeActual = SessionManager.get().getSedeId();

        // Crea lista filtrada sobre las asignaciones del DataStore
        filtradas = new FilteredList<>(DataStore.getInstance().getAsignaciones(), a -> {
            // Encontrar el almacén para esa asignación
            Almacen alm = findAlmacenByIdSafe(a.getIdAlmacen());
            // Aceptar solo si el almacén existe y pertenece a mi sede
            return alm != null && sedeActual.equalsIgnoreCase(safe(alm.getSedeId()));
        });

        // Bind a la tabla
        tablaClientes.setItems(filtradas);

        // Si cambia el inventario (ej. crean/eliminan almacenes), re-evalúa el filtro
        appStore.getInventario().addListener((ListChangeListener<Almacen>) c -> {
            // Fuerza re-evaluación del predicado
            filtradas.setPredicate(filtradas.getPredicate());
        });

        // Si cambian las asignaciones (altas/bajas), TableView se actualiza solo
        // gracias a la FilteredList ya conectada a DataStore.getAsignaciones()
    }

    /** Busca el almacén por id con helper del AppStore si existe; si no, hace un fallback lineal. */
    private Almacen findAlmacenByIdSafe(String idAlmacen) {
        // Si tienes implementado AppStore.findAlmacenById(id), úsalo:
        try {
            // Descomenta esto si ya añadiste el helper en AppStore:
            // return appStore.findAlmacenById(idAlmacen);
        } catch (Throwable ignored) {}

        // Fallback: búsqueda lineal en el inventario
        if (idAlmacen == null) return null;
        for (Almacen a : appStore.getInventario()) {
            if (idAlmacen.equalsIgnoreCase(a.getId())) return a;
        }
        return null;
    }

    private String safe(String s) { return s == null ? "" : s; }

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
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de InicioAdminSede.");
        }
    }


}
