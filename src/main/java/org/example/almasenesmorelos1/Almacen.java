package org.example.almasenesmorelos1;

public class Almacen {
    private final String id;
    private final double tamanoM2;
    private final String ubicacion;
    private final double precio;

    public Almacen(String id, double tamanoM2, String ubicacion, double precio) {
        this.id = id;
        this.tamanoM2 = tamanoM2;
        this.ubicacion = ubicacion;
        this.precio = precio;
    }

    public String getId() { return id; }
    public double getTamanoM2() { return tamanoM2; }
    public String getUbicacion() { return ubicacion; }
    public double getPrecio() { return precio; }
}
