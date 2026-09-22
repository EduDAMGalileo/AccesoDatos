package persistencia.ficheros;

import java.nio.file.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Stream;
import java.io.File;


public class EjemploBorradoRecursivo {

    // MÉTODO 1: Moderno (Java NIO - Files)
	public static void borrarModerno(Path raiz) throws IOException {
	    // Files.walk crea un "camino" (Stream) que recorre todo el árbol automáticamente
	    try (Stream<Path> stream = Files.walk(raiz)) {
	        
	        // El Stream no borra nada todavía, solo prepara la lista
	        stream.sorted(Comparator.reverseOrder()) // <--- ESTO ES LA CLAVE
	              .forEach(p -> { // Por cada elemento (p) de la lista...
	                  try {
	                      // Borra el elemento
	                	  System.out.println("Borrando (NIO): " + p);
	                      Files.delete(p);
	                  } catch (IOException e) {
	                      throw new RuntimeException(e);
	                  }
	              });
	    }
	}

    // MÉTODO 2: Clásico (java.io.File)
    public static void borrarClasico(File archivo) {
        System.out.println("--- Iniciando borrado CLÁSICO (java.io) ---");
        borrarRecursivoClasico(archivo);
    }
    
    private static void borrarRecursivoClasico(File archivo) {
        // PASO 1: ¿Es una carpeta?
        if (archivo.isDirectory()) {
            // Obtenemos una lista de todo lo que hay dentro
            File[] hijos = archivo.listFiles();
            
            // Si no está vacía, recorremos cada "hijo" (archivo o carpeta)
            if (hijos != null) {
                for (File hijo : hijos) {
                    // AQUÍ ESTÁ LA RECURSIVIDAD: Nos llamamos a nosotros mismos.
                    // Le pedimos al programa: "¡Oye, haz lo mismo con este hijo!"
                    borrarRecursivoClasico(hijo); 
                }
            }
        }
        
        // PASO 2: Cuando llegamos aquí, el "if" de arriba ya terminó.
        // Esto significa que ya no quedan archivos dentro (porque los borramos antes).
        // Ahora podemos borrar el archivo o la carpeta vacía.
        System.out.println("Borrando (IO): " + archivo.getAbsolutePath()); 
        archivo.delete();
    }



    public static void main(String[] args) throws IOException {
        // Creamos una estructura común para los dos ejemplos
        // OJO: Tendremos que recrearla entre una llamada y otra
        Path raiz = Path.of("mi_carpeta_borrado");
        
        // 1. Probamos el Clásico
        crearEstructura(raiz);
        borrarClasico(raiz.toFile());

        System.out.println("\n-----------------------------------\n");

        // 2. Probamos el Moderno
        crearEstructura(raiz);
        borrarModerno(raiz);
    }

    private static void crearEstructura(Path raiz) throws IOException {
        Files.createDirectories(raiz.resolve("subcarpeta"));
        Files.createFile(raiz.resolve("archivo1.txt"));
        Files.createFile(raiz.resolve("subcarpeta/archivo2.txt"));
        System.out.println("Estructura creada en: " + raiz);
    }
}