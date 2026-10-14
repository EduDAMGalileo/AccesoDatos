package persistencia.P4accesos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ModificarSecuencialDesastre {

    public static void main(String[] args) throws IOException {
        Path archivo = Path.of("alumnos.csv");

        // 1. Creamos el archivo con nuestros rockeros
        Files.write(archivo, List.of(
            "16,Cobain,Kurt,kurt@nirvana.com",
            "17,Mercury,Freddie,freddie_viejo@queen.com",
            "18,Osbourne,Ozzy,ozzy@sabbath.com"
        ), StandardCharsets.UTF_8);

        System.out.println("=== ANTES DE INTENTAR MODIFICAR ===");
        Files.readAllLines(archivo).forEach(System.out::println);

        // 2. EL INTENTO INGENUO:
        // Intentamos leer de 'archivo' y escribir en 'archivo' a la vez sin temporal
        try (BufferedReader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8);
             BufferedWriter escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {

        	//Como abrimos el escritor sobre el archivo que estamos leyendo nos lo hemos cargado
        	//Así se duplicaría
        	//BufferedWriter escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.startsWith("17,")) {
                    escritor.write("17,Mercury,Freddie,freddie_NUEVO@queen.com");
                } else {
                    escritor.write(linea);
                }
                escritor.newLine();
            }
        }

        // 3. Comprobamos qué ha pasado...
        System.out.println("\n=== DESPUÉS ===");
        List<String> resultado = Files.readAllLines(archivo);
        System.out.println("Líneas en el fichero: " + resultado.size());
        resultado.forEach(System.out::println);

        // Limpieza
        //Files.deleteIfExists(archivo);
    }
}
