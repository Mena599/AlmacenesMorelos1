package org.example.almasenesmorelos1.model;

import java.sql.Date;

public class Contrato {
    private int idContrato;
    private int idCliente;
    private String idAlmacen;       // <- CAMBIO: String (coincide con ALMACEN.ID_ALMACEN)
    private String tipoContrato;    // "RENTA" o "VENTA"
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private double precioAplicado;  // <- NUEVO

    // Getters y Setters
    public int getIdContrato() { return idContrato; }
    public void setIdContrato(int idContrato) { this.idContrato = idContrato; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getIdAlmacen() { return idAlmacen; }
    public void setIdAlmacen(String idAlmacen) { this.idAlmacen = idAlmacen; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getPrecioAplicado() { return precioAplicado; }
    public void setPrecioAplicado(double precioAplicado) { this.precioAplicado = precioAplicado; }
}
