package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.example.almasenesmorelos1.daos.UserDAO;
import org.example.almasenesmorelos1.model.User;
import org.example.almasenesmorelos1.utils.PasswordUtils;
import org.example.almasenesmorelos1.utils.ConexionDB;

// 👇 NUEVO
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.model.AdminSede;
import org.example.almasenesmorelos1.model.SessionManager;

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
        // Probar conexión a la base de datos (si falla, NO rompe el login de admin de sede en memoria)
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
        String nombreUsuario = txtUsuario.getText().trim();
        String password = txtContrasena.getText().trim();

        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("⚠ Ingresa usuario y contraseña.");
            return;
        }

        // 1) SuperAdmin fijo (como pediste)
        if (nombreUsuario.equals("root") && password.equals("admin123")) {
            cargarVista("InicioSuperAdmin.fxml", "Inicio - SuperAdmin");
            return;
        }

        // 2) NUEVO: Intentar login como Admin de Sede en MEMORIA (DataStore)
        AdminSede admin = DataStore.getInstance().loginAdminSede(nombreUsuario, password);
        if (admin != null) {
            // Guardar sesión en memoria y abrir la vista única del Admin de Sede
            SessionManager.get().login(admin);
            String titulo = "Sede: " + safe(admin.getSedeId()) + " — Admin: " + safe(admin.getUsername());
            cargarVista("InicioAdminSede.fxml", titulo);
            return;
        }

        // 3) Si no es Admin de Sede en memoria, seguimos con tu validación por BD (UserDAO)
        try {
            UserDAO dao = new UserDAO();
            User user = dao.findByUsername(nombreUsuario);

            if (user != null && PasswordUtils.verificarPassword(password, user.getPasswordHash())) {
                String tipoUsuario = user.getTipoUsuario();

                String fxmlDestino = switch (tipoUsuario.toUpperCase()) {
                    case "SUPERADMIN" -> "InicioSuperAdmin.fxml";
                    case "ADMIN"      -> "InicioAdmin.fxml";
                    case "CLIENTE"    -> "InicioCliente.fxml";
                    default -> null;
                };

                if (fxmlDestino != null) {
                    cargarVista(fxmlDestino, "Inicio - " + tipoUsuario);
                } else {
                    lblMensaje.setText("⚠ Rol no válido: " + tipoUsuario);
                }
            } else {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            lblMensaje.setText("⚠ Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void cargarVista(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setTitle(titulo);
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar la vista: " + e.getMessage());
        }
    }

    private String safe(String s) { return s == null ? "" : s; }
}
