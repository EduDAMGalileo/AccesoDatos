package persistencia.P2ficheros;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EjemploResolve{
    public static void main(String[] args) {
        // 1. Definimos la ruta base en Windows
        Path base = Paths.get("C:\\Usuarios\\Pepito");

        // CASO 1: Pasar una ruta RELATIVA (Se añade al final de la base)
        Path relativa = Paths.get("Documentos\\factura.pdf");
        Path resultado1 = base.resolve(relativa);
        
        System.out.println("Casuística 1 (Relativa) -> " + resultado1);

        // CASO 2: Pasar una ruta ABSOLUTA (Ignora la base por completo)
        Path absoluta = Paths.get("D:\\Backups\\fotos");
        Path resultado2 = base.resolve(absoluta);
        
        System.out.println("Casuística 2 (Absoluta) -> " + resultado2);

    }
}