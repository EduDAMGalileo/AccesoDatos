package repaso.enums;

public class AppMaquinaEstados {

    public static void main(String[] args) {

        // =====================================================================
        // CASO 1: FLUJO FELIZ (Transiciones legales completas)
        // =====================================================================
        System.out.println("=== 1. FLUJO NORMAL DE UN PEDIDO ===");
        Pedido pedido1 = new Pedido("PED-001");
        System.out.println("Estado inicial: " + pedido1.getEstado());

        pedido1.cambiarEstado(EstadoDePedido.PROCESANDO); // Legal
        pedido1.cambiarEstado(EstadoDePedido.ENVIADO);    // Legal
        pedido1.cambiarEstado(EstadoDePedido.ENTREGADO);  // Legal


        // =====================================================================
        // CASO 2: CANCELACIÓN DESDE EL PRINCIPIO
        // =====================================================================
        System.out.println("\n=== 2. FLUJO DE CANCELACIÓN TEMPRANA ===");
        Pedido pedido2 = new Pedido("PED-002");
        pedido2.cambiarEstado(EstadoDePedido.CANCELADO);  // Legal desde PENDIENTE


        // =====================================================================
        // CASO 3: INTENTO DE TRANSICIÓN ILEGAL (Saltarse pasos)
        // =====================================================================
        System.out.println("\n=== 3. PROBANDO TRANSICIONES ILEGALES ===");
        Pedido pedido3 = new Pedido("PED-003");

        try {
            System.out.println("Intentando pasar directo de PENDIENTE a ENTREGADO...");
            pedido3.cambiarEstado(EstadoDePedido.ENTREGADO); // No se puede saltar pasos
        } catch (IllegalStateException e) {
            System.out.println("-> Error capturado: " + e.getMessage());
        }

        try {
            System.out.println("\nIntentando cancelar el PED-001 que ya estaba ENTREGADO...");
            pedido1.cambiarEstado(EstadoDePedido.CANCELADO); // ENTREGADO es estado final
        } catch (IllegalStateException e) {
            System.out.println("-> Error capturado: " + e.getMessage());
        }
    }
}
