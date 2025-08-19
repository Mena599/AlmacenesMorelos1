package org.example.almasenesmorelos1.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.example.almasenesmorelos1.Almacen;
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
    //  INVENTARIO (ALMACENES)
    // ==============================
    private final ObservableList<Almacen> inventario = FXCollections.observableArrayList();

    public ObservableList<Almacen> getInventario() { return inventario; }

    public void addAlmacen(Almacen a) {
        if (a == null) return;
        // Evitar duplicados por ID
        boolean exists = inventario.stream().anyMatch(x -> {
            String xi = x.getId(), ai = a.getId();
            return xi != null && ai != null && xi.equalsIgnoreCase(ai);
        });
        if (!exists) inventario.add(a);
    }

    public void removeAlmacen(Almacen a) { inventario.remove(a); }
    public void clearInventario() { inventario.clear(); }

    /** Buscar almacén por ID dentro de inventario. */
    public Almacen findAlmacenById(String id) {
        if (id == null) return null;
        for (Almacen a : inventario) {
            if (id.equalsIgnoreCase(a.getId())) return a;
        }
        return null;
    }

    // ==============================
    //  SEDES
    // ==============================
    private final ObservableList<Sede> sedes = FXCollections.observableArrayList();
    public ObservableList<Sede> getSedes() { return sedes; }

    public void agregarSede(Sede s) {
        if (s == null) return;
        boolean exists = sedes.stream().anyMatch(x -> x.getId() != null && x.getId().equalsIgnoreCase(s.getId()));
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
        // Evitar duplicado por correo
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
    //  Helpers internos
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

    /** ¿Está ocupada una sede? Derivado: si algún admin ya tiene esa sede asignada. */
    public boolean isSedeOcupada(String sedeId) {
        if (sedeId == null) return false;
        return admins.stream().anyMatch(a ->
                a.getSedeId() != null && a.getSedeId().equalsIgnoreCase(sedeId)
        );
    }

    // ==============================
    //  Asignar sede a admin + credenciales
    // ==============================
    /**
     * Asigna una sede libre a un admin, genera credenciales (username/password = username),
     * y vincula el admin a esa sede. No modifica el objeto Sede (ocupación se deriva).
     * @return true si tuvo éxito; false si la sede está ocupada o datos inválidos.
     */
    public synchronized boolean asignarSedeAAdmin(Sede sede, AdminSede admin, String usernameDeseado) {
        if (sede == null || admin == null) return false;

        // Validar que la sede esté libre (derivado)
        if (isSedeOcupada(sede.getId())) return false;

        // Generar username único
        String base = (usernameDeseado == null || usernameDeseado.isBlank())
                ? generarUserDesdeNombre(admin.getNombre())
                : usernameDeseado.trim().toLowerCase();
        String u = base;
        int i = 1;
        while (usernameTomado(u)) u = base + i++;

        admin.setUsername(u);
        admin.setPassword(u);         // demo: password = username
        admin.setSedeId(sede.getId());

        // Asegurar admin en lista
        if (!admins.contains(admin)) admins.add(admin);

        // UI de sedes puede consultar isSedeOcupada(sede.getId()) para pintar "ocupada"
        return true;
    }

    /** Variante por id de sede (por comodidad). */
    public synchronized boolean asignarSedeAAdmin(String sedeId, AdminSede admin, String usernameDeseado) {
        return asignarSedeAAdmin(findSedeById(sedeId), admin, usernameDeseado);
    }

    // ==============================
    //  Login simple de Admin de Sede (en memoria)
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
