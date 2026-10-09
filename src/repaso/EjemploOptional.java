package repaso;

import java.util.Optional;

public class EjemploOptional {

    public static void main(String[] args) {

        // =============================================================
        // CASO 1: El dato SÍ existe dentro de la caja
        // =============================================================
        Optional<String> resultado1 = buscarCapital("España");

        // Forma 1: Con if y isPresent() (el equivalente actual al != null)
        if (resultado1.isPresent()) {
            String capital = resultado1.get(); // .get() saca el dato de la caja
            System.out.println("Forma 1 -> Encontrada: " + capital);
        }

        // Forma 2: Con ifPresent (ejecuta la acción solo si hay dato)
        resultado1.ifPresent(capital -> System.out.println("Forma 2 -> Encontrada: " + capital));


        System.out.println("\n----------------------------------------\n");


        // =============================================================
        // CASO 2: El dato NO existe (la caja viene vacía)
        // =============================================================
        Optional<String> resultado2 = buscarCapital("Narnia");

        // ¿Qué pasa con isPresent()?
        if (resultado2.isPresent()) {
            System.out.println("Encontrada: " + resultado2.get());
        } else {
            System.out.println("Forma 1 -> No se encontró la capital de ese país.");
        }

        // Forma 3: orElse() -> "Dame el dato, o si está vacía, dame este valor por defecto"
        String capitalOTextoPorDefecto = resultado2.orElse("Desconocida");
        System.out.println("Forma 3 (con orElse) -> Capital: " + capitalOTextoPorDefecto);
    }

    // =================================================================
    // EL MÉTODO QUE DEVUELVE OPTIONAL
    // =================================================================
    public static Optional<String> buscarCapital(String pais) {
        if ("España".equalsIgnoreCase(pais)) {
            // Optional.of(...) envuelve un valor en la caja
            return Optional.of("Madrid"); 
        } else if ("Francia".equalsIgnoreCase(pais)) {
            return Optional.of("París");
        } else {
            // Optional.empty() devuelve una caja expresamente vacía
            return Optional.empty(); 
        }
    }
}