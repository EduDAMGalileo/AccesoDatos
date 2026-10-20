package persistencia.P5serializacion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

class Direccion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String calle;
    private String ciudad;

    public Direccion(String calle, String ciudad) {
        this.calle = calle;
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return calle + ", " + ciudad;
    }
}


class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    private String asignatura;
    private int curso;

    public Matricula(String asignatura, int curso) {
        this.asignatura = asignatura;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return asignatura + " (" + curso + "º)";
    }
}


class Alumno implements Serializable {
    // Buena práctica: definir la versión de la clase
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private Direccion direccion;
    private Set<Matricula> matriculas;

    public Alumno(int id, String nombre, Direccion direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.matriculas = new HashSet<>();
    }

    public void agregarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
    }

    @Override
    public String toString() {
        return "Alumno [id=" + id + ", nombre=" + nombre + 
               ", direccion=" + direccion + ", matriculas=" + matriculas + "]";
    }
}

//Clase Principal para probar Guardar y Recuperar
public class EjemploSerializacion {

    public static void main(String[] args) {
        Path ruta = Path.of("alumno.dat");

        // Creamos los objetos
        Direccion dir = new Direccion("Gran Vía 12", "Madrid");
        Alumno alumnoOriginal = new Alumno(101, "Edu López", dir);
        alumnoOriginal.agregarMatricula(new Matricula("Programación", 1));
        alumnoOriginal.agregarMatricula(new Matricula("Bases de Datos", 1));

        System.out.println("--- OBJETO ORIGINAL ---");
        System.out.println(alumnoOriginal);

        // ==========================================
        // GUARDAR (SERIALIZACIÓN)
        // ==========================================
        try (ObjectOutputStream salida = new ObjectOutputStream(
                 new BufferedOutputStream(Files.newOutputStream(ruta)))) {

            salida.writeObject(alumnoOriginal);
            System.out.println("\n>> Objeto guardado correctamente en: " + ruta.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }

        // ==========================================
        // RECUPERAR (DESERIALIZACIÓN)
        // ==========================================
        try (ObjectInputStream entrada = new ObjectInputStream(
                 new BufferedInputStream(Files.newInputStream(ruta)))) {

            Alumno alumnoRecuperado = (Alumno) entrada.readObject();

            System.out.println("\n--- OBJETO RECUPERADO ---");
            System.out.println(alumnoRecuperado);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al recuperar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Limpieza: borramos el archivo temporal generado
            try {
                Files.deleteIfExists(ruta);
            } catch (IOException ignored) {
            	//Esto es una mala práctica
            }
        }
    }
}