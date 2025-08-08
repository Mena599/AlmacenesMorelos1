package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.model.Almacen;
import org.example.almasenesmorelos1.utils.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmacenDAO {

    public void insertar(Almacen almacen) {
        String sql = "INSERT INTO ALMACEN (ID_SEDE, TIPO, PRECIO_VENTA, PRECIO_RENTA, ESTADO) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, almacen.getIdSede());
            stmt.setString(2, almacen.getTipo());
            stmt.setDouble(3, almacen.getPrecioVenta());
            stmt.setDouble(4, almacen.getPrecioRenta());
            stmt.setString(5, almacen.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Almacen> obtenerTodos() {
        List<Almacen> lista = new ArrayList<>();
        String sql = "SELECT * FROM ALMACEN";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Almacen a = new Almacen();
                a.setIdAlmacen(rs.getInt("ID_ALMACEN"));
                a.setIdSede(rs.getInt("ID_SEDE"));
                a.setTipo(rs.getString("TIPO"));
                a.setPrecioVenta(rs.getDouble("PRECIO_VENTA"));
                a.setPrecioRenta(rs.getDouble("PRECIO_RENTA"));
                a.setEstado(rs.getString("ESTADO"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarEstado(int idAlmacen, String nuevoEstado) {
        String sql = "UPDATE ALMACEN SET ESTADO=? WHERE ID_ALMACEN=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idAlmacen);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idAlmacen) {
        String sql = "DELETE FROM ALMACEN WHERE ID_ALMACEN=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlmacen);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
