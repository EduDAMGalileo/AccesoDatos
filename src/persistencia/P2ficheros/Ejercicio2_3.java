package persistencia.P2ficheros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class Ejercicio2_3 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Dime una ruta o fichero y te digo si existe y alguna cosa más");
		Path ruta = Path.of(sc.nextLine());
		
		if (Files.exists(ruta)) {
			BasicFileAttributes attr = Files.readAttributes(ruta, BasicFileAttributes.class);
			System.out.println ("El fichero existe");
			System.out.println( "Y es un " + (attr.isDirectory() ? "Directorio" : "Fichero"));
			System.out.println( "Y su última fecha de modificaión es:  " + attr.lastModifiedTime());
			//Y seguimos...
			
		} else {
			System.out.println ("El fichero no existe");
		}
		
		

	}

}
