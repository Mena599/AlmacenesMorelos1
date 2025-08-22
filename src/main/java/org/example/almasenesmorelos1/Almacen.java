package org.example.almasenesmorelos1;

/**
 * Modelo de datos para un almacén.
 * Ahora soporta dos precios: venta y renta.
 */
public class Almacen {
    private final String id;            // Identificador único del almacén (ej. ALM-001)
    private final double tamanoM2;      // Tamaño en metros cuadrados
    private final String ubicacion;     // Dirección o ubicación del almacén
    private final String sedeId;        // ID de la sede propietaria del almacén

    // NUEVO
    private final double precioVenta;   // Precio de venta
    private final double precioRenta;   // Precio de renta

    // --- Constructor principal (NUEVO) ---
    public Almacen(String id, double tamanoM2, String ubicacion, String sedeId,
                   double precioVenta, double precioRenta) {
        this.id = id;
        this.tamanoM2 = tamanoM2;
        this.ubicacion = ubicacion;
        this.sedeId = sedeId;
        this.precioVenta = precioVenta;
        this.precioRenta = precioRenta;
    }

    // --- Getters ---
    public String getId() { return id; }
    public double getTamanoM2() { return tamanoM2; }
    public String getUbicacion() { return ubicacion; }
    public String getSedeId() { return sedeId; }

    public double getPrecioVenta() { return precioVenta; }
    public double getPrecioRenta() { return precioRenta; }

    /**
     * Compatibilidad hacia atrás:
     * Si alguna parte antigua del código usa getPrecio(),
     * devolvemos el precio de RENTA por defecto.
     */
    @Deprecated
    public double getPrecio() { return precioRenta; }
}
