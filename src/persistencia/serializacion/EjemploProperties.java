package persistencia.serializacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class EjemploProperties {

    private static final Path RUTA = Paths.get("configuracion.properties");

    public static void main(String[] args) {
        // ====================================================================
        // GUARDAR (CREAR EL FICHERO DE CONFIGURACIÓN)
        // ====================================================================
        System.out.println("=== 1. GUARDANDO PROPIEDADES ===");
        
        Properties configParaGuardar = new Properties();

        // IMPORTANTE: Usa siempre setProperty() en lugar de put()
        // para garantizar que tanto la clave como el valor sean cadenas (String).
        // Ojo, aquí las estamos guardando, para entender la clase, pero esto NUNCA va en el código
        configParaGuardar.setProperty("bd.url", "jdbc:postgresql://localhost:5432/centrofp");
        configParaGuardar.setProperty("bd.usuario", "app_centro");
        configParaGuardar.setProperty("bd.pool.maximo", "10");
        configParaGuardar.setProperty("exportacion.carpeta", "/var/centro/exportaciones");
        configParaGuardar.setProperty("exportacion.formato", "csv");
        configParaGuardar.setProperty("app.idioma", "es");

        // Guardamos usando Writer y UTF-8 (para soportar tildes y caracteres especiales)
        try (BufferedWriter escritor = Files.newBufferedWriter(RUTA, StandardCharsets.UTF_8)) {
            // El segundo parámetro es un comentario que se escribirá en la cabecera
            configParaGuardar.store(escritor, "Configuración de la aplicación del centro");
            System.out.println("Fichero guardado en: " + RUTA.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }

        // ====================================================================
        // CARGAR Y CONSULTAR EL FICHERO
        // ====================================================================
        System.out.println("\n=== 2. CARGANDO Y LEYENDO VALORES ===");

        Properties configCargada = new Properties();

        try (BufferedReader lector = Files.newBufferedReader(RUTA, StandardCharsets.UTF_8)) {
            // Carga todas las parejas clave-valor en memoria
            configCargada.load(lector);

            // --- Lectura simple de Strings ---
            String url = configCargada.getProperty("bd.url");
            String usuario = configCargada.getProperty("bd.usuario");
            String idioma = configCargada.getProperty("app.idioma");

            System.out.println("URL BD: " + url);
            System.out.println("Usuario BD: " + usuario);
            System.out.println("Idioma: " + idioma);

            // --- Conversión de tipos numéricos ---
            // Las propiedades siempre son texto; la conversión la hace el programador
            String maximoStr = configCargada.getProperty("bd.pool.maximo", "5");
            int maximoPool = Integer.parseInt(maximoStr);
            System.out.println("Conexiones máximas (int): " + maximoPool);

            // --- Uso del valor por defecto (Fallback) ---
            // Si la clave "bd.timeout" no existe en el fichero, devuelve "30" en vez de null
            int timeout = Integer.parseInt(configCargada.getProperty("bd.timeout", "30"));
            System.out.println("Timeout (no estaba en el fichero, usa default): " + timeout + " seg");

            // Si no pones valor por defecto y la clave no existe, devuelve null:
            String claveInexistente = configCargada.getProperty("servidor.correo");
            System.out.println("Servidor correo: " + claveInexistente); // Imprime: null

        } catch (IOException e) {
            System.err.println("Error al leer: " + e.getMessage());
        } finally {
            // Opcional: Descomenta si quieres que borre el fichero al terminar la prueba
            // try { Files.deleteIfExists(RUTA); } catch (IOException ignored) {}
        }
    }
}