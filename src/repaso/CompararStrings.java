package repaso;

public class CompararStrings {
	public static void main(String[] args) {
		String nombre = "Eduardo";
		String nombre2 = "Eduardo";
		
		System.out.println("nombre igual a nombre2 --> " + (nombre==nombre2));
		System.out.println("nombre igual a nombre2 --> " + nombre.equals(nombre2));
		
		String nombre3 = new String ("Eduardo");
		
		System.out.println("nombre igual a nombre3 --> " + (nombre==nombre3));
		System.out.println("nombre igual a nombre3 --> " + nombre.equals(nombre3));
		
		String nombre4 = "Edu";
		nombre4=nombre4 + "ardo";
		
		System.out.println("nombre igual a nombre4 --> " + (nombre==nombre4));
		System.out.println("nombre igual a nombre4 --> " + nombre.equals(nombre4));
	}

}
