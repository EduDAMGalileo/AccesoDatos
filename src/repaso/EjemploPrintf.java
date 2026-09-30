package repaso;

import java.util.Locale;

public class EjemploPrintf {

    public static void main(String[] args) {
    	
    	System.out.println(System.getProperty("native.encoding"));

        System.out.println("=== NÚMEROS ENTEROS (%d, %X) ===");
        int unidades = 42;
        long poblacion = 47420000L;

        System.out.printf("Normal:                   %d%n", unidades);
        System.out.printf("Con separador de miles:   %,d%n", poblacion);
        System.out.printf("Con signo obligatorio:    %+d%n", unidades);
        System.out.printf("Relleno con ceros (5 dgt):%05d%n", unidades);      
        System.out.printf("Hexadecimal (min/MAY):    %x | 0x%02X%n", 255, 255); 

        System.out.println("\n=== DECIMALES Y DINERO (%f) ===");
        double precio = 1299.9876;
        double pi = Math.PI;

        System.out.printf("Por defecto (6 decimales): %f%n", precio);
        System.out.printf("Redondeado a 2 decimales:  %.2f €%n", precio);    
        System.out.printf("Miles y 2 decimales:       %,.2f €%n", precio);   
        System.out.printf("Pi con 4 decimales:        %.4f%n", pi);

        System.out.println("\n=== TEXTO Y ALINEACIÓN EN COLUMNAS (%s) ===");
        String producto1 = "Café";
        String producto2 = "Portátil Gamer";

        // %-15s = texto alineado a la izquierda ocupando 15 espacios
        // %8.2f = número alineado a la derecha ocupando 8 espacios
        System.out.printf("%-15s | %8s%n", "PRODUCTO", "PRECIO");
        System.out.println("---------------------------");
        System.out.printf("%-15s | %8.2f €%n", producto1, 1.50);
        System.out.printf("%-15s | %8.2f €%n", producto2, 1250.00);

        System.out.println("\n=== TRUNCAR CADENAS DE TEXTO (%.N s) ===");
        String textoLargo = "Programación en Java";
        System.out.printf("Texto completo:    %s%n", textoLargo);
        System.out.printf("Solo 7 caracteres: %.7s...%n", textoLargo);

        System.out.println("\n=== REUTILIZAR EL MISMO ARGUMENTO (<) ===");
        int numero = 15;
        // El símbolo '<' significa: "usa el mismo dato anterior pero con otro formato"
        System.out.printf("Decimal: %d | Hex: %<X | Octal: %<o%n", numero);

        System.out.println("\n=== FORZAR UN IDIOMA ESPECÍFICO (Locale) ===");
        double valor = 1234.56;
        // En España usa coma para decimales; en EEUU usa punto
        System.out.printf(Locale.of("es", "ES"), "Formato Español: %,.2f%n", valor);
        System.out.printf(Locale.US,            "Formato USA:     %,.2f%n", valor);
    }
}