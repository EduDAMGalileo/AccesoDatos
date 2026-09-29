package repaso.colecciones;

import java.util.Iterator;

class ColeccionEspia implements Iterable<String> {
    private final String[] datos;

    public ColeccionEspia(String[] datos) {
        this.datos = datos;
    }
    
/* ============================================================================
     * ¿QUÉ ESTAMOS HACIENDO AQUÍ?
     * ============================================================================
     * 1. CONTRATO DE 'Iterable':
     *    Al declarar 'implements Iterable<String>', Java nos obliga a sobrescribir
     *    este método iterator(). El bucle for-each lo invoca UNA SOLA VEZ al iniciar.
     *
     * 2. CREACIÓN MEDIANTE CLASE ANÓNIMA ('new Iterator<>() { ... }'):
     *    Como la interfaz 'Iterator' no se puede instanciar directamente, creamos una
     *    implementación al vuelo (sin crear un archivo .java aparte).
     *
     * 3. SOBRESCRITURA DE MÉTODOS DE 'Iterator':
     *    Dentro de esa clase anónima implementamos/sobrescribimos sus dos métodos clave:
     *      - hasNext(): Define la condición del bucle (¿quedan elementos por leer?).
     *      - next():    Obtiene el valor actual y avanza el cursor (indice++).
     *
     * Ambos métodos son los que el compilador llamará en cada iteración del bucle.
     * ============================================================================
     */

    @Override
    public Iterator<String> iterator() {
        System.out.println(">> [COMPILADOR] Ha invocado a iterator()");
        
        return new Iterator<>() {
            private int indice = 0;

            @Override
            public boolean hasNext() {
                boolean hayMas = indice < datos.length;
                System.out.println(">> [COMPILADOR] Ha invocado a hasNext() -> Devuelve: " + hayMas);
                return hayMas;
            }

            @Override
            public String next() {
                String valor = datos[indice++];
                System.out.println(">> [COMPILADOR] Ha invocado a next() -> Devuelve: " + valor);
                return valor;
            }
        };
    }
}