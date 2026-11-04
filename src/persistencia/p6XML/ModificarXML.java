package persistencia.p6XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ModificarXML {

    public static void main(String[] args) {
        String rutaArchivo = "centros.xml";

        try {
            // CARGAR EL DOCUMENTO EN MEMORIA
            File archivo = new File(rutaArchivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(archivo);
            documento.getDocumentElement().normalize();

            // OBTENER TODOS LOS ALUMNOS
            NodeList alumnos = documento.getElementsByTagName("alumno");
            boolean huboCambios = false;

            // RECORRER EL ÁRBOL BUSCANDO AL QUE LE FALTE EL CORREO
            for (int i = 0; i < alumnos.getLength(); i++) {
                Element alumno = (Element) alumnos.item(i);

                // Comprobamos si tiene correo usando la función auxiliar
                String correoActual = texto(alumno, "correo");

                // Si el correo es null, significa que no lo tiene
                if (correoActual == null) {
                    String nombreAlumno = texto(alumno, "nombre");
                    System.out.println(">> Alumno encontrado sin correo: " + nombreAlumno);

                    // A) Creamos la nueva etiqueta <correo>
                    Element nuevoCorreo = documento.createElement("correo");
                    // El correo que le falta
                    nuevoCorreo.setTextContent("iker.bengoetxea@example.org"); 

                    // B.1) Añadimos el correo al alumno (lo metemos al final de su nodo)
                    //alumno.appendChild(nuevoCorreo);
                    
                    // B.2) Añadimos el correo donde toca, por si hya DTD o XSD, en ese caso fallaría
                    Element matriculas = (Element) alumno.getElementsByTagName("matriculas").item(0);
                    alumno.insertBefore(nuevoCorreo, matriculas);
                    
                    huboCambios = true;
                    System.out.println(">> ¡Correo añadido a " + nombreAlumno + " en la memoria RAM!");

                }
            }

            // SI HUBO CAMBIOS, GUARDAR EN EL DISCO DURO
            if (huboCambios) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                
                // Mantener el formato tabulado bonito
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource fuente = new DOMSource(documento);
                StreamResult resultado = new StreamResult(new File(rutaArchivo));
                transformer.transform(fuente, resultado);

                System.out.println(">> ¡Archivo XML actualizado y guardado con éxito!");
            } else {
                System.out.println(">> Todos los alumnos ya tenían correo. No hay cambios.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Función auxiliar para buscar texto de forma segura, es la misma que en el otro documento
     */
    private static String texto(Element elementoPadre, String etiquetaHija) {
        NodeList lista = elementoPadre.getElementsByTagName(etiquetaHija);
        if (lista.getLength() > 0) {
            Node nodo = lista.item(0);
            if (nodo != null) {
                return nodo.getTextContent();
            }
        }
        return null;
    }
}