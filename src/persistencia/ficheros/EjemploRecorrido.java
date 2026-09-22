package persistencia.ficheros;

import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;
import java.io.File;

public class EjemploRecorrido {

    public static void main(String[] args) throws IOException {
        //Crear estructura
        Path raiz = Path.of("centro");
        Files.createDirectories(raiz.resolve("docs/2026"));
        Files.writeString(raiz.resolve("datos.csv"), "1,2,3");
        Files.writeString(raiz.resolve("docs/report.csv"), "4,5,6");
        
        System.out.println("Estructura creada en: " + raiz.toAbsolutePath() + "\n");
        
        // LISTADOS INMEDIATOS (Un nivel)
        listarInmediatoModerno(raiz);    // Files.list()
        listarInmediatoTradicional(raiz.toFile()); // File.list()

        // RECORRIDOS PROFUNDOS (Todos los niveles)
        recorrerProfundoModerno(raiz);   // Files.walk()
       	System.out.println(">>> Tradicional: Profundo (Recursividad con File)");
        recorrerProfundoTradicional(raiz.toFile()); // 
    }

    // MODERNO: SOLO UN NIVEL
    public static void listarInmediatoModerno(Path raiz) throws IOException {
        System.out.println(">>> Moderno: Solo un nivel (Files.list)");
        try (Stream<Path> stream = Files.list(raiz)) {
            stream.forEach(p -> System.out.println("  - " + p));
        }
    }

    // TRADICIONAL: SOLO UN NIVEL
    public static void listarInmediatoTradicional(File raiz) {
        System.out.println(">>> Tradicional: Solo un nivel (File.list)");
        String[] nombres = raiz.list();
        if (nombres != null) {
            for (String n : nombres) System.out.println("  - " + n);
        }
    }

    // MODERNO: PROFUNDO
    public static void recorrerProfundoModerno(Path raiz) throws IOException {
        System.out.println(">>> Moderno: Profundo (Files.walk)");
        try (Stream<Path> stream = Files.walk(raiz)) {
            stream.forEach(p -> System.out.println("  - " + p));
        }
    }

    // TRADICIONAL: PROFUNDO (Recursivo)
    public static void recorrerProfundoTradicional(File archivo) {
        // Nota: Esta parte se imprimirá una sola vez al inicio del método
        // En la versión tradicional, el print suele ir fuera
        System.out.println("  - " + archivo.getPath());
        if (archivo.isDirectory()) {
            File[] hijos = archivo.listFiles();
            if (hijos != null) {
                for (File hijo : hijos) recorrerProfundoTradicional(hijo);
            }
        }
    }
}
