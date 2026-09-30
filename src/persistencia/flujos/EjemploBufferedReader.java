package persistencia.flujos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjemploBufferedReader {

    public static void main(String[] args) {
        Path ruta = Path.of("documento.txt");

        //  Creamos un archivo de prueba con texto que contenga caracteres especiales
        prepararArchivoPrueba(ruta);

        //  Bloque try-with-resources: abre el BufferedReader y lo cierra automáticamente
        // Usamos StandardCharsets.UTF_8 para decodificar los bytes a caracteres correctamente
        // Prueba a cambiar por StandardCharsets.ISO_8859_1
        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {

            String linea;
            int contadorLineas = 0;

            System.out.println("=== Inicio de lectura línea a línea (orientada a caracteres) ===");

            // readLine() devuelve:
            // -> Un String con el contenido de la línea (sin incluir el salto de línea \n o \r\n).
            // -> El valor 'null' cuando llega al fin del archivo (EOF).
            while ((linea = lector.readLine()) != null) {
                contadorLineas++;
                procesar(contadorLineas, linea);
            }

            System.out.println("===============================================================");
            System.out.println("Lectura finalizada. Líneas totales leídas: " + contadorLineas);

        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Simulación del método 'procesar(linea)'
     */
    private static void procesar(int numLinea, String linea) {
        // Mostramos el número de línea, la cantidad de caracteres y el texto procesado
        System.out.printf("Línea #%d [%2d caracteres] -> %s%n", numLinea, linea.length(), linea);
    }

    /**
     * Utilidad para crear el fichero de ejemplo si no existe en disco
     */
    private static void prepararArchivoPrueba(Path ruta) {
        if (!Files.exists(ruta)) {
            try {
                String contenido = 
                    "En un lugar de la Mancha,\n" +
                    "de cuyo nombre no quiero acordarme...\n" +
                    "Comprobando codificación UTF-8: ¡ñ, á, é, í, ó, ú!\n" +
                    "Última línea del archivo.";

                Files.writeString(ruta, contenido, StandardCharsets.UTF_8);
                System.out.println("Archivo de prueba creado en: " + ruta.toAbsolutePath() + "\n");
            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo de prueba: " + e.getMessage());
            }
        }
    }
}
