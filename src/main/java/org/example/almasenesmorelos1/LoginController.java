package org.example.almasenesmorelos1;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Label lblMensaje;

    @FXML
    private void handleLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (usuario.equals("root") && contrasena.equals("admin123")) {
            lblMensaje.setText("✅ Bienvenido, SuperAdmin.");
            // Aquí puedes cargar otra escena más adelante
        } else {
            lblMensaje.setText("❌ Credenciales incorrectas.");
        }
    }

}