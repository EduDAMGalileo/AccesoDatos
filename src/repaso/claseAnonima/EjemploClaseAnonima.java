package repaso.claseAnonima;

//Usa el la interfaz notificador
public class EjemploClaseAnonima {

 public static void main(String[] args) {
     
     // Variable local para mostrar cómo la clase anónima puede acceder a ella
     String usuario = "Carlos";

     // =====================================================================
     // EJEMPLO 1: Creación de la Clase Anónima
     // =====================================================================
     /*
      * ¿Qué está pasando aquí?
      * 1. No se puede hacer 'new' directamente de una interfaz porque no tiene código.
      * 2. Sin embargo, al abrir llaves '{ }' después de 'new Notificador()', le decimos
      *    a Java: "Crea una clase sin nombre que implemente Notificador y define 
      *    su comportamiento aquí mismo".
      * 3. Se crea una única instancia de esa clase temporal.
      */
     Notificador notificadorEmail = new Notificador() {
         // Podemos añadir atributos propios a esta clase anónima
         private String servidor = "smtp.servidor.com";

         @Override
         public void enviar(String mensaje) {
             // Puede acceder a variables del método exterior (deben ser finales o 'effectively final')
             System.out.println("Conectando a " + servidor + "...");
             System.out.println("Enviando EMAIL a " + usuario + ": " + mensaje);
         }
     }; // <-- OJO: Lleva punto y coma al final porque es una sentencia de asignación.


     // =====================================================================
     // EJEMPLO 2: Otra Clase Anónima con comportamiento totalmente diferente
     // =====================================================================
     // Creamos otro objeto a partir de la misma interfaz, pero para SMS
     Notificador notificadorSMS = new Notificador() {
         @Override
         public void enviar(String mensaje) {
             System.out.println("Enviando SMS al número de " + usuario + ": " + mensaje);
         }
     };


     // =====================================================================
     // EJEMPLO 3: Pasarla directamente como argumento de un método
     // =====================================================================
     // Muy común en interfaces gráficas (listeners/botones) o tareas en segundo plano.
     procesarAlerta("¡Servidor caído!", new Notificador() {
         @Override
         public void enviar(String mensaje) {
             System.out.println("ALERTA CRÍTICA en pantalla: " + mensaje);
         }
     });


     // -------------------------------------------------------------
     // Probamos las instancias que creamos anteriormente:
     System.out.println("\n--- Ejecutando notificadores creados previamente ---");
     notificadorEmail.enviar("Tu pedido ha sido enviado.");
     notificadorSMS.enviar("Tu código de verificación es 4821.");
 }

 // Método auxiliar que recibe cualquier objeto que cumpla el contrato de 'Notificador'
 public static void procesarAlerta(String texto, Notificador notificador) {
     notificador.enviar(texto);
 }
}