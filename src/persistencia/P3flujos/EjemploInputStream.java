package persistencia.P3flujos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjemploInputStream {

    public static void main(String[] args) {
        Path rutaOrigen = Path.of("origen.txt");
        Path rutaDestino = Path.of("copia.txt");

        // Preparamos un archivo de prueba con un texto simple
        prepararArchivoPrueba(rutaOrigen);

        // Abrimos tanto el InputStream (lectura) como el OutputStream (escritura)
        try (InputStream in = Files.newInputStream(rutaOrigen);
             OutputStream out = Files.newOutputStream(rutaDestino)) {

            // Creamos un array (búfer). 
            // En proyectos reales suele ser de 4 KB u 8 KB (4096 u 8192 bytes).
            // Usamos 16 bytes aquí para forzar a que dé varias vueltas en el bucle y verlo claro:
            byte[] buffer = new byte[16];

            int bytesLeidos;
            int numBloque = 0;

            System.out.println("=== Leyendo y copiando archivo en bloques de 16 bytes ===");

            // IMPORTANTE:
            // in.read(buffer) intenta llenar el array 'buffer' y devuelve:
            // -> La CANTIDAD de bytes leídos (entre 1 y 16 en este caso).
            // -> El valor -1 cuando ya no hay más datos por leer (EOF).
            while ((bytesLeidos = in.read(buffer)) != -1) {
                numBloque++;

                // Convertimos a texto SOLO los bytes válidos de esta vuelta, usando un constructor de la clase String que no habíamos usado hasta ahora
                // Convierte a texto únicamente el tramo de bytes válidos leídos en esta iteración (de 0 a bytesLeidos),
                // evitando que el texto incluya contenido residual de lecturas anteriores que aún esté en el búfer.
                String fragmento = new String(buffer, 0, bytesLeidos, StandardCharsets.UTF_8);

                System.out.printf("Bloque #%d | Bytes leídos: %2d | Contenido: \"%s\"%n", 
                        numBloque, bytesLeidos, fragmento);

                // Escribimos en el destino ÚNICAMENTE los bytes que acabamos de leer
                out.write(buffer, 0, bytesLeidos);
            }

            System.out.println("========================================================");
            System.out.println("Copia completada con éxito en: " + rutaDestino.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error durante la operación de E/S: " + e.getMessage());
        }
    }

    private static void prepararArchivoPrueba(Path ruta) {
        try {
            // Frase de exactamente 55 bytes
            String contenido = "Aprender InputStream usando un búfer acelera la lectura.";
            Files.writeString(ruta, contenido, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("No se pudo crear el archivo: " + e.getMessage());
        }
    }
}