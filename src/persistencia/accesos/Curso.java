package persistencia.accesos;

public class Curso {

	private final int id;
	private final String codigo;
	private final String nombre;
	private final int horas;
	private final double precio;

	public Curso(int id, String codigo, String nombre, int horas, double precio) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.horas = horas;
		this.precio = precio;
	}

	public int getId() { 
		return id; 
	}

	public String getCodigo() { 
		return codigo; 
	}

	public String getNombre() { 
		return nombre;
	}

	public int getHoras() { 
		return horas;
	}

	public double getPrecio() { 
		return precio; 
	}

	@Override
	public String toString() {
		return String.format("Curso [ID=%d | Código=%s | Nombre=%s | Horas=%dh | Precio=%.2f€]",
				id, codigo, nombre, horas, precio);
	}
}