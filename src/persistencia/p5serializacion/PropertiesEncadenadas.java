package persistencia.p5serializacion;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/*
Para el default.properties
 
app.idioma=es
bd.pool.maximo=5
bd.url=jdbc:postgresql://localhost:5432/base_por_defecto
exportacion.formato=csv
 
Para config.properties
# Fíjate que solo sobrescribimos el idioma y el pool. 
# La url y el formato se heredarán automáticamente de default.properties
app.idioma=en
bd.pool.maximo=25
  
 
 */

public class PropertiesEncadenadas {

    public static void main(String[] args) {
        Path rutaDefault = Paths.get("default.properties");
        Path rutaConfig = Paths.get("config.properties");

        try {

            // CARGAR EL FICHERO DE VALORES POR OMISIÓN (default.properties)
            Properties propiedadesPorOmision = new Properties();
            try (Reader lectorDefault = Files.newBufferedReader(rutaDefault, StandardCharsets.UTF_8)) {
                propiedadesPorOmision.load(lectorDefault);
            }


            // CREAR EL CONFIG APOYÁNDOSE EN EL ANTERIOR (¡El truco del constructor!)

            // Al pasarle 'propiedadesPorOmision', configuramos el "padre" de la jerarquía.
            Properties config = new Properties(propiedadesPorOmision);

            // CARGAR EL FICHERO DEL USUARIO (config.properties)

            try (Reader lectorConfig = Files.newBufferedReader(rutaConfig, StandardCharsets.UTF_8)) {
                config.load(lectorConfig);
            }


            // PROBAR RESULTADOS
            System.out.println(">> ¡Configuraciones encadenadas leídas con éxito!\n");

            // Propiedad sobrescrita en config.properties
            System.out.println("Idioma (Del usuario): " + config.getProperty("app.idioma"));
            System.out.println("Pool máximo (Del usuario): " + config.getProperty("bd.pool.maximo"));

            // Propiedad NO definida en config.properties -> ¡La busca en default.properties!
            System.out.println("URL Base de Datos (Heredada de default): " + config.getProperty("bd.url"));
            System.out.println("Formato de exportación (Heredado de default): " + config.getProperty("exportacion.formato"));

        } catch (IOException e) {
            System.err.println("Error al leer los ficheros de propiedades: " + e.getMessage());
        }
    }
}