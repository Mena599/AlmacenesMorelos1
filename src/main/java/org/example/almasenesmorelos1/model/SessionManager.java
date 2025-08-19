package org.example.almasenesmorelos1.model;
/**
 * SessionManager
 * --------------
 * Gestor de sesión en memoria para la aplicación.
 * - Mantiene el Admin de Sede actualmente autenticado.
 * - No persiste en BD; es solo para el ciclo de vida de la app.
 *
 * Uso típico:
 *   // En Login (tras validar credenciales)
 *   SessionManager.get().login(adminEncontrado);
 *
 *   // En controladores (para saber quién es y qué sede maneja)
 *   AdminSede admin = SessionManager.get().getCurrentAdmin();
 *   String sedeId   = SessionManager.get().getSedeId();
 *
 *   // Al cerrar sesión
 *   SessionManager.get().logout();
 */
public final class SessionManager {

    // --- Singleton ---
    private static final SessionManager INSTANCE = new SessionManager();
    public static SessionManager get() { return INSTANCE; }
    private SessionManager() {}

    // --- Estado de sesión ---
    private AdminSede currentAdmin = null;

    /**
     * Inicia sesión con el Admin de Sede indicado.
     * @param admin AdminSede autenticado (no null)
     */
    public void login(AdminSede admin) {
        if (admin == null) {
            throw new IllegalArgumentException("admin no puede ser null");
        }
        this.currentAdmin = admin;
    }

    /**
     * Cierra la sesión actual (limpia el admin).
     */
    public void logout() {
        this.currentAdmin = null;
    }

    /**
     * @return true si hay un admin logueado, false si no.
     */
    public boolean isLoggedIn() {
        return currentAdmin != null;
    }

    /**
     * @return el Admin de Sede actual o null si no hay sesión.
     */
    public AdminSede getCurrentAdmin() {
        return currentAdmin;
    }

    /**
     * Helper: devuelve el username del admin actual, o "" si no hay sesión.
     */
    public String getUsername() {
        return (currentAdmin == null || currentAdmin.getUsername() == null)
                ? "" : currentAdmin.getUsername();
    }

    /**
     * Helper: devuelve el ID de la sede del admin actual, o "" si no hay sesión.
     */
    public String getSedeId() {
        return (currentAdmin == null || currentAdmin.getSedeId() == null)
                ? "" : currentAdmin.getSedeId();
    }

    /**
     * Versión estricta: si no hay admin logueado, lanza IllegalStateException.
     * Útil en controladores donde se requiere sesión sí o sí.
     * @return Admin de Sede actual (nunca null si no lanza excepción).
     */
    public AdminSede requireAdmin() {
        if (currentAdmin == null) {
            throw new IllegalStateException("No hay sesión activa de Admin de Sede.");
        }
        return currentAdmin;
    }
}
