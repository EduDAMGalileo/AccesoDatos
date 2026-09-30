package persistencia.flujos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CerrarFicheros {

    public static void main(String[] args) {
        Path rutaMala = Path.of("perdida_de_datos.txt");
        Path rutaBuena = Path.of("datos_salvados.txt");

        String datoCritico = "DATO CRÍTICO: Transacción bancaria de 50.000€ realizada con éxito.";

        // =====================================================================
        // CASO 1: EL ERROR (Sin try-with-resources)
        // =====================================================================
        System.out.println("=== 1. CASO INCORRECTO: Cierre manual interrumpido por error ===");
        try {
            BufferedWriter w = Files.newBufferedWriter(rutaMala);

            // Escribimos en el búfer (se queda en la memoria RAM, NO en el disco todavía)
            w.write(datoCritico);

            // Simulamos una catástrofe antes del close (cálculo que falla, corte de red, etc.)
            simularFalloInesperado();

            // ¡PELIGRO! Esta línea NUNCA se llegará a ejecutar por culpa de la excepción
            w.close();

        } catch (Exception e) {
            System.out.println("-> Saltó un error: " + e.getMessage());
        }

        // Comprobamos qué ha quedado grabado físicamente en el disco
        inspeccionarArchivo(rutaMala);

        System.out.println("\n------------------------------------------------------------\n");

        // =====================================================================
        // CASO 2: LA FORMA SEGURA (Con try-with-resources)
        // =====================================================================
        System.out.println("=== 2. CASO CORRECTO: Con try-with-resources ===");
        try (BufferedWriter w = Files.newBufferedWriter(rutaBuena)) {

            // Escribimos exactamente lo mismo en el búfer
            w.write(datoCritico);

            // Provocamos exactamente el mismo fallo inesperado
            simularFalloInesperado();

        } catch (Exception e) {
            // ¡MAGIA!: Aunque saltó el error, Java ejecutó automáticamente w.close(),
            // lo que forzó a vaciar (flush) el búfer hacia el disco antes de entrar aquí.
            System.out.println("-> Saltó el mismo error: " + e.getMessage());
        }

        // Comprobamos el disco de nuevo
        inspeccionarArchivo(rutaBuena);

        // Limpieza de archivos de prueba
        limpiarArchivos(rutaMala, rutaBuena);
    }

    private static void simularFalloInesperado() {
        throw new RuntimeException("¡Error imprevisto en mitad del proceso!");
    }

    private static void inspeccionarArchivo(Path ruta) {
        try {
            long tamano = Files.size(ruta);
            String contenido = Files.readString(ruta);

            System.out.println("--- Inspeccionando: " + ruta.getFileName() + " ---");
            System.out.println("Tamaño físico en disco: " + tamano + " bytes");
            
            if (contenido.isEmpty()) {
                System.out.println("Contenido: [¡TOTALMENTE VACÍO! Los datos se perdieron en la RAM]");
            } else {
                System.out.println("Contenido: \"" + contenido + "\" [¡DATOS GUARDADOS A SALVO!]");
            }
        } catch (IOException e) {
            System.err.println("Error leyendo: " + e.getMessage());
        }
    }

    private static void limpiarArchivos(Path... rutas) {
        for (Path ruta : rutas) {
            try {
                Files.deleteIfExists(ruta);
            } catch (IOException ignored) {}
        }
    }
}