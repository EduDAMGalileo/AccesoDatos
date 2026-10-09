package persistencia.P2ficheros;

import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;

public class EjemploPatrones {
    public static void main(String[] args) throws IOException {
        Path raiz = Path.of("centro");
        
        //Crear estructura de prueba
        Files.createDirectories(raiz.resolve("docs"));
        Files.writeString(raiz.resolve("datos.csv"), "data");
        Files.writeString(raiz.resolve("reporte.csv"), "data");
        Files.writeString(raiz.resolve("foto.jpg"), "binary");
        Files.writeString(raiz.resolve("docs/notas.txt"), "text");

        System.out.println("--- Buscando ficheros .csv con GLOB ---");
        buscarPorGlob(raiz, "*.csv");

        System.out.println("\n--- Buscando ficheros que empiezan por 're' con GLOB ---");
        buscarPorGlob(raiz, "re*");
    }

    public static void buscarPorGlob(Path raiz, String patronGlob) throws IOException {
        // Creamos el matcher. El prefijo "glob:" es obligatorio.
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + patronGlob);

        try (Stream<Path> todos = Files.walk(raiz)) {
            todos.filter(pp -> matcher.matches(pp.getFileName()))
                 .forEach(p -> System.out.println("Encontrado: " + p));
        }
    }
}
