package org.example.almasenesmorelos1;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
<<<<<<< Updated upstream
=======
import org.example.almasenesmorelos1.daos.UserDAO;
import org.example.almasenesmorelos1.model.User;
import org.example.almasenesmorelos1.utils.PasswordUtils;
import org.example.almasenesmorelos1.utils.ConexionDB;
>>>>>>> Stashed changes

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;
    @FXML private ImageView imgLogo;
    @FXML private ImageView imgUser;

    @FXML
    public void initialize() {
        // Prueba la conexión a la base de datos cuando se carga el controlador
        try (Connection conn = ConexionDB.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                lblMensaje.setText("✅ Base de datos conectada correctamente.");
            } else {
                lblMensaje.setText("❌ No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            lblMensaje.setText("❌ Error en conexión a BD: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogin() {
<<<<<<< Updated upstream
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
=======
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
                    stage.sizeToScene();
                    stage.setTitle("Inicio - " + tipoUsuario);
                } else {
                    lblMensaje.setText("⚠ Rol no válido: " + tipoUsuario);
                }

            } else {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
>>>>>>> Stashed changes
            }
        } else {
            // ❌ Credenciales incorrectas
            lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
        }
    }
<<<<<<< Updated upstream
}
=======
}

>>>>>>> Stashed changes
