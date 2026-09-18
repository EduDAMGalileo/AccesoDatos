package persistencia.primerIntento;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionarAlumnos {

	// Clase encargada de guardar y cargar alumnos desde un archivo.
    public void guardar(List<Alumno> alumnos, Path destino) throws IOException {

        List<String> lineas = new ArrayList<>();

        for (Alumno a : alumnos) {

            lineas.add(
                a.getId() + "|"
                + a.getNombre() + "|"
                + a.getApellidos() + "|"
                + a.getFechaNacimiento() + "|"
                + a.getCorreo()
            );
        }

        Files.write(destino, lineas);
    }

    public List<Alumno> cargar(Path origen) throws IOException {
        List<Alumno> alumnos = new ArrayList<>();
        
        //Recorre todas las líneas que se han leído del archivo.
        for (String linea : Files.readAllLines(origen)) {
        	// Separa cada línea usando "|" y guarda los datos en un array.
            String[] campos = linea.split("\\|");
            Alumno a = new Alumno(campos[1], LocalDate.parse(campos[3]));
            a.setId(Integer.parseInt(campos[0]));
            a.setApellidos(campos[2]);
            a.setCorreo(campos[4]);
            alumnos.add(a);
        }

        return alumnos;
    }
}

