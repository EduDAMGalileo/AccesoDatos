package repaso.colecciones;

import java.util.Iterator;
import java.util.List;

public class ForEachPorDentro {

    public static void main(String[] args) {
        System.out.println("=== PARTE 1: Comparativa de sintaxis ===");
        List<String> nombres = List.of("Ana", "Luis", "Maria");

        System.out.println("--- 1. Con bucle for-each ---");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }

        System.out.println("\n--- 2. Con Iterator explícito (lo que hace el compilador) ---");
        Iterator<String> it = nombres.iterator();
        while (it.hasNext()) {
            String nombre = it.next();
            System.out.println(nombre);
        }

        System.out.println("\n==================================================");
        System.out.println("=== PARTE 2: Viendo las tripas del for-each ======");
        System.out.println("==================================================");

        ColeccionEspia espia = new ColeccionEspia(new String[]{"Ana", "Luis", "Maria"});

        System.out.println("Ejecutamos un 'for-each' simple sobre nuestra colección:\n");
        // ¡Fíjate que aquí solo escribimos un for-each normal!
        for (String elemento : espia) {
            System.out.println("   [CUERPO DEL BUCLE] Procesando elemento: " + elemento);
        }
    }
}