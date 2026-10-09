package persistencia.P3flujos;

import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class Idiomas {

    public static void main(String[] args) {
        // ---------------------------------------------------------------------
        // LO QUE VE JAVA: Caracteres puros en memoria (sin codificación)
        // ---------------------------------------------------------------------
        String s = "Ñuño";

        System.out.println("=== DENTRO DE JAVA ===");
        System.out.println("Texto en variable String: \"" + s + "\"");
        System.out.println("Longitud según Java:      " + s.length() + " caracteres");
        System.out.println("----------------------------------------------------\n");

        // ---------------------------------------------------------------------
        // LA FRONTERA DE SALIDA: Convertir el texto a bytes
        // ---------------------------------------------------------------------
        byte[] enUtf8  = s.getBytes(StandardCharsets.UTF_8);
        byte[] enLatin = s.getBytes(StandardCharsets.ISO_8859_1);

        HexFormat hex = HexFormat.ofDelimiter(" ").withUpperCase();

        System.out.println("=== CRUZANDO LA FRONTERA A BYTES (Serialización) ===");
        System.out.printf("1. En UTF-8      (%d bytes): [ %s ]%n", enUtf8.length, hex.formatHex(enUtf8));
        System.out.println("   -> 'Ñ' y 'ñ' consumen 2 bytes cada una (C3 91 y C3 B1).");

        System.out.printf("2. En ISO-8859-1 (%d bytes): [ %s ]%n", enLatin.length, hex.formatHex(enLatin));
        System.out.println("   -> Cada letra consume exactamente 1 byte (D1, 75, F1, 6F).");
        System.out.println("----------------------------------------------------\n");

        // ---------------------------------------------------------------------
        // LA FRONTERA DE ENTRADA: Convertir bytes de vuelta a String
        // ---------------------------------------------------------------------
        System.out.println("=== CRUZANDO LA FRONTERA DE REGRESO (Decodificación) ===");

        // CASO CORRECTO: Usar la misma tabla con la que se guardó
        System.out.println("A) LECTURAS CORRECTAS (misma tabla):");
        System.out.println("   Bytes UTF-8 leídos con UTF-8:        \"" 
                + new String(enUtf8, StandardCharsets.UTF_8) + "\"");
        System.out.println("   Bytes Latin-1 leídos con ISO-8859-1:  \"" 
                + new String(enLatin, StandardCharsets.ISO_8859_1) + "\"");

        System.out.println();

        // CASO INCORRECTO: El error silencioso (Mojibake)
        System.out.println("B) LECTURAS CON LA TABLA EQUIVOCADA (nadie da error):");

        // Error 1: Guardado en UTF-8 (6 bytes) y leído como ISO-8859-1
        String error1 = new String(enUtf8, StandardCharsets.ISO_8859_1);
        System.out.println("   Bytes UTF-8 leídos con ISO-8859-1:   \"" + error1 + "\"");
        System.out.println("   -> Longitud resultante: " + error1.length() + " caracteres (los 6 bytes pasaron a ser 6 letras)");

        // Error 2: Guardado en ISO-8859-1 (4 bytes) y leído como UTF-8
        String error2 = new String(enLatin, StandardCharsets.UTF_8);
        System.out.println("   Bytes Latin-1 leídos con UTF-8:      \"" + error2 + "\"");
        System.out.println("   -> UTF-8 no reconoce los bytes D1 y F1 sueltos y pone el símbolo de reemplazo ()");

        System.out.println("----------------------------------------------------");
        System.out.println("CONCLUSIÓN: El ordenador no avisa con ninguna excepción.");
        System.out.println("Simplemente interpreta los números con el diccionario equivocado.");
    }
}