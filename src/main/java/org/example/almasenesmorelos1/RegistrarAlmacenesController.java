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
    @FXML private TextField txtPrecio;
    @FXML private Button btnRegistrar;

    private static int contadorId = 1;
    private final AppStore store = AppStore.getInstance();

    @FXML
    public void initialize() {
        // ID autogenerado
        txtIdAlmacen.setText(generarIdAlmacen());

        // Si el Combo viene vacío desde el FXML, lo llenamos aquí
        if (comboTamano.getItems() == null || comboTamano.getItems().isEmpty()) {
            comboTamano.getItems().setAll("Grande", "Mediano", "Pequeño");
        }
        // Selección por defecto
        comboTamano.getSelectionModel().selectFirst();
    }

    private String generarIdAlmacen() {
        // Obtener la fecha actual
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String fechaRegistro = dateFormat.format(now); // Formato ddMMyy

        // Generar 4 números aleatorios
        Random random = new Random();
        int randomNumbers = random.nextInt(10000); // Genera un número entre 0 y 9999

        // Formatear el ID del almacén
        return String.format("ALM%d-%s-%04d", contadorId, fechaRegistro, randomNumbers);
    }

    @FXML
    private void OnRegistarAction() {
        // --- Validaciones básicas ---
        if (comboTamano == null || comboTamano.getValue() == null) {
            warn("Selecciona un tamaño.");
            return;
        }
        if (txtUbicacion == null || txtUbicacion.getText().isBlank()) {
            warn("Ingresa la ubicación.");
            return;
        }
        if (txtPrecio == null || txtPrecio.getText().isBlank()) {
            warn("Ingresa el precio (ej. 1000).");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio <= 0) {
                warn("El precio debe ser mayor que cero.");
                return;
            }
        } catch (NumberFormatException ex) {
            warn("El precio debe ser numérico (ej. 1000 o 1000.50).");
            return;
        }

        String id = txtIdAlmacen.getText().trim();
        String tam = comboTamano.getValue();
        String ubicacion = txtUbicacion.getText().trim();

        double m2 = mapTamanoToM2(tam); // Grande/Mediano/Pequeño → m²

        // ⚠️ Sede del admin actual (sesión en memoria)
        String sedeId = SessionManager.get().getSedeId();
        if (sedeId == null || sedeId.isBlank()) {
            warn("No se pudo obtener la sede del administrador. Vuelve a iniciar sesión.");
            return;
        }

        // --- CREA EL MODELO ---
        Almacen almacen = new Almacen(id, m2, ubicacion, sedeId, precio);

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
