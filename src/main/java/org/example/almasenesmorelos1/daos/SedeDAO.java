// daos/SedeDAO.java
package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.models.Sede;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SedeDAO {

    public Long crear(Sede s) throws SQLException {
        String sql = "INSERT INTO sedes (municipio, id_admin_sede, telefono, fecha_registro) VALUES (?,?,?,DEFAULT)";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getMunicipio());
            if (s.getIdAdminSede() == null) ps.setNull(2, Types.NUMERIC);
            else ps.setLong(2, s.getIdAdminSede());
            ps.setString(3, s.getTelefono());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return null;
    }

    public boolean actualizar(Sede s) throws SQLException {
        String sql = "UPDATE sedes SET municipio=?, id_admin_sede=?, telefono=? WHERE id_sede=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, s.getMunicipio());
            if (s.getIdAdminSede() == null) ps.setNull(2, Types.NUMERIC);
            else ps.setLong(2, s.getIdAdminSede());
            ps.setString(3, s.getTelefono());
            ps.setLong(4, s.getIdSede());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM sedes WHERE id_sede=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public Optional<Sede> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id_sede, municipio, id_admin_sede, telefono, fecha_registro FROM sedes WHERE id_sede=?";
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    public List<Sede> listar() throws SQLException {
        String sql = "SELECT id_sede, municipio, id_admin_sede, telefono, fecha_registro FROM sedes ORDER BY id_sede DESC";
        List<Sede> out = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    private Sede map(ResultSet rs) throws SQLException {
        Date fr = rs.getDate("fecha_registro");
        LocalDate fecha = (fr == null) ? null : fr.toLocalDate();
        Long admin = rs.getObject("id_admin_sede") == null ? null : rs.getLong("id_admin_sede");
        return new Sede(
                rs.getLong("id_sede"),
                rs.getString("municipio"),
                admin,
                rs.getString("telefono"),
                fecha
        );
    }
}
