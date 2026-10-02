package repaso.enums;

public class MetodosEnum {

    public static void main(String[] args) {

        EstadoPedido estado = EstadoPedido.ENVIADO;

        // =====================================================================
        //name() frente a toString()
        // =====================================================================
        System.out.println("=== 1. name() VS toString() ===");
        
        // name() NUNCA cambia: siempre devuelve el identificador técnico exacto en mayúsculas
        System.out.println("name():     " + estado.name());     // "ENVIADO" (ideal para base de datos/JSON)
        
        // toString() devuelve el texto bonito que programamos nosotros arriba
        System.out.println("toString(): " + estado.toString()); // "En camino con el repartidor" (ideal para mostrar en pantalla)


        // =====================================================================
        // ordinal() — La posición física
        // =====================================================================
        System.out.println("\n=== 2. ordinal() ===");
        System.out.println("Posición de PENDIENTE:  " + EstadoPedido.PENDIENTE.ordinal());  // 0
        System.out.println("Posición de ENVIADO:    " + EstadoPedido.ENVIADO.ordinal());    // 2
        System.out.println("Posición de CANCELADO:  " + EstadoPedido.CANCELADO.ordinal());  // 4
        
        //  PELIGRO: Si mañana añades 'PAGADO' entre PENDIENTE y PROCESANDO,
        //  ENVIADO pasará de ser el 2 a ser el 3. ¡Nunca guardes el ordinal en base de datos!


        // =====================================================================
        // values() — Iterar sobre todos los valores
        // =====================================================================
        System.out.println("\n=== 3. values() (recorrer el Enum) ===");
        
        // values() devuelve un array: EstadoPedido[]
        for (EstadoPedido e : EstadoPedido.values()) {
            System.out.printf("  [%d] Constante: %-10s -> Texto: \"%s\"%n", 
                    e.ordinal(), e.name(), e);
        }


        // =====================================================================
        // valueOf(String) — Convertir texto a Enum
        // =====================================================================
        System.out.println("\n=== 4. valueOf(String) ===");

        // Caso A: Conversión perfecta (coincidencia exacta)
        EstadoPedido obtenido = EstadoPedido.valueOf("PROCESANDO");
        System.out.println("Conversión correcta: " + obtenido.name());

        // Caso B: Fallo por minúsculas (sensible a mayúsculas/minúsculas)
        try {
            System.out.println("Intentando convertir 'enviado'...");
            EstadoPedido fallo = EstadoPedido.valueOf("enviado");
        } catch (IllegalArgumentException ex) {
            System.out.println("-> ¡ERROR capturado! No coincide exactamente: " + ex.getMessage());
        }

        // Caso C: La forma segura y recomendada si el dato viene de fuera
        // (por ejemplo, escrito por un usuario en un formulario web con espacios o minúsculas)
        String entradaUsuario = "  cancelado  ";
        
        // Limpiamos espacios con trim() y pasamos a mayúsculas con toUpperCase()
        EstadoPedido seguro = EstadoPedido.valueOf(entradaUsuario.trim().toUpperCase());
        System.out.println("Conversión segura desde '" + entradaUsuario + "' -> " + seguro.name());
    }
}
