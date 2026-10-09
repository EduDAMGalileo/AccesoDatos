package persistencia.accesos;

public final class FormatoCurso {

    public static final int CODIGO = 8;   // Ej: "DAM-PROG"
    public static final int NOMBRE = 40;  // Ej: "Programacion en Java"

    //boolean (borrado), int (id), texto en UTF-16 (writeChars), int (horas), double (precio)
    //Total = 113 bytes
    public static final int TAMANO = 1 + 4 + (CODIGO + NOMBRE) * 2  + 4 + 8; 


    public static long posicionDe(int numeroRegistro) {
        // Conversión obligatoria a long para evitar desbordamiento
        return (long) numeroRegistro * TAMANO;
    }
}