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



	public Alumno(int id, String nombre, LocalDate nacimiento, String correo, List<Matricula> matriculas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacimiento = nacimiento;
		this.correo = correo;
		this.matriculas = matriculas;
	}
	

	public Alumno(int id, String nombre, LocalDate nacimiento, List<Matricula> matriculas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacimiento = nacimiento;
		this.correo=null;
		this.matriculas = matriculas;
	}
	
	public Alumno() {
		// TODO Auto-generated constructor stub
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
	
	public LocalDate getNacimiento() {
		return nacimiento;
	}
	
	public void setNacimiento(LocalDate nacimiento) {
		this.nacimiento = nacimiento;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public List<Matricula> getMatriculas() {
		return matriculas;
	}
	
	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}


	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", nacimiento=" + nacimiento + ", correo=" + correo
				+ ", matriculas=" + matriculas + "]";
	}

	
}
