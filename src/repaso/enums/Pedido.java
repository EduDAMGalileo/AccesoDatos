package repaso.enums;

public class Pedido {

    private final String id;
    private EstadoDePedido estado;

    public Pedido(String id) {
        this.id = id;
        // Todo pedido recién creado empieza en PENDIENTE
        this.estado = EstadoDePedido.PENDIENTE;
    }

    /**
     * Modifica el estado del pedido validando antes si la transición es legal
     */
    public void cambiarEstado(EstadoDePedido nuevoEstado) {
        if (!this.estado.puedeIrA(nuevoEstado)) {
            throw new IllegalStateException(
                "Transición inválida: " + this.estado + " -> " + nuevoEstado
            );
        }

        this.estado = nuevoEstado;
        System.out.printf("Pedido %s: cambio con éxito a %s%n", this.id, this.estado);
    }

    public String getId() { 
        return id; 
    }

    public EstadoDePedido getEstado() { 
        return estado; 
    }
}