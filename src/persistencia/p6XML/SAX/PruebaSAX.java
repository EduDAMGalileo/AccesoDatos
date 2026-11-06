package persistencia.p6XML.SAX;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import persistencia.p6XML.Alumno;

public class PruebaSAX {

    public static void main(String[] args) {
        try {
            // Ruta a tu fichero XML
            Path ruta = Paths.get("centros.xml");

            //Configurar la factoría
            SAXParserFactory fabrica = SAXParserFactory.newInstance();
            
            // OBLIGATORIO: El manejador usa el parámetro "local", que solo se 
            // rellena si namespaceAware está en true (si no, vendría vacío).
            fabrica.setNamespaceAware(true);
            
            // Seguridad: Protección contra XXE (fábrica segura)
            fabrica.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            // Crear el manejador y lanzar el parseo
            ManejadorAlumnos manejador = new ManejadorAlumnos();
            fabrica.newSAXParser().parse(ruta.toFile(), manejador);

            // Obtener la lista y mostrarla
            List<Alumno> alumnos = manejador.getAlumnos();
            
            System.out.println("Alumnos cargados: " + alumnos.size());
            for (Alumno a : alumnos) {
                System.out.println(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}