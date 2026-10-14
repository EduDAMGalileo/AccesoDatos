package ejercicios;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class cambiarCorreo {
	public static void main (String[] args) {
		Path archivo = Path.of("alumnos.csv");
		//System.out.print(Files.exists(archivo));
		leerFichero(archivo);
		cambiarLinea(archivo, "17", "17,Mercury,Freddie,freddie_nuevo_y_mas_largo_aun_todavia@queen.com");
		leerFichero(archivo);
	}
	
	public static void cambiarLinea(Path ruta, String id, String nuevaLinea) {
		String linea;
		try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8);
			 BufferedWriter escritor= Files.newBufferedWriter(ruta, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
			while ((linea = lector.readLine())!=null) {
				if (linea.startsWith(id)){
					System.out.println("Estoy modificando el correo de Freddy");
					escritor.write(nuevaLinea);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void leerFichero(Path ruta) {
		String linea;
		try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)){
			while ((linea = lector.readLine())!=null) {
				System.out.println(linea);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
