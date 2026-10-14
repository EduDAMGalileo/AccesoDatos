package persistencia.P3flujos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LeerAño {

    public static void main(String[] args) {
        Path ruta = Path.of("palabra.txt");

        // 1. Guardamos exactamente la palabra "año" en UTF-8 (sin saltos de línea)
        try {
            Files.writeString(ruta, "año", StandardCharsets.UTF_8);
            System.out.println("Archivo 'palabra.txt' creado con el texto: \"año\"\n");
        } catch (IOException e) {
            System.err.println("Error creando archivo: " + e.getMessage());
            return;
        }

        // =========================================================================
        // FORMA 1: Con InputStream (Orientado a BYTES en crudo)
        // =========================================================================
        System.out.println("--- 1. LEYENDO CON INPUTSTREAM (ve bytes físicos) ---");
        try (InputStream in = Files.newInputStream(ruta)) {
            int b;
            int totalBytes = 0;

            // in.read() lee 1 byte físico cada vez
            while ((b = in.read()) != -1) {
                totalBytes++;
                System.out.printf("Vuelta #%d -> Byte leído: %3d | Hex: 0x%02X%n", totalBytes, b, b);
            }
            System.out.println("Total de vueltas (bytes): " + totalBytes);

        } catch (IOException e) {
            System.err.println("Error en InputStream: " + e.getMessage());
        }

        System.out.println();

        // =========================================================================
        // FORMA 2: Con Reader / BufferedReader (Orientado a CARACTERES humanos)
        // =========================================================================
        System.out.println("--- 2. LEYENDO CON READER (ve caracteres y entiende UTF-8) ---");
        try (BufferedReader reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            int c;
            int totalCaracteres = 0;

            // reader.read() lee 1 carácter Unicode cada vez (junta los bytes que hagan falta)
            while ((c = reader.read()) != -1) {
                totalCaracteres++;
                char letra = (char) c;
                System.out.printf("Vuelta #%d -> Carácter leído: '%c' | Código Unicode: %d%n",
                        totalCaracteres, letra, c);
            }
            System.out.println("Total de vueltas (caracteres): " + totalCaracteres);

        } catch (IOException e) {
            System.err.println("Error en Reader: " + e.getMessage());
        }
    }
}