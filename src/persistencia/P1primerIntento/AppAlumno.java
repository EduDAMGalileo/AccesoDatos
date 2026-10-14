package persistencia.P1primerIntento;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Clase principal desde la que se ejecuta y prueba el programa.
public class AppAlumno {

    public static void main(String[] args) {

        List<Alumno> alumnos = new ArrayList<>();
        // LocalDate.of Crea una fecha indicando año, mes y día.
        Alumno alumno1 = new Alumno("Eduardo", LocalDate.of(2000, 5, 15));
        alumno1.setId(1);
        alumno1.setApellidos("Rodriguez Pérez");
        alumno1.setCorreo("edu@gmail.com");
        
        Alumno alumno2 = new Alumno("Carla", LocalDate.of(2001, 10, 20));

        alumno2.setId(2);
        alumno2.setApellidos("López González");
        alumno2.setCorreo("carla@gmail.com");

        alumnos.add(alumno1);
        alumnos.add(alumno2);

        GestionarAlumnos gestor = new GestionarAlumnos();
        
        // Indica la ruta y el nombre del archivo donde se guardarán los datos.
        Path archivo = Path.of("alumnos.txt");

        try {

            // Guardamos los alumnos
            gestor.guardar(alumnos, archivo);

            System.out.println("Alumnos guardados.");

            // Cargamos los alumnos
            List<Alumno> alumnosCargados = gestor.cargar(archivo);

            System.out.println("Alumnos cargados:");

            for (Alumno a : alumnosCargados) {
                System.out.println(a);
            }

        } catch (IOException e) {

            System.out.println("Error al trabajar con el archivo:");
            e.printStackTrace();
        }
    }
}

