package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Controlador simplificado para el formulario de registro de clientes
 * Sin usar clase Cliente, todo directamente en el controller
 */
public class RegistrarClientesController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private Button btnRegistrar;

    /**
     * Método para manejar el evento del botón registrar
     * Valida y registra un nuevo cliente directamente sin clase Cliente
     */
    @FXML
    private void registrarCliente() {
        try {
            // Validar que todos los campos estén completos
            if (!validarCampos()) {
                return;
            }

            // Obtener datos directamente de los campos
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String fechaNacimiento = dpFechaNacimiento.getValue().toString();

            // Guardar cliente directamente
            boolean registroExitoso = guardarClienteDirecto(nombre, apellidos, correo, telefono, fechaNacimiento);

            if (registroExitoso) {
                mostrarMensaje("Éxito", "Cliente registrado correctamente");
                limpiarCampos();
            } else {
                mostrarMensaje("Error", "No se pudo registrar el cliente");
            }

        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al registrar: " + e.getMessage());
        }
    }

    /**
     * Valida que todos los campos del formulario estén completos y sean válidos
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        // Validar nombre
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("Validación", "Por favor ingrese el nombre");
            txtNombre.requestFocus();
            return false;
        }

        // Validar apellidos
        if (txtApellidos.getText() == null || txtApellidos.getText().trim().isEmpty()) {
            mostrarMensaje("Validación", "Por favor ingrese los apellidos");
            txtApellidos.requestFocus();
            return false;
        }

        // Validar correo electrónico
        String correo = txtCorreo.getText();
        if (correo == null || correo.trim().isEmpty()) {
            mostrarMensaje("Validación", "Por favor ingrese el correo electrónico");
            txtCorreo.requestFocus();
            return false;
        }
        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarMensaje("Validación", "Por favor ingrese un correo válido");
            txtCorreo.requestFocus();
            return false;
        }

        // Validar teléfono
        if (txtTelefono.getText() == null || txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("Validación", "Por favor ingrese el número de teléfono");
            txtTelefono.requestFocus();
            return false;
        }

        // Validar fecha de nacimiento
        if (dpFechaNacimiento.getValue() == null) {
            mostrarMensaje("Validación", "Por favor seleccione la fecha de nacimiento");
            dpFechaNacimiento.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Guarda los datos del cliente directamente
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param correo Correo electrónico del cliente
     * @param telefono Teléfono del cliente
     * @param fechaNacimiento Fecha de nacimiento como String
     * @return true si se guardó exitosamente
     */
    private boolean guardarClienteDirecto(String nombre, String apellidos, String correo, String telefono, String fechaNacimiento) {
        // Aquí puedes implementar la lógica para guardar en base de datos
        // Por ahora solo imprimimos los datos

        System.out.println("=== REGISTRANDO NUEVO CLIENTE ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Correo: " + correo);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Fecha Nacimiento: " + fechaNacimiento);
        System.out.println("Fecha Registro: " + java.time.LocalDate.now());
        System.out.println("==================================");

        return true; // Simulando que siempre se guarda exitosamente
    }

    /**
     * Muestra un mensaje al usuario
     * @param titulo Título del mensaje
     * @param mensaje Contenido del mensaje
     */
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Limpia todos los campos del formulario después de un registro exitoso
     */
    private void limpiarCampos() {
        txtNombre.clear();
        txtApellidos.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
        txtNombre.requestFocus();
    }
}