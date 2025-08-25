package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.Almacen; // <- la clase que usa tu controller
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmacenDAO {

    /** Inserta usando defaults de BD para TIPO/ESTADO */
    public void insertar(Almacen a) {
        String sql = "INSERT INTO ALMACEN " +
                "(ID_ALMACEN, ID_SEDE, TAMANO_M2, UBICACION, PRECIO_VENTA, PRECIO_RENTA) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getId());
            ps.setString(2, a.getSedeId());
            ps.setDouble(3, a.getTamanoM2());
            ps.setString(4, a.getUbicacion());
            ps.setDouble(5, a.getPrecioVenta());
            ps.setDouble(6, a.getPrecioRenta());

            ps.executeUpdate();
        } catch (SQLException e) {
            // Aquí puedes loggear mejor o propagar una RuntimeException
            e.printStackTrace();
        }
    }

    /** Variante si NO tienes defaults en BD y quieres mandar TIPO/ESTADO explícitos */
    public void insertarConValores(Almacen a, String tipo, String estado) {
        String sql = "INSERT INTO ALMACEN " +
                "(ID_ALMACEN, ID_SEDE, TIPO, TAMANO_M2, UBICACION, PRECIO_VENTA, PRECIO_RENTA, ESTADO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getId());
            ps.setString(2, a.getSedeId());
            ps.setString(3, tipo);
            ps.setDouble(4, a.getTamanoM2());
            ps.setString(5, a.getUbicacion());
            ps.setDouble(6, a.getPrecioVenta());
            ps.setDouble(7, a.getPrecioRenta());
            ps.setString(8, estado);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Almacen> obtenerTodos() {
        List<Almacen> list = new ArrayList<>();
        String sql = "SELECT ID_ALMACEN, ID_SEDE, TAMANO_M2, UBICACION, PRECIO_VENTA, PRECIO_RENTA FROM ALMACEN";
        try (Connection conn = ConexionDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Almacen> obtenerPorSede(String sedeId) {
        List<Almacen> list = new ArrayList<>();
        String sql = "SELECT ID_ALMACEN, ID_SEDE, TAMANO_M2, UBICACION, PRECIO_VENTA, PRECIO_RENTA " +
                "FROM ALMACEN WHERE ID_SEDE = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sedeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Almacen findById(String idAlmacen) {
        String sql = "SELECT ID_ALMACEN, ID_SEDE, TAMANO_M2, UBICACION, PRECIO_VENTA, PRECIO_RENTA " +
                "FROM ALMACEN WHERE ID_ALMACEN = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idAlmacen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Cambia solo el estado (si lo usas en tarjetas, etc.) */
    public void actualizarEstado(String idAlmacen, String nuevoEstado) {
        String sql = "UPDATE ALMACEN SET ESTADO = ? WHERE ID_ALMACEN = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setString(2, idAlmacen);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** (Opcional) Actualizar precios/ubicación/tamaño si lo necesitas en futuro */
    public void actualizarDatos(String idAlmacen, Double m2, String ubicacion, Double precioVenta, Double precioRenta) {
        String sql = "UPDATE ALMACEN SET TAMANO_M2 = ?, UBICACION = ?, PRECIO_VENTA = ?, PRECIO_RENTA = ? " +
                "WHERE ID_ALMACEN = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, m2);
            ps.setString(2, ubicacion);
            ps.setDouble(3, precioVenta);
            ps.setDouble(4, precioRenta);
            ps.setString(5, idAlmacen);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(String idAlmacen) {
        String sql = "DELETE FROM ALMACEN WHERE ID_ALMACEN = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idAlmacen);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- Helper ----------
    private Almacen mapRow(ResultSet rs) throws SQLException {
        return new Almacen(
                rs.getString("ID_ALMACEN"),
                rs.getDouble("TAMANO_M2"),
                rs.getString("UBICACION"),
                rs.getString("ID_SEDE"),
                rs.getDouble("PRECIO_VENTA"),
                rs.getDouble("PRECIO_RENTA")
        );
    }
}
