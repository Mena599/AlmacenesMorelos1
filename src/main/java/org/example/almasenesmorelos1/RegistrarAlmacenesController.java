package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

        // (Opcional) Selección por defecto
        if (comboTamano.getItems() != null && !comboTamano.getItems().isEmpty()) {
            comboTamano.getSelectionModel().selectFirst();
        }
    }

    private String generarIdAlmacen() {
        return String.format("ALM-%03d", contadorId);
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

        String id = txtIdAlmacen.getText();
        String tam = comboTamano.getValue();
        String ubicacion = txtUbicacion.getText().trim();

        double m2 = mapTamanoToM2(tam); // Grande/Mediano/Pequeño → m²

        // --- CREA EL MODELO ---
        // ⬇️ Elige una de estas dos opciones según tu clase Almacen:



        // Opción B: si tu Almacen es (id, tamanoM2, ubicacion, precio)
        Almacen almacen = new Almacen(id, m2, ubicacion, precio);

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
