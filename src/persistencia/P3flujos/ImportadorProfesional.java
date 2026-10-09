package persistencia.P3flujos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImportadorProfesional {

    // 1. Modelos
	//Podrían estar en otra clase, pero como solo los usamos aquí...
    record Alumno(String id, String nombre, double nota) {}
    record ErrorLinea(int numero, String contenido, String motivo) {}

    // 2. Objeto contenedor: empaqueta el resultado para que el llamante decida
    record ResultadoImportacion(List<Alumno> correctos, List<ErrorLinea> errores) {
        public boolean fuePerfecta() {
            return errores.isEmpty();
        }

        public double porcentajeExito() {
            int total = correctos.size() + errores.size();
            return total == 0 ? 0 : (correctos.size() * 100.0) / total;
        }
    }

    static class DatosInvalidosException extends Exception {
        public DatosInvalidosException(String mensaje) {
            super(mensaje);
        }
    }

    public static void main(String[] args) throws IOException {
        Path rutaCsv = Path.of("alumnos.csv");
        Path rutaLogErrores = Path.of("informe_errores.log");

        // Creamos un archivo de prueba con 5 alumnos (3 buenos y 2 con fallos)
        prepararDatosPrueba(rutaCsv);

        // =====================================================================
        // PASO 1: LEER ACUMULANDO AMBAS LISTAS
        // =====================================================================
        ResultadoImportacion resultado = importar(rutaCsv);

        // =====================================================================
        // PASO 2: QUIEN LLAMA TOMA LAS DECISIONES
        // =====================================================================
        System.out.println("=== RESUMEN DEL PROCESAMIENTO ===");
        System.out.printf("Tasa de éxito: %.1f%% (%d correctos, %d errores)%n%n",
                resultado.porcentajeExito(),
                resultado.correctos().size(),
                resultado.errores().size());

        // DECISIÓN 1: ¿Qué hacemos con los errores?
        // En lugar de callarnos, generamos automáticamente un archivo de log con los fallos
        if (!resultado.fuePerfecta()) {
            System.out.println("[!] Hay registros con errores. Generando 'informe_errores.log'...");

            StringBuilder log = new StringBuilder("INFORME DE ERRORES DE IMPORTACIÓN\n");
            log.append("===================================\n");
            for (ErrorLinea err : resultado.errores()) {
                log.append(String.format("Línea %d | Motivo: %s%n  Contenido: \"%s\"%n%n",
                        err.numero(), err.motivo(), err.contenido()));
            }

            // Usamos el atajo Files.writeString para guardar el log
            Files.writeString(rutaLogErrores, log.toString());
            System.out.println("    -> Log guardado para que el usuario pueda corregirlo.");
        }

        System.out.println();

        // DECISIÓN 2: Política de aceptación (¿Abortar o continuar?)
        // Regla: "Si el porcentaje de éxito es menor al 70%, rechazamos todo"
        if (resultado.porcentajeExito() < 70.0) {
            System.out.println("[IMPORTACIÓN RECHAZADA]");
            System.out.println("Motivo: Demasiados errores en el fichero fuente. No se guarda ningún alumno.");
        } else {
            System.out.println("[IMPORTACIÓN PARCIAL ACEPTADA]");
            System.out.printf("Se han guardado en la base de datos %d alumnos válidos.%n", 
                    resultado.correctos().size());
            for (Alumno a : resultado.correctos()) {
                System.out.printf("   - Guardado: %s (%s, Nota: %.1f)%n", a.nombre(), a.id(), a.nota());
            }
        }

        // Limpieza de archivos de prueba
        Files.deleteIfExists(rutaCsv);
        Files.deleteIfExists(rutaLogErrores);
    }

    /**
     * Función pura de importación: solo lee y clasifica, NO toma decisiones
     */
    public static ResultadoImportacion importar(Path ruta) throws IOException {
        List<Alumno> alumnos = new ArrayList<>();
        List<ErrorLinea> errores = new ArrayList<>();
        int n = 0;

        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                n++;
                try {
                    alumnos.add(convertir(linea));
                } catch (DatosInvalidosException e) {
                    errores.add(new ErrorLinea(n, linea, e.getMessage()));
                }
            }
        }

        return new ResultadoImportacion(alumnos, errores);
    }

    private static Alumno convertir(String linea) throws DatosInvalidosException {
        String[] partes = linea.split(",");
        if (partes.length < 3) {
            throw new DatosInvalidosException("Faltan columnas (se esperan ID, Nombre, Nota)");
        }

        String id = partes[0].trim();
        String nombre = partes[1].trim();
        double nota;

        try {
            nota = Double.parseDouble(partes[2].trim());
        } catch (NumberFormatException e) {
            throw new DatosInvalidosException("La nota no es un número válido: " + partes[2]);
        }

        if (nota < 0 || nota > 10) {
            throw new DatosInvalidosException("La nota debe estar entre 0 y 10 (recibido: " + nota + ")");
        }

        return new Alumno(id, nombre, nota);
    }

    private static void prepararDatosPrueba(Path ruta) throws IOException {
        String contenido = 
            "A01,Laura Perez,8.5\n" +      // OK (Línea 1)
            "A02,Marcos Sanz,quince\n" +   // Error: nota no numérica (Línea 2)
            "A03,Beatriz Gil,9.0\n" +      // OK (Línea 3)
            "A04,Juan Gomez,-2.0\n" +      // Error: nota fuera de rango (Línea 4)
            "A05,Carlos Diaz,7.2\n";       // OK (Línea 5)

        Files.writeString(ruta, contenido);
    }
}