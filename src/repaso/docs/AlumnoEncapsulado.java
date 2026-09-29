package repaso.docs;

import java.time.LocalDate;

public class AlumnoEncapsulado {
	
    private int id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;

	
	public AlumnoEncapsulado(String nombre, LocalDate fechaNacimiento) {
	    if (nombre == null || nombre.isBlank()) {
	        throw new IllegalArgumentException("El nombre es obligatorio");
	    }
	    if (fechaNacimiento.isAfter(LocalDate.now())) {
	        throw new IllegalArgumentException("Fecha de nacimiento en el futuro");
	    }
	    this.nombre = nombre;
	    this.fechaNacimiento = fechaNacimiento;
	}


}
