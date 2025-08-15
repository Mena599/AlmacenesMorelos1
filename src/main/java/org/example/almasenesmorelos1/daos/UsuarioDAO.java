// daos/UsuarioDAO.java
package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.models.Usuario;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.Optional;

public class UsuarioDAO {

    public Optional<Usuario> findByUsername(String username) throws SQLException {
        String sql = """
            SELECT id_usuario, nombre, apellido, correo, usuario, contraseña, rol
            FROM usuarios WHERE usuario = ?
        """;
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    public Long crear(Usuario u) throws SQLException {
        String sql = """
            INSERT INTO usuarios (nombre, apellido, correo, usuario, contraseña, rol)
            VALUES (?,?,?,?,?,?)
        """;
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getUsuario());
            ps.setString(5, u.getPasswordHash());
            ps.setString(6, u.getRol());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return null;
    }

    private Usuario map(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getLong("id_usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("usuario"),
                rs.getString("contraseña"),
                rs.getString("rol")
        );
    }
}
