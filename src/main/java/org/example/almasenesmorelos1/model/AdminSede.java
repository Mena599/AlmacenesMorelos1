package org.example.almasenesmorelos1.model;

public class AdminSede {
    private String nombre;
    private String correo;
    private String telefono;

    // NUEVO
    private String username;
    private String password;
    private String sedeId;

    public AdminSede(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public AdminSede(String nombre, String correo, String telefono, String sedeId, String username) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.sedeId = sedeId;
        this.username = username;
        this.password = username; // demo: password = username
    }

    // getters
    public String getNombre()   { return nombre; }
    public String getCorreo()   { return correo; }
    public String getTelefono() { return telefono; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSedeId()   { return sedeId; }

    // setters
    public void setSedeId(String sedeId)   { this.sedeId = sedeId; }
    public void setUsername(String u)      { this.username = u; }
    public void setPassword(String p)      { this.password = p; }
}
