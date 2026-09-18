package repaso;

import java.time.LocalDate;
import java.time.Period;

public class Alumno {

    // Estas cuatro variables son el estado.
    // Lo que se guarda en el fichero o en la tabla es esto.
    private int id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
   
    // Composición: Alumno TIENE UNA dirección, no ES UNA dirección
    private Direccion direccion;

    
   // Este método es comportamiento. No se persiste: se recalcula.
    public int edad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

	public Alumno() {
		
	}

	public Alumno(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Alumno(String nombre, LocalDate fechaNacimiento) {
		this(nombre);
		this.fechaNacimiento=fechaNacimiento;
		
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
    public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumno otro)) return false;
        return id == otro.id;
    }
    
    /*
     El segundo if es lo mismo que:
     if (!(o instanceof Alumno)) {
		    return false;
		}
		
		Alumno otro = (Alumno) o;
		return id == otro.id;
	
     */

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }



}
