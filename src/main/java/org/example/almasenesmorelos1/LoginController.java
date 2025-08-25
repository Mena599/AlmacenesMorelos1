package org.example.almasenesmorelos1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.example.almasenesmorelos1.daos.UserDAO;
import org.example.almasenesmorelos1.daos.SedeDAO;
import org.example.almasenesmorelos1.model.User;
import org.example.almasenesmorelos1.model.Sede;
import org.example.almasenesmorelos1.utils.PasswordUtils;
import org.example.almasenesmorelos1.utils.ConexionDB;

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
        String username = safe(txtUsuario.getText()).trim();
        String password = safe(txtContrasena.getText()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("⚠ Ingresa usuario y contraseña.");
            return;
        }

        // 1) SuperAdmin fijo
        if (username.equals("root") && password.equals("admin123")) {
            precargarDataStoreTodo();
            cargarVista("InicioSuperAdmin.fxml", "Inicio - SuperAdmin");
            return;
        }

        // 2) Login por BD
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByUsername(username);

            if (user == null) {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
                return;
            }

            String stored = safe(user.getPasswordHash());
            // Regla que pediste: contraseña debe ser el username
            boolean passOK =
                    password.equals(username) ||
                            PasswordUtils.verificarPassword(password, stored) ||
                            password.equals(stored);

            if (!passOK) {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
                return;
            }

            String rol = safe(user.getTipoUsuario()).toUpperCase();
            switch (rol) {
                case "ADMIN_SEDE": {
                    // Encontrar sede asignada a este admin
                    SedeDAO sedeDAO = new SedeDAO();
                    Sede sede = sedeDAO.findByAdminUsername(username);
                    if (sede == null) {
                        lblMensaje.setText("⚠ No hay sede asignada a este admin.");
                        return;
                    }

                    // Reconstruir AdminSede para la sesión
                    AdminSede admin = new AdminSede(
                            username,  // nombre visible = username
                            "",        // correo (no persistido)
                            "",        // teléfono (no persistido)
                            sede.getId(),
                            username
                    );
                    admin.setPassword(password); // opcional

                    SessionManager.get().login(admin);

                    // Carga los datos que usa el home del admin
                    DataStore ds = DataStore.getInstance();
                    ds.refrescarAlmacenesDesdeBD();
                    ds.refrescarAsignacionesDesdeBD();

                    String titulo = "Sede: " + safe(sede.getId()) + " — Admin: " + username;
                    cargarVista("InicioAdminSede.fxml", titulo);
                    return;
                }

                case "SUPERADMIN":
                    precargarDataStoreTodo();
                    cargarVista("InicioSuperAdmin.fxml", "Inicio - SuperAdmin");
                    return;

                case "ADMIN":
                    cargarVista("InicioAdmin.fxml", "Inicio - ADMIN");
                    return;

                case "CLIENTE":
                    cargarVista("InicioCliente.fxml", "Inicio - CLIENTE");
                    return;

                default:
                    lblMensaje.setText("⚠ Rol no válido: " + user.getTipoUsuario());
                    return;
            }

        } catch (Exception e) {
            lblMensaje.setText("⚠ Error al iniciar sesión: " + e.getMessage());
        }
    }

    // ---- Helpers ----
    private void precargarDataStoreTodo() {
        try {
            DataStore ds = DataStore.getInstance();
            ds.refrescarSedesDesdeBD();
            ds.refrescarAlmacenesDesdeBD();
            ds.refrescarClientesDesdeBD();
            ds.refrescarAsignacionesDesdeBD();
        } catch (Throwable ignored) {}
    }

    private void cargarVista(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/almasenesmorelos1/" + fxml));
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

