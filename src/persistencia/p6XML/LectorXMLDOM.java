package persistencia.p6XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;

public class LectorXMLDOM {

    public static void main(String[] args) {
        // Ruta al fichero XML de ejemplo
        Path ruta = Path.of("centros.xml");

        try {
            // CREAR Y CONFIGURAR LA FÁBRICA
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            
            // ¡Muy importante! Evita que las búsquedas fallen por culpa de los espacios de nombres
            fabrica.setNamespaceAware(true);

            // OBTENER EL CONSTRUCTOR A PARTIR DE LA FÁBRICA
            DocumentBuilder constructor = fabrica.newDocumentBuilder();

            //  ANALIZAR EL FICHERO Y CONSTRUIR EL ÁRBOL EN MEMORIA
            // El resultado es un objeto 'Document' que representa todo el XML en RAM
            Document documento = constructor.parse(ruta.toFile());

            // --- A PARTIR DE AQUÍ TRABAJAMOS CON EL ÁRBOL ---

            // Leer un atributo del elemento raíz (<centro curso="2026-2027">)
            Element raiz = documento.getDocumentElement();
            String curso = raiz.getAttribute("curso");
            System.out.println("Curso académico del centro: " + curso);
            System.out.println("--------------------------------------------------");

            // Obtener la lista de todos los elementos <alumno>
            NodeList alumnos = documento.getElementsByTagName("alumno");
            System.out.println();
            // Recorrer el listado de alumnos
            for (int i = 0; i < alumnos.getLength(); i++) {
                Element alumno = (Element) alumnos.item(i);

                // Leer un atributo del elemento
                int id = Integer.parseInt(alumno.getAttribute("id"));

                // Usar el método auxiliar para extraer el texto de los hijos de forma segura
                String nombre = texto(alumno, "nombre");
                
                // Devolverá null si el elemento no existe
                String correo = texto(alumno, "correo"); 

                // Mostrar el resultado por pantalla
                System.out.println("ID Alumno : " + id);
                System.out.println("Nombre    : " + nombre);
                
                // Demostración de cómo distinguir un dato ausente de una cadena vacía
                System.out.println("Correo    : " + (correo != null ? correo : "[Sin correo]"));
                System.out.println("--------------------------------------------------");
            }

        } catch (Exception e) {
            System.err.println("Error al procesar el documento XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar (obligatorio para trabajar bien con DOM)
     * Se encarga de buscar una etiqueta hija y extraer su contenido de forma segura.
     */
    private static String texto(Element padre, String etiqueta) {
        NodeList lista = padre.getElementsByTagName(etiqueta);
        
        // Si el elemento no está en el XML, devolvemos null (¡importante no devolver ""!)
        if (lista.getLength() == 0) {
            return null; 
        }
        
        // Extrae el texto del nodo y recorta los espacios sobrantes
        return lista.item(0).getTextContent().trim();
    }
}