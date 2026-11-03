package ejercicios.bloque1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import persistencia.P1primerIntento.Alumno;

public class GestorLibros {
	
	
	public List<Libro> cargarLibros(Path rutaFichero) throws NumberFormatException, IOException{
		
		List<Libro> libros = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero.toString()))){
			//Me salto la cabecera
			br.readLine();
			String linea;
			while ((linea = br.readLine())!=null) {
				String[] campos = linea.split(",");
	            Libro a = new Libro(campos[0], campos[1], campos[2], Integer.parseInt(campos[3]));
	            libros.add(a);
			}	
		}
        
        return libros;

		
	}

}
