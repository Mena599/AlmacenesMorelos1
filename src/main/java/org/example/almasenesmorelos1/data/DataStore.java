package org.example.almasenesmorelos1.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.example.almasenesmorelos1.Almacen;
import org.example.almasenesmorelos1.daos.ContratoDAO;
import org.example.almasenesmorelos1.model.*;
import org.example.almasenesmorelos1.daos.AlmacenDAO;
import org.example.almasenesmorelos1.daos.ClienteDAO;
import org.example.almasenesmorelos1.model.Cliente;
import org.example.almasenesmorelos1.model.Contrato;
import org.example.almasenesmorelos1.daos.SedeDAO;
import org.example.almasenesmorelos1.model.AsignacionCliente;

import java.time.LocalDate;
import java.util.stream.Collectors;

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
        try {
            // 1) Persistir en BD
            SedeDAO dao = new SedeDAO();
            dao.insertar(s);

            // 2) Reflejar en UI (evita duplicados por ID como ya hacías)
            boolean exists = sedes.stream().anyMatch(x ->
                    x.getId() != null && s.getId() != null && x.getId().equalsIgnoreCase(s.getId())
            );
            if (!exists) sedes.add(s);

        } catch (Exception e) {
            e.printStackTrace();
            // Si quieres, aquí podrías loguear y no agregar a UI para no desincronizar
        }
    }

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

        // 1) Validar sede libre (en memoria). Si quieres, también puedes validar con BD leyendo SedeDAO.findById(...)
        if (isSedeOcupada(sede.getId())) return false;

        // 2) Generar username único (checa en memoria y en BD)
        String base = (usernameDeseado == null || usernameDeseado.isBlank())
                ? generarUserDesdeNombre(admin.getNombre())
                : usernameDeseado.trim().toLowerCase();
        String u = base;
        int i = 1;

        org.example.almasenesmorelos1.daos.UserDAO userDAO = new org.example.almasenesmorelos1.daos.UserDAO();
        while (usernameTomado(u) || userDAO.findByUsername(u) != null) {
            u = base + i++;
        }

        // 3) Completar datos del AdminSede en memoria
        admin.setUsername(u);
        admin.setPassword(u);           // demo: pass = username (puedes hashearla si prefieres)
        admin.setSedeId(sede.getId());

        // 4) Persistir USUARIO si no existe
        if (userDAO.findByUsername(u) == null) {
            org.example.almasenesmorelos1.model.User user = new org.example.almasenesmorelos1.model.User();
            user.setNombreUsuario(u);
            user.setPasswordHash(admin.getPassword()); // aquí podrías hashear si quieres
            user.setTipoUsuario("ADMIN_SEDE");
            userDAO.insertar(user);
        }

        // 5) Persistir asignación en SEDE (ID_ADMIN) y marcar ocupada
        org.example.almasenesmorelos1.daos.SedeDAO sedeDAO = new org.example.almasenesmorelos1.daos.SedeDAO();
        sedeDAO.asignarAdmin(sede.getId(), u);

        // 6) Reflejar en listas observables y en el objeto Sede
        sede.setIdAdmin(u);
        sede.setOcupada(true);
        if (!admins.contains(admin)) admins.add(admin);

        return true;
    }

    public synchronized boolean desasignarSede(String sedeId) {
        Sede s = findSedeById(sedeId);
        if (s == null) return false;

        // Persistir en BD
        new org.example.almasenesmorelos1.daos.SedeDAO().desasignarAdmin(sedeId);

        // Memoria/UI
        s.setIdAdmin("");
        s.setOcupada(false);
        // También podrías remover al AdminSede de la lista "admins" si corresponde
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
    public void agregarAsignacion(AsignacionCliente a) {
        if (a == null) return;

        // 1) Asegurar CLIENTE en BD (por correo único)
        ClienteDAO cdao = new ClienteDAO();
        Cliente existente = (a.getCorreo() == null || a.getCorreo().isBlank())
                ? null
                : cdao.findByCorreo(a.getCorreo());

        int idCliente;
        if (existente != null) {
            idCliente = existente.getIdCliente();
        } else {
            Cliente nuevo = new Cliente(
                    a.getNombreCliente(),   // nombreCompleto
                    a.getTelefono(),
                    a.getCorreo()
            );
            Integer id = cdao.insertarYDevolverId(nuevo);
            if (id == null) {
                // Fallback (si el driver no devolvió keys)
                Cliente rec = cdao.findByCorreo(nuevo.getCorreo());
                if (rec == null) return; // no pudimos persistir
                id = rec.getIdCliente();
            }
            idCliente = id;
            // También refleja en UI de clientes si quieres:
            agregarCliente(new Cliente(idCliente, nuevo.getNombreCompleto(), nuevo.getTelefono(), nuevo.getCorreo()));
        }


        // 2) Crear CONTRATO (asignación) en BD
        ContratoDAO xdao = new ContratoDAO();
        Contrato x = new Contrato();
        x.setIdCliente(idCliente);
        x.setIdAlmacen(a.getIdAlmacen()); // String
        x.setTipoContrato(a.getEstatus().name()); // VENTA/RENTA
        x.setFechaInicio(java.sql.Date.valueOf(a.getFechaAdquisicion()));
        // Si es VENTA, FECHA_FIN = null; si es RENTA, usar la fechaExpiracion del DTO
        x.setFechaFin("VENTA".equalsIgnoreCase(a.getEstatus().name()) || a.getFechaExpiracion() == null
                ? null
                : java.sql.Date.valueOf(a.getFechaExpiracion()));
        x.setEstado("ACTIVO");
        x.setPrecioAplicado(a.getPrecioAplicado()); // NUEVO

        xdao.insertar(x);

        // 3) Finalmente, reflejar en la UI como ya lo hacías
        asignaciones.add(a);
    }

    public void refrescarAlmacenesDesdeBD() {
        AlmacenDAO dao = new AlmacenDAO();
        List<Almacen> lista = dao.obtenerTodos();
        inventario.setAll(lista); // reemplaza contenido y notifica a la UI
    }
    //Los refrecos
    // --- SEDES ---
    public void refrescarSedesDesdeBD() {
        SedeDAO dao = new SedeDAO();
        List<Sede> lista = dao.obtenerTodas();
        sedes.setAll(lista);
    }

    // --- ASIGNACIONES (reconstruidas desde CONTRATO + CLIENTE) ---
    public void refrescarAsignacionesDesdeBD() {
        ContratoDAO cdao = new ContratoDAO();
        ClienteDAO  cldao = new ClienteDAO();

        List<org.example.almasenesmorelos1.model.Contrato> contratos = cdao.obtenerTodos();

        List<AsignacionCliente> lista = contratos.stream().map(k -> {
            // cliente
            Cliente cli = cldao.findById(k.getIdCliente());
            String nombre = (cli != null ? cli.getNombreCompleto() : "Cliente " + k.getIdCliente());
            String correo = (cli != null ? cli.getCorreo() : "");
            String tel    = (cli != null ? cli.getTelefono() : "");

            // estatus
            EstatusOperacion est;
            try { est = EstatusOperacion.valueOf(k.getTipoContrato().toUpperCase()); }
            catch (Exception ex) { est = EstatusOperacion.RENTA; }

            // fechas
            LocalDate fIni = (k.getFechaInicio() != null) ? k.getFechaInicio().toLocalDate() : null;
            LocalDate fFin = (k.getFechaFin()     != null) ? k.getFechaFin().toLocalDate()     : null;

            // precio
            double precio = k.getPrecioAplicado();

            // Construye tu DTO tal como lo usas en la UI:
            return new AsignacionCliente(
                    k.getIdAlmacen(),   // String
                    nombre,
                    correo,
                    tel,
                    est,
                    fIni,
                    fFin,
                    precio
            );
        }).collect(Collectors.toList());

        asignaciones.setAll(lista);
    }
}
