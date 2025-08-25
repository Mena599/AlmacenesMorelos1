package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.model.Cliente;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (NOMBRE_COMPLETO, TELEFONO, CORREO) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombreCompleto());
            stmt.setString(2, cliente.getTelefono());
            stmt.setString(3, cliente.getCorreo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Integer insertarYDevolverId(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (NOMBRE_COMPLETO, TELEFONO, CORREO) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[] {"ID_CLIENTE"})) {
            ps.setString(1, cliente.getNombreCompleto());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getCorreo());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // como fallback podrías hacer findByCorreo(cliente.getCorreo())
    }


    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT ID_CLIENTE, NOMBRE_COMPLETO, TELEFONO, CORREO FROM CLIENTE";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("ID_CLIENTE"));
                c.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                c.setTelefono(rs.getString("TELEFONO"));
                c.setCorreo(rs.getString("CORREO"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Cliente findById(int idCliente) {
        String sql = "SELECT ID_CLIENTE, NOMBRE_COMPLETO, TELEFONO, CORREO FROM CLIENTE WHERE ID_CLIENTE = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("ID_CLIENTE"),
                            rs.getString("NOMBRE_COMPLETO"),
                            rs.getString("TELEFONO"),
                            rs.getString("CORREO")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente findByCorreo(String correo) {
        String sql = "SELECT ID_CLIENTE, NOMBRE_COMPLETO, TELEFONO, CORREO FROM CLIENTE WHERE CORREO = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("ID_CLIENTE"),
                            rs.getString("NOMBRE_COMPLETO"),
                            rs.getString("TELEFONO"),
                            rs.getString("CORREO")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existeCorreo(String correo) {
        String sql = "SELECT 1 FROM CLIENTE WHERE CORREO = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void actualizar(Cliente cliente) {
        String sql = "UPDATE CLIENTE SET NOMBRE_COMPLETO=?, TELEFONO=?, CORREO=? WHERE ID_CLIENTE=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombreCompleto());
            stmt.setString(2, cliente.getTelefono());
            stmt.setString(3, cliente.getCorreo());
            stmt.setInt(4, cliente.getIdCliente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idCliente) {
        String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
