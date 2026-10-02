package repaso.clasesEstaticas;

public class AppConexion {
    public static void main(String[] args) {
        
        // CASO 1: Configuración personalizada completa
        // Fíjate en lo legible que resulta: no hay dudas sobre qué dato es cada uno
        // (a diferencia de pasar 'new Conexion("api.ejemplo.com", 443, 5000, true)').
        Conexion conPersonalizada = new Conexion.Builder() 
            .host("api.ejemplo.com") 
            .puerto(443) 
            .ssl(true) 
            .timeout(5000) 
            .build(); 

        System.out.println("1. Conexión personalizada:");
        System.out.println(conPersonalizada);

        // CASO 2: Aprovechando los valores por defecto
        // Solo cambiamos el puerto; el host seguirá siendo 'localhost', 
        // el timeout 3000ms y ssl 'false'.
        Conexion conPorDefecto = new Conexion.Builder()
            .puerto(9090)
            .build();

        System.out.println("\n2. Conexión con valores por defecto:");
        System.out.println(conPorDefecto);
    }

}
