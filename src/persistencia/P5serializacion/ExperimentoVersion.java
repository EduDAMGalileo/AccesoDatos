package persistencia.P5serializacion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// ============================================================================
// CLASE PERSONA
// ============================================================================
class Persona implements Serializable {

    // PASO CLAVE: Cambia este 1L por 2L tras haber guardado por primera vez
    private static final long serialVersionUID = 2L;

    private int id;
    private String nombre;

    public Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", nombre=" + nombre + "]";
    }
}

// ============================================================================
// PROGRAMA PRINCIPAL CON MENÚ
// ============================================================================
public class ExperimentoVersion {

    private static final Path RUTA = Paths.get("persona_version.dat");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("============================================");
        System.out.println(" PROVOCADOR DE InvalidClassException");
        System.out.println("============================================");
        System.out.println("1. Guardar Persona (Serializar)");
        System.out.println("2. Recuperar Persona (Deserializar)");
        System.out.print("Elige una opción (1 o 2): ");

        int opcion = scanner.nextInt();

        //Versión nueva del switch
        switch (opcion) {
            case 1 -> guardar();
            case 2 -> recuperar();
            default -> System.out.println("Opción no válida.");
        }
    }

    private static void guardar() {
        Persona persona = new Persona(101, "Laura Martínez");

        try (ObjectOutputStream salida = new ObjectOutputStream(
                 new BufferedOutputStream(Files.newOutputStream(RUTA)))) {

            salida.writeObject(persona);
            System.out.println("\n[OK] Objeto guardado con éxito en: " + RUTA.toAbsolutePath());
            System.out.println("Objeto guardado: " + persona);
            System.out.println("\n--- AHORA HAZ ESTO: ---");
            System.out.println("1. Ve al código fuente.");
            System.out.println("2. Cambia 'serialVersionUID = 1L;' por 'serialVersionUID = 2L;' en Persona.");
            System.out.println("3. Vuelve a ejecutar el programa y elige la opción 2 (Recuperar).");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recuperar() {
        if (!Files.exists(RUTA)) {
            System.err.println("El fichero no existe. Primero debes elegir la opción 1.");
            return;
        }

        try (ObjectInputStream entrada = new ObjectInputStream(
                 new BufferedInputStream(Files.newInputStream(RUTA)))) {

            Persona persona = (Persona) entrada.readObject();
            System.out.println("\n[OK] Objeto recuperado: " + persona);

        } catch (InvalidClassException e) {
            System.out.println("\n¡BOMBA! Se ha provocado la excepción esperada:");
            System.out.println("--------------------------------------------------");
            System.out.println("Tipo de error: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("--------------------------------------------------");
            // Imprimimos la traza para ver el error nativo:
            e.printStackTrace(System.out);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}