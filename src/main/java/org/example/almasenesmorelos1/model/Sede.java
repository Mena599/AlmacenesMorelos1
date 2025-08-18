package org.example.almasenesmorelos1.model;

import java.util.Objects;

public class Sede {

    // Identidad y datos visibles
    private String id;
    private String nombre;        // municipio / nombre visible
    private String idAdmin;       // correo/ID del admin asignado (null/"" si libre)
    private String telefono;
    private String fechaRegistro;

    // NUEVO: estado de ocupación
    private boolean ocupada;      // false = libre, true = ocupada

    // ---- Constructores ----

    // Compatibilidad con tu uso actual
    public Sede(String id, String nombre) {
        this(id, nombre, "", "", "", false);
    }

    // Constructor “clásico”
    public Sede(String id, String nombre, String idAdmin, String telefono, String fechaRegistro) {
        this(id, nombre, idAdmin, telefono, fechaRegistro, (idAdmin != null && !idAdmin.isBlank()));
    }

    // NUEVO: constructor completo incluyendo ocupada
    public Sede(String id, String nombre, String idAdmin, String telefono, String fechaRegistro, boolean ocupada) {
        this.id = id;
        this.nombre = nombre;
        this.idAdmin = idAdmin;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.ocupada = ocupada;
    }

    // ---- Getters ----
    public String getId() { return id; }
    public String getNombre() { return nombre; }           // alias de "municipio"
    public String getMunicipio() { return nombre; }        // por legibilidad
    public String getIdAdmin() { return idAdmin; }
    public String getTelefono() { return telefono; }
    public String getFechaRegistro() { return fechaRegistro; }
    public boolean isOcupada() { return ocupada; }
    public boolean isLibre() { return !ocupada; }

    // ---- Setters (necesarios para cambiar estado desde DataStore/controladores) ----
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setIdAdmin(String idAdmin) { this.idAdmin = idAdmin; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }

    @Override public String toString() { return nombre; }

    // Para evitar duplicados por id en listas observables
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sede)) return false;
        Sede sede = (Sede) o;
        return Objects.equals(id, sede.id);
    }

    @Override public int hashCode() { return Objects.hash(id); }
}
