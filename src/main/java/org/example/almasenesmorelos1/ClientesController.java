package org.example.almasenesmorelos1;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientesController {


/**
 * Clase Cliente - Modelo de datos para representar un cliente
 * Contiene todos los atributos y métodos necesarios para gestionar clientes
 */

    // Generador automático de IDs
    private static final AtomicInteger contador = new AtomicInteger(1);

    private  int id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private  LocalDate fechaRegistro;

    /**
     * Constructor para crear un nuevo cliente
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param correo Correo electrónico del cliente
     * @param telefono Número de teléfono del cliente
     * @param fechaNacimiento Fecha de nacimiento del cliente
     */
    public ClientesController(String nombre, String apellidos, String correo, String telefono, LocalDate fechaNacimiento) {
        this.id = contador.getAndIncrement();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = LocalDate.now();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Método para obtener el nombre completo del cliente
     * @return String con nombre y apellidos concatenados
     */
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    /**
     * Representación en cadena del cliente
     * @return String con información del cliente
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}