package repaso.clasesEstaticas;

public class AppGrafo {
	public static void main (String[] args) {
		// Se instancia SIN necesitar un objeto Grafo: 
		Grafo.Nodo madrid = new Grafo.Nodo("Madrid"); 
		Grafo.Nodo barcelona = new Grafo.Nodo("Barcelona"); 
		madrid.conectar(barcelona); 

		// Podemos añadirlos a un grafo: 
		Grafo g = new Grafo(); 
		g.añadirNodo(madrid); 
		g.añadirNodo(barcelona); 
		
	}
}
