package repaso;

import java.util.List;

public class ListaInmutable {
	public static void main(String[] args) {
		List<Integer> listaInmutable = List.of(1,2,3,4,5);
		
		for(int i : listaInmutable) {
			System.out.println("Elemento: " + i);
		}
	}

}
