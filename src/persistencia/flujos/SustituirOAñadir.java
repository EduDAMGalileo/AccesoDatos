package persistencia.flujos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SustituirOAñadir {

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("nota.txt");

        // 1. SOBRESCRIBIR (Comportamiento normal: vacía el archivo y escribe, si no existe lo crea)
        Files.writeString(ruta, "Línea 1\n");
        System.out.println("1. Tras escribir Línea 1:\n" + Files.readString(ruta));

        // 2. AÑADIR (APPEND: respeta lo que había y añade al final)
        //Si no ponemos el CREATE y solo dejamos APPEND si no exite peta!
        Files.writeString(ruta, "Línea 2\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("2. Tras AÑADIR Línea 2 (conserva ambas):\n" + Files.readString(ruta));

        // 3. SOBRESCRIBIR OTRA VEZ (Sin opciones: borra todo lo anterior)
        Files.writeString(ruta, "Línea 3\n");
        System.out.println("3. Tras escribir Línea 3 sin opciones (¡borró las anteriores!):\n" + Files.readString(ruta));
    }
}