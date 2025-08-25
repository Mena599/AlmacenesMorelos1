package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.model.Contrato;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {

    public void insertar(Contrato c) {
        String sql = "INSERT INTO CONTRATO " +
                "(ID_CLIENTE, ID_ALMACEN, TIPO_CONTRATO, FECHA_INICIO, FECHA_FIN, ESTADO, PRECIO_APLICADO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getIdCliente());
            ps.setString(2, c.getIdAlmacen());
            ps.setString(3, c.getTipoContrato());
            ps.setDate(4, c.getFechaInicio());
            ps.setDate(5, c.getFechaFin()); // puede ser null si VENTA
            ps.setString(6, c.getEstado());
            ps.setDouble(7, c.getPrecioAplicado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contrato> obtenerTodos() {
        List<Contrato> lista = new ArrayList<>();
        String sql = "SELECT * FROM CONTRATO";
        try (Connection conn = ConexionDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Contrato c = new Contrato();
                c.setIdContrato(rs.getInt("ID_CONTRATO"));
                c.setIdCliente(rs.getInt("ID_CLIENTE"));
                c.setIdAlmacen(rs.getString("ID_ALMACEN"));
                c.setTipoContrato(rs.getString("TIPO_CONTRATO"));
                c.setFechaInicio(rs.getDate("FECHA_INICIO"));
                c.setFechaFin(rs.getDate("FECHA_FIN"));
                c.setEstado(rs.getString("ESTADO"));
                c.setPrecioAplicado(rs.getDouble("PRECIO_APLICADO"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarEstado(int idContrato, String nuevoEstado) {
        String sql = "UPDATE CONTRATO SET ESTADO=? WHERE ID_CONTRATO=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idContrato);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idContrato) {
        String sql = "DELETE FROM CONTRATO WHERE ID_CONTRATO=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idContrato);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
