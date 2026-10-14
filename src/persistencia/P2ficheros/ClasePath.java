package persistencia.P2ficheros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//Para documentar los apuntes, no hace nada, estamos lanzado una excepción desde el main, es decir, a la nada
public class ClasePath {
	public static void main (String [] args) throws IOException {
		Path p = Path.of("datos.csv");

		if (Files.exists(p)) {
		    System.out.println("Tamaño: " + Files.size(p));
		}

		Files.delete(p);       // si falla, LANZA una excepción con el motivo
		
	}
	



}
