package persistencia.serializacion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Humano implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;

    public Humano(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Humano #" + id + ": " + nombre;
    }
}

public class LecturaSecuencial {

    public static void main(String[] args) {
        Path ruta = Paths.get("personas_secuencia.dat");

        // ========================================================
        // GUARDAR VARIOS OBJETOS SECUENCIALMENTE
        // ========================================================
        try (ObjectOutputStream out = new ObjectOutputStream(
                 new BufferedOutputStream(Files.newOutputStream(ruta)))) {

            // Opción recomendada: Guardar cuántos objetos hay en el archivo
            out.writeInt(3);

            // Guardamos de uno en uno
            out.writeObject(new Humano(1, "Ana"));
            out.writeObject(new Humano(2, "Edu"));
            out.writeObject(new Humano(3, "Paco"));

            System.out.println("3 personas guardadas una tras otra.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ========================================================
        // LEER A DEMANDA (UNO A UNO)
        // ========================================================
        System.out.println("\n--- Leyendo a demanda ---");
        try (ObjectInputStream in = new ObjectInputStream(
                 new BufferedInputStream(Files.newInputStream(ruta)))) {

            // Leemos el total que guardamos al principio
            int total = in.readInt();

            for (int i = 0; i < total; i++) {
                // Cada readObject() avanza el cursor al siguiente objeto
            	Humano p = (Humano) in.readObject();
                
                System.out.println("Leído bajo demanda: " + p);
                // Aquí podrías procesarlo, insertarlo en BD, etc.,
                // sin tener que tener todos los objetos cargados a la vez.
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                Files.deleteIfExists(ruta);
            } catch (IOException ignored) {}
        }
    }
}