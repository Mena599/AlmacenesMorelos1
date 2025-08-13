package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.example.almasenesmorelos1.daos.UsuarioDAO;
import org.example.almasenesmorelos1.models.Usuario;
import org.example.almasenesmorelos1.utils.PasswordUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;

    @FXML
    private void handleLogin() {
        lblMensaje.setText(""); // limpia mensaje

        final String nombreUsuario = txtUsuario.getText().trim();
        final String password = txtContrasena.getText().trim();

        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("⚠ Ingresa usuario y contraseña.");
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Optional<Usuario> opt = dao.findByUsername(nombreUsuario);

            if (opt.isEmpty()) {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
                return;
            }

            Usuario user = opt.get();

            // Verifica hash (BCrypt) con tu utilidad
            if (!PasswordUtils.verificarPassword(password, user.getPasswordHash())) {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
                return;
            }

            // Navegación por rol
            String rol = user.getRol() == null ? "" : user.getRol().toUpperCase();
            String fxmlDestino = switch (rol) {
                case "SUPERADMIN" -> "InicioSuperAdmin.fxml";
                case "ADMIN_SEDE" -> "InicioAdminSede.fxml";
                case "CLIENTE"    -> "InicioCliente.fxml"; // Asegúrate de tenerlo
                default -> null;
            };

            if (fxmlDestino == null) {
                lblMensaje.setText("⚠ Rol no válido: " + rol);
                return;
            }

            // Cargar y cambiar escena
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlDestino));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setTitle("Inicio - " + rol);

        } catch (SQLException e) {
            e.printStackTrace();
            lblMensaje.setText("⚠ Error de base de datos.");
        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setText("⚠ Error al cargar la interfaz.");
        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("⚠ Error al intentar iniciar sesión.");
        }
    }
}
