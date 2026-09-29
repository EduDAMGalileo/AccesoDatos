package repaso.colecciones;

public class AppBiblioteca {
	 public static void main(String[] args) {
	        System.out.println("=== CREANDO Y LLENANDO LA BIBLIOTECA ===");
	        Biblioteca miBiblioteca = new Biblioteca(10);

	        miBiblioteca.añadir("Don Quijote de la Mancha");
	        miBiblioteca.añadir("Cien años de soledad");
	        miBiblioteca.añadir("La Regenta");
	        miBiblioteca.añadir("El principito");

	        System.out.println("\n=== RECORRIENDO CON BUCLE FOR-EACH ===");
	        // Gracias a Iterable, recorremos la Biblioteca de forma limpia y directa:
	        for (String libro : miBiblioteca) {
	            System.out.println("- " + libro);
	        }

	        System.out.println("\n=== DEMOSTRACIÓN DE ENCAPSULACIÓN SEGURA ===");
	        // Observa la gran ventaja:
	        // Aunque la capacidad del array era 10, el bucle for-each SOLO se ejecuta 4 veces.
	        // Nunca vemos valores 'null' porque el iterador controla de forma privada
	        // la variable 'cantidad' y oculta el array interno.
	    }

}
