package org.example.almasenesmorelos1.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.example.almasenesmorelos1.model.Sede;
import org.example.almasenesmorelos1.model.AdminSede;
import org.example.almasenesmorelos1.model.Cliente;
import org.example.almasenesmorelos1.model.AsignacionCliente;

// (Opcional) si quieres cargar desde BD
import org.example.almasenesmorelos1.daos.ClienteDAO;

import java.util.List;

public class DataStore {

    // --- Singleton ---
    private static final DataStore INSTANCE = new DataStore();
    public static DataStore getInstance() { return INSTANCE; }
    private DataStore() {}

    // ==============================
    //  SEDES
    // ==============================
    private final ObservableList<Sede> sedes = FXCollections.observableArrayList();
    public ObservableList<Sede> getSedes() { return sedes; }

    public void agregarSede(Sede s) {
        if (s == null) return;
        boolean exists = sedes.stream().anyMatch(x -> x.getId().equalsIgnoreCase(s.getId()));
        if (!exists) sedes.add(s);
    }
    public void eliminarSede(Sede s) { sedes.remove(s); }
    public void limpiarSedes() { sedes.clear(); }

    // ==============================
    //  ADMINS DE SEDE
    // ==============================
    private final ObservableList<AdminSede> admins = FXCollections.observableArrayList();
    public ObservableList<AdminSede> getAdmins() { return admins; }

    public void agregarAdmin(AdminSede a) {
        if (a == null) return;
        // Evitar duplicado por correo, por ejemplo
        boolean exists = admins.stream().anyMatch(x -> {
            String xc = x.getCorreo(), ac = a.getCorreo();
            return xc != null && ac != null && xc.equalsIgnoreCase(ac);
        });
        if (!exists) admins.add(a);
    }
    public void eliminarAdmin(AdminSede a) { admins.remove(a); }
    public void limpiarAdmins() { admins.clear(); }

    // ==============================
    //  CLIENTES (BD opcional)
    // ==============================
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    public ObservableList<Cliente> getClientes() { return clientes; }

    public void agregarCliente(Cliente c) {
        if (c == null) return;
        boolean exists = clientes.stream().anyMatch(x -> {
            if (c.getIdCliente() > 0 && x.getIdCliente() == c.getIdCliente()) return true;
            String xc = x.getCorreo(), cc = c.getCorreo();
            return xc != null && cc != null && xc.equalsIgnoreCase(cc);
        });
        if (!exists) clientes.add(c);
    }
    public void eliminarCliente(Cliente c) { clientes.remove(c); }
    public void limpiarClientes() { clientes.clear(); }

    /** Carga clientes desde BD (si usas DAO). */
    public void refrescarClientesDesdeBD() {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> lista = dao.obtenerTodos();
        clientes.setAll(lista); // reemplaza contenido, notifica a la UI
    }

    // ==============================
    //  ASIGNACIONES (tabla Clients)
    // ==============================
    private final ObservableList<AsignacionCliente> asignaciones = FXCollections.observableArrayList();
    public ObservableList<AsignacionCliente> getAsignaciones() { return asignaciones; }

    public void agregarAsignacion(AsignacionCliente a) {
        if (a == null) return;
        asignaciones.add(a);
    }
    public void eliminarAsignacion(AsignacionCliente a) { asignaciones.remove(a); }
    public void limpiarAsignaciones() { asignaciones.clear(); }

    // ==============================
    //  NUEVO: Helpers internos
    // ==============================
    private String generarUserDesdeNombre(String nombre) {
        if (nombre == null) return "admin";
        String base = nombre.trim().toLowerCase().replaceAll("\\s+", ".");
        return base.isBlank() ? "admin" : base;
    }

    private Sede findSedeById(String sedeId) {
        if (sedeId == null) return null;
        return sedes.stream()
                .filter(s -> s.getId() != null && s.getId().equalsIgnoreCase(sedeId))
                .findFirst()
                .orElse(null);
    }

    private boolean usernameTomado(String username) {
        if (username == null || username.isBlank()) return false;
        return admins.stream().anyMatch(a ->
                a.getUsername() != null && a.getUsername().equalsIgnoreCase(username)
        );
    }

    // ==============================
    //  NUEVO: Asignar sede a admin + credenciales
    // ==============================
    /**
     * Asigna una sede libre a un admin, genera credenciales (username/password),
     * y marca la sede como ocupada. Retorna true si tuvo éxito.
     */
    public synchronized boolean asignarSedeAAdmin(Sede sede, AdminSede admin, String usernameDeseado) {
        if (sede == null || admin == null) return false;
        // Validar que la sede esté libre (requiere Sede.isOcupada())
        try {
            // Si el modelo no tiene ocupada, esto dará NPE; asegúrate de tenerlo
            if (sede.isOcupada()) return false;
        } catch (Exception e) {
            // Si no existe el campo ocupada en tu modelo, quita este try/catch y añade el campo.
            return false;
        }

        // Generar username único
        String base = (usernameDeseado == null || usernameDeseado.isBlank())
                ? generarUserDesdeNombre(admin.getNombre())
                : usernameDeseado.trim().toLowerCase();
        String u = base;
        int i = 1;
        while (usernameTomado(u)) u = base + i++;

        admin.setUsername(u);
        admin.setPassword(u);         // demo: misma contraseña
        admin.setSedeId(sede.getId());

        // Marcar sede como ocupada y vincular admin
        sede.setOcupada(true);
        sede.setIdAdmin(admin.getCorreo()); // o un ID propio si lo manejas

        // Asegurar admin en lista
        if (!admins.contains(admin)) admins.add(admin);

        // sedes es observable y trabajamos sobre la MISMA instancia de la lista,
        // por lo que tu UI reacciona y repinta solo con setStyle o listener ya configurado.
        return true;
    }

    /** Variante por id de sede (por comodidad). */
    public synchronized boolean asignarSedeAAdmin(String sedeId, AdminSede admin, String usernameDeseado) {
        return asignarSedeAAdmin(findSedeById(sedeId), admin, usernameDeseado);
    }

    // ==============================
    //  NUEVO: Login simple de Admin de Sede
    // ==============================
    public AdminSede loginAdminSede(String username, String password) {
        if (username == null || password == null) return null;
        return admins.stream()
                .filter(a -> a.getUsername() != null && a.getPassword() != null)
                .filter(a -> a.getUsername().equalsIgnoreCase(username) && a.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
