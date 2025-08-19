package org.example.almasenesmorelos1;

/**
 * Modelo de datos para un almacén.
 * Cada almacén está ligado a un ID de sede (sedeId),
 * que indica a qué administrador de sede pertenece.
 */
public class Almacen {
    private final String id;        // Identificador único del almacén (ej. ALM-001)
    private final double tamanoM2;  // Tamaño en metros cuadrados
    private final String ubicacion; // Dirección o ubicación del almacén
    private final double precio;    // Precio del almacén
    private final String sedeId;    // ID de la sede propietaria del almacén

    // --- Constructor principal ---
    public Almacen(String id, double tamanoM2, String ubicacion, String sedeId, double precio) {
        this.id = id;
        this.tamanoM2 = tamanoM2;
        this.ubicacion = ubicacion;
        this.sedeId = sedeId;
        this.precio = precio;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public double getTamanoM2() {
        return tamanoM2;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getSedeId() {
        return sedeId;
    }
}
