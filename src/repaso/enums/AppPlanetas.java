package repaso.enums;

public class AppPlanetas {

    public static void main(String[] args) {
        double masaPersonaKg = 75.0;

        // Cálculo directo con un solo planeta
        double pesoEnTierra = Planeta.TIERRA.peso(masaPersonaKg);
        System.out.printf("Una persona de %.0f kg pesa %.2f N en la Tierra%n%n", 
                masaPersonaKg, pesoEnTierra);

        // Tabla comparativa recorriendo todos los planetas
        System.out.printf("%-10s | %-16s | %-10s%n", "Planeta", "Gravedad (m/s²)", "Peso (N)");
        System.out.println("--------------------------------------------");

        for (Planeta p : Planeta.values()) {
            System.out.printf("%-10s | %13.2f m/s² | %7.2f N%n",
                    p.name(),
                    p.gravedadSuperficial(),
                    p.peso(masaPersonaKg));
        }
    }
}
