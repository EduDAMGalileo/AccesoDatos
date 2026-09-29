package repaso.claseAnonima;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// =============================================================================
// PASO 1: Clase separada con nombre propio (Java clásico / tradicional)
// Requiere definir una clase completa e implementar la interfaz.
// =============================================================================
class ComparadorPorLongitud implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        return Integer.compare(a.length(), b.length());
    }
}

public class EvolucionComparator2 {

    public static void main(String[] args) {

        // ---------------------------------------------------------------------
        // 1. CLASE SEPARADA CON NOMBRE
        // ---------------------------------------------------------------------
        List<String> lista1 = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));
        lista1.sort(new ComparadorPorLongitud());
        System.out.println("1. Clase separada con nombre: " + lista1);


        // ---------------------------------------------------------------------
        // 2. CLASE ANÓNIMA
        // Ahorramos crear un archivo/clase externa; la creamos al vuelo.
        // ---------------------------------------------------------------------
        List<String> lista2 = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));
        lista2.sort(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(a.length(), b.length());
            }
        });
        System.out.println("2. Clase anónima:             " + lista2);


        // ---------------------------------------------------------------------
        // 3. EXPRESIÓN LAMBDA (Java 8+)
        // Eliminamos todo el código repetitivo y dejamos solo la lógica.
        // ---------------------------------------------------------------------
        List<String> lista3 = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));
        lista3.sort((a, b) -> Integer.compare(a.length(), b.length()));
        System.out.println("3. Expresión Lambda:          " + lista3);


        // ---------------------------------------------------------------------
        // 4. REFERENCIA A MÉTODO (Java 8+ máxima concisión)
        // Estilo puramente declarativo: "ordena comparando la longitud del String".
        // ---------------------------------------------------------------------
        List<String> lista4 = new ArrayList<>(List.of("Carlos", "Ana", "Beatriz", "David"));
        lista4.sort(Comparator.comparingInt(String::length));
        System.out.println("4. Referencia a método:       " + lista4);
    }
}