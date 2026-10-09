package persistencia.P2ficheros;

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;
import java.util.Set;

public class EjemploPermisos {
    public static void main(String[] args) throws IOException {
        Path p = Path.of("centro", "datos.csv");
        
        if (Files.notExists(p)) {
            System.out.println("El fichero no existe, crea uno para probar.");
            return;
        }

        System.out.println("--- Inspeccionando permisos en: " + System.getProperty("os.name") + " ---");

        // 1. LÓGICA PARA LINUX/UNIX (POSIX)
        if (Files.getFileStore(p).supportsFileAttributeView(PosixFileAttributeView.class)) {
            System.out.println("Sistema detectado: POSIX (Linux/macOS)");
            Set<PosixFilePermission> perms = Files.getPosixFilePermissions(p);
            System.out.println("Permisos actuales: " + PosixFilePermissions.toString(perms));
            
        // 2. LÓGICA PARA WINDOWS (DosFileAttributes)
        } else if (Files.getFileStore(p).supportsFileAttributeView(DosFileAttributeView.class)) {
            System.out.println("Sistema detectado: Windows (DOS)");
            DosFileAttributes dosAttrs = Files.readAttributes(p, DosFileAttributes.class);
            
            System.out.println("¿Es de solo lectura?: " + dosAttrs.isReadOnly());
            System.out.println("¿Es oculto?: " + dosAttrs.isHidden());
            System.out.println("¿Es archivo de sistema?: " + dosAttrs.isSystem());
            System.out.println("¿Es archivo de archivo (archived)?: " + dosAttrs.isArchive());
            
        } else {
            System.out.println("Sistema de archivos genérico. Permisos básicos.");
        }
    }
}
