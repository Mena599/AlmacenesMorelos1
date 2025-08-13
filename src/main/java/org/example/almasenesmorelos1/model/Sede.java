package org.example.almasenesmorelos1.model;

import java.util.Objects;

public class Sede {
    // Mantengo "nombre" como el visible (municipio)
    private String id;
    private String nombre;
    private String idAdmin;
    private String telefono;
    private String fechaRegistro;

    // Constructor antiguo (compatibilidad): si no tienes los demás datos aún
    public Sede(String id, String nombre) {
        this(id, nombre, "", "", "");
    }

    // Constructor completo (para registrar y repintar)
    public Sede(String id, String nombre, String idAdmin, String telefono, String fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.idAdmin = idAdmin;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }        // alias de "municipio"
    public String getIdAdmin() { return idAdmin; }
    public String getTelefono() { return telefono; }
    public String getFechaRegistro() { return fechaRegistro; }

    // (Opcional) alias por legibilidad, si prefieres llamar "municipio"
    public String getMunicipio() { return nombre; }

    @Override public String toString() { return nombre; }

    // Para evitar duplicados por id en listas (opcional, útil en DataStore.agregarSede)
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sede)) return false;
        Sede sede = (Sede) o;
        return Objects.equals(id, sede.id);
    }

    @Override public int hashCode() { return Objects.hash(id); }
}
