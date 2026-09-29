package repaso.docs;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//Clase sin mucho sentido, hay que verla acompañada de los apuntes de Acceso a Datos, concretamente en la guía inicial 1

public class AppAlumnoEncapsulado {
	
	public static void main (String[] args) {
		// Objeto mal formado: se puede crear vacío y en estado inválido
		Alumno a = new Alumno();
		a.setNombre(null);
		a.setFechaNacimiento(LocalDate.of(2150, 1, 1));   // nacerá dentro de 124 años
		
		AlumnoEncapsulado ae = new AlumnoEncapsulado("Eduardo", LocalDate.of(1984, 4, 14));
		
		//Los que no son correctos fallarán, descomentar para comprobar
		//AlumnoEncapsulado ae2 = new AlumnoEncapsulado(null, LocalDate.of(1984, 4, 14));
		AlumnoEncapsulado ae3 = new AlumnoEncapsulado("Anastasio", LocalDate.of(2036, 8, 8));
		
		
		List<Alumno> alumnos = new ArrayList<>();
		alumnos.add(new Alumno("Lucía", LocalDate.of(2006, 4, 12)));

		Alumno primero = alumnos.get(0);   // sin conversión: el compilador ya lo sabe

		for (Alumno alum : alumnos) {
		    System.out.println(alum.getNombre());
		}
		
		// MAL: lanza ConcurrentModificationException
		for (Alumno al : alumnos) {
		    if (al.edad() < 16) {
		        alumnos.remove(al);
		        
		    }
		}
		
		// Bien: usando el método de la colección
		alumnos.removeIf(alumn -> alumn.edad() < 16);
		
		
		List<String> nombresMayores = alumnos.stream()
			    .filter(mayor -> mayor.edad() >= 18)
			    .map(Alumno::getNombre)
			    .sorted()
			    .toList();

		//El equivalente en POO
		List<String> nombres = new ArrayList<>();

		for (Alumno amayor : alumnos) {
		    if (amayor.edad() >= 18) {
		        nombres.add(amayor.getNombre());
		    }
		}

		
	}
	
	// Acepta List<Alumno>, List<String>, List<lo que sea>
	// Pero NO permite añadir nada, porque no se sabe de qué tipo es
	
	private static Alumno buscarPorId(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public void imprimirTodos(List<?> lista) {
	    for (Object o : lista) {
	        System.out.println(o);
	    }
	}



}
