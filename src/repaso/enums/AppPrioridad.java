package repaso.enums;

import java.util.Arrays;
import java.util.Comparator;

public class AppPrioridad {

    public static void main(String[] args) {
        Prioridad p = Prioridad.ALTA;

        // Acceder a los datos que viajan con el enum
        System.out.println("Etiqueta: " + p.getEtiqueta()); // "Alta"
        System.out.println("Nivel:    " + p.getNivel());    // 3
        System.out.println("toString: " + p);               // "Alta"

        // Lógica de negocio comparando niveles
        if (p.getNivel() >= Prioridad.ALTA.getNivel()) {
            System.out.println("-> Requiere atención inmediata.");
        }

        // Ordenar los valores del enum por su nivel numérico
        Prioridad[] todas = Prioridad.values();
        //Aquí hay código complejo, el Comparator
        Arrays.sort(todas, Comparator.comparingInt(Prioridad::getNivel));

        System.out.println("\nPrioridades ordenadas de menor a mayor:");
        for (Prioridad item : todas) {
            System.out.println("Nivel " + item.getNivel() + " -> " + item.getEtiqueta());
        }
    }
}