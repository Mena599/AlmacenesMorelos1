package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.model.Contrato;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {

    public void insertar(Contrato contrato) {
        String sql = "INSERT INTO CONTRATO (ID_CLIENTE, ID_ALMACEN, TIPO_CONTRATO, FECHA_INICIO, FECHA_FIN, ESTADO) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contrato.getIdCliente());
            stmt.setInt(2, contrato.getIdAlmacen());
            stmt.setString(3, contrato.getTipoContrato());
            stmt.setDate(4, contrato.getFechaInicio());
            stmt.setDate(5, contrato.getFechaFin());
            stmt.setString(6, contrato.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contrato> obtenerTodos() {
        List<Contrato> lista = new ArrayList<>();
        String sql = "SELECT * FROM CONTRATO";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Contrato c = new Contrato();
                c.setIdContrato(rs.getInt("ID_CONTRATO"));
                c.setIdCliente(rs.getInt("ID_CLIENTE"));
                c.setIdAlmacen(rs.getInt("ID_ALMACEN"));
                c.setTipoContrato(rs.getString("TIPO_CONTRATO"));
                c.setFechaInicio(rs.getDate("FECHA_INICIO"));
                c.setFechaFin(rs.getDate("FECHA_FIN"));
                c.setEstado(rs.getString("ESTADO"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Contrato contrato) {
        String sql = "UPDATE CONTRATO SET ESTADO=? WHERE ID_CONTRATO=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contrato.getEstado());
            stmt.setInt(2, contrato.getIdContrato());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idContrato) {
        String sql = "DELETE FROM CONTRATO WHERE ID_CONTRATO=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idContrato);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
