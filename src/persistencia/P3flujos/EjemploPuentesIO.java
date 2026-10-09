package persistencia.P3flujos;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjemploPuentesIO {

    public static void main(String[] args) {
        Path ruta = Path.of("carta.txt");
        String texto = "¡Hola desde Java! Incluye tildes, la letra ñ y el símbolo del euro: 50€.";

        // =========================================================================
        // 1. ESCRITURA: OutputStreamWriter (Caracteres -> Bytes)
        // =========================================================================
        System.out.println("=== 1. ESCRIBIENDO CON OutputStreamWriter ===");

        // Flujo: Texto String -> OutputStreamWriter (aplica UTF-8) -> OutputStream (bytes a disco)
        try (OutputStream canalBytes = Files.newOutputStream(ruta);
             Writer puenteEscritura = new OutputStreamWriter(canalBytes, StandardCharsets.UTF_8)) {

            // Le entregamos un String (caracteres).
            // OutputStreamWriter lo traduce a bytes según UTF-8 y se los da al OutputStream:
            puenteEscritura.write(texto);
            
            System.out.println("Texto escrito con éxito en: " + ruta.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al escribir: " + e.getMessage());
        }

        System.out.println("\n--------------------------------------------------\n");

        // =========================================================================
        // 2. LECTURA: InputStreamReader (Bytes -> Caracteres)
        // =========================================================================
        System.out.println("=== 2. LEYENDO CON InputStreamReader ===");

        // Flujo: Disco (bytes) -> InputStream -> InputStreamReader (traduce con UTF-8) -> Caracteres
        try (InputStream canalBytes = Files.newInputStream(ruta);
             Reader puenteLectura = new InputStreamReader(canalBytes, StandardCharsets.UTF_8)) {

            // En lugar de un byte[] buffer, aquí usamos un char[] buffer (búfer de letras)
            char[] bufferCaracteres = new char[32];
            int caracteresLeidos;
            StringBuilder contenido = new StringBuilder();

            // puenteLectura.read(char[]) devuelve la cantidad de CARACTERES leídos (o -1 si es EOF)
            while ((caracteresLeidos = puenteLectura.read(bufferCaracteres)) != -1) {
                // Añadimos solo los caracteres válidos leídos en esta vuelta
                contenido.append(bufferCaracteres, 0, caracteresLeidos);
            }

            System.out.println("Texto leído y decodificado correctamente:");
            System.out.println("\"" + contenido + "\"");

        } catch (IOException e) {
            System.err.println("Error al leer: " + e.getMessage());
        }
    }
}