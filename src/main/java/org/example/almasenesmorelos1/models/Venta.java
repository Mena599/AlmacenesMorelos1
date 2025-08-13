package org.example.almasenesmorelos1.models;

import java.time.LocalDate;

public class Venta {
    private Long idVenta;
    private Long idCliente;
    private Long idAlmacen;
    private LocalDate fecha;
    private Double monto;

    public Venta() {}

    public Venta(Long idVenta, Long idCliente, Long idAlmacen, LocalDate fecha, Double monto) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idAlmacen = idAlmacen;
        this.fecha = fecha;
        this.monto = monto;
    }

    public Long getIdVenta() { return idVenta; }
    public void setIdVenta(Long idVenta) { this.idVenta = idVenta; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Long getIdAlmacen() { return idAlmacen; }
    public void setIdAlmacen(Long idAlmacen) { this.idAlmacen = idAlmacen; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", idCliente=" + idCliente +
                ", idAlmacen=" + idAlmacen +
                ", fecha=" + fecha +
                ", monto=" + monto +
                '}';
    }
}
