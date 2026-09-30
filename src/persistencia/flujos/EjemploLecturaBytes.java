package persistencia.flujos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjemploLecturaBytes {

    public static void main(String[] args) {
        Path ruta = Path.of("foto.jpg");

        // Creamos un archivo de prueba con bytes arbitrarios si no existe
        crearArchivoSiNoExiste(ruta);

        // Bloque try-with-resources: asegura el cierre del InputStream automáticamente
        try (InputStream entrada = Files.newInputStream(ruta)) {
            int b;
            long total = 0;

            System.out.println("--- Leyendo los primeros bytes (rango 0 a 255) ---");

            // entrada.read() lee un byte y devuelve:
            // - Un entero entre 0 y 255 si se leyó un byte.
            // - El valor -1 si se alcanzó el fin del archivo (EOF).
            while ((b = entrada.read()) != -1) {
                total++;
                System.out.printf("Byte #%d -> Valor int: %3d | Hex: 0x%02X%n", total, b, b);
            }

            System.out.println("-------------------------------------------------");
            System.out.println("Fin del archivo alcanzado (read() devolvió -1).");
            System.out.println("Bytes leídos en total: " + total);

        } catch (IOException e) {
            System.err.println("Error de E/S al procesar el archivo: " + e.getMessage());
        }
        
        System.out.println("--- Ahora leemos como byte, sin pasarlo a int ---");
        
        try (InputStream entrada = Files.newInputStream(ruta)) {
            byte by;
            long total = 0;

            System.out.println("--- Leyendo los primeros bytes (rango 0 a 255) ---");

            // entrada.read() lee un byte y devuelve:
            // - El valor -1 se acanza con el byte 255, no con el final de fichero.
            while ((by = (byte) entrada.read()) != -1) {
                total++;
                System.out.printf("Byte #%d -> Valor byte: %3d | Hex: 0x%02X%n", total, by, by);
            }

            System.out.println("-------------------------------------------------");
            System.out.println("Fin del archivo alcanzado (read() devolvió -1).");
            System.out.println("Bytes leídos en total: " + total);

        } catch (IOException e) {
            System.err.println("Error de E/S al procesar el archivo: " + e.getMessage());
        }
    }
    
  

    /**
     * Utilidad para crear un fichero de prueba si no existe,
     * conteniendo valores límite como 0, 128 o 255.
     */
    private static void crearArchivoSiNoExiste(Path ruta) {
        if (!Files.exists(ruta)) {
            try {
                // Forzamos bytes con valores que superan el rango con signo estándar de Java
            	// El tipo 'byte' en Java solo abarca de -128 a 127.
            	// Usamos el cast (byte) para valores > 127: se truncan a 8 bits (ej. 255 pasa a ser -1 en memoria).
            	// Al leerlo luego con read(), se recuperará como el 'int' sin signo 255, no como fin de fichero.
                byte[] contenido = new byte[]{0, 65, 127, (byte) 128, (byte) 266, (byte) 255, 42, 12};
                Files.write(ruta, contenido);
                System.out.println("Archivo de prueba creado en: " + ruta.toAbsolutePath() + "\n");
            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo de prueba: " + e.getMessage());
            }
        }
    }
}