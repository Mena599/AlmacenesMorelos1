// daos/RentaDAO.java
package org.example.almasenesmorelos1.daos;

import org.example.almasenesmorelos1.models.Renta;
import org.example.almasenesmorelos1.utils.ConexionDB;

import java.sql.*;
import java.time.LocalDate;

public class RentaDAO {

    /**
     * Crea la renta y marca el almacén como NO disponible, en una transacción.
     */
    public Long crearRentaYBloquearAlmacen(Renta r) throws SQLException {
        String sqlRenta = "INSERT INTO rentas (id_cliente, id_almacen, fecha_inicio, fecha_fin, monto) VALUES (?,?,?,?,?)";
        String sqlBloqueo = "UPDATE almacenes SET disponible='N' WHERE id_almacen=? AND disponible='S'";

        try (Connection cn = ConexionDB.getConnection()) {
            cn.setAutoCommit(false);
            try (PreparedStatement ps1 = cn.prepareStatement(sqlRenta, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps2 = cn.prepareStatement(sqlBloqueo)) {

                ps1.setLong(1, r.getIdCliente());
                ps1.setLong(2, r.getIdAlmacen());
                ps1.setDate(3, Date.valueOf(r.getFechaInicio() == null ? LocalDate.now() : r.getFechaInicio()));
                ps1.setDate(4, Date.valueOf(r.getFechaFin() == null ? LocalDate.now().plusDays(30) : r.getFechaFin()));
                ps1.setDouble(5, r.getMonto());
                ps1.executeUpdate();

                Long idRenta = null;
                try (ResultSet rs = ps1.getGeneratedKeys()) {
                    if (rs.next()) idRenta = rs.getLong(1);
                }

                ps2.setLong(1, r.getIdAlmacen());
                int updated = ps2.executeUpdate();
                if (updated != 1) {
                    cn.rollback();
                    throw new SQLException("No se pudo bloquear el almacén (ya estaba ocupado).");
                }

                cn.commit();
                return idRenta;
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }

    /**
     * Finaliza una renta (libera el almacén).
     */
    public boolean finalizarRentaYLiberarAlmacen(Long idRenta, Long idAlmacen) throws SQLException {
        String sqlBorrarRenta = "DELETE FROM rentas WHERE id_renta=?";
        String sqlLiberar = "UPDATE almacenes SET disponible='S' WHERE id_almacen=?";

        try (Connection cn = ConexionDB.getConnection()) {
            cn.setAutoCommit(false);
            try (PreparedStatement ps1 = cn.prepareStatement(sqlBorrarRenta);
                 PreparedStatement ps2 = cn.prepareStatement(sqlLiberar)) {

                ps1.setLong(1, idRenta);
                int del = ps1.executeUpdate();

                ps2.setLong(1, idAlmacen);
                int up = ps2.executeUpdate();

                if (del == 1 && up == 1) {
                    cn.commit();
                    return true;
                } else {
                    cn.rollback();
                    return false;
                }
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }
}
