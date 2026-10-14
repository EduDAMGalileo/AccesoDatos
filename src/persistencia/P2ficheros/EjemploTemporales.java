package persistencia.P2ficheros;

import java.nio.file.*;
import java.io.IOException;

public class EjemploTemporales {
    public static void main(String[] args) {
        try {
            // Crear un fichero temporal único
            // El sistema le añadirá caracteres aleatorios para que no se repita
            Path temporal = Files.createTempFile("exportacion-", ".csv");
            System.out.println("Fichero temporal creado en: " + temporal);

            // Crear un directorio temporal
            Path carpetaTemporal = Files.createTempDirectory("proceso-");
            System.out.println("Carpeta temporal creada en: " + carpetaTemporal);

            // Escribir datos en el temporal
            Files.writeString(temporal, "ID,Nombre,Fecha\n1,Edu,2026-09-21");

            // Aquí haríamos lo que hiciese falta
            System.out.println("Procesando datos...");

            // LIMPIEZA: Borrar los temporales
            // Es vital borrar lo que creas para no llenar el disco del usuario
            Files.deleteIfExists(temporal);
            Files.deleteIfExists(carpetaTemporal);
            System.out.println("Temporales eliminados.");

        } catch (IOException e) {
            System.err.println("Error trabajando con temporales: " + e.getMessage());
        }
    }
}
