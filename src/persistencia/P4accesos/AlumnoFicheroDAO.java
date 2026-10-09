package persistencia.P4accesos;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

public class AlumnoFicheroDAO {

    /**
     * Escribe un registro en la posición actual del puntero.
     */
    public void escribir(RandomAccessFile f, Alumno a) throws IOException {
    	// no borrado (1 byte)
    	f.writeBoolean(false);
    	// id (4 bytes)
        f.writeInt(a.getId());
        
        escribirTexto(f, a.getNia(), FormatoAlumno.NIA);
        escribirTexto(f, a.getNombre(), FormatoAlumno.NOMBRE);
        escribirTexto(f, a.getApellidos(), FormatoAlumno.APELLIDOS);

        // fecha (8 bytes)
        f.writeLong(a.getFechaNacimiento().toEpochDay()); 
        escribirTexto(f, a.getCorreo() == null ? "" : a.getCorreo(), FormatoAlumno.CORREO);
    }

    private void escribirTexto(RandomAccessFile f, String s, int ancho) throws IOException {
        if (s.length() > ancho) {
            throw new DatosInvalidosException(
                "No cabe en " + ancho + " caracteres: " + s);
        }
        StringBuilder b = new StringBuilder(s);
        while (b.length() < ancho) {
            b.append(' ');
        }
        f.writeChars(b.toString());
    }

    /**
     * Lee un registro completo desde la posición actual del puntero.
     */
    public Alumno leer(RandomAccessFile f) throws IOException {
        boolean borrado = f.readBoolean();
        int id = f.readInt();
        String nia = leerTexto(f, FormatoAlumno.NIA);
        String nombre = leerTexto(f, FormatoAlumno.NOMBRE);
        String apellidos = leerTexto(f, FormatoAlumno.APELLIDOS);
        long epochDay = f.readLong();
        String correo = leerTexto(f, FormatoAlumno.CORREO);

        return new Alumno(id, nia, nombre, apellidos, LocalDate.ofEpochDay(epochDay), correo);
    }

    private String leerTexto(RandomAccessFile f, int ancho) throws IOException {
        char[] chars = new char[ancho];
        for (int i = 0; i < ancho; i++) {
            chars[i] = f.readChar();
        }
        return new String(chars).trim();
    }
}
