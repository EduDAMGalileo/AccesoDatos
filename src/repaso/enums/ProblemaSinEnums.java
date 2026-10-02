package repaso.enums;

public class ProblemaSinEnums {

    // -------------------------------------------------------------------------
    // OPCIÓN 1: Constantes enteras (int)
    // -------------------------------------------------------------------------
    public static final int PENDIENTE  = 0;
    public static final int PROCESANDO = 1;
    public static final int ENVIADO    = 2;
    public static final int ENTREGADO  = 3;

    // Imagina que en otra parte del sistema tienes estas otras constantes:
    public static final int PRIORIDAD_ALTA = 2; // ¡Mismo valor numérico que ENVIADO!

    // Método que procesa con enteros
    public static void gestionarEnvioPorInt(int estado) {
        if (estado == ENVIADO) {
            System.out.println("-> El pedido está en camino con el repartidor.");
        } else if (estado == ENTREGADO) {
            System.out.println("-> El pedido ya llegó.");
        } else if (estado == PENDIENTE || estado == PROCESANDO) {
            System.out.println("-> El pedido está en el almacén.");
        } else {
            System.out.println("-> ERROR: Estado desconocido (" + estado + ")");
        }
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 2: Cadenas de texto (String)
    // -------------------------------------------------------------------------
    public static void gestionarEnvioPorString(String estado) {
        if ("ENVIADO".equals(estado)) {
            System.out.println("-> El pedido está en camino con el repartidor.");
        } else {
            System.out.println("-> Pedido no enviado (o estado no reconocido: '" + estado + "')");
        }
    }

    // =========================================================================
    // DEMOSTRACIÓN DE LOS FALLOS
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("=== 1. FALLOS CON CONSTANTES ENTERAS (int) ===");

        // Fallo A: Valores absurdos
        // Java permite pasar cualquier número. Compila perfectamente sin avisarte:
        gestionarEnvioPorInt(999); 

        // Fallo B: Confusión de conceptos (Falta de seguridad de tipos / Type Safety)
        // Pasamos por error un nivel de PRIORIDAD en vez de un ESTADO.
        // Como ambos son 'int' y valen 2, Java no se queja y el programa cree que el pedido fue enviado.
        gestionarEnvioPorInt(PRIORIDAD_ALTA); 


        System.out.println("\n=== 2. FALLOS CON CADENAS DE TEXTO (String) ===");

        // Fallo C: Un simple error tipográfico (typo)
        // El compilador no sabe ortografía. Compila, pero falla silenciosamente en ejecución:
        gestionarEnvioPorString("ENVIADOO"); 

        // Fallo D: Problemas de mayúsculas / minúsculas
        gestionarEnvioPorString("enviado"); 

        // Fallo E: Valores nulos inesperados
        gestionarEnvioPorString(null); 
    }
}