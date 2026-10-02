package repaso.clasesEstaticas;

public class Conexion { 

    // CAMPOS INMUTABLES:
    // Al ser 'final', una vez creada la conexión nadie puede alterar sus valores.
    // Esto hace que la clase sea segura en entornos multihilo (Thread-Safe).
    private final String host; 
    private final int puerto; 
    private final int timeout; 
    private final boolean ssl; 

    // CONSTRUCTOR PRIVADO:
    // Al ser 'private', nadie desde fuera puede hacer 'new Conexion(...)'.
    // Obligamos a todo el mundo a pasar por el Builder, evitando el "antipatrón 
    // del constructor telescópico" (constructores con 4 o 5 parámetros confusos).
    private Conexion(Builder b) { 
        this.host    = b.host; 
        this.puerto  = b.puerto; 
        this.timeout = b.timeout; 
        this.ssl     = b.ssl; 
    } 

    // Método para imprimir la conexión de forma legible
    @Override 
    public String toString() { 
        // Si ssl es true usa "https", si no "http"
        return (ssl ? "https" : "http") + "://" + host + ":" + puerto 
            + " (timeout: " + timeout + "ms)"; 
    } 

    // =========================================================================
    // EL BUILDER (Clase interna estática)
    // =========================================================================
    // ¿Por qué 'static'?
    // Porque queremos poder instanciar el Builder SIN tener previamente un objeto 
    // Conexion creado: 'new Conexion.Builder()'. Si no fuera estática, Java 
    // exigiría tener una Conexion ya viva para poder crear su Builder.
    public static class Builder { 
        
        // Valores por defecto: si el usuario no configura alguno, se usarán estos.
        private String  host    = "localhost"; 
        private int     puerto  = 8080; 
        private int     timeout = 3000; 
        private boolean ssl     = false; 

        // MÉTODOS FLUIDOS (Fluent API):
        // Cada método modifica un atributo y devuelve 'this' (la propia instancia del Builder).
        // Al devolver 'this', permitimos encadenar llamadas con puntos: .host(...).puerto(...)
        
        public Builder host(String host) { 
            this.host = host;    
            return this; // Permite seguir encadenando
        } 

        public Builder puerto(int puerto) { 
            this.puerto = puerto;  
            return this; 
        } 

        public Builder timeout(int timeout) { 
            this.timeout = timeout; 
            return this; 
        } 

        public Builder ssl(boolean ssl) { 
            this.ssl = ssl;     
            return this; 
        } 

        // PASO FINAL:
        // Crea y devuelve el objeto definitivo pasándole este Builder al constructor privado.
        // Aquí también se podrían añadir validaciones (ej: comprobar que el puerto esté entre 1 y 65535).
        public Conexion build() { 
            return new Conexion(this); 
        } 
    } 

}