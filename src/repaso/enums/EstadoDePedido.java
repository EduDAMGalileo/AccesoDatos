package repaso.enums;

//Renombrado a EstadoDePedido para evitar conflictos en el mismo paquete
public enum EstadoDePedido {

	PENDIENTE {
		@Override
		public boolean puedeIrA(EstadoDePedido siguiente) {
			// Desde PENDIENTE solo podemos empezar a procesar o cancelar
			return siguiente == PROCESANDO || siguiente == CANCELADO;
		}
	},

	PROCESANDO {
		@Override
		public boolean puedeIrA(EstadoDePedido siguiente) {
			// Desde el almacén solo puede salir al repartidor o cancelarse
			return siguiente == ENVIADO || siguiente == CANCELADO;
		}
	},

	ENVIADO {
		@Override
		public boolean puedeIrA(EstadoDePedido siguiente) {
			// Si ya está en la furgoneta de reparto, solo puede terminar entregado
			return siguiente == ENTREGADO;
		}
	},

	ENTREGADO {
		@Override
		public boolean puedeIrA(EstadoDePedido siguiente) {
			// Estado final: una vez entregado al cliente, no se puede cambiar de estado
			return false;
		}
	},

	CANCELADO {
		@Override
		public boolean puedeIrA(EstadoDePedido siguiente) {
			// Estado final: no se puede reactivar un pedido cancelado
			return false;
		}
	};

	/**
	 * Cada constante está obligada a definir sus propias transiciones válidas.
	 */
	public abstract boolean puedeIrA(EstadoDePedido siguiente);
}
