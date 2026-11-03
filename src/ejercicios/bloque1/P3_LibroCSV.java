package ejercicios.bloque1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class P3_LibroCSV {
	public static void main(String[] args) throws NumberFormatException, IOException {
		Path ruta = Path.of("libros.csv");
		GestorLibros gl = new GestorLibros();
		
		List<Libro> misLibros = gl.cargarLibros(ruta);
		
		for (Libro l : misLibros) {
			System.out.println(l.toString());
		}
	}
}

