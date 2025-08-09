package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.math.BigDecimal;

/**
 * Controller del formulario "Registrar Almacenes".
 * - Valida campos
 * - Construye el modelo Almacen
 * - Llama al AppStore para agregarlo al inventario y (opcional) publicar en Venta/Renta
 * - Limpia el formulario y cierra la ventana si corresponde
 *
 * Nota: Ya NO agrega tarjetas manualmente al FlowPane.
 *       Las vistas (Almacenes/Venta/Renta) se actualizarán solas al escuchar el Store.
 */
public class RegistrarAlmacenesController {

    // --- UI: Menú Venta/Renta ---
    @FXML private MenuButton mbTipo;
    @FXML private MenuItem miVenta, miRenta;
    // Si quieres permitir “solo inventario”, agrega otro MenuItem (Ninguna) en el FXML.

    // --- UI: Menú Tamaño ---
    @FXML private MenuButton mbTamano;
    @FXML private MenuItem miGrande, miMediano, miPequeno;

    // --- UI: Menú Sede ---
    @FXML private MenuButton mbSede;
    @FXML private MenuItem miSede1, miSede2, miSede3;

    // --- UI: Campos de texto ---
    @FXML private TextField txtIdAlmacen;   // se autogenera
    @FXML private TextField txtPrecio;      // requerido si es Venta/Renta
    @FXML private TextField txtUbicacion;   // requerido

    @FXML private Button btnRegistrar;

    // --- Estado interno (selecciones) ---
    private String tipoSeleccionado = null;   // "Venta" | "Renta" | (opcional) "Ninguna"
    private String tamanoSeleccionado = null; // "Grande" | "Mediano" | "Pequeño"
    private String sedeSeleccionada = null;   // "Sede 1" | "Sede 2" | "Sede 3"

    // Contador simple para ID local (puedes moverlo a Store/BD luego)
    private static int contadorId = 1;

    // --- Store central (reactivo) ---
    private final AppStore store = AppStore.getInstance();

    @FXML
    public void initialize() {
        // 1) ID inicial
        txtIdAlmacen.setText(generarIdAlmacen());

        // 2) Opciones de tipo (Venta/Renta)
        miVenta.setOnAction(e -> {
            mbTipo.setText("Venta");
            tipoSeleccionado = "Venta";
        });
        miRenta.setOnAction(e -> {
            mbTipo.setText("Renta");
            tipoSeleccionado = "Renta";
        });
        // Si agregas “Ninguna” en el FXML:
        // miNinguna.setOnAction(e -> { mbTipo.setText("Solo inventario"); tipoSeleccionado = "Ninguna"; });

        // 3) Tamaño
        miGrande.setOnAction(e -> { mbTamano.setText("Grande"); tamanoSeleccionado = "Grande"; });
        miMediano.setOnAction(e -> { mbTamano.setText("Mediano"); tamanoSeleccionado = "Mediano"; });
        miPequeno.setOnAction(e -> { mbTamano.setText("Pequeño"); tamanoSeleccionado = "Pequeño"; });

        // 4) Sede
        miSede1.setOnAction(e -> { mbSede.setText("Sede 1"); sedeSeleccionada = "Sede 1"; });
        miSede2.setOnAction(e -> { mbSede.setText("Sede 2"); sedeSeleccionada = "Sede 2"; });
        miSede3.setOnAction(e -> { mbSede.setText("Sede 3"); sedeSeleccionada = "Sede 3"; });
    }

    // Genera ID tipo ALM-001
    private String generarIdAlmacen() {
        return String.format("ALM-%03d", contadorId);
    }

    /**
     * Handler único conectado desde el FXML (onAction="#OnRegistrar")
     * Valida → Crea modelos → Llama store.addAlmacen() → Limpia/Cierra
     */
    @FXML
    private void OnRegistrar(ActionEvent event) {
        // 1) Validación de campos UI
        if (!validarCampos()) return;

        // 2) Parse/Map de entradas
        String id = txtIdAlmacen.getText();
        String ubicacion = txtUbicacion.getText().trim();
        double tamanoM2 = mapTamanoCategoriaToM2(tamanoSeleccionado); // Ajusta los m² a tu criterio
        String sedeId = mapSedeTextoToId(sedeSeleccionada);          // Mapea "Sede 1" → "SEDE1", etc.

        // 3) Construir Almacén (modelo de inventario)
        //    Nota: nuestra clase Almacen genera su propio UUID interno, el 'id' visual es solo para UI
        Almacen almacen = new Almacen(id /*nombre o alias*/, tamanoM2, ubicacion, sedeId);

        // 4) Tipo de publicación y precio (si aplica)
        TipoPublicacion tipo = mapTipoTextoToEnum(tipoSeleccionado);

        BigDecimal precio = null;
        if (tipo == TipoPublicacion.VENTA || tipo == TipoPublicacion.RENTA) {
            try {
                double p = Double.parseDouble(txtPrecio.getText());
                if (p <= 0) {
                    mostrarInfo("El precio debe ser mayor que cero.");
                    return;
                }
                precio = BigDecimal.valueOf(p);
            } catch (NumberFormatException ex) {
                mostrarInfo("El precio debe ser un número válido (ej. 5000.00).");
                return;
            }
        }

        // 5) Persistir en Store (¡dispara la actualización de vistas!)
        store.addAlmacen(almacen, tipo, precio);

        // 6) Feedback + limpiar + cerrar (si este formulario es modal)
        mostrarInfo("¡Almacén registrado correctamente!");
        limpiarFormulario();
        // Si este formulario es una ventana emergente, puedes cerrarla:
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        if (stage != null) stage.close();
    }

    // --- Helpers de validación y mapeos ---

    private boolean validarCampos() {
        if (tipoSeleccionado == null) {
            mostrarInfo("Selecciona si es Venta o Renta.");
            return false;
        }
        if (tamanoSeleccionado == null) {
            mostrarInfo("Selecciona el tamaño del almacén.");
            return false;
        }
        if (sedeSeleccionada == null) {
            mostrarInfo("Selecciona la sede.");
            return false;
        }
        if (txtUbicacion.getText().isBlank()) {
            mostrarInfo("Ingresa la ubicación del almacén.");
            return false;
        }
        if ((tipoSeleccionado.equals("Venta") || tipoSeleccionado.equals("Renta"))
                && txtPrecio.getText().isBlank()) {
            mostrarInfo("Ingresa el precio (requerido para Venta/Renta).");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        contadorId++;
        txtIdAlmacen.setText(generarIdAlmacen());
        mbTipo.setText("Selecciona opción");
        mbTamano.setText("Selecciona tamaño");
        mbSede.setText("Selecciona sede");
        txtPrecio.clear();
        txtUbicacion.clear();
        tipoSeleccionado = null;
        tamanoSeleccionado = null;
        sedeSeleccionada = null;
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private TipoPublicacion mapTipoTextoToEnum(String tipoTexto) {
        if ("Venta".equalsIgnoreCase(tipoTexto)) return TipoPublicacion.VENTA;
        if ("Renta".equalsIgnoreCase(tipoTexto)) return TipoPublicacion.RENTA;
        // Si agregas “solo inventario”:
        // if ("Ninguna".equalsIgnoreCase(tipoTexto)) return TipoPublicacion.NINGUNA;
        return TipoPublicacion.NINGUNA; // default seguro
    }

    /**
     * Mapea la categoría textual a un tamaño aproximado.
     * Ajusta estos valores a lo que maneje tu negocio.
     */
    private double mapTamanoCategoriaToM2(String cat) {
        if ("Grande".equalsIgnoreCase(cat))  return 100.0;
        if ("Mediano".equalsIgnoreCase(cat)) return 60.0;
        if ("Pequeño".equalsIgnoreCase(cat)) return 30.0;
        return 0.0;
    }

    /**
     * Mapea el texto visible de sede a un ID lógico interno.
     * Ajusta si tienes IDs reales en BD.
     */
    private String mapSedeTextoToId(String sedeTexto) {
        if ("Sede 1".equalsIgnoreCase(sedeTexto)) return "SEDE1";
        if ("Sede 2".equalsIgnoreCase(sedeTexto)) return "SEDE2";
        if ("Sede 3".equalsIgnoreCase(sedeTexto)) return "SEDE3";
        return "SEDE_DESCONOCIDA";
    }
}


