package persistencia.p6XML;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;

public class PruebaNodosTextoFunciona {
    public static void main(String[] args) throws Exception {
        var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                  .parse(Path.of("alumno_indentado.xml").toFile());

        Element alumno = (Element) doc.getElementsByTagName("alumno").item(0);

        NodeList hijos = alumno.getChildNodes();

        for (int i = 0; i < hijos.getLength(); i++) {
            Node hijo = hijos.item(i);

            // EL FILTRO: Si no es un elemento XML (es espacio/salto de línea), lo ignoramos
            if (hijo.getNodeType() != Node.ELEMENT_NODE) {
            	System.out.println("Espacio o salto de línea detectado");
                continue; 
            }

            // Si pasa el filtro, ahora sí podemos castear con seguridad total
            Element elementoReal = (Element) hijo;
            System.out.println("Elemento hijo detectado: " + elementoReal.getTagName() 
                               + " -> Valor: " + elementoReal.getTextContent());
        }
    }
}