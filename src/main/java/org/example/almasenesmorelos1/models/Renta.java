package org.example.almasenesmorelos1.models;

import java.time.LocalDate;

public class Renta {
    private Long idRenta;
    private Long idCliente;
    private Long idAlmacen;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double monto;

    public Renta() {}

    public Renta(Long idRenta, Long idCliente, Long idAlmacen,
                 LocalDate fechaInicio, LocalDate fechaFin, Double monto) {
        this.idRenta = idRenta;
        this.idCliente = idCliente;
        this.idAlmacen = idAlmacen;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.monto = monto;
    }

    public Long getIdRenta() { return idRenta; }
    public void setIdRenta(Long idRenta) { this.idRenta = idRenta; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Long getIdAlmacen() { return idAlmacen; }
    public void setIdAlmacen(Long idAlmacen) { this.idAlmacen = idAlmacen; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    @Override
    public String toString() {
        return "Renta{" +
                "idRenta=" + idRenta +
                ", idCliente=" + idCliente +
                ", idAlmacen=" + idAlmacen +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", monto=" + monto +
                '}';
    }
}
