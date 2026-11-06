package persistencia.p6XML.SAX;

import java.io.File;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class LeerXML {

    public static void main(String[] args) throws Exception {
        
        // Apuntas a tu archivo XML existente
        File archivo = new File("centros.xml");

        // El lector con el interruptor para "nombre"
        DefaultHandler manejador = new DefaultHandler() {
            
            boolean esNombre = false;

            @Override
            public void startElement(String uri, String local, String qName, Attributes atts) {
                if (qName.equals("nombre")) {
                	// Empieza <nombre>
                    esNombre = true;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                if (esNombre) {
                    // Lee y muestra el texto que hay dentro
                    System.out.println("Alumno: " + new String(ch, start, length));
                }
            }

            @Override
            public void endElement(String uri, String local, String qName) {
                if (qName.equals("nombre")) {
                	 // Termina </nombre>
                    esNombre = false;
                }
            }
        };

        // 3. Lanzar el parseo sobre el archivo
        SAXParserFactory.newInstance()
                        .newSAXParser()
                        .parse(archivo, manejador);
    }
}