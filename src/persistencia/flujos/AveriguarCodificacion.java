package persistencia.flujos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class AveriguarCodificacion {

    public static void main(String[] args) {
        Path ficheroUtf8  = Path.of("prueba_utf8.txt");
        Path ficheroLatin = Path.of("prueba_latin.txt");

        // Texto con caracteres especiales compatibles con ambos alfabetos (¡, ó, ñ, ü)
        String texto = "¡Atención! La cigüeña vuela en otoño: 15 euros.";

        try {
            // Creamos los dos ficheros de prueba en disco con distinta codificación
            Files.writeString(ficheroUtf8,  texto, StandardCharsets.UTF_8);
            Files.writeString(ficheroLatin, texto, StandardCharsets.ISO_8859_1);
            System.out.println("Archivos de prueba creados correctamente.\n");

            // Analizamos el archivo guardado en UTF-8
            System.out.println("==================================================");
            System.out.println("1. ANALIZANDO: prueba_utf8.txt");
            System.out.println("==================================================");
            inspeccionarCodificacion(ficheroUtf8);

            System.out.println("\n==================================================");
            System.out.println("2. ANALIZANDO: prueba_latin.txt");
            System.out.println("==================================================");
            inspeccionarCodificacion(ficheroLatin);

        } catch (IOException e) {
            System.err.println("Error general: " + e.getMessage());
        } finally {
            // Limpieza: borramos los archivos temporales al terminar
            try {
                Files.deleteIfExists(ficheroUtf8);
                Files.deleteIfExists(ficheroLatin);
            } catch (IOException ignored) {}
        }
    }

    /**
     * Valida si un archivo cumple estrictamente las reglas matemáticas de UTF-8
     */
    private static void inspeccionarCodificacion(Path ruta) {

        // Configuramos el decodificador para que NO se calle los fallos:
        // Si encuentra una secuencia que no sea UTF-8, lanzará una excepción
        CharsetDecoder decodificadorEstricto = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);

        // Usamos InputStreamReader para conectar el flujo de bytes con el decodificador estricto
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(Files.newInputStream(ruta), decodificadorEstricto))) {

            String linea = lector.readLine();
            System.out.println("Texto leído: \"" + linea + "\"");
            System.out.println("=> DIAGNÓSTICO: Es UTF-8 válido al 100%.");

        } catch (MalformedInputException e) {
            // Capturamos el momento exacto en que un byte no cumple las reglas de UTF-8
            System.out.println("=> DIAGNÓSTICO: ¡NO es UTF-8!");
            System.out.println("   Motivo: Contiene bytes que violan la gramática de UTF-8.");
            System.out.println("   Sospecha: Es un archivo en formato ISO-8859-1 o Windows-1252.");

            // Si falla como UTF-8, lo abrimos con el plan B (ISO-8859-1)
            leerComoPlanB(ruta);

        } catch (IOException e) {
            System.err.println("Error de E/S al leer: " + e.getMessage());
        }
    }

    /**
     * Plan B: abrirlo asumiendo que es ISO-8859-1
     */
    private static void leerComoPlanB(Path ruta) {
        System.out.println("\n   -> Ejecutando Plan B: Leyendo con ISO-8859-1...");
        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.ISO_8859_1)) {
            String contenido = lector.readLine();
            System.out.println("   -> Contenido recuperado con éxito: \"" + contenido + "\"");
        } catch (IOException e) {
            System.err.println("   Error al leer en Plan B: " + e.getMessage());
        }
    }
}