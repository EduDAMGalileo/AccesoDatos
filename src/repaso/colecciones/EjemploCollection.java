package repaso.colecciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class EjemploCollection {

    public static void main(String[] args) {

        // =====================================================================
        // OPERACIONES MASIVAS (TEORÍA DE CONJUNTOS)
        // =====================================================================
        System.out.println("=== 1. OPERACIONES MASIVAS ===");

        // UNIÓN: addAll()
        // Añade todos los elementos del segundo grupo al primero
        List<String> base = new ArrayList<>(List.of("Ana", "Luis", "Maria"));
        List<String> nuevos = List.of("Luis", "Pedro");

        base.addAll(nuevos);
        System.out.println("1. Union (addAll):         " + base);

        // DIFERENCIA / RESTA: removeAll()
        // Elimina del primero todos los que aparezcan en el segundo
        base.removeAll(List.of("Luis", "Pedro"));
        System.out.println("2. Resta (removeAll):       " + base);

        // INTERSECCIÓN: retainAll()
        // Conserva SOLO los elementos que estén presentes en ambos grupos
        List<String> equipoA = new ArrayList<>(List.of("Ana", "Carlos", "David", "Beatriz"));
        List<String> equipoB = List.of("David", "Beatriz", "Elena");

        equipoA.retainAll(equipoB);
        System.out.println("3. Interseccion (retainAll):" + equipoA); 
        // SUBCONJUNTO: containsAll()
        // Devuelve true solo si TODOS los elementos indicados están presentes
        List<String> plantilla = List.of("Ana", "Luis", "Maria", "Pedro", "Sara");
        List<String> requeridos = List.of("Ana", "Pedro");
        List<String> otros = List.of("Ana", "Lucas");

        System.out.println("4. ¿Contiene a Ana y Pedro?: " + plantilla.containsAll(requeridos)); 
        System.out.println("   ¿Contiene a Ana y Lucas?: " + plantilla.containsAll(otros));     


        // =====================================================================
        // 2. ELIMINACIÓN CONDICIONAL MODERNA: removeIf()
        // =====================================================================
        System.out.println("\n=== 2. FILTRADO CON removeIf ===");
        List<String> nombres = new ArrayList<>(List.of("Ana", "Alejandro", "Bea", "Bernardo"));

        // Elimina todos los nombres que tengan más de 4 letras
        nombres.removeIf(nombre -> nombre.length() > 4);
        System.out.println("Nombres cortos (<= 4 letras): " + nombres);


        // =====================================================================
        // 3. EL PODER DEL POLIMORFISMO DE 'Collection'
        // =====================================================================
        System.out.println("\n=== 3. POLIMORFISMO ===");
        // Un método que recibe 'Collection' puede aceptar tanto un List como un Set:
        List<String> miLista = List.of("Madrid", "Barcelona");
        Set<String> miConjunto = Set.of("Sevilla", "Valencia");

        imprimirCualquierColeccion("Lista", miLista);
        imprimirCualquierColeccion("Set", miConjunto);
    }

    /**
     * Al usar 'Collection' como parámetro, este método funciona con CUALQUIER
     * estructura de datos de Java (ArrayList, HashSet, LinkedList, TreeSet, etc.)
     */
    public static void imprimirCualquierColeccion(String tipo, Collection<?> coleccion) {
        System.out.println(tipo + " (tamaño: " + coleccion.size() + ") -> " + coleccion);
    }
}