package repaso.claseAnonima;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EvolucionComparator {
	//Para entender este ejemplo necesitamos colecciones, lambdas y alguna cosa más.
    public static void main(String[] args) {

        // =====================================================================
        // ANTES DE JAVA 8: Clase Anónima (Mucha ceremonia y código repetitivo)
        // =====================================================================
        List<String> nombres1 = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));

        Collections.sort(nombres1, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length(); // Orden ascendente por longitud
            }
        });
        System.out.println("1. Clase Anónima:      " + nombres1);


        // =====================================================================
        // JAVA 8: Expresión Lambda (Directo al grano)
        // =====================================================================
        List<String> nombres2 = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));

        // Nos ahorramos 'new Comparator', el nombre del método y los tipos
        nombres2.sort((a, b) -> a.length() - b.length());
        System.out.println("2. Expresión Lambda:   " + nombres2);


        // =====================================================================
        // JAVA 8+: Referencia a método con Comparator.comparingInt
        // =====================================================================
        List<String> nombres3 = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));

        // Forma declarativa: "compara usando como criterio la longitud del String"
        nombres3.sort(Comparator.comparingInt(String::length));
        System.out.println("3. Method Reference: " + nombres3);


        // =====================================================================
        // BONUS: ¿Y si lo queremos de mayor a menor (descendente)?
        // =====================================================================
        List<String> nombresBonus = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));

        // Con la forma actual solo tienes que encadenar '.reversed()'
        nombresBonus.sort(Comparator.comparingInt(String::length).reversed());
        System.out.println("\nBonus (Inverso):" + nombresBonus);
    }
}
