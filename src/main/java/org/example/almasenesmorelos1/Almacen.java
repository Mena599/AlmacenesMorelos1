package org.example.almasenesmorelos1;

import java.util.Objects;
import java.util.UUID;

/**
 * Modelo base de Almacén (inventario interno de una sede).
 * Nota: mantenemos campos simples; si luego quieres propiedades JavaFX (StringProperty, etc.)
 * se puede migrar sin cambiar el resto de la arquitectura.
 */
public class Almacen {
    private final String id;       // ID único interno
    private String nombre;         // Nombre o alias del almacén
    private double tamanoM2;       // Tamaño en m²
    private String ubicacion;      // Dirección / zona
    private String sedeId;         // Sede a la que pertenece

    public Almacen(String nombre, double tamanoM2, String ubicacion, String sedeId) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.tamanoM2 = tamanoM2;
        this.ubicacion = ubicacion;
        this.sedeId = sedeId;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getTamanoM2() { return tamanoM2; }
    public String getUbicacion() { return ubicacion; }
    public String getSedeId() { return sedeId; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTamanoM2(double tamanoM2) { this.tamanoM2 = tamanoM2; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setSedeId(String sedeId) { this.sedeId = sedeId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Almacen)) return false;
        Almacen almacen = (Almacen) o;
        return Objects.equals(id, almacen.id);
    }

    @Override public int hashCode() { return Objects.hash(id); }
}
