package repaso.docs;

public class Refrencias {
	public static void main (String[] args) {
		Alumno a = new Alumno();
		a.setNombre("Lucía");

		// b y a apuntan al MISMO objeto
		Alumno b = a;             
		b.setNombre("Marta");

		System.out.println(a.getNombre()); 
		
	}
}

