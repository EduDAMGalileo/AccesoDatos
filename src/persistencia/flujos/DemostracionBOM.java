package persistencia.flujos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DemostracionBOM {

    public static void main(String[] args) {
        Path rutaCsv = Path.of("usuarios_con_bom.csv");

        try {
            //Fabricamos un archivo CSV con la marca BOM al principio (como hace Excel o el Bloc de Notas)
            crearCsvConBom(rutaCsv);

            // 2. Leemos la primera línea del CSV
            try (BufferedReader lector = Files.newBufferedReader(rutaCsv, StandardCharsets.UTF_8)) {
                String primeraLinea = lector.readLine();
                String[] columnas = primeraLinea.split(",");

                String primeraColumna = columnas[0]; // Debería ser "id"
                String esperado = "id";

                // -------------------------------------------------------------
                // EL MISTERIO: Comparación visual vs Comparación real
                // -------------------------------------------------------------
                System.out.println("=== 1. EL FALSO POSITIVO (EL MISTERIO) ===");
                System.out.println("Texto leído de la primera columna: \"" + primeraColumna + "\"");
                System.out.println("Texto que esperamos:               \"" + esperado + "\"");
                
                System.out.println("\n¿Son iguales según Java (.equals)? -> " 
                        + primeraColumna.equals(esperado)); // ¡FALSO!

                // -------------------------------------------------------------
                // EL DIAGNÓSTICO: Mirar las longitudes y los códigos
                // -------------------------------------------------------------
                System.out.println("\n=== 2. EL DIAGNÓSTICO (DESTAPANDO AL FANTASMA) ===");
                System.out.printf("Longitud de \"%s\" esperada:  %d caracteres%n", esperado, esperado.length());
                System.out.printf("Longitud de \"%s\" leída:     %d caracteres%n", primeraColumna, primeraColumna.length());

                // Inspeccionamos el primer carácter invisible
                char fantasma = primeraColumna.charAt(0);
                System.out.printf("El primer carácter es invisible: Código decimal %d (Unicode: \\u%04X)%n", 
                        (int) fantasma, (int) fantasma);

                // -------------------------------------------------------------
                // LA SOLUCIÓN: Limpieza de la marca BOM (\uFEFF)
                // -------------------------------------------------------------
                System.out.println("\n=== 3. LA SOLUCIÓN ===");
                String columnaLimpia = limpiarBOM(primeraColumna);

                System.out.println("Texto tras limpiar: \"" + columnaLimpia + "\"");
                System.out.println("Longitud tras limpiar: " + columnaLimpia.length());
                System.out.println("¿Son iguales ahora? -> " + columnaLimpia.equals(esperado)); // ¡VERDADERO!
            }

        } catch (IOException e) {
            System.err.println("Error de archivo: " + e.getMessage());
        } finally {
            //Limpieza
            try {
                Files.deleteIfExists(rutaCsv);
            } catch (IOException ignored) {}
            
        }
    }

    /**
     * Elimina el carácter BOM (\uFEFF) si se encuentra al principio de la cadena
     */
    private static String limpiarBOM(String texto) {
        if (texto != null && !texto.isEmpty() && texto.charAt(0) == '\uFEFF') {
            return texto.substring(1); // Quita el primer carácter y se queda con el resto
        }
        return texto;
    }

    /**
     * Utilidad para escribir manualmente los 3 bytes del BOM UTF-8 (EF BB BF)
     * seguidos del contenido del CSV.
     */
    
    //No os preocupeis por no entender esto... aún
    private static void crearCsvConBom(Path ruta) throws IOException {
        byte[] bytesBOM = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        byte[] textoCsv = "id,nombre,email\n1,Carlos,carlos@email.com".getBytes(StandardCharsets.UTF_8);

        // Juntamos el BOM + el texto en un único array
        byte[] archivoCompleto = new byte[bytesBOM.length + textoCsv.length];
        System.arraycopy(bytesBOM, 0, archivoCompleto, 0, bytesBOM.length);
        System.arraycopy(textoCsv, 0, archivoCompleto, bytesBOM.length, textoCsv.length);

        Files.write(ruta, archivoCompleto);
        System.out.println("Archivo generado con BOM en los primeros 3 bytes.\n");
    }
}
