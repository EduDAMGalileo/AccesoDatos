package repaso.claseAnonima;

//Usa la clase Animal
public class AnonimaExtiendeClase {

	public static void main(String[] args) {

		// 1. Objeto normal de la clase padre (sin sobreescribir nada)
		Animal animalNormal = new Animal("Bicho");

		// 2. Clase anónima que HEREDA de Animal y sobreescribe hablar()
		// Equivale a: class Perro extends Animal { ... }
		Animal perro = new Animal("Rex") {
			@Override
			void hablar() {
				System.out.println(nombre + " dice: ¡Guau!");
			}
		};

		// 3. Otra clase anónima distinta que también hereda de Animal
		// Equivale a: class Gato extends Animal { ... }
		Animal gato = new Animal("Misifu") {
			@Override
			void hablar() {
				System.out.println(nombre + " dice: ¡Miau!");
			}
		};

		// ==========================================
		// PROBAMOS EL POLIMORFISMO
		// ==========================================
		System.out.println("=== COMPORTAMIENTO DE CADA OBJETO ===");
		// Comportamiento original
		animalNormal.hablar(); 
		// Comportamientos modificados al vuelo
		perro.hablar();        
		gato.hablar();         
	}
}