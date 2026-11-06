package persistencia.p6XML.SAX;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes; // <-- Faltaba este import
import org.xml.sax.helpers.DefaultHandler;
import persistencia.p6XML.Alumno;

public class ManejadorAlumnos extends DefaultHandler {

    private final List<Alumno> alumnos = new ArrayList<>();
    private Alumno actual;
    private StringBuilder contenido = new StringBuilder();

    @Override
    public void startElement(String uri, String local, String nombre, Attributes atts) {
        contenido.setLength(0);
        if ("alumno".equals(local)) {
            actual = new Alumno();
            actual.setId(Integer.parseInt(atts.getValue("id")));
        }
    }

    @Override
    public void characters(char[] ch, int inicio, int longitud) {
        contenido.append(ch, inicio, longitud);
    }

    @Override
    public void endElement(String uri, String local, String nombre) {
        switch (local) {
            case "nombre" -> actual.setNombre(contenido.toString().trim());
            case "correo" -> actual.setCorreo(contenido.toString().trim());
            case "alumno" -> alumnos.add(actual);
            default -> { }
        }
    }

    // <-- Método imprescindible para recoger los datos al terminar
    public List<Alumno> getAlumnos() {
        return alumnos;
    }
}