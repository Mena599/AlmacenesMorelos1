// daos/ClienteDAO.java
package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.models.Cliente;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO {

    public Long crear(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, apellidos, telefono, correo) VALUES (?,?,?,?)";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return null;
    }

    public boolean actualizar(Cliente c) throws SQLException {
        String sql = "UPDATE clientes SET nombre=?, apellidos=?, telefono=?, correo=? WHERE id_cliente=?";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.setLong(5, c.getIdCliente());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public Optional<Cliente> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id_cliente, nombre, apellidos, telefono, correo FROM clientes WHERE id_cliente=?";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT id_cliente, nombre, apellidos, telefono, correo FROM clientes ORDER BY id_cliente DESC";
        List<Cliente> out = new ArrayList<>();
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    private Cliente map(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getLong("id_cliente"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("telefono"),
                rs.getString("correo")
        );
    }
}
