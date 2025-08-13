package org.example.almasenesmorelos1.model;

public class Sede {
    private final String id;
    private final String nombre; // usaré el "municipio" como nombre visible

    public Sede(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public String getId() { return id; }
    public String getNombre() { return nombre; }

    @Override public String toString() { return nombre; } // útil para ComboBox
}
