package repaso.claseAnonima;

public class AppSaludo {
	public static void main (String[] args) {
		// Clase anónima que implementa Saludo:
		Saludo saludoFormal = new Saludo() {
		    @Override
		    public void saludar(String nombre) {
		        System.out.println("Buenos días, " + nombre + ".");
		    }
		};

		Saludo saludoInformal = new Saludo() {
		    @Override
		    public void saludar(String nombre) {
		        System.out.println("Hola " + nombre + "!");
		    }
		};

		saludoFormal.saludar("Señor López");   
		saludoInformal.saludar("Maricarmen");

	}

}
