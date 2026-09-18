package persistencia;

import java.time.LocalDate;

public class Alumno {

    private String nombre;
    private LocalDate fechaNacimiento;
    private Direccion direccion;
    private Matricula matricula;

    // Constructor
    public Alumno(String nombre, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getter y setter de dirección
    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    // Método para matricular al alumno
    public void matricularEn(AccesoADatos accesoADatos) {
        // Creamos una matrícula y la asociamos al alumno
        this.matricula = new Matricula("47001");
        
        // Simulamos guardar el alumno en la base de datos
        accesoADatos.guardar(this);
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Matricula getMatricula() {
        return matricula;
    }
}
