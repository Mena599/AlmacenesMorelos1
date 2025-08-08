package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;

public class RegistrarAlmacenesController {

    @FXML private MenuButton mbTipo;
    @FXML private MenuItem miVenta, miRenta;

    @FXML private MenuButton mbTamano;
    @FXML private MenuItem miGrande, miMediano, miPequeno;

    @FXML private MenuButton mbSede;
    @FXML private MenuItem miSede1, miSede2, miSede3;

    @FXML private TextField txtIdAlmacen;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtUbicacion;

    @FXML private Button btnRegistrar;

    // Variables para capturar la selección
    private String tipoSeleccionado = null;
    private String tamanoSeleccionado = null;
    private String sedeSeleccionada = null;

    // Para generar el ID automático
    private static int contadorId = 1;

    @FXML
    public void initialize() {
        // Inicializa el ID automáticamente
        txtIdAlmacen.setText(generarIdAlmacen());

        // ----- MenuButton Venta/Renta -----
        miVenta.setOnAction(event -> {
            mbTipo.setText("Venta");
            tipoSeleccionado = "Venta";
        });
        miRenta.setOnAction(event -> {
            mbTipo.setText("Renta");
            tipoSeleccionado = "Renta";
        });

        // ----- MenuButton Tamaño -----
        miGrande.setOnAction(event -> {
            mbTamano.setText("Grande");
            tamanoSeleccionado = "Grande";
        });
        miMediano.setOnAction(event -> {
            mbTamano.setText("Mediano");
            tamanoSeleccionado = "Mediano";
        });
        miPequeno.setOnAction(event -> {
            mbTamano.setText("Pequeño");
            tamanoSeleccionado = "Pequeño";
        });

        // ----- MenuButton Sede -----
        miSede1.setOnAction(event -> {
            mbSede.setText("Sede 1");
            sedeSeleccionada = "Sede 1";
        });
        miSede2.setOnAction(event -> {
            mbSede.setText("Sede 2");
            sedeSeleccionada = "Sede 2";
        });
        miSede3.setOnAction(event -> {
            mbSede.setText("Sede 3");
            sedeSeleccionada = "Sede 3";
        });
    }

    // Genera el ID del almacén (tipo ALM-001)
    private String generarIdAlmacen() {
        return String.format("ALM-%03d", contadorId);
    }

    @FXML
    private void onRegistrar(ActionEvent event) {
        // Validar todos los campos
        if (!validarCampos()) {
            return;
        }

        // Obtener datos
        String idAlmacen = txtIdAlmacen.getText();
        String precioStr = txtPrecio.getText();
        String ubicacion = txtUbicacion.getText();

        // Validar y formatear precio
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                mostrarAlerta("El precio debe ser mayor que cero.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("El precio debe ser un número válido.");
            return;
        }

        // Mostrar datos en consola (aquí puedes crear la lógica para TargetaAlmacen, etc.)
        System.out.println("Registro exitoso:");
        System.out.println("Tipo: " + tipoSeleccionado);
        System.out.println("Tamaño: " + tamanoSeleccionado);
        System.out.println("Sede: " + sedeSeleccionada);
        System.out.println("ID: " + idAlmacen);
        System.out.println("Precio: $" + precio + " MXN");
        System.out.println("Ubicación: " + ubicacion);

        // Aquí puedes crear y agregar la tarjeta correspondiente
        // Ejemplo:
        // if (tipoSeleccionado.equals("Venta")) {
        //     agregarTargetaVenta(...);
        // } else {
        //     agregarTargetaRenta(...);
        // }

        // Mensaje de éxito
        mostrarAlerta("¡Almacén registrado correctamente!");

        // Limpiar formulario y preparar siguiente ID
        limpiarFormulario();
    }

    private boolean validarCampos() {
        if (tipoSeleccionado == null) {
            mostrarAlerta("Selecciona si es Venta o Renta.");
            return false;
        }
        if (tamanoSeleccionado == null) {
            mostrarAlerta("Selecciona el tamaño del almacén.");
            return false;
        }
        if (sedeSeleccionada == null) {
            mostrarAlerta("Selecciona la sede.");
            return false;
        }
        if (txtPrecio.getText().isEmpty()) {
            mostrarAlerta("Ingresa el precio del almacén.");
            return false;
        }
        if (txtUbicacion.getText().isEmpty()) {
            mostrarAlerta("Ingresa la ubicación del almacén.");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        // Incrementa el ID para el siguiente registro
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

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
