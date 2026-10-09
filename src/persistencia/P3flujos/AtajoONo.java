package persistencia.P3flujos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AtajoONo {

    public static void main(String[] args) throws IOException {

        // =====================================================================
        // CASO 1: USA EL ATAJO (Files.write / Files.writeString)
        // =====================================================================
        // ¿Cuándo? 
        // Cuando ya tienes los datos en la mano (un String o una List<String> pequeña).
        // Sería absurdo montar un bucle y un try-with-resources para esto.
        System.out.println("=== CASO 1: USANDO EL ATAJO DE FILES ===");

        Path archivoAtajo = Path.of("atajo_configuracion.txt");

        // Tienes una lista en memoria con 3 canciones de rock:
        List<String> canciones = List.of(
            "Bohemian Rhapsody - Queen",
            "Smells Like Teen Spirit - Nirvana",
            "Crazy Train - Ozzy Osbourne"
        );

        // ¡UNA SOLA LÍNEA! Java se encarga de abrir, escribir con buffer y cerrar.
        Files.write(archivoAtajo, canciones);

        System.out.println("Fichero guardado en 1 línea con Files.write():");
        Files.readAllLines(archivoAtajo).forEach(linea -> System.out.println("  " + linea));


        // =====================================================================
        // CASO 2: NO USES EL ATAJO (Abre un BufferedWriter)
        // =====================================================================
        // ¿Cuándo?
        // Cuando generas datos en un bucle grande o procesas línea a línea.
        // Si metieras 500.000 líneas en una List<String> para usar el atajo,
        // saturarías la memoria RAM. Es mejor ir escribiendo poco a poco.
        System.out.println("\n=== CASO 2: NO USAR ATAJO (BufferedWriter tradicional) ===");

        Path archivoBuffer = Path.of("sensores_temperatura.log");
        int totalLecturas = 50_000; // Simulamos 50.000 lecturas de un sensor

        System.out.println("Generando " + totalLecturas + " líneas sobre la marcha sin llenar la RAM...");

        // Abrimos el BufferedWriter porque vamos a escribir paso a paso en un bucle:
        try (BufferedWriter escritor = Files.newBufferedWriter(archivoBuffer)) {

            escritor.write("timestamp,sensor_id,temperatura");
            escritor.newLine();

            for (int i = 1; i <= totalLecturas; i++) {
                double temp = 20.0 + (Math.random() * 5.0); // Temperatura aleatoria

                // Se envía al buffer de la RAM (8 KB) y de ahí al disco periódicamente
                escritor.write(System.currentTimeMillis() + ",SENSOR_" + (i % 5) + "," + String.format("%.2f", temp));
                escritor.newLine();
            }
        } // Aquí se vacía el buffer al disco y se cierra el archivo

        System.out.println("¡Listo! Fichero generado consumiendo prácticamente 0 MB de RAM.");


        // =====================================================================
        // LIMPIEZA DE FICHEROS DE PRUEBA
        // =====================================================================
        Files.deleteIfExists(archivoAtajo);
        Files.deleteIfExists(archivoBuffer);
    }
}