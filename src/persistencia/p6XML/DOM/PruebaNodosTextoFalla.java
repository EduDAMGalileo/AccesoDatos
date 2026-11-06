package persistencia.p6XML.DOM;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;

public class PruebaNodosTextoFalla {
    public static void main(String[] args) throws Exception {
        var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                  .parse(Path.of("alumno_indentado.xml").toFile());

        Element alumno = (Element) doc.getElementsByTagName("alumno").item(0);

        // Pedimos todos los hijos directos
        NodeList hijos = alumno.getChildNodes();
        // ¡Imprimirá 5!
        System.out.println("Cantidad total de nodos hijos encontrados: " + hijos.getLength()); 

        for (int i = 0; i < hijos.getLength(); i++) {
            // INTENTO DE CONVERSIÓN FORZADA QUE FALLA:
            // El primer hijo NO es un Element, es un Node de tipo TEXTO (el salto de línea)
            Element hijoElemento = (Element) hijos.item(i); // <-- ¡ClassCastException aquí!
            
            System.out.println(hijoElemento.getTagName());
        }
    }
}
