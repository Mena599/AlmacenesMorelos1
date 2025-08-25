package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.model.Sede;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SedeDAO {

    public void insertar(Sede s) {
        String sql = "INSERT INTO SEDE (ID_SEDE, NOMBRE, ID_ADMIN, TELEFONO, FECHA_REGISTRO, OCUPADA) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, s.getId());
            ps.setString(2, s.getNombre());
            // si no hay admin aún, dejamos NULL
            ps.setString(3, (s.getIdAdmin() == null || s.getIdAdmin().isBlank()) ? null : s.getIdAdmin());
            ps.setString(4, s.getTelefono());
            ps.setString(5, s.getFechaRegistro());
            ps.setInt(6, s.isOcupada() ? 1 : 0);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Sede> obtenerTodas() {
        List<Sede> out = new ArrayList<>();
        String sql = "SELECT ID_SEDE, NOMBRE, ID_ADMIN, TELEFONO, FECHA_REGISTRO, OCUPADA " +
                "FROM SEDE ORDER BY ID_SEDE";
        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) out.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public Sede findById(String idSede) {
        String sql = "SELECT ID_SEDE, NOMBRE, ID_ADMIN, TELEFONO, FECHA_REGISTRO, OCUPADA FROM SEDE WHERE ID_SEDE = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, idSede);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /** Actualiza datos capturados en tu formulario (municipio/nombre, tel, fecha). */
    public void actualizarBasicos(String idSede, String nombre, String telefono, String fechaRegistro) {
        String sql = "UPDATE SEDE SET NOMBRE=?, TELEFONO=?, FECHA_REGISTRO=? WHERE ID_SEDE=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, fechaRegistro);
            ps.setString(4, idSede);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /** Marca/limpia ocupación. */
    public void actualizarOcupada(String idSede, boolean ocupada) {
        String sql = "UPDATE SEDE SET OCUPADA=? WHERE ID_SEDE=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, ocupada ? 1 : 0);
            ps.setString(2, idSede);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public Sede findByAdminUsername(String username) {
        String sql = "SELECT ID_SEDE, NOMBRE, ID_ADMIN, TELEFONO, FECHA_REGISTRO, OCUPADA " +
                "FROM SEDE WHERE ID_ADMIN = ?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sede(
                            rs.getString("ID_SEDE"),
                            rs.getString("NOMBRE"),
                            rs.getString("ID_ADMIN"),
                            rs.getString("TELEFONO"),
                            rs.getString("FECHA_REGISTRO"),
                            rs.getInt("OCUPADA") == 1
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }


    /** Asigna admin por username y marca ocupada. */
    public void asignarAdmin(String idSede, String usernameAdmin) {
        String sql = "UPDATE SEDE SET ID_ADMIN=?, OCUPADA=1 WHERE ID_SEDE=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usernameAdmin);
            ps.setString(2, idSede);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /** Quita admin (y marca libre). */
    public void desasignarAdmin(String idSede) {
        String sql = "UPDATE SEDE SET ID_ADMIN=NULL, OCUPADA=0 WHERE ID_SEDE=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, idSede);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminar(String idSede) {
        String sql = "DELETE FROM SEDE WHERE ID_SEDE=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, idSede);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ---- Mapper ----
    private Sede mapRow(ResultSet rs) throws SQLException {
        return new Sede(
                rs.getString("ID_SEDE"),
                rs.getString("NOMBRE"),
                rs.getString("ID_ADMIN"),   // ← ahora sí lo rellenamos
                rs.getString("TELEFONO"),
                rs.getString("FECHA_REGISTRO"),
                rs.getInt("OCUPADA") == 1
        );
    }
}
