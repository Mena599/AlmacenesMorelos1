package org.example.almasenesmorelos1.models;

public class Usuario {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String usuario;
    private String passwordHash; // corresponde a columna CONTRASEÑA en BD
    private String rol;          // SUPERADMIN | ADMIN_SEDE | CLIENTE

    public Usuario() {}

    public Usuario(Long idUsuario, String nombre, String apellido, String correo,
                   String usuario, String passwordHash, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.usuario = usuario;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", usuario='" + usuario + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
