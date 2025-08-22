package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.model.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RegistrarAlmacenesController {

    @FXML private TextField txtIdAlmacen;
    @FXML private ComboBox<String> comboTamano;   // Grande / Mediano / Pequeño
    @FXML private TextField txtUbicacion;

    // NUEVO: dos precios
    @FXML private TextField txtPrecioVenta;
    @FXML private TextField txtPrecioRenta;

    @FXML private Button btnRegistrar;

    private static int contadorId = 1;

    // Usa tu store actual (dejas AppStore si es el que tienes)
    private final AppStore store = AppStore.getInstance();
    // Si en tu proyecto usas DataStore en vez de AppStore, cambia la línea de arriba por:
    // private final DataStore store = DataStore.getInstance();

    @FXML
    public void initialize() {
        txtIdAlmacen.setText(generarIdAlmacen());

        if (comboTamano.getItems() == null || comboTamano.getItems().isEmpty()) {
            comboTamano.getItems().setAll("Grande", "Mediano", "Pequeño");
        }
        comboTamano.getSelectionModel().selectFirst();
    }

    private String generarIdAlmacen() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String fechaRegistro = dateFormat.format(now);
        Random random = new Random();
        int randomNumbers = random.nextInt(10000);
        return String.format("ALM%d-%s-%04d", contadorId, fechaRegistro, randomNumbers);
    }

    @FXML
    private void OnRegistarAction() {
        if (comboTamano == null || comboTamano.getValue() == null) {
            warn("Selecciona un tamaño.");
            return;
        }
        if (txtUbicacion == null || txtUbicacion.getText().isBlank()) {
            warn("Ingresa la ubicación.");
            return;
        }
        if (txtPrecioVenta == null || txtPrecioVenta.getText().isBlank()) {
            warn("Ingresa el precio de VENTA (ej. 1000).");
            return;
        }
        if (txtPrecioRenta == null || txtPrecioRenta.getText().isBlank()) {
            warn("Ingresa el precio de RENTA (ej. 800).");
            return;
        }

        double precioVenta;
        double precioRenta;
        try {
            precioVenta = Double.parseDouble(txtPrecioVenta.getText().trim());
            precioRenta = Double.parseDouble(txtPrecioRenta.getText().trim());
            if (precioVenta <= 0 || precioRenta <= 0) {
                warn("Los precios deben ser mayores que cero.");
                return;
            }
        } catch (NumberFormatException ex) {
            warn("Los precios deben ser numéricos (ej. 1000 o 1000.50).");
            return;
        }

        String id = txtIdAlmacen.getText().trim();
        String tam = comboTamano.getValue();
        String ubicacion = txtUbicacion.getText().trim();

        double m2 = mapTamanoToM2(tam);

        String sedeId = SessionManager.get().getSedeId();
        if (sedeId == null || sedeId.isBlank()) {
            warn("No se pudo obtener la sede del administrador. Vuelve a iniciar sesión.");
            return;
        }

        // --- CREA EL MODELO con DOS PRECIOS ---
        Almacen almacen = new Almacen(id, m2, ubicacion, sedeId, precioVenta, precioRenta);

        // --- Guarda en Store y cierra ---
        store.addAlmacen(almacen);

        info("¡Almacén registrado correctamente!");
        contadorId++;
        cerrarVentana();
    }

    private double mapTamanoToM2(String t) {
        if (t == null) return 0.0;
        switch (t.toLowerCase()) {
            case "grande":  return 100.0;
            case "mediano": return 60.0;
            case "pequeño":
            case "pequeno": return 30.0;
            default:        return 0.0;
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        if (stage != null) stage.close();
    }

    private void warn(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }

    private void info(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}
