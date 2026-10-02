package repaso.clasesEstaticas;

import java.util.ArrayList;
import java.util.List;

public class Grafo { 

	private List<Nodo> nodos = new ArrayList<>(); 

	// Clase interna estática: no necesita instancia de Grafo 
	// Podemos tener Nodos o tener Grafos con Nodos
	public static class Nodo { 
		private final String id; 
		private final List<Nodo> vecinos = new ArrayList<>(); 

		public Nodo(String id) { 
			this.id = id; 
		} 

		public void conectar(Nodo otro) { 
			vecinos.add(otro); 
			otro.vecinos.add(this); 
		} 

		public String getId() { 
			return id; 
		} 

		public List<Nodo> getVecinos() { 
			return vecinos; 
		} 

	} 

	//Esto es del Grafo, no del Nodo
	public void añadirNodo(Nodo n) { 
		nodos.add(n); 
	} 

} 