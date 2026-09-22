package persistencia.ficheros;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EjemploRutas {
    public static void main(String[] args) {
        // Partimos de una ruta con "puntos" (sube y baja) que ensucian la ruta
        Path ruta = Path.of("documentos/./proyectos/../trabajo/datos.csv");

        System.out.println("--- PRUEBA DE RUTAS ---");
        
        // 1. normalize: Limpia los "./" y "../"
        Path limpia = ruta.normalize();
        System.out.println("1. normalize:       " + limpia);

        // 2. toAbsolutePath: Convierte la ruta relativa en completa
        System.out.println("2. toAbsolutePath:  " + limpia.toAbsolutePath());

        // 3. getFileName: Obtiene solo el archivo final
        System.out.println("3. getFileName:     " + limpia.getFileName());

        // 4. getParent: Obtiene la carpeta contenedora
        System.out.println("4. getParent:       " + limpia.getParent());

        // 5. resolve: Añade un nuevo segmento al final, tenga o no sentido
        Path nueva = limpia.resolve("respaldo/backup.csv");
        System.out.println("5. resolve:         " + nueva);

        // 6. resolveSibling: Sustituye el archivo actual por otro en la misma carpeta
        Path hermano = limpia.resolveSibling("config.json");
        System.out.println("6. resolveSibling:  " + hermano);

        // 7. relativize: Calcula cómo llegar de una ruta a otra
        Path rutaA = Path.of("proyecto/a");
        Path rutaB = Path.of("proyecto/b/datos.csv");
        System.out.println("7. relativize:      " + rutaA.relativize(rutaB));
    }
}