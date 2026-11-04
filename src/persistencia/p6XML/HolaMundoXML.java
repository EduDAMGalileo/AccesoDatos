package persistencia.p6XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class HolaMundoXML {

    public static void main(String[] args) {
        String rutaArchivo = "saludo.xml";


        // ESCRIBIR UN XML DESDE CERO

        try {
            // Pedimos la "Fábrica" de constructores
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // La fábrica nos da el "Constructor" (el obrero)
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            // El constructor nos crea una "Mesa de trabajo" (un Documento XML vacío en memoria)
            Document documento = dBuilder.newDocument();

            // Creamos el elemento raíz: <mensaje idioma="es">
            Element raiz = documento.createElement("mensaje");
            raiz.setAttribute("idioma", "es");
            // Lo metemos en el documento principal
            documento.appendChild(raiz); 

            // Creamos un elemento hijo: <texto>¡Hola, Mundo desde XML!</texto>
            Element textoElemento = documento.createElement("texto");
            textoElemento.setTextContent("¡Hola, Mundo desde XML!");
            raiz.appendChild(textoElemento); // Lo metemos dentro de la raíz

            // --- TRUCO  PARA GUARDARLO EN DISCO ---
            // Como el XML ahora mismo está en la RAM (en el objeto 'documento'), 
            // usamos un "Transformer" para volcarlo a un archivo físico.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource fuente = new DOMSource(documento);
            StreamResult resultado = new StreamResult(new File(rutaArchivo));
            transformer.transform(fuente, resultado);

            System.out.println(">> ¡Archivo XML escrito con éxito en disco!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // LEER EL XML QUE ACABAMOS DE CREAR
        try {
            System.out.println(">> Leyendo el archivo XML...");
            File archivo = new File(rutaArchivo);

            // Nuevamente necesitamos la fábrica y el constructor
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // ¡AQUÍ ESTÁ LO CHULO DE LA LECTURA! 
            // En vez de newDocument(), le decimos que *parsee* (analice) un archivo físico.
            Document documento = dBuilder.parse(archivo);

            // Normalizamos el documento (recomendado por Java para limpiar saltos de línea raros)
            documento.getDocumentElement().normalize();

            // Obtenemos la raíz y leemos su atributo
            Element raiz = documento.getDocumentElement();
            String idioma = raiz.getAttribute("idioma");

            // Buscamos la etiqueta "texto" dentro del documento
            NodeList listaTextos = documento.getElementsByTagName("texto");
            String contenidoTexto = listaTextos.item(0).getTextContent();

            // Mostramos los resultados por pantalla
            System.out.println("Atributo idioma de la raíz: " + idioma);
            System.out.println("Contenido de la etiqueta <texto>: " + contenidoTexto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}