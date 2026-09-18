package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EjemploPathFile {

    public static void main(String[] args) {

        // Creamos la ruta del fichero
        Path ruta = Path.of("personas.txt");

        // Datos que queremos guardar, la lista tiene 3 elementos
        List<String> alumnos = List.of(
            "1-Eduardo-García",
            "2-Elena-Nito",
            "3-Pedro-del Olmo"
        );
        
        //Para comprobarlo podemos imprir el segundo dato
        System.out.println(alumnos.get(1));

        try {

            // Escribimos los datos en el fichero, no hace falta bucle, van en una lista y Java se encarga
            Files.write(ruta, alumnos);
            System.out.println("Fichero guardado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar el fichero.");
        }
    }
}