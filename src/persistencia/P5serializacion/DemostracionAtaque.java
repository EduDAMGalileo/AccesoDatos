package persistencia.P5serializacion;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;

// La clase que el servidor cree que va a recibir
class Mascota implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
}

// EL GADGET: Una clase legítima del proyecto que tiene utilidades de mantenimiento
// (Por ejemplo, diseñada para limpiar archivos temporales al apagarse o reiniciarse)
class LimpiadorCache implements Serializable {
    private static final long serialVersionUID = 1L;
    private String rutaDirectorioABorrar;

    public LimpiadorCache(String rutaDirectorioABorrar) {
        this.rutaDirectorioABorrar = rutaDirectorioABorrar;
    }

    // EL DISPARADOR: Java ejecuta este método automáticamente durante la deserialización
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Lee los atributos

        // Acción automática de la clase: limpiar la ruta que tiene asignada
        System.out.println("\n[!] ALERTA: 'LimpiadorCache.readObject()' se ha ejecutado automáticamente.");
        System.out.println("[!] Vaciando el directorio: " + rutaDirectorioABorrar);
        
        Path carpeta = Paths.get(rutaDirectorioABorrar);
        if (Files.exists(carpeta)) {
            // Borra los ficheros que haya dentro
            try (var stream = Files.walk(carpeta)) {
                stream.sorted(Comparator.reverseOrder())
                      .filter(p -> !p.equals(carpeta))
                      .forEach(p -> {
                          try {
                              Files.delete(p);
                              System.out.println("    [-] Archivo eliminado: " + p.getFileName());
                          } catch (IOException e) {
                              System.err.println("No se pudo borrar: " + p);
                          }
                      });
            }
        }
    }
}

public class DemostracionAtaque {

    private static final Path CARPETA_VICTIMA = Paths.get("documentos_importantes");
    private static final Path FICHERO_PAYLOAD = Paths.get("ataque.bin");

    public static void main(String[] args) throws Exception {
        // --- PREPARACIÓN DEL ENTORNO ---
        prepararArchivosVictima();

        // ====================================================================
        // FASE 1: EL ATACANTE PREPARA EL ATAQUE
        // ====================================================================
        System.out.println("=== FASE 1: El atacante genera el paquete malicioso ===");
        // El atacante no tiene acceso a los ficheros, pero sabe que en el proyecto
        // existe la clase 'LimpiadorCache' y la usa con la ruta que quiere destruir.
        LimpiadorCache gadgetMalicioso = new LimpiadorCache(CARPETA_VICTIMA.toString());

        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(FICHERO_PAYLOAD))) {
            out.writeObject(gadgetMalicioso);
        }
        System.out.println("Payload malicioso guardado en: " + FICHERO_PAYLOAD);

        // ====================================================================
        // FASE 2: EL SERVIDOR VÍCTIMA DESERIALIZA
        // ====================================================================
        System.out.println("\n=== FASE 2: El servidor recibe y procesa los datos ===");
        System.out.println("El servidor ejecuta: 'Mascota a = (Mascota) in.readObject();'");

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(FICHERO_PAYLOAD))) {

            // ¡AQUÍ ESTÁ EL FALLO! 
            // El servidor espera una 'Mascota', pero 'readObject()' procesará lo que venga en el archivo.
        	Mascota mascota = (Mascota) in.readObject();
            System.out.println("Mascota procesada: " + mascota);

        } catch (ClassCastException e) {
            System.out.println("\n[!] Servidor: '¡Error! El objeto no era una Mascota: " + e.getMessage() + "'");
            System.out.println("[!] Pero ya es tarde... el código del atacante ya se ejecutó.");
        }

        // ====================================================================
        // VERIFICACIÓN DEL DAÑO
        // ====================================================================
        System.out.println("\n=== RESULTADO FINAL EN EL DISCO ===");
        try (var archivos = Files.list(CARPETA_VICTIMA)) {
            long total = archivos.count();
            System.out.println("Archivos restantes en la carpeta importante: " + total);
        }

        // Limpieza final
        Files.deleteIfExists(FICHERO_PAYLOAD);
        Files.deleteIfExists(CARPETA_VICTIMA);
    }

    private static void prepararArchivosVictima() throws IOException {
        Files.createDirectories(CARPETA_VICTIMA);
        Files.writeString(CARPETA_VICTIMA.resolve("nomina.pdf"), "Contenido privado...");
        Files.writeString(CARPETA_VICTIMA.resolve("claves.txt"), "Contenido super secreto...");
        System.out.println("-> Se han creado 2 archivos de prueba en: " + CARPETA_VICTIMA.toAbsolutePath());
    }
}