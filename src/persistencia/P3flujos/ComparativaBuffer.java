package persistencia.P3flujos;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ComparativaBuffer {

	// Tamaño del fichero de prueba
    private static final int TAMANO_MB = 5; 

    public static void main(String[] args) {
        Path ruta = Path.of("archivo_prueba.bin");

        try {
            // Creamos el archivo de 5 MB rápidamente en bloques
            crearArchivoDePrueba(ruta, TAMANO_MB);

            System.out.println("=== INICIANDO BENCHMARK (Fichero de " + TAMANO_MB + " MB) ===");
            System.out.println("Ambas pruebas leen byte a byte llamando a read() ~5.200.000 veces.\n");

            // -------------------------------------------------------------------------
            // PRUEBA 1: Sin almacenamiento intermedio (Directo al SO/disco). A lo bruto
            // -------------------------------------------------------------------------
            System.out.println("1. Leyendo SIN almacenamiento intermedio (espera un momento...)...");
            long inicioSin = System.nanoTime();

            try (InputStream e = Files.newInputStream(ruta)) {
                while (e.read() != -1) {
                    // Leyendo byte a byte sin procesar
                }
            }

            long finSin = System.nanoTime();
            long tiempoSinMs = (finSin - inicioSin) / 1_000_000;
            System.out.printf("   -> Tiempo SIN buffer: %,d ms%n%n", tiempoSinMs);

            // ------------------------------------------------------------------------------
            // PRUEBA 2: Con almacenamiento intermedio (BufferedInputStream). Como Dios manda
            // ------------------------------------------------------------------------------
            System.out.println("2. Leyendo CON almacenamiento intermedio (BufferedInputStream)...");
            long inicioCon = System.nanoTime();

            // Solo añadimos el envoltorio BufferedInputStream
            try (InputStream e = new BufferedInputStream(Files.newInputStream(ruta))) {
                while (e.read() != -1) {
                    // Seguimos llamando a read() byte a byte exactamente igual
                }
            }

            long finCon = System.nanoTime();
            long tiempoConMs = (finCon - inicioCon) / 1_000_000;
            System.out.printf("   -> Tiempo CON buffer: %,d ms%n%n", tiempoConMs);

            // ---------------------------------------------------------------
            // RESULTADOS
            // ---------------------------------------------------------------
            System.out.println("=================================================");
            if (tiempoConMs > 0) {
                double vecesMasRapido = (double) tiempoSinMs / tiempoConMs;
                System.out.printf("¡La versión CON buffer es %.1f veces más rápida!%n", vecesMasRapido);
            }
            System.out.println("=================================================");

        } catch (IOException e) {
            System.err.println("Error durante la prueba: " + e.getMessage());
        } finally {
            // Limpieza: borramos el archivo de prueba al terminar
            try {
                Files.deleteIfExists(ruta);
            } catch (IOException ignored) {}
        }
    }

    /**
     * Utilidad para crear rápidamente un fichero de varios megabytes
     */
    private static void crearArchivoDePrueba(Path ruta, int megabytes) throws IOException {
        System.out.printf("Generando archivo temporal de %d MB...%n", megabytes);
        byte[] bloque = new byte[64 * 1024]; // Bloques de 64 KB para escribir rápido
        try (OutputStream out = Files.newOutputStream(ruta)) {
            long bytesTotales = (long) megabytes * 1024 * 1024;
            long escritos = 0;
            while (escritos < bytesTotales) {
                out.write(bloque);
                escritos += bloque.length;
            }
        }
    }
}
