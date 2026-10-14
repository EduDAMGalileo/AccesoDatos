package persistencia.P2ficheros;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class EjemploMetadatos {
    public static void main(String[] args) throws IOException {
        Path p = Path.of("centro", "datos.csv");
        
        //Creamos el fichero, en caso de que no exista (ojo con la ruta)
        if (Files.notExists(p)) {
            Files.createFile(p);
            }

        // IMPORTANTE: Primero aseguramos que el archivo existe, por lo que haya podido ocurrir
        if (Files.exists(p)) {
            try {
                // Leemos todos los atributos básicos de golpe
                BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);

                System.out.println("--- Metadatos de " + p + " ---");
                System.out.println("Tamaño: " + attr.size() + " bytes");
                System.out.println("Creado el: " + attr.creationTime());
                System.out.println("Última modificación: " + attr.lastModifiedTime());
                System.out.println("Último acceso: " + attr.lastAccessTime());
                System.out.println("¿Es directorio?: " + attr.isDirectory());
                System.out.println("¿Es enlace simbólico?: " + attr.isSymbolicLink());
                System.out.println("¿Es fichero normal?: " + attr.isRegularFile());

            } catch (IOException e) {
                System.err.println("No se pudieron leer los atributos: " + e.getMessage());
            }
        } else {
            System.out.println("El archivo no existe, no puedo leer sus metadatos.");
        }
    }
}