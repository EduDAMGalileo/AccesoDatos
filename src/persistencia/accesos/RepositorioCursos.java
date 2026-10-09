package persistencia.accesos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RepositorioCursos {

    private final Path ruta;
    
    // Tabla en memoria: ID del curso -> Byte de inicio en el archivo
    private final Map<Integer, Long> indice = new HashMap<>();

    public RepositorioCursos(Path ruta) {
        this.ruta = ruta;
        if (ruta.toFile().exists()) {
            construirIndice();
        }
    }

    /**
     * Escanea el archivo secuencialmente para reconstruir el índice en RAM.
     * Solo se ejecuta una vez al arrancar el programa o tras una restauración.
     */
    public void construirIndice() {
        indice.clear();
        File archivo = ruta.toFile();

        if (!archivo.exists() || archivo.length() == 0) {
            return;
        }

        try (RandomAccessFile f = new RandomAccessFile(archivo, "r")) {
            long posicion = 0;
            while (posicion < f.length()) {
                f.seek(posicion);

                boolean borrado = f.readBoolean();
                int id = f.readInt();

                // Si no está borrado lógicamente, lo apuntamos en el índice
                if (!borrado) {
                    indice.put(id, posicion);
                }

                // Salto matemático al siguiente registro
                posicion += FormatoCurso.TAMANO;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reconstruyendo el índice de cursos", e);
        }
    }

    /**
     * Búsqueda instantánea O(1) usando el índice.
     */
    public Optional<Curso> buscarPorId(int id) {
        Long posicion = indice.get(id);

        if (posicion == null) {
            return Optional.empty(); // No existe en memoria
        }

        try (RandomAccessFile f = new RandomAccessFile(ruta.toFile(), "r")) {
        	// Salto directo al disco sin recorrer nada más
            f.seek(posicion); 
            return Optional.of(leerCurso(f));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el curso " + id, e);
        }
    }

    /**
     * Añade un nuevo curso al final del archivo y actualiza el índice en RAM.
     */
    public void guardar(Curso c) {
        if (indice.containsKey(c.getId())) {
            throw new IllegalArgumentException("Ya existe un curso con el ID " + c.getId());
        }

        try (RandomAccessFile f = new RandomAccessFile(ruta.toFile(), "rw")) {
            long posicionNueva = f.length(); // Fin del archivo
            f.seek(posicionNueva);

            escribirCurso(f, c);

            // Se indexa de inmediato
            indice.put(c.getId(), posicionNueva);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el curso", e);
        }
    }

    /**
     * Borrado lógico: pone a true el primer byte en disco y lo quita del índice.
     */
    public boolean borrarPorId(int id) {
        Long posicion = indice.get(id);
        if (posicion == null) {
            return false;
        }

        try (RandomAccessFile f = new RandomAccessFile(ruta.toFile(), "rw")) {
            f.seek(posicion);
            f.writeBoolean(true); // Marca de borrado

            indice.remove(id);   // Ya no es visible en búsquedas
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error al borrar el curso " + id, e);
        }
    }

    public int totalActivos() {
        return indice.size();
    }

    // =========================================================================
    // LECTURA / ESCRITURA BINARIA
    // =========================================================================

    private void escribirCurso(RandomAccessFile f, Curso c) throws IOException {
        f.writeBoolean(false); // Borrado = false (1 byte)
        f.writeInt(c.getId());  // ID (4 bytes)
        escribirTexto(f, c.getCodigo(), FormatoCurso.CODIGO);
        escribirTexto(f, c.getNombre(), FormatoCurso.NOMBRE);
        f.writeInt(c.getHoras());   // Horas (4 bytes)
        f.writeDouble(c.getPrecio()); // Precio (8 bytes)
    }

    private Curso leerCurso(RandomAccessFile f) throws IOException {
        boolean borrado = f.readBoolean();
        int id = f.readInt();
        String codigo = leerTexto(f, FormatoCurso.CODIGO);
        String nombre = leerTexto(f, FormatoCurso.NOMBRE);
        int horas = f.readInt();
        double precio = f.readDouble();

        return new Curso(id, codigo, nombre, horas, precio);
    }

    private void escribirTexto(RandomAccessFile f, String s, int ancho) throws IOException {
        if (s.length() > ancho) {
            throw new IllegalArgumentException("El texto excede el tamaño máximo de " + ancho + ": " + s);
        }
        StringBuilder b = new StringBuilder(s);
        while (b.length() < ancho) {
            b.append(' '); // Relleno de huecos
        }
        f.writeChars(b.toString()); // 2 bytes por carácter
    }

    private String leerTexto(RandomAccessFile f, int ancho) throws IOException {
        char[] chars = new char[ancho];
        for (int i = 0; i < ancho; i++) {
            chars[i] = f.readChar();
        }
        return new String(chars).trim(); // Eliminamos el relleno de espacios
    }
}
