package repaso.enums;

public class EnumsConMetodos {

    public static void main(String[] args) {
        double a = 10.0;
        double b = 3.0;

        System.out.println("=== POLIMORFISMO EN ACCIÓN CON EL ENUM ===");

        // Recorremos todas las operaciones sin un solo 'if' ni 'switch'.
        // Cada constante sabe exactamente qué cálculo matemático ejecutar:
        for (Operacion op : Operacion.values()) {
            double resultado = op.aplicar(a, b);
            System.out.printf("  %.1f %s %.1f = %.2f%n", a, op, b, resultado);
        }

        // =====================================================================
        // USO DIRECTO Y CONTROL DE EXCEPCIONES
        // =====================================================================
        System.out.println("\n=== PROBANDO LA VALIDACIÓN DE DIVISIÓN POR CERO ===");
        
        try {
            Operacion div = Operacion.DIVISION;
            System.out.println("Intentando dividir 5 / 0...");
            div.aplicar(5, 0);
        } catch (ArithmeticException e) {
            System.out.println("-> Excepción capturada con éxito: " + e.getMessage());
        }
    }
}