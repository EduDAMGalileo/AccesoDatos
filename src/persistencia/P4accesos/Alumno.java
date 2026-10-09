package persistencia.P4accesos;

import java.time.LocalDate;

public class Alumno {

    private final int id;
    private final String nia;
    private final String nombre;
    private final String apellidos;
    private final LocalDate fechaNacimiento;
    private final String correo;

    public Alumno(int id, String nia, String nombre, String apellidos, LocalDate fechaNacimiento, String correo) {
        this.id = id;
        this.nia = nia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
    }

    public int getId() { 
    	return id; 
    	}
    
    public String getNia() { 
    	return nia; 
    	}
    
    public String getNombre() { 
    	return nombre; 
    	}
    
    public String getApellidos() { 
    	return apellidos; 
    	}
    
    public LocalDate getFechaNacimiento() { 
    	return fechaNacimiento; 
    	}
    
    public String getCorreo() { 
    	return correo; 
    	}

    @Override
    public String toString() {
        return String.format("Alumno[ID=%d, NIA='%s', Nombre='%s %s', Fecha=%s, Correo='%s']",
                id, nia, nombre, apellidos, fechaNacimiento, correo);
    }
}