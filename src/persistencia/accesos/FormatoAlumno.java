package persistencia.accesos;

public final class FormatoAlumno {

    public static final int NIA = 8;
    public static final int NOMBRE = 30;
    public static final int APELLIDOS = 60;
    public static final int CORREO = 60;

    // Marca de borrado + indentificador + datos + fecha
    public static final int TAMANO = 1 + 4 + (NIA + NOMBRE + APELLIDOS + CORREO) * 2 + 8;                      

    public static long posicionDe(int numeroRegistro) {
        return (long) numeroRegistro * TAMANO;
    }
}
