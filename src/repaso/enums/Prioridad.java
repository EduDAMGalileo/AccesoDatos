package repaso.enums;

public enum Prioridad {

	BAJA    ("Baja", 1),
	MEDIA   ("Media", 2),
	ALTA    ("Alta", 3),
	CRITICA ("Crítica", 4);

	// Atributos inmutables asociados a cada valor
	private final String etiqueta;
	private final int nivel;

	// Constructor privado: se ejecuta una vez por cada constante al iniciar la JVM
	Prioridad(String etiqueta, int nivel) {
		this.etiqueta = etiqueta;
		this.nivel = nivel;
	}

	public String getEtiqueta() { 
		return etiqueta; 
	}

	public int getNivel() { 
		return nivel; 
	}

	@Override
	public String toString() { 
		return etiqueta; 
	}
}