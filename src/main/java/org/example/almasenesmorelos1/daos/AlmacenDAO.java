// daos/AlmacenDAO.java
package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.models.Almacen;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlmacenDAO {

    public Long crear(Almacen a) throws SQLException {
        String sql = """
            INSERT INTO almacenes (nombre, ubicacion, capacidad, precio_renta, precio_venta, disponible)
            VALUES (?,?,?,?,?,?)
        """;
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getUbicacion());
            if (a.getCapacidad() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, a.getCapacidad());
            if (a.getPrecioRenta() == null) ps.setNull(4, Types.NUMERIC); else ps.setDouble(4, a.getPrecioRenta());
            if (a.getPrecioVenta() == null) ps.setNull(5, Types.NUMERIC); else ps.setDouble(5, a.getPrecioVenta());
            ps.setString(6, a.isDisponible() ? "S" : "N");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return null;
    }

    public boolean actualizar(Almacen a) throws SQLException {
        String sql = """
            UPDATE almacenes SET nombre=?, ubicacion=?, capacidad=?, precio_renta=?, precio_venta=?, disponible=?
            WHERE id_almacen=?
        """;
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getUbicacion());
            if (a.getCapacidad() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, a.getCapacidad());
            if (a.getPrecioRenta() == null) ps.setNull(4, Types.NUMERIC); else ps.setDouble(4, a.getPrecioRenta());
            if (a.getPrecioVenta() == null) ps.setNull(5, Types.NUMERIC); else ps.setDouble(5, a.getPrecioVenta());
            ps.setString(6, a.isDisponible() ? "S" : "N");
            ps.setLong(7, a.getIdAlmacen());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM almacenes WHERE id_almacen=?";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public Optional<Almacen> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id_almacen, nombre, ubicacion, capacidad, precio_renta, precio_venta, disponible FROM almacenes WHERE id_almacen=?";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    public List<Almacen> listarTodos() throws SQLException {
        String sql = "SELECT id_almacen, nombre, ubicacion, capacidad, precio_renta, precio_venta, disponible FROM almacenes ORDER BY id_almacen DESC";
        List<Almacen> out = new ArrayList<>();
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    public List<Almacen> listarDisponibles() throws SQLException {
        String sql = "SELECT id_almacen, nombre, ubicacion, capacidad, precio_renta, precio_venta, disponible FROM almacenes WHERE disponible='S' ORDER BY id_almacen DESC";
        List<Almacen> out = new ArrayList<>();
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    public boolean marcarDisponible(Long idAlmacen, boolean disponible) throws SQLException {
        String sql = "UPDATE almacenes SET disponible=? WHERE id_almacen=?";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, disponible ? "S" : "N");
            ps.setLong(2, idAlmacen);
            return ps.executeUpdate() == 1;
        }
    }

    private Almacen map(ResultSet rs) throws SQLException {
        return new Almacen(
                rs.getLong("id_almacen"),
                rs.getString("nombre"),
                rs.getString("ubicacion"),
                (Integer) rs.getObject("capacidad"),
                rs.getObject("precio_renta") == null ? null : rs.getDouble("precio_renta"),
                rs.getObject("precio_venta") == null ? null : rs.getDouble("precio_venta"),
                "S".equals(rs.getString("disponible"))
        );
    }
}
