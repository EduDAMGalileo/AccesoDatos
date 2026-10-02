package repaso.enums;

public class EjemploEnum {

    public static void main(String[] args) {

        System.out.println("=== 1. ASIGNACIÓN Y SEGURIDAD DE TIPOS ===");

        // Usamos el Enum como cualquier tipo de dato (como un int o un String)
        EstadoPedido estado = EstadoPedido.PENDIENTE;
        System.out.println("Estado actual: " + estado); 

        // Cambiar a otro valor válido:
        estado = EstadoPedido.ENVIADO;

        // Las siguientes líneas provocarían un ERROR DE COMPILACIÓN antes de ejecutar:
        // estado = EstadoPedido.VOLANDO; // Error: 'VOLANDO' no existe en el Enum
        // estado = 2;                    // Error: no puedes meter un 'int' en un EstadoPedido
        // estado = "ENVIADO";            // Error: no puedes meter un 'String'


        System.out.println("\n=== 2. COMPARACIÓN CON == ===");

        // Como cada valor del Enum es una única instancia en toda la memoria (Singleton),
        // se comparan directamente con '==', lo que resulta más rápido y limpio.
        if (estado == EstadoPedido.ENVIADO) {
            System.out.println("-> El pedido ya ha sido enviado (usando ==).");
        }

        // Gran ventaja de '==' sobre '.equals()': Protección frente a null
        EstadoPedido estadoDesconocido = null;

        // Con '==' no pasa nada raro: simplemente evalúa a false
        if (estadoDesconocido == EstadoPedido.ENVIADO) {
            System.out.println("No entrará aquí.");
        }

        // Con '.equals()', si la variable es null, lanzaría un temido NullPointerException:
        // estadoDesconocido.equals(EstadoPedido.ENVIADO); // ¡BOOM! Lanza excepción


        System.out.println("\n=== 3. SWITCH CLÁSICO ===");

        estado = EstadoPedido.PROCESANDO;

        // Switch tradicional de sentencias con 'break':
        switch (estado) {
            case PENDIENTE:   System.out.println("Esperando confirmación."); break;
            case PROCESANDO:  System.out.println("Preparando el pedido.");   break;
            case ENVIADO:     System.out.println("En camino.");              break;
            case ENTREGADO:   System.out.println("Entregado.");              break;
            case CANCELADO:   System.out.println("Pedido cancelado.");       break;
        }


        System.out.println("\n=== 4. SWITCH EXPRESSION (Java 14+) ===");

        // La Switch Expression devuelve un valor directamente (flechas '->').
        // Además es EXHAUSTIVA: Si borras el caso CANCELADO, el código NO COMPILA
        // avisándote de que no cubres todos los estados posibles.
        String mensaje = switch (estado) {
            case PENDIENTE   -> "Esperando confirmación.";
            case PROCESANDO  -> "Preparando el pedido.";
            case ENVIADO     -> "En camino.";
            case ENTREGADO   -> "Entregado.";
            case CANCELADO   -> "Pedido cancelado.";
        };

        System.out.println("Resultado de la expresión: " + mensaje);
    }
}