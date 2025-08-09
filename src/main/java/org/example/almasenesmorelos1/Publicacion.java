package org.example.almasenesmorelos1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Publicación que “expone” un almacén para VENTA o RENTA al cliente final.
 * Contiene el Almacén y la info pública (precio, tipo, fecha).
 */
public class Publicacion {
    private final String id;
    private final Almacen almacen;          // Referencia directa al almacén
    private final TipoPublicacion tipo;     // VENTA o RENTA
    private final BigDecimal precio;        // Precio de venta o renta mensual
    private final LocalDateTime fechaAlta;  // Cuándo se publicó
    private boolean activa = true;          // Por si luego quieres desactivarla

    public Publicacion(Almacen almacen, TipoPublicacion tipo, BigDecimal precio) {
        this.id = UUID.randomUUID().toString();
        this.almacen = almacen;
        this.tipo = tipo;
        this.precio = precio;
        this.fechaAlta = LocalDateTime.now();
    }

    public String getId() { return id; }
    public Almacen getAlmacen() { return almacen; }
    public TipoPublicacion getTipo() { return tipo; }
    public BigDecimal getPrecio() { return precio; }
    public LocalDateTime getFechaAlta() { return fechaAlta; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publicacion)) return false;
        Publicacion that = (Publicacion) o;
        return Objects.equals(id, that.id);
    }

    @Override public int hashCode() { return Objects.hash(id); }
}
