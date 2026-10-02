package repaso.enums;

public enum EstadoPedido {
	PENDIENTE,
	PROCESANDO,
	ENVIADO,
	ENTREGADO,
	CANCELADO;

	@Override
	public String toString() {
		return switch (this) {
		case PENDIENTE  -> "Pendiente de confirmación";
		case PROCESANDO -> "En preparación en el almacén";
		case ENVIADO    -> "En camino con el repartidor";
		case ENTREGADO  -> "Entregado con éxito";
		case CANCELADO  -> "Pedido cancelado";
		};
	}
}
