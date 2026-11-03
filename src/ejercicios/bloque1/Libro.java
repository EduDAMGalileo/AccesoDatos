package ejercicios.bloque1;

public class Libro {
	private String titulo;
	private String autor;
	private String editorial;
	private int ano_publicacion;
	
	public Libro(String titulo, String autor, String editorial, int ano_publicacion) {
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.ano_publicacion = ano_publicacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public int getAno_publicacion() {
		return ano_publicacion;
	}
	
	
	
	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial + ", ano_publicacion="
				+ ano_publicacion + "]";
	}

	public String toCSV() {
		//return titulo + "," + autor + "," + editorial + "," + ano_publicacion;
		return String.format("%s,%s,%s,%d", titulo, autor, editorial, ano_publicacion);
		
	}
	
	
	
	
	
	

}
