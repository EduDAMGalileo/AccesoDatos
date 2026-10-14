package persistencia.P2ficheros;

import java.nio.file.*;
import java.io.IOException;

public class EjemploFiles {
	//Lanzamos la excepción a la nada, para leer mejor el código
	//Pero siempre tenemos que usar try catch
    public static void main(String[] args) throws IOException {
        // Creamos una ruta de prueba
        Path dir = Path.of("mi_carpeta");
        Path archivo = dir.resolve("datos.csv");

        // Preparación: Creamos un directorio y un archivo real para hacer pruebas
        Files.createDirectories(dir);
        Files.writeString(archivo, "Hola Mundo");

        System.out.println("--- Inspeccionando con Files ---");

        // 1. exists vs notExists
        System.out.println("¿Existe?: " + Files.exists(archivo));
        System.out.println("¿No existe?: " + Files.notExists(archivo));
        // NOTA: Si el archivo no existe, ambos pueden devolver false (es un estado desconocido)

        // 2. ¿Qué es lo que hay ahí?
        System.out.println("¿Es archivo regular?: " + Files.isRegularFile(archivo));
        System.out.println("¿Es directorio?: " + Files.isDirectory(dir));

        // 3. Permisos
        System.out.println("¿Es legible?: " + Files.isReadable(archivo));
        System.out.println("¿Es escribible?: " + Files.isWritable(archivo));

        // 4. Otros atributos
        System.out.println("¿Está oculto?: " + Files.isHidden(archivo));

        // Limpieza: Borramos lo creado para no dejar basura
        Files.delete(archivo);
        Files.delete(dir);
    }
}