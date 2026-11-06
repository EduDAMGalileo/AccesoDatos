package persistencia.p6XML;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;

public class DemoXXE {

    public static void main(String[] args) throws Exception {
        // Preparamos un archivo simulando un fichero sensible del servidor (/etc/passwd, claves, etc.)
        File archivoSecreto = File.createTempFile("secreto_servidor", ".txt");
        archivoSecreto.deleteOnExit();
        //Ojo, esto está aquí para completar el ejemplo, pero jamás se ponen claves en el código
        try (FileWriter fw = new FileWriter(archivoSecreto)) {
            fw.write("DATOS_SECRETOS: DB_PASSWORD=Admin123*; JWT_SECRET=xyz987;");
        }

        // Construimos el XML malicioso que inyecta la entidad externa apuntando a nuestro archivo
        String urlArchivo = archivoSecreto.toURI().toString();
        String xmlMalicioso = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE centro [\n" +
            "  <!ENTITY fuga SYSTEM \"" + urlArchivo + "\">\n" +
            "]>\n" +
            "<centro curso=\"2026-2027\">\n" +
            "    <alumno id=\"1\"><nombre>&fuga;</nombre></alumno>\n" +
            "</centro>";

        System.out.println("=== XML RECIBIDO ===");
        System.out.println(xmlMalicioso);
        System.out.println("==================================================\n");

        // Demostración con la fábrica INSEGURA (por omisión)
        System.out.println("--- 1. PARSEO CON FÁBRICA POR OMISIÓN (INSEGURA) ---");
        parsearInseguro(xmlMalicioso);

        System.out.println("\n--------------------------------------------------");

        // Demostración con la fábrica CONFIGURADA (segura)
        System.out.println("--- 2. PARSEO CON FÁBRICA SEGURA (PROTEGIDA) ---");
        parsearSeguro(xmlMalicioso);
    }

    /**
     * Fábrica por defecto: resuelve entidades externas automáticamente.
     */
    public static void parsearInseguro(String xml) {
        try {
            // Fábrica sin opciones adicionales (comportamiento habitual por omisión)
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new InputSource(new StringReader(xml)));
            Element nombreElem = (Element) doc.getElementsByTagName("nombre").item(0);

            // ¡PELIGRO! El analizador resolvió la entidad y leyó el archivo
            System.out.println("Resultado obtenido en 'nombre':");
            System.out.println(">>> " + nombreElem.getTextContent());
            System.out.println("[ALERTA] ¡El atacante ha conseguido leer el fichero del servidor!");

        } catch (Exception e) {
            System.out.println("Error durante el parseo: " + e.getMessage());
        }
    }

    /**
     * Fábrica segura: desactiva la declaración de DTDs por completo
     * (Recomendación estándar de OWASP para prevenir XXE y 'Billion Laughs').
     */
    public static void parsearSeguro(String xml) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Opción más segura: Prohibir DOCTYPE por completo si no es estrictamente necesario
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            // Si por motivos de negocio necesitaras DTDs, al menos desactiva las entidades externas:
            // dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            // dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            // dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            // dbf.setXIncludeAware(false);
            // dbf.setExpandEntityReferences(false);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xml)));

            Element nombreElem = (Element) doc.getElementsByTagName("nombre").item(0);
            System.out.println("Resultado: " + nombreElem.getTextContent());

        } catch (Exception e) {
            System.out.println("[CORRECTO] El analizador bloqueó el ataque:");
            System.out.println("Excepción capturada: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}