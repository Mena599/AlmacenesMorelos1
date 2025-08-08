
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

    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE";
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
