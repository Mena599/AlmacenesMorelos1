// src/main/java/org/example/almasenesmorelos1/models/Almacen.java
package org.example.almasenesmorelos1.models;

public class Almacen {
    private Long idAlmacen;
    private String nombre;
    private String ubicacion;
    private Integer capacidad;
    private Double precioRenta;
    private Double precioVenta;
    private boolean disponible;  // 'S'/'N' en BD
    private Long idSede;         // opcional, FK a SEDES

    public Almacen() {}

    public Almacen(Long idAlmacen, String nombre, String ubicacion, Integer capacidad,
                   Double precioRenta, Double precioVenta, boolean disponible) {
        this.idAlmacen = idAlmacen;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.precioRenta = precioRenta;
        this.precioVenta = precioVenta;
        this.disponible = disponible;
    }

    public Almacen(Long idAlmacen, String nombre, String ubicacion, Integer capacidad,
                   Double precioRenta, Double precioVenta, boolean disponible, Long idSede) {
        this(idAlmacen, nombre, ubicacion, capacidad, precioRenta, precioVenta, disponible);
        this.idSede = idSede;
    }

    public Long getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Long idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Double getPrecioRenta() {
        return precioRenta;
    }

    public void setPrecioRenta(Double precioRenta) {
        this.precioRenta = precioRenta;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Long getIdSede() {
        return idSede;
    }

    public void setIdSede(Long idSede) {
        this.idSede = idSede;
    }

    @Override
    public String toString() {
        return "Almacen{" +
                "idAlmacen=" + idAlmacen +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", capacidad=" + capacidad +
                ", precioRenta=" + precioRenta +
                ", precioVenta=" + precioVenta +
                ", disponible=" + disponible +
                ", idSede=" + idSede +
                '}';
    }
}
