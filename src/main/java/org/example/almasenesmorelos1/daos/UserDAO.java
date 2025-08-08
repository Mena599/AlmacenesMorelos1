package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.model.User;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // INSERTAR
    public void insertar(User user) {
        String sql = "INSERT INTO USUARIO (NOMBRE_USUARIO, PASSWORD_HASH, TIPO_USUARIO) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNombreUsuario());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getTipoUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // OBTENER TODOS
    public List<User> obtenerTodos() {
        List<User> lista = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setIdUsuario(rs.getInt("ID_USUARIO"));
                user.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                user.setPasswordHash(rs.getString("PASSWORD_HASH"));
                user.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                lista.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ACTUALIZAR
    public void actualizar(User user) {
        String sql = "UPDATE USUARIO SET NOMBRE_USUARIO=?, PASSWORD_HASH=?, TIPO_USUARIO=? WHERE ID_USUARIO=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNombreUsuario());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getTipoUsuario());
            stmt.setInt(4, user.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ELIMINAR
    public void eliminar(int idUsuario) {
        String sql = "DELETE FROM USUARIO WHERE ID_USUARIO=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // BUSCAR POR NOMBRE_USUARIO
    public User findByUsername(String nombreUsuario) {
        String sql = "SELECT * FROM USUARIO WHERE NOMBRE_USUARIO = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUsuario(rs.getInt("ID_USUARIO"));
                    user.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                    user.setPasswordHash(rs.getString("PASSWORD_HASH"));
                    user.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
