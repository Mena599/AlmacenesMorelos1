package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.almasenesmorelos1.daos.UserDAO;
import org.example.almasenesmorelos1.model.User;
import org.example.almasenesmorelos1.utils.PasswordUtils;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;

    @FXML
    private void handleLogin() {
        String nombreUsuario = txtUsuario.getText().trim();
        String password = txtContrasena.getText().trim();

        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("⚠ Ingresa usuario y contraseña.");
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            User user = dao.findByUsername(nombreUsuario);

            if (user != null && PasswordUtils.verificarPassword(password, user.getPasswordHash())) {
                String tipoUsuario = user.getTipoUsuario();

                String fxmlDestino = switch (tipoUsuario.toUpperCase()) {
                    case "SUPERADMIN" -> "InicioSuperAdmin.fxml";
                    case "ADMIN" -> "InicioAdmin.fxml";
                    case "CLIENTE" -> "InicioCliente.fxml";
                    default -> null;
                };

                if (fxmlDestino != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlDestino));
                    Scene scene = new Scene(loader.load());
                    Stage stage = (Stage) txtUsuario.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Inicio - " + tipoUsuario);
                } else {
                    lblMensaje.setText("⚠ Rol no válido: " + tipoUsuario);
                }

            } else {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setText("⚠ Error al cargar la interfaz.");
        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("⚠ Error al intentar iniciar sesión.");
        }
    }
}
