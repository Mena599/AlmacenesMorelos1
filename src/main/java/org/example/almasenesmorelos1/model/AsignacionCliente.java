package org.example.almasenesmorelos1.model;

import java.time.LocalDate;

public class AsignacionCliente {

    // Campos existentes
    private final String idAlmacen;
    private final String nombreCliente;
    private final String correo;
    private final String telefono;
    private final EstatusOperacion estatus;   // VENTA o RENTA
    private final LocalDate fechaAdquisicion;
    private final LocalDate fechaExpiracion;

    // NUEVO: precioAplicado (opcional)
    private final Double precioAplicado; // deja Double para poder ser null si usas el constructor antiguo

    // Constructor original (compatibilidad)
    public AsignacionCliente(
            String idAlmacen,
            String nombreCliente,
            String correo,
            String telefono,
            EstatusOperacion estatus,
            LocalDate fechaAdquisicion,
            LocalDate fechaExpiracion
    ) {
        this(idAlmacen, nombreCliente, correo, telefono, estatus, fechaAdquisicion, fechaExpiracion, null);
    }

    // Constructor nuevo con precioAplicado
    public AsignacionCliente(
            String idAlmacen,
            String nombreCliente,
            String correo,
            String telefono,
            EstatusOperacion estatus,
            LocalDate fechaAdquisicion,
            LocalDate fechaExpiracion,
            Double precioAplicado
    ) {
        this.idAlmacen = idAlmacen;
        this.nombreCliente = nombreCliente;
        this.correo = correo;
        this.telefono = telefono;
        this.estatus = estatus;
        this.fechaAdquisicion = fechaAdquisicion;
        this.fechaExpiracion = fechaExpiracion;
        this.precioAplicado = precioAplicado;
    }

    // Getters
    public String getIdAlmacen() { return idAlmacen; }
    public String getNombreCliente() { return nombreCliente; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public EstatusOperacion getEstatus() { return estatus; }
    public LocalDate getFechaAdquisicion() { return fechaAdquisicion; }
    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public Double getPrecioAplicado() { return precioAplicado; }
}
