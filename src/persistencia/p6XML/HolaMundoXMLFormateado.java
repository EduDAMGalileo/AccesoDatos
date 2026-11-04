package persistencia.p6XML;

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

public class HolaMundoXMLFormateado {

    public static void main(String[] args) {
        String rutaArchivo = "saludo_bonito.xml";

        try {
            // Fábrica y Constructor
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            // Documento en memoria
            Document documento = dBuilder.newDocument();

            // Crear estructura (Árbol)
            Element raiz = documento.createElement("mensaje");
            raiz.setAttribute("idioma", "es");
            documento.appendChild(raiz);

            Element textoElemento = documento.createElement("texto");
            textoElemento.setTextContent("¡Hola, Mundo desde XML con formato!");
            raiz.appendChild(textoElemento);

            // GUARDAR CON FORMATO HUMANO (Tabulado)
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Propiedad para activar la indentación
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Propiedad específica de Java para definir el tamaño de la sangría (4 espacios)
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource fuente = new DOMSource(documento);
            StreamResult resultado = new StreamResult(new File(rutaArchivo));
            transformer.transform(fuente, resultado);

            System.out.println(">> ¡XML escrito y formateado correctamente!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}