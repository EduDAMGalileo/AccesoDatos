package repaso.colecciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EjemploCollections {

    public static void main(String[] args) {

        // =====================================================================
        // 1. MANIPULACIÓN Y JUEGOS DE REORDENAMIENTO
        // =====================================================================
        System.out.println("=== 1. MANIPULACIÓN ===");
        List<String> baraja = new ArrayList<>(List.of("As", "Rey", "Reina", "Jota", "Diez"));
        System.out.println("Original:        " + baraja);

        // Barajar aleatoriamente (ideal para juegos de cartas o sorteos)
        Collections.shuffle(baraja);
        System.out.println("Barajada:        " + baraja);

        // Invertir el orden completo
        Collections.reverse(baraja);
        System.out.println("Invertida:       " + baraja);

        // Intercambiar dos posiciones específicas (ej. intercambia pos 0 y pos 4)
        Collections.swap(baraja, 0, 4);
        System.out.println("Swap (0 <-> 4):  " + baraja);

        // Rotar elementos N posiciones como una ruleta
        Collections.rotate(baraja, 2); // Mueve los dos últimos al principio
        System.out.println("Rotada +2:       " + baraja);


        // =====================================================================
        // 2. BÚSQUEDAS Y ESTADÍSTICAS
        // =====================================================================
        System.out.println("\n=== 2. ESTADÍSTICAS Y CONSULTAS ===");
        List<Integer> notas = List.of(5, 8, 10, 3, 8, 8, 2);

        // Mínimo y Máximo sin necesidad de ordenar ni hacer bucles
        int min = Collections.min(notas);
        int max = Collections.max(notas);
        System.out.println("Notas:           " + notas);
        System.out.println("Nota mínima:     " + min);
        System.out.println("Nota máxima:     " + max);

        // Frecuencia: ¿Cuántas veces aparece un elemento?
        int vecesOcho = Collections.frequency(notas, 8);
        System.out.println("Veces que sale 8:" + vecesOcho);

        // Disjoint: Comprueba si dos listas NO tienen NADA en común (conjuntos disjuntos)
        List<String> frutasA = List.of("Manzana", "Pera");
        List<String> frutasB = List.of("Plátano", "Naranja");
        List<String> frutasC = List.of("Pera", "Kiwi");

        boolean noCompartenNada = Collections.disjoint(frutasA, frutasB); // true
        boolean compartenAlgo   = Collections.disjoint(frutasA, frutasC); // false (ambas tienen "Pera")

        System.out.println("¿frutasA y frutasB son totalmente distintas?: " + noCompartenNada);
        System.out.println("¿frutasA y frutasC son totalmente distintas?: " + compartenAlgo);


        // =====================================================================
        // 3. SEGURIDAD Y VISTAS INMUTABLES (Patrón Defensivo)
        // =====================================================================
        System.out.println("\n=== 3. PROTECCIÓN DE DATOS (Read-Only) ===");
        List<String> listaModificable = new ArrayList<>(List.of("Datos", "Sensibles"));

        // Creamos una vista de solo lectura:
        List<String> soloLectura = Collections.unmodifiableList(listaModificable);

        System.out.println("Elementos: " + soloLectura);
        
        try {
            // Si alguien intenta modificar la lista protegida:
            soloLectura.add("Intruso");
        } catch (UnsupportedOperationException e) {
            System.out.println(">> ¡Excepción capturada! La lista está protegida contra modificaciones.");
        }
    }
}