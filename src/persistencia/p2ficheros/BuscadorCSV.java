package persistencia.p2ficheros;

import java.nio.file.*;
import java.util.stream.Stream;

public class BuscadorCSV {
    public static void main(String[] args) {
        Path rutaInicio = Paths.get("tu/ruta/de/inicio");

        // Recorre todo el árbol (incluye subcarpetas)
        try (Stream<Path> stream = Files.walk(rutaInicio)) {
            stream.filter(Files::isRegularFile) // Solo archivos, ignora carpetas
                  .filter(path -> path.toString().endsWith(".csv"))
                  .forEach(path -> {
                      // Aquí haces lo que necesites con cada archivo CSV
                      System.out.println("Encontrado: " + path);
                  });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}