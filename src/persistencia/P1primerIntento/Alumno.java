package persistencia.P1primerIntento;

import java.time.LocalDate;

//Clase que representa los datos de un alumno.
public class Alumno {

    private int id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String correo;

    public Alumno(String nombre, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + apellidos
                + " | " + fechaNacimiento + " | " + correo;
    }
}
