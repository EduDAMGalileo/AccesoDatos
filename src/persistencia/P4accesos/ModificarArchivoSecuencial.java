package persistencia.P4accesos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ModificarArchivoSecuencial {

    /**
     * Modifica los datos del alumno indicado aplicando el patrón de los 3 pasos:
     * 1. Leer el original.
     * 2. Escribir en un fichero temporal sustituyendo la línea objetivo.
     * 3. Reemplazar atómicamente el original con el temporal.
     */
    public static void modificarAlumno(Path destino, String idAlumno, String nuevaLinea) throws IOException {

        // Aseguramos obtener la carpeta padre (si pasas solo "alumnos.csv", getParent() daría null)
        Path directorioPadre = destino.toAbsolutePath().getParent();

        // PASO 1 Y 2: CREAR EL FICHERO TEMPORAL
        // ¿Por qué en 'directorioPadre' y no en el temporal del sistema?
        // Porque para que el movimiento atómico (ATOMIC_MOVE) funcione en el sistema operativo,
        // ambos ficheros DEBEN residir en el mismo disco / sistema de archivos físico. Lo estamos generando en el mismo directorio
        Path temporal = Files.createTempFile(directorioPadre, "alumnos-", ".tmp");

        try {
            // Abrimos ambos flujos en un único try-with-resources:
            // Uno lee el original y el otro escribe en el temporal simultáneamente.
            try (BufferedReader lector = Files.newBufferedReader(destino, StandardCharsets.UTF_8);
                 BufferedWriter escritor = Files.newBufferedWriter(temporal, StandardCharsets.UTF_8)) {

                String linea;
                String prefijoId = idAlumno + ",";

                while ((linea = lector.readLine()) != null) {
                    // Si la línea empieza por "17,", escribimos la nueva versión;
                    // si no, copiamos la línea tal cual estaba.
                    if (linea.startsWith(prefijoId)) {
                        escritor.write(nuevaLinea);
                    } else {
                        escritor.write(linea);
                    }
                    // Escribimos el salto de línea específico del sistema operativo (\n o \r\n)
                    escritor.newLine();
                }
            } // Aquí se cierran y vacían (flush) ambos ficheros obligatoriamente

            // PASO 3: SUSTITUIR EL ORIGINAL POR EL TEMPORAL
            // StandardCopyOption.REPLACE_EXISTING: Sobrescribe el fichero destino original.
            // StandardCopyOption.ATOMIC_MOVE:
            //   Garantía de integridad. El cambio de nombre ocurre en un solo paso del SO.
            //   Si se va la luz en mitad del proceso, o el fichero queda intacto con los datos viejos
            //   o queda con los nuevos, pero NUNCA a medio escribir ni corrupto.
            Files.move(temporal, destino,
                       StandardCopyOption.REPLACE_EXISTING,
                       StandardCopyOption.ATOMIC_MOVE);

            System.out.println("-> Modificación completada de forma atómica.");

        } catch (IOException e) {
            // Si algo falla a mitad de la lectura/escritura, borramos el temporal para no dejar basura
            Files.deleteIfExists(temporal);
            throw e;
        }
    }

    // =========================================================================
    // MÉTODO MAIN DE PRUEBA
    // =========================================================================
    public static void main(String[] args) {
        Path archivo = Path.of("alumnos.csv");

        try {
            // 1. Preparamos un archivo de prueba con 3 alumnos
            List<String> lineasIniciales = List.of(
                "nia,apellidos,nombre,correo",
                "16,Cobain,Kurt,kurt@nirvana.com",
                "17,Mercury,Freddie,freddie_viejo@queen.com", // <- Este es el que cambiaremos
                "18,Osbourne,Ozzy,ozzy@sabbath.com"
            );
            Files.write(archivo, lineasIniciales, StandardCharsets.UTF_8);

            System.out.println("=== CONTENIDO ORIGINAL ===");
            Files.readAllLines(archivo).forEach(System.out::println);

            // 2. Definimos la nueva línea para el alumno 17 (cambiamos su correo por uno más largo)
            String nuevaLinea17 = "17,Mercury,Freddie,freddie_nuevo_y_mas_largo@queen.com";

            System.out.println("\nModificando el alumno 17...");
            modificarAlumno(archivo, "17", nuevaLinea17);

            // 3. Comprobamos el resultado final
            System.out.println("\n=== CONTENIDO TRAS LA MODIFICACIÓN ===");
            Files.readAllLines(archivo).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error durante la prueba: " + e.getMessage());
        } finally {
            // Limpieza del archivo de prueba al terminar
          /*  try {
                Files.deleteIfExists(archivo);
            } catch (IOException ignored) {}*/
        }
    }
}
