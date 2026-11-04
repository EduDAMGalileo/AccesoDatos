package persistencia.p6XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class AñadirXML{

    public static void main(String[] args) {
        String rutaArchivo = "centros.xml";

        try {
            //Cargar el documento XML en memoria
            File archivo = new File(rutaArchivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
 
            //IGNORAR ESPACIOS VACÍOS AL LEER (Evita que se multipliquen los intros) o no...
            dbFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(archivo);
            documento.getDocumentElement().normalize();

            // Obtener el elemento raíz
            Element raiz = documento.getDocumentElement();

            // Crear la estructura del nuevo alumno
            Element nuevoAlumno = documento.createElement("alumno");
            nuevoAlumno.setAttribute("id", "3");

            Element nombre = documento.createElement("nombre");
            nombre.setTextContent("Marc Soler");
            nuevoAlumno.appendChild(nombre);

            Element nacimiento = documento.createElement("nacimiento");
            nacimiento.setTextContent("2005-07-15");
            nuevoAlumno.appendChild(nacimiento);

            Element correo = documento.createElement("correo");
            correo.setTextContent("marc.soler@example.org");
            nuevoAlumno.appendChild(correo);

            // Crear el bloque de matrículas y su matrícula interna
            Element matriculas = documento.createElement("matriculas");
            
            Element matricula = documento.createElement("matricula");
            matricula.setAttribute("modulo", "0486");
            matricula.setTextContent("Acceso a datos");
            matriculas.appendChild(matricula); // Metemos la matrícula dentro de <matriculas>

            nuevoAlumno.appendChild(matriculas); // Metemos <matriculas> dentro del <alumno>

            // Añadir el nuevo alumno completo dentro de la raíz
            raiz.appendChild(nuevoAlumno);

            // Guardar los cambios en el disco duro con formato tabulado
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource fuente = new DOMSource(documento);
            StreamResult resultado = new StreamResult(new File(rutaArchivo));
            transformer.transform(fuente, resultado);

            System.out.println(">> ¡Nuevo alumno añadido y guardado con éxito en '" + rutaArchivo + "'!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}