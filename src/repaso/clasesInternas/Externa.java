package repaso.clasesInternas;

public class Externa {

	private String nombre = "Soy la clase EXTERNA";

	public void saludar() {
		System.out.println(">> Saludo que viene de EXTERNA");
	}

	// ==========================================
	// CLASE INTERNA
	// ==========================================
	public class Interna {

		private String nombre = "Soy la clase INTERNA";

		public void saludar() {
			System.out.println(">> Saludo que viene de INTERNA");
		}

		public void probar() {
			String nombre = "Soy una variable LOCAL del método";

			// 1. Acceso a los atributos con el mismo nombre:
			System.out.println("--- Variables ---");
			// La más cercana (local)
			System.out.println(nombre);
			// La de esta clase (Interna)
			System.out.println(this.nombre);
			// La de la clase de fuera (Externa)
			System.out.println(Externa.this.nombre);  

			// 2. Llamada a métodos con el mismo nombre:
			System.out.println("\n--- Métodos ---");
			// Ejecuta el método de Interna
			this.saludar();
			// Ejecuta el método de Externa
			Externa.this.saludar();  
		}
	}

}