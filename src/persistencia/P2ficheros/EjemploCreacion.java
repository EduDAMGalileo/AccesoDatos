package persistencia.P2ficheros;

import java.nio.file.*;
import java.io.IOException;

public class EjemploCreacion {
	//Entre ejecuciones tenemos que borrar los ficheros, la idea es jugar con el fichero
    public static void main(String[] args) {
        // Definimos las rutas
        Path carpetaRaiz = Path.of("centro");
        Path subCarpeta = carpetaRaiz.resolve("2026").resolve("exportaciones");
        Path archivoVacio = carpetaRaiz.resolve("vacio.txt");
        System.out.println("carpetaRaiz " + carpetaRaiz + 
        		"\nsubCarpeta " +  subCarpeta +
        		"\narchivoVacio " + archivoVacio + 
        		"\n----------------- \n" );

        try {
            // 1. Crear directorio con padres (createDirectories es el más seguro)
            // No falla si el directorio ya existe
            if (Files.notExists(subCarpeta)) {
                Files.createDirectories(subCarpeta);
                System.out.println("Directorio creado: " + subCarpeta);
            }

            // 2. Crear un directorio simple (createDirectory)
            // Este falla si "centro" no existiera previamente
            Path carpetaAux = Path.of("centro", "datos_adicionales");
            if (Files.notExists(carpetaAux)) {
                Files.createDirectory(carpetaAux);
                System.out.println("Directorio simple creado: " + carpetaAux);
            }

            // 3. Crear un fichero vacío
            if (Files.notExists(archivoVacio)) {
            	// Si ejecutas esto dos veces, el programa fallará si no capturamos la excepción
                Files.createFile(archivoVacio);
                //Files.createFile(archivoVacio);
                System.out.println("Fichero creado: " + archivoVacio);
            } else {
                System.out.println("El fichero ya existía, no se creó de nuevo.");
            }

        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}
