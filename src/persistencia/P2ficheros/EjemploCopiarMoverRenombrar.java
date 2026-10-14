package persistencia.P2ficheros;

import java.nio.file.*;
import java.io.IOException;

public class EjemploCopiarMoverRenombrar {
    public static void main(String[] args) {
        Path origen = Path.of("centro", "datos.csv");
        Path copia = Path.of("centro", "copia.csv");
        Path renombrado = Path.of("centro", "datos_finales.csv");
        System.out.println("origen " + origen + 
        		"\ncopia " +  copia +
        		"\nrenombrado " + renombrado + 
        		"\n----------------- \n" );

        try {
            // Aseguramos que el origen exista para el ejemplo
            if (Files.notExists(origen)) {
                Files.createDirectories(origen.getParent());
                Files.createFile(origen);
            }

            // 1. COPIAR
            // REPLACE_EXISTING: Sobrescribe si ya existe. 
            // COPY_ATTRIBUTES: Intenta mantener la fecha de creación/modificación.
            Files.copy(origen, copia, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo copiado con éxito.");

            // 2. MOVER / RENOMBRAR
            // Mover y renombrar es, para Java, la misma operación: 'move'
            // Si el destino está en la misma carpeta, es un renombrado.
            // Si el destino está en otra carpeta, es un movimiento.
            Files.move(copia, renombrado, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo renombrado con éxito.");

            // 3. ELIMINAR
            // deleteIfExists es más seguro que delete, porque no lanza excepción si no existe
            Files.deleteIfExists(origen);
            System.out.println("Archivo original eliminado.");

        } catch (IOException e) {
            System.err.println("Error en la operación: " + e.getMessage());
        }
    }
}