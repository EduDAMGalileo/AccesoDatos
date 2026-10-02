package repaso.enums;

import java.util.List;

public class AppEnumInterfaces {

    // =========================================================================
    // MÉTODO POLIMÓRFICO:
    // Acepta CUALQUIER cosa que implemente Describible (La interfaz).
    // No le importa si es un EstadoEnvio, un EstadoIncidencia o incluso una clase normal.
    // =========================================================================
    public static void mostrar(Describible d) {
        System.out.println("-> Descripción: " + d.getDescripcion());
    }

    public static void main(String[] args) {

        System.out.println("=== 1. USO DIRECTO POLIMÓRFICO ===");
        
        // Le pasamos una constante de EstadoEnvio:
        mostrar(EstadoEnvio.ENVIADO);

        // Le pasamos una constante de EstadoIncidencia al MISMO método:
        mostrar(EstadoIncidencia.EN_TRAMITE);


        System.out.println("\n=== 2. LISTA HETEROGÉNEA DE ENUMS ===");

        // Podemos meter valores de DIFERENTES Enums dentro de la misma lista
        // porque ambos son de tipo 'Describible':
        List<Describible> eventosDelDia = List.of(
                EstadoEnvio.PROCESANDO,
                EstadoIncidencia.ABIERTA,
                EstadoEnvio.ENTREGADO,
                EstadoIncidencia.RESUELTA
        );

        // Recorremos la lista tratando a todos por igual:
        for (Describible item : eventosDelDia) {
            mostrar(item);
        }
    }
}
