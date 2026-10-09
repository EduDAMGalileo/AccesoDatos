package persistencia.P3flujos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Atajos {

    public static void main(String[] args) {
        Path rutaArchivo = Path.of("tareas.txt");

        try {
            // =====================================================================
            // 1. ESCRIBIR
            // =====================================================================
            System.out.println("=== 1. ESCRIBIENDO ARCHIVOS (Atajos) ===");

            // Opción A: Escribir un String completo (crea el archivo o lo sobrescribe)
            String contenidoInicial = "Comprar café\nRevisar correo\n\nEstudiar flujos de Java\n  \nEnviar informe";
            Files.writeString(rutaArchivo, contenidoInicial);
            System.out.println("Archivo creado con Files.writeString()");

            // Opción B: Escribir una lista de líneas directamente
            List<String> lineasNuevas = List.of(
                "Tarea 1: Comprar café",
                "Tarea 2: Revisar correo",
                "",                         // Línea vacía a propósito
                "Tarea 3: Estudiar Java (¡con tildes y eñes!)",
                "   ",                      // Línea con solo espacios
                "Tarea 4: Enviar informe"
            );
            Files.write(rutaArchivo, lineasNuevas);
            System.out.println("Archivo sobrescrito con una List<String> usando Files.write()\n");

            // =====================================================================
            // 2. LEER TODO DE GOLPE (Para archivos pequeños o medianos)
            // =====================================================================
            System.out.println("=== 2. LECTURA COMPLETA EN MEMORIA ===");

            // Opción A: Leer todo el archivo como un único String
            String todoElTexto = Files.readString(rutaArchivo);
            System.out.println("--- Leído con Files.readString() ---");
            System.out.println(todoElTexto);
            System.out.println("------------------------------------");

            // Opción B: Leer todas las líneas directamente a una List<String>
            List<String> todasLasLineas = Files.readAllLines(rutaArchivo);
            System.out.printf("Leído con Files.readAllLines(): %d líneas cargadas en la lista.%n%n", 
                    todasLasLineas.size());

            // =====================================================================
            // 3. PROCESAMIENTO PEREZOSO / FLUJO (Para archivos grandes)
            // =====================================================================
            System.out.println("=== 3. LECTURA CON STREAM (Files.lines) ===");
            System.out.println("No carga el archivo entero en la RAM; va leyendo línea a línea:");

            // ¡IMPORTANTE!: Files.lines abre el archivo en el sistema operativo.
            // Es OBLIGATORIO usar try-with-resources para que cierre el archivo al terminar el Stream.
            try (Stream<String> flujoLineas = Files.lines(rutaArchivo)) {
                flujoLineas
                    .filter(linea -> !linea.isBlank())       // Descarta líneas vacías o con solo espacios
                    .map(String::toUpperCase)                // Pasa el texto a mayúsculas
                    .forEach(linea -> procesarLinea(linea)); // Procesa cada elemento
            }

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } finally {
            // Limpieza del archivo de prueba
            try {
                Files.deleteIfExists(rutaArchivo);
            } catch (IOException ignored) {}
        }
    }

    private static void procesarLinea(String linea) {
        System.out.println("  -> [PROCESADO]: " + linea);
    }
}