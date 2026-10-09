package persistencia.P2ficheros;

import java.nio.file.Path;

public class DirectorioTrabajo {
	public static void main (String[] args) {
		//Rutas relativas, fragil
		System.out.println(Path.of("datos.csv").toAbsolutePath());
		System.out.println(System.getProperty("user.dir"));
		
		// MAL: solo funciona en Windows
		Path p1 = Path.of("C:\\Users\\lucia\\centro\\datos.csv");

		// MAL: concatenar separadores a mano
		String carpeta = "Documentos";
		String nombre = "fichero.csv";
		String ruta = carpeta + "/" + nombre;

		// BIEN: se le pasan los segmentos y él pone el separador que toque
		Path p2 = Path.of("centro", "datos", "alumnos.csv");

		
		//Bien. Usando variables del sistema, robusto, portable
		Path base = Path.of(System.getProperty("user.home"));
		Path fichero = base.resolve("centro").resolve("alumnos.csv");


	}

}
