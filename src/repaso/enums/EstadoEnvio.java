package repaso.enums;

//Este Enum representa el ciclo del envío e implementa la interfaz
public enum EstadoEnvio implements Describible {

	PENDIENTE  ("Esperando confirmación del cliente"),
	PROCESANDO ("El pedido está siendo preparado en almacén"),
	ENVIADO    ("El pedido está en camino con el transportista"),
	ENTREGADO  ("El pedido ha sido entregado en destino"),
	CANCELADO  ("El pedido fue cancelado antes de salir");

	// Atributo que almacena el texto
	private final String descripcion;

	// Constructor privado del enum
	EstadoEnvio(String descripcion) {
		this.descripcion = descripcion;
	}

	// Obligado por la interfaz Describible
	@Override
	public String getDescripcion() {
		return this.descripcion;
	}
}
