package repaso.colecciones;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ejemplo: Encapsulación de una colección interna haciendo
 * que la clase sea Iterable.
 *
*/
public class Biblioteca implements Iterable<String> {

    // 1. ENCAPSULACIÓN:
    // Los datos internos son privados. Quien use la clase Biblioteca
    // NO SABE (ni le importa) si por dentro usamos un String[], un ArrayList
    // o una base de datos. Solo sabe que puede añadir libros y recorrerlos.
    private final String[] libros;
    private int cantidad = 0;

    public Biblioteca(int capacidad) {
        this.libros = new String[capacidad];
    }

    public void añadir(String libro) {
        if (cantidad >= libros.length) {
            System.out.println("(!) Capacidad máxima alcanzada. No se pudo añadir: " + libro);
            return;
        }
        libros[cantidad++] = libro;
    }

    // 2. CONTRATO ITERABLE:
    // Al implementar iterator(), le damos permiso al compilador para usar 'for-each'.
    @Override
    public Iterator<String> iterator() {
        
        // Usamos una CLASE ANÓNIMA para implementar el recorrido sobre nuestro array privado
        return new Iterator<String>() {
            private int indice = 0;

            @Override
            public boolean hasNext() {
                // Hay más libros mientras el cursor no supere la cantidad de libros añadidos
                // (Ignoramos las posiciones vacías del array).
                return indice < cantidad;
            }

            @Override
            public String next() {
                // Buena práctica del contrato de Java: si piden un elemento y no hay más, se lanza esta excepción.
                if (!hasNext()) {
                    throw new NoSuchElementException("No quedan más libros en la biblioteca.");
                }
                return libros[indice++];
            }
        };
    }
  }