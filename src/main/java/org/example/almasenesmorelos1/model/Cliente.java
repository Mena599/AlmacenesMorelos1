package org.example.almasenesmorelos1.model;

public class Cliente {

    private int idCliente;          // ID_CLIENTE
    private String nombreCompleto;  // NOMBRE_COMPLETO
    private String telefono;        // TELEFONO
    private String correo;          // CORREO

    public Cliente() { }

    public Cliente(int idCliente, String nombreCompleto, String telefono, String correo) {
        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Cliente(String nombreCompleto, String telefono, String correo) {
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters & Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    @Override
    public String toString() {
        return nombreCompleto + " (" + correo + ")";
    }
}
