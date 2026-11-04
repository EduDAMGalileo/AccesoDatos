package persistencia.p6XML;

import java.time.LocalDate;
import java.util.List;

public class Alumno {
	private int id;
	private String nombre;
	private LocalDate nacimiento;
	// puede ser null
	private String correo;              
	private List<Matricula> matriculas;
}
