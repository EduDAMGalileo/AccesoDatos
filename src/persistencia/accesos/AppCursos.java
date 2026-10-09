package persistencia.accesos;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class AppCursos {

    public static void main(String[] args) {
        Path ruta = Paths.get("cursos.dat");
        RepositorioCursos repo = new RepositorioCursos(ruta);

        try {
            System.out.println("=== FORMATO ===");
            System.out.println("Cada registro de curso ocupa estrictamente: " + FormatoCurso.TAMANO + " bytes\n");

            // 1. Guardar cursos con identificadores no secuenciales
            System.out.println("=== 1. GUARDANDO CURSOS ===");
            repo.guardar(new Curso(801, "DAM-PROG", "Programacion Java", 240, 180.00));
            repo.guardar(new Curso(204, "DAW-DWES", "Desarrollo Web Entorno Servidor", 180, 150.50));
            repo.guardar(new Curso(990, "ASIR-ISO", "Implantación Sistemas Operativos", 120, 99.90));

            System.out.println("Cursos guardados y activos en el índice: " + repo.totalActivos());
            System.out.println("Tamaño actual del archivo en disco: " + ruta.toFile().length() + " bytes");
            System.out.println("(Esperado: 3 registros * 113 bytes = " + (3 * 113) + " bytes)\n");

            // 2. Búsqueda directa por ID
            System.out.println("=== 2. BÚSQUEDA DIRECTA POR ÍNDICE ===");
            int idBuscado = 204;
            System.out.println("Consultando ID: " + idBuscado);
            Optional<Curso> cursoOpt = repo.buscarPorId(idBuscado);
            cursoOpt.ifPresent(c -> System.out.println("-> Encontrado al instante: " + c));

            // 3. Borrado lógico
            System.out.println("\n=== 3. BORRADO LÓGICO DEL CURSO 801 ===");
            repo.borrarPorId(801);
            System.out.println("Cursos activos en el índice tras borrar: " + repo.totalActivos());

            System.out.println("Intentando volver a buscar el 801:");
            System.out.println("¿Existe?: " + repo.buscarPorId(801).isPresent());

            // El tamaño del archivo sigue siendo el mismo porque no se ha reescrito:
            System.out.println("Tamaño del archivo en disco sigue intacto: " + ruta.toFile().length() + " bytes");

            // 4. Simulación de reinicio del sistema (reconstrucción)
            System.out.println("\n=== 4. RECONSTRUCCIÓN DEL ÍNDICE TRAS REINICIO ===");
            repo.construirIndice();
            System.out.println("Cursos recuperados en el nuevo índice: " + repo.totalActivos());
            System.out.println("(El curso 801 no se ha cargado porque estaba marcado como borrado)");

        } finally {
            // Limpieza del archivo creado
            File f = ruta.toFile();
            if (f.exists()) {
                f.delete();
            }
        }
    }
}
