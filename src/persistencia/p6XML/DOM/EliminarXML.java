package persistencia.p6XML.DOM;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class EliminarXML {

    public static void main(String[] args) {
        String rutaArchivo = "centros.xml";
        // Vamos a borrarle el correo al alumno con id="2"
        String idBuscado = "2"; 

        try {
            // Cargar el documento XML en memoria
            File archivo = new File(rutaArchivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // Evitar espacios basura
            dbFactory.setIgnoringElementContentWhitespace(true); 
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(archivo);
            documento.getDocumentElement().normalize();

            // Obtener todos los alumnos y buscar al que tenga el id buscado
            NodeList listaAlumnos = documento.getElementsByTagName("alumno");
            boolean encontradoYBorrado = false;

            for (int i = 0; i < listaAlumnos.getLength(); i++) {
                Element alumno = (Element) listaAlumnos.item(i);

                // Comprobamos si el atributo 'id' coincide
                if (alumno.getAttribute("id").equals(idBuscado)) {
                    
                    // Buscar la etiqueta <correo> DENTRO de este alumno
                    NodeList listaCorreos = alumno.getElementsByTagName("correo");

                    if (listaCorreos.getLength() > 0) {
                        Element correoAEliminar = (Element) listaCorreos.item(0);


                        // El padre expulsa al hijo
                        alumno.removeChild(correoAEliminar);
                        
                        encontradoYBorrado = true;
                        System.out.println(">> ¡Correo del alumno con ID " + idBuscado + " eliminado con éxito!");
                    } else {
                        System.out.println(">> El alumno con ID " + idBuscado + " no tiene correo para eliminar.");
                    }
                    break; // Ya encontramos al alumno, salimos del bucle
                }
            }

            // Si hubo cambios, guardamos el archivo en disco
            if (encontradoYBorrado) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource fuente = new DOMSource(documento);
                StreamResult resultado = new StreamResult(new File(rutaArchivo));
                transformer.transform(fuente, resultado);

                System.out.println(">> ¡Archivo actualizado en disco!");
            } else {
                System.out.println(">> No se encontró ningún alumno con el ID: " + idBuscado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}