package org.example.almasenesmorelos1;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.collections.transformation.FilteredList; // NUEVO
import org.example.almasenesmorelos1.model.SessionManager; // NUEVO

import javax.swing.text.html.ImageView;
import java.io.IOException;

/**
 * Controlador de la vista "Almacenes".
 * - Abre el formulario de registro en un modal.
 * - Se suscribe al AppStore para renderizar las tarjetas del inventario.
 * - Ya NO recibe tarjetas desde el formulario; todo es reactivo.
 */
public class AlmacenesController {

    @FXML
    public Button btnAgregar;
    @FXML
    public Button btnir;
    @FXML
    public ImageView imgLongOut;
    @FXML
    private FlowPane TargetasFlow; // FlowPane donde van las tarjetas del inventario
    // NUEVO
    private FilteredList<Almacen> filtrados;

    // Store central (listas observables)
    private final AppStore store = AppStore.getInstance();

    // (Opcional) Getter si otros módulos lo siguen usando, pero ya no debería ser necesario.
    public FlowPane getTargetasFlow() {
        return TargetasFlow;
    }

    @FXML
    private void initialize() {
        // 1) Render inicial con lo que ya haya en el store
        TargetasFlow.getChildren().clear();
        store.getInventario().forEach(a -> TargetasFlow.getChildren().add(createAlmacenCard(a)));

        // 2) Escuchar cambios en el inventario (reactivo)
        store.getInventario().addListener((ListChangeListener<Almacen>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Almacen a : change.getAddedSubList()) {
                        TargetasFlow.getChildren().add(createAlmacenCard(a));
                    }
                }
                if (change.wasRemoved()) {
                    // Estrategia simple: re-render completo para mantener consistencia
                    TargetasFlow.getChildren().setAll(
                            store.getInventario().stream().map(this::createAlmacenCard).toList()
                    );
                }
            }
        });

        // 3) Botón para abrir el formulario (modal)
        btnAgregar.setOnAction(this::abrirFormularioAlmacen);
        // ==========================
// FILTRO POR SEDE DEL ADMIN
// ==========================
        String sedeActual = SessionManager.get().getSedeId();

// Crea la lista filtrada desde el inventario del store
        filtrados = new FilteredList<>(store.getInventario(),
                a -> sedeActual != null
                        && a != null
                        && sedeActual.equalsIgnoreCase(safe(a.getSedeId())));

// Render inicial SOLO con los de mi sede
        TargetasFlow.getChildren().setAll(
                filtrados.stream().map(this::createAlmacenCard).toList()
        );

// Si el inventario cambia (altas/bajas), re-pintamos con el filtro
        store.getInventario().addListener((ListChangeListener<Almacen>) c -> {
            TargetasFlow.getChildren().setAll(
                    filtrados.stream().map(this::createAlmacenCard).toList()
            );
        });

    }
    // NUEVO
    private String safe(String s) { return s == null ? "" : s; }

    /**
     * Crea una tarjeta simple de Almacén (versión placeholder).
     * Si tienes un CardAlmacen.fxml propio, dime y lo cargamos con FXMLLoader aquí.
     */
    private Node createAlmacenCard(Almacen a) {
        try {
            var url = getClass().getResource("/org/example/almasenesmorelos1/TarjetasAlmacen.fxml");
            if (url == null) throw new IllegalStateException("No se encontró TarjetasAlmacen.fxml");

            FXMLLoader loader = new FXMLLoader(url);
            Node card = loader.load();

            TargetasAlmacenesController ctrl = loader.getController();
            ctrl.setData(a); // <- pásale un texto fijo por ahora

            return card;
        } catch (Exception e) {
            e.printStackTrace();
            return new Label("Error cargando tarjeta");
        }
    }

    /**
     * Abre el formulario de registro como ventana modal.
     * Nota: ya NO pasamos referencia de este controlador al formulario.
     * El formulario, al guardar, llamará al AppStore y eso disparará la actualización aquí.
     */
    @FXML
    private void abrirFormularioAlmacen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/almasenesmorelos1/RegistrarAlmacenes.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar Almacén");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir el formulario. Revisa la ruta del FXML.").showAndWait();
        }
    }
    @FXML
    private void handleLogoutIconAction(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/org/example/almasenesmorelos1/InicioAdminSede.fxml")
            );

            // Obtener la ventana desde el nodo que disparó el evento (100% seguro)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de InicioAdminSede.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }


    /**
     * Navegación a Venta (revisa el título que pones).
     * En tu código cargabas Venta.fxml pero ponías "Renta" en el título.
     */

}
