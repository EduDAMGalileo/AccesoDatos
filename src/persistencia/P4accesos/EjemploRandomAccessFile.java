package persistencia.P4accesos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class EjemploRandomAccessFile {

    private static final String ARCHIVO = "empleados.dat";

    public static void main(String[] args) {
        try {
            // =================================================================
            // CREACIÓN Y ESCRITURA INICIAL (Modo "rw" -> Lectura y Escritura)
            // =================================================================
            System.out.println("--- 1. Escribiendo datos iniciales ---");
            try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
                
                // Estructura por empleado: ID (int: 4 bytes) + Sueldo (double: 8 bytes)
                // Total por registro = 12 bytes
                raf.writeInt(101);      // Empleado 1
                raf.writeDouble(1500.50);

                raf.writeInt(102);      // Empleado 2
                raf.writeDouble(2300.00);

                raf.writeInt(103);      // Empleado 3
                raf.writeDouble(1850.75);

                System.out.println("Posición actual del puntero: " + raf.getFilePointer() + " bytes");
                System.out.println("Tamaño total del archivo: " + raf.length() + " bytes\n");
            }

            // =================================================================
            // LECTURA SECUENCIAL (Modo "r" -> Solo Lectura)
            // =================================================================
            System.out.println("--- 2. Lectura completa del archivo ---");
            try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) {
            	System.out.println("El puntero empieza en: " + raf.getFilePointer());
                while (raf.getFilePointer() < raf.length()) {
                    int id = raf.readInt();    
                    double sueldo = raf.readDouble();
                    System.out.printf("Empleado ID: %d | Sueldo: %.2f €%n", id, sueldo);
                }
                System.out.println();
            }

            // =================================================================
            // ACCESO ALEATORIO: MODIFICAR UN DATO EN EL MEDIO (seek)
            // =================================================================
            System.out.println("--- 3. Modificando el sueldo del segundo empleado ---");
            try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
                // Cada registro ocupa: 4 (int) + 8 (double) = 12 bytes.
                // El Empleado 2 empieza en el byte 12.
                // Su ID ocupa del 12 al 15. Su sueldo empieza en el byte 16.
                
                long posicionSueldoEmp2 = 12 + Integer.BYTES; // 12 + 4 = byte 16
                
                System.out.println("El puntero empieza en: " + raf.getFilePointer());
                // Movemos el puntero directamente a esa posición
                raf.seek(posicionSueldoEmp2);
                System.out.println("El puntero está en: " + raf.getFilePointer());
                
                // Leemos su valor actual
                double sueldoAnterior = raf.readDouble();
                System.out.println("Sueldo anterior del Empleado 102: " + sueldoAnterior + " €");

                // Volvemos a colocar el puntero en el byte 16 para sobreescribirlo
                raf.seek(posicionSueldoEmp2);
                raf.writeDouble(3100.00); // Nuevo sueldo
                System.out.println("¡Sueldo actualizado directamente en el disco!\n");
            }

            // =================================================================
            // AÑADIR DATOS AL FINAL (APPEND)
            // =================================================================
            System.out.println("--- 4. Añadiendo un nuevo empleado al final ---");
            try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
                // Saltamos al final exacto del archivo
                raf.seek(raf.length());
                
                raf.writeInt(104);       // Empleado 4
                raf.writeDouble(2900.20);
                System.out.println("Nuevo tamaño del archivo: " + raf.length() + " bytes\n");
            }

            // =================================================================
            // COMPROBACIÓN FINAL
            // =================================================================
            System.out.println("--- 5. Verificando el archivo tras las modificaciones ---");
            try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) {
                while (raf.getFilePointer() < raf.length()) {
                    int id = raf.readInt();
                    double sueldo = raf.readDouble();
                    System.out.printf("Empleado ID: %d | Sueldo: %.2f €%n", id, sueldo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Limpieza del archivo de prueba
            File f = new File(ARCHIVO);
            if (f.exists()) {
                f.delete();
            }
        }
    }
}
