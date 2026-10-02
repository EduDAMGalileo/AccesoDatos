package repaso.enums;

//Un Enum totalmente diferente pero que comparte el mismo contrato 'Describible'
public enum EstadoIncidencia implements Describible {

	ABIERTA      ("Incidencia registrada, pendiente de revisión"),
	EN_TRAMITE   ("El departamento de soporte está investigando"),
	RESUELTA     ("Problema solucionado favorablemente"),
	RECHAZADA    ("La reclamación no procede");

	private final String descripcion;

	EstadoIncidencia(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String getDescripcion() {
		return this.descripcion;
	}
}