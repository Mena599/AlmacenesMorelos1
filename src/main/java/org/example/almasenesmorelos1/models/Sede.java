package org.example.almasenesmorelos1.models;

import java.time.LocalDate;

public class Sede {
    private Long idSede;
    private String municipio;
    private Long idAdminSede;   // FK a USUARIOS.ID_USUARIO (puede ser null)
    private String telefono;
    private LocalDate fechaRegistro;

    public Sede() {}

    public Sede(Long idSede, String municipio, Long idAdminSede, String telefono, LocalDate fechaRegistro) {
        this.idSede = idSede;
        this.municipio = municipio;
        this.idAdminSede = idAdminSede;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getIdSede() { return idSede; }
    public void setIdSede(Long idSede) { this.idSede = idSede; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public Long getIdAdminSede() { return idAdminSede; }
    public void setIdAdminSede(Long idAdminSede) { this.idAdminSede = idAdminSede; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return "Sede{" +
                "idSede=" + idSede +
                ", municipio='" + municipio + '\'' +
                ", idAdminSede=" + idAdminSede +
                ", telefono='" + telefono + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
