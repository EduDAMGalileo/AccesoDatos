package repaso.claseAnonima;

//Clase base normal y corriente (concreta, no interfaz)
class Animal {
	protected String nombre;

	Animal(String nombre) {
		this.nombre = nombre;
	}

	void hablar() {
		System.out.println(nombre + " hace algún sonido generico");
	}
}

