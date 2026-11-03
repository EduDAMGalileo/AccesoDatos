package ejercicios.bloque1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class P1P2_LibroCSV {
	public static void main(String[] args) {
		//Libros individuales
		Libro libro1 = new Libro("aaa", "bbb", "ccc", 1956);
		
		List<Libro> libros = new ArrayList<>();
		libros.add(libro1);
		libros.add(new Libro("1984", "George Orwell", "EditorialA", 1950));
		libros.add(new Libro("1985", "George Orwell", "EditorialA", 1952));
		
		try (BufferedWriter escritor = new BufferedWriter (new FileWriter("libros.csv"))){
			escritor.write("titulo, autor, editorial, ano_publicacion\n");
			for (Libro l: libros) {
				escritor.write(l.toCSV() + "\n");
			}
			
		} catch (IOException e) {
			//La dejo tal cual
			e.printStackTrace();
		}
		
	}
	

}
