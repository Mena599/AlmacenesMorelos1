package org.example.almasenesmorelos1;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;
    @FXML private ImageView imgLogo;
    @FXML private ImageView imgUser;

    @FXML
    private void handleLogin() {
        String SUPER_USER = txtUsuario.getText().trim();
        String SUPER_PASS = txtContrasena.getText().trim();
        if (SUPER_USER.equals("root") && SUPER_PASS.equals("admin123")) {
            // ✅ Credenciales correctas → ir al inicio
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InicioSuperAdmin.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) txtUsuario.getScene().getWindow(); // obtener ventana actual
                stage.setScene(scene);
                stage.setTitle("Inicio - SuperAdmin");
            } catch (IOException e) {
                e.printStackTrace();
                lblMensaje.setText("Error al cargar la vista de inicio.");
            }
        } else {
            // ❌ Credenciales incorrectas
            lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
        }
    }
    }