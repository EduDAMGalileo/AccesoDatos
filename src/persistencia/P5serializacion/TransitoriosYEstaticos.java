package persistencia.P5serializacion;


	import java.io.*;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;

	class Profesor implements Serializable {
	    private static final long serialVersionUID = 1L;

	    // ----------------------------------------------------
	    // CAMPO ESTÁTICO: Pertenece a la clase, no al objeto
	    // ----------------------------------------------------
	    public static String instituto = "IES Julián Marías";

	    // Campos normales (estos son los que se guardan)
	    private int id;
	    private String nombre;

	    // ----------------------------------------------------
	    // CAMPOS TRANSITORIOS (NO se guardan)
	    // ----------------------------------------------------
	    // Un estado temporal que se pierde (boolean por defecto pasará a false)
	    private transient boolean haFichadoHoy; 
	    // Un recurso que no se puede o no se debe guardar directamente
	    private transient String sesionActiva; 

	    // ----------------------------------------------------
	    // EL CONSTRUCTOR
	    // ----------------------------------------------------
	    public Profesor(int id, String nombre) {
	        System.out.println(">>> [CONSTRUCTOR] Ejecutándose para: " + nombre);

	        // Validación que solo protege si se pasa por el constructor
	        if (nombre == null || nombre.isBlank()) {
	            throw new IllegalArgumentException("El nombre es obligatorio");
	        }

	        this.id = id;
	        this.nombre = nombre;
	        this.haFichadoHoy = true;
	        this.sesionActiva = "TOKEN-XYZ-123";
	    }

	    // ----------------------------------------------------
	    // EL PUNTO DE ENGANCHE: readObject
	    // Aquí es donde solucionas "la trampa" de los transitorios
	    // y donde puedes volver a validar si lo necesitas.
	    // ----------------------------------------------------
	    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	        // Lee los campos normales por defecto (id, nombre)
	        in.defaultReadObject();

	        // Comprobamos la "trampa": los transitorios están a nulo/falso
	        // Aquí tenemos la oportunidad de reconstruirlos:
	        this.haFichadoHoy = false; // Política: al arrancar de nuevo, no ha fichado
	        this.sesionActiva = "SESION-RECONECTADA"; // Reconstrucción de la conexión/sesión

	        // Validar de nuevo: como el constructor no se ejecutó,
	        // validamos aquí por si alguien alteró el archivo binario:
	        if (this.nombre == null || this.nombre.isBlank()) {
	            throw new InvalidObjectException("El nombre recuperado es inválido");
	        }
	    }

	    @Override
	    public String toString() {
	        return "Profesor {" +
	                "id=" + id +
	                ", nombre='" + nombre + '\'' +
	                ", instituto='" + instituto + '\'' +           // Estático
	                ", haFichadoHoy=" + haFichadoHoy +             // Transitorio
	                ", sesionActiva='" + sesionActiva + '\'' +     // Transitorio
	                '}';
	    }
	}

	public class TransitoriosYEstaticos {

	    public static void main(String[] args) {
	        Path ruta = Paths.get("profesor.dat");

	        System.out.println("=== 1. CREANDO OBJETO ===");
	        Profesor prof = new Profesor(1, "Eduardo Niévares");
	        System.out.println("Estado antes de guardar:\n" + prof);

	        //GUARDAR (SERIALIZACIÓN)
	        System.out.println("\n=== 2. GUARDANDO OBJETO ===");
	        try (ObjectOutputStream salida = new ObjectOutputStream(
	                 new BufferedOutputStream(Files.newOutputStream(ruta)))) {

	            salida.writeObject(prof);
	            System.out.println("Profesor guardado en disco.");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        //ALTERAMOS EL ESTADO ESTÁTICO EN MEMORIA
	        System.out.println("\n=== 3. CAMBIANDO CAMPO ESTÁTICO ===");
	        Profesor.instituto = "IES Galileo";
	        System.out.println("Nuevo valor de Profesor.instituto: " + Profesor.instituto);

	        // RECUPERAR (DESERIALIZACIÓN)
	        System.out.println("\n=== 4. RECUPERANDO OBJETO ===");
	        try (ObjectInputStream entrada = new ObjectInputStream(
	                 new BufferedInputStream(Files.newInputStream(ruta)))) {

	            // ¡Fíjate que NO saldrá el print del constructor!
	            Profesor profRecuperado = (Profesor) entrada.readObject();

	            System.out.println("Estado tras recuperar:\n" + profRecuperado);

	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                Files.deleteIfExists(ruta);
	            } catch (IOException ignored) {}
	        }
	    }
	
}
