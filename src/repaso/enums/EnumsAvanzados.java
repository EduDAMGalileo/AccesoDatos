package repaso.enums;

import java.util.Arrays;
import java.util.Comparator;

public class EnumsAvanzados {

    // =========================================================================
    // 4.1. EJEMPLO: PRIORIDAD CON ETIQUETA Y NIVEL
    // =========================================================================
    public enum Prioridad {

        // Cada constante invoca al constructor pasándole sus argumentos
        BAJA    ("Baja",    1),
        MEDIA   ("Media",   2),
        ALTA    ("Alta",    3),
        CRITICA ("Crítica", 4); // El punto y coma ';' es obligatorio al añadir atributos

        // Atributos: deben ser 'final' para garantizar la inmutabilidad
        private final String etiqueta;
        private final int    nivel;

        // Constructor: NUNCA puede ser 'public'. 
        // En un Enum el constructor es siempre privado (Java no permite crear instancias desde fuera)
        Prioridad(String etiqueta, int nivel) {
            this.etiqueta = etiqueta;
            this.nivel    = nivel;
        }

        public String getEtiqueta() { return etiqueta; }
        public int    getNivel()    { return nivel; }

        @Override
        public String toString() { 
            return etiqueta; 
        }
    }

    // =========================================================================
    // 4.2. EJEMPLO: PLANETAS CON MASA, RADIO Y LÓGICA DE NEGOCIO
    // =========================================================================
    public enum Planeta {

        // Masa en kilogramos (notación científica) y radio en metros
        MERCURIO (3.303e+23, 2.4397e6),
        VENUS    (4.869e+24, 6.0518e6),
        TIERRA   (5.976e+24, 6.37814e6),
        MARTE    (6.421e+23, 3.3972e6);

        private final double masa;  // kg
        private final double radio; // metros

        Planeta(double masa, double radio) {
            this.masa  = masa;
            this.radio = radio;
        }

        // Constante gravitacional universal (G)
        private static final double G = 6.67300E-11;

        /** Calcula la gravedad superficial del planeta en m/s²: g = G * M / R² */
        public double gravedadSuperficial() {
            return G * masa / (radio * radio);
        }

        /** Calcula el peso en Newtons (N) de un objeto según su masa en kg */
        public double peso(double masaObjeto) {
            return masaObjeto * gravedadSuperficial();
        }
    }

    // =========================================================================
    // MÉTODO MAIN DE PRUEBAS
    // =========================================================================
    public static void main(String[] args) {

        // ---------------------------------------------------------------------
        // PROBANDO EL ENUM PRIORIDAD
        // ---------------------------------------------------------------------
        System.out.println("=== 1. ENUM PRIORIDAD ===");

        Prioridad p = Prioridad.ALTA;

        // Consultamos sus atributos propios
        System.out.println("Etiqueta:  " + p.getEtiqueta()); // "Alta"
        System.out.println("Nivel:     " + p.getNivel());    // 3
        System.out.println("toString:  " + p);               // "Alta"

        // Comparar niveles numéricos para reglas de negocio
        if (p.getNivel() >= Prioridad.ALTA.getNivel()) {
            System.out.println("-> ¡Requiere atención inmediata!");
        }

        // Ordenar valores del Enum usando un Comparator
        Prioridad[] prioridades = Prioridad.values();
        // Ordenamos de mayor a menor nivel:
        Arrays.sort(prioridades, Comparator.comparingInt(Prioridad::getNivel).reversed());

        System.out.println("\nPrioridades ordenadas de mayor a menor urgencia:");
        for (Prioridad prio : prioridades) {
            System.out.printf("  Nivel %d -> %s%n", prio.getNivel(), prio.getEtiqueta());
        }


        // ---------------------------------------------------------------------
        // PROBANDO EL ENUM PLANETA
        // ---------------------------------------------------------------------
        System.out.println("\n=== 2. ENUM PLANETA (FÍSICA Y MÉTODOS) ===");

        double masaPersonaKg = 75.0; // Una persona de 75 kg

        // Peso concreto en la Tierra (Fuerza = masa * gravedad)
        double pesoEnTierra = Planeta.TIERRA.peso(masaPersonaKg);
        System.out.printf("Una persona de %.0f kg pesa %.2f N en la Tierra%n%n", masaPersonaKg, pesoEnTierra);

        // Tabla comparativa en todos los planetas
        System.out.printf("%-10s | %-15s | %-12s%n", "Planeta", "Gravedad (m/s²)", "Peso (N)");
        System.out.println("-------------------------------------------");
        for (Planeta planeta : Planeta.values()) {
            System.out.printf("%-10s | %10.2f m/s² | %8.2f N%n",
                    planeta,
                    planeta.gravedadSuperficial(),
                    planeta.peso(masaPersonaKg));
        }
    }
}