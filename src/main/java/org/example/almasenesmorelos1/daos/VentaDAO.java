// daos/VentaDAO.java
package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.models.Venta;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.time.LocalDate;

public class VentaDAO {

    /**
     * Crea la venta y marca el almacén como NO disponible, todo en una transacción.
     */
    public Long crearVentaYBloquearAlmacen(Venta v) throws SQLException {
        String sqlVenta = "INSERT INTO ventas (id_cliente, id_almacen, fecha, monto) VALUES (?,?,?,?)";
        String sqlBloqueo = "UPDATE almacenes SET disponible='N' WHERE id_almacen=? AND disponible='S'";

        try (Connection cn = ConexionDB.getConexion()) {
            cn.setAutoCommit(false);
            try (PreparedStatement ps1 = cn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps2 = cn.prepareStatement(sqlBloqueo)) {

                // Insert venta
                ps1.setLong(1, v.getIdCliente());
                ps1.setLong(2, v.getIdAlmacen());
                ps1.setDate(3, Date.valueOf(v.getFecha() == null ? LocalDate.now() : v.getFecha()));
                ps1.setDouble(4, v.getMonto());
                ps1.executeUpdate();

                Long idVenta = null;
                try (ResultSet rs = ps1.getGeneratedKeys()) {
                    if (rs.next()) idVenta = rs.getLong(1);
                }

                // Bloquear almacén
                ps2.setLong(1, v.getIdAlmacen());
                int updated = ps2.executeUpdate();
                if (updated != 1) {
                    cn.rollback();
                    throw new SQLException("No se pudo bloquear el almacén (ya estaba vendido/rentado).");
                }

                cn.commit();
                return idVenta;
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }
}
