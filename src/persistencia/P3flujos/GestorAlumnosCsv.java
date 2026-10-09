package persistencia.P3flujos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class GestorAlumnosCsv {

    private static final String CABECERA_ESPERADA = "nia,apellidos,nombre,fecha_nacimiento,correo";

    // =========================================================================
    // ESCRIBIR (Sin usar 'get...', usando los métodos directos del record)
    // =========================================================================
    public void exportar(List<Alumno> alumnos, Path destino) {
        try {
            if (destino.getParent() != null) {
                Files.createDirectories(destino.getParent());
            }

            try (BufferedWriter w = Files.newBufferedWriter(destino, StandardCharsets.UTF_8)) {
                w.write(CABECERA_ESPERADA);
                w.newLine();

                for (Alumno a : alumnos) {
                    w.write(String.join(",",
                            a.nia(),
                            escapar(a.apellidos()),
                            escapar(a.nombre()),
                            a.fechaNacimiento().toString(),
                            a.correo() == null ? "" : a.correo()));
                    w.newLine();
                }
            }
        } catch (IOException e) {
            throw new AccesoDatosException("No se pudo exportar a " + destino.toAbsolutePath(), e);
        }
    }

    // =========================================================================
    // 11.2. LEER
    // =========================================================================
    public ResultadoImportacion importar(Path origen) {
        List<Alumno> alumnos = new ArrayList<>();
        List<ErrorLinea> errores = new ArrayList<>();

        try (BufferedReader lector = Files.newBufferedReader(origen, StandardCharsets.UTF_8)) {
            String cabecera = lector.readLine();
            if (cabecera == null) {
                throw new AccesoDatosException("Fichero vacío: " + origen.toAbsolutePath());
            }

            cabecera = sinMarcaInicial(cabecera);
            comprobarCabecera(cabecera);

            String linea;
            int n = 1;
            while ((linea = lector.readLine()) != null) {
                n++;
                if (linea.isBlank()) continue;

                try {
                    alumnos.add(convertir(linea));
                } catch (RuntimeException e) {
                    errores.add(new ErrorLinea(n, linea, e.getMessage()));
                }
            }
        } catch (IOException e) {
            throw new AccesoDatosException("No se pudo leer " + origen.toAbsolutePath(), e);
        }

        return new ResultadoImportacion(alumnos, errores);
    }

    // =========================================================================
    // MÉTODOS AUXILIARES
    // =========================================================================
    private String sinMarcaInicial(String texto) {
        if (texto != null && texto.startsWith("\uFEFF")) {
            return texto.substring(1);
        }
        return texto;
    }

    private void comprobarCabecera(String cabecera) {
        if (!CABECERA_ESPERADA.equalsIgnoreCase(cabecera.trim())) {
            throw new AccesoDatosException("Cabecera no válida: '" + cabecera + "'. Se esperaba: '" + CABECERA_ESPERADA + "'");
        }
    }

    private String escapar(String valor) {
        if (valor == null) return "";
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }

    private Alumno convertir(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length != 5) {
            throw new IllegalArgumentException("Se esperaban 5 columnas y hay " + partes.length);
        }

        String nia = partes[0].trim();
        String apellidos = partes[1].trim();
        String nombre = partes[2].trim();

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(partes[3].trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha no válida (esperado AAAA-MM-DD): " + partes[3].trim());
        }

        String correo = partes[4].trim().isEmpty() ? null : partes[4].trim();

        return new Alumno(nia, apellidos, nombre, fecha, correo);
    }

    // =========================================================================
    // MODELOS EN UNA SOLA LÍNEA - RECORDS
    // =========================================================================
    public record Alumno(String nia, String apellidos, String nombre, LocalDate fechaNacimiento, String correo) {}

    public record ErrorLinea(int numeroLinea, String contenido, String mensajeError) {}

    public record ResultadoImportacion(List<Alumno> alumnos, List<ErrorLinea> errores) {}

    public static class AccesoDatosException extends RuntimeException {
        public AccesoDatosException(String mensaje) { super(mensaje); }
        public AccesoDatosException(String mensaje, Throwable causa) { super(mensaje, causa); }
    }

    // =========================================================================
    // MAIN
    // =========================================================================
    public static void main(String[] args) throws IOException {
        GestorAlumnosCsv gestor = new GestorAlumnosCsv();
        Path archivoCsv = Path.of("datos", "rockeros.csv");

        // 1. Alumnos rockeros originales
        List<Alumno> listaRockeros = List.of(
                new Alumno("1001", "Mercury", "Freddie", LocalDate.of(1946, 9, 5), "freddie.mercury@queen.com"),
                new Alumno("1002", "Osbourne", "Ozzy", LocalDate.of(1948, 12, 3), null), // Ozzy sin correo
                new Alumno("1003", "Joplin", "Janis", LocalDate.of(1943, 1, 19), "janis.joplin@woodstock.com")
        );

        System.out.println("1. EXPORTANDO ROCKEROS...");
        gestor.exportar(listaRockeros, archivoCsv);
        System.out.println("-> Archivo exportado en: " + archivoCsv.toAbsolutePath());

        // 2. Colamos a King África con fecha inválida ("LA_BOMBA")
        Files.writeString(archivoCsv, "1004,África,King,LA_BOMBA,king@bomba.com\n", 
                StandardCharsets.UTF_8, StandardOpenOption.APPEND);

        System.out.println("\n2. IMPORTANDO...");
        ResultadoImportacion resultado = gestor.importar(archivoCsv);

        System.out.println("\n--- ROCKEROS IMPORTADOS (" + resultado.alumnos().size() + ") ---");
        for (Alumno a : resultado.alumnos()) {
            System.out.printf("  [%s] %s, %s | Nacido: %s | Correo: %s%n",
                    a.nia(), a.apellidos(), a.nombre(), a.fechaNacimiento(),
                    a.correo() == null ? "<SIN CORREO>" : a.correo());
        }

        System.out.println("\n--- ERRORES / INTRUSOS (" + resultado.errores().size() + ") ---");
        for (ErrorLinea error : resultado.errores()) {
            System.out.printf("  Línea %d: \"%s\"%n  -> Fallo: %s%n",
                    error.numeroLinea(), error.contenido(), error.mensajeError());
        }

        // Limpieza de archivos temporales
        Files.deleteIfExists(archivoCsv);
        Files.deleteIfExists(archivoCsv.getParent());
    }
}