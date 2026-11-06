package persistencia.p6XML.DOM;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SaneadorXML {

    /**
     * Método estático para sanear y formatear cualquier archivo XML.
     * @param rutaArchivo Ruta del archivo XML que quieres arreglar.
     */
    public static void sanear(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                System.out.println("[Error] El archivo " + rutaArchivo + " no existe.");
                return;
            }

            // Cargar el XML ignorando los espacios en blanco nativos
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(archivo);
            
            documento.getDocumentElement().normalize();

            // Limpiar cualquier nodo de texto vacío remanente en el árbol
            eliminarEspaciosEnBlanco(documento.getDocumentElement());

            // 3. Volver a escribirlo aplicando un formato limpio y compacto
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource fuente = new DOMSource(documento);
            StreamResult resultado = new StreamResult(archivo); // Sobrescribe el archivo limpio
            transformer.transform(fuente, resultado);

            System.out.println(">> [Saneador] ¡Archivo '" + rutaArchivo + "' saneado y formateado con éxito!");

        } catch (Exception e) {
            System.out.println("[Error al sanear el XML]");
            e.printStackTrace();
        }
    }

    /**
     * Método recursivo auxiliar para eliminar nodos de texto vacíos.
     */
    private static void eliminarEspaciosEnBlanco(Node nodo) {
        NodeList hijos = nodo.getChildNodes();
        for (int i = hijos.getLength() - 1; i >= 0; i--) {
            Node hijo = hijos.item(i);
            if (hijo.getNodeType() == Node.TEXT_NODE) {
                if (hijo.getTextContent().trim().isEmpty()) {
                    nodo.removeChild(hijo);
                }
            } else if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                eliminarEspaciosEnBlanco(hijo);
            }
        }
    }

    // --- BLOQUE DE PRUEBA (Main opcional por si quieres ejecutarlo solo) ---
    public static void main(String[] args) {
        // Si ejecutas esta clase directamente, sanea tu centro.xml
        sanear("centros.xml");
    }
}