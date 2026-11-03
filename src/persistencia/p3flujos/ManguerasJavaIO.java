package persistencia.p3flujos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ManguerasJavaIO {

    public static void main(String[] args) {
        String archivoTexto = "apuntes_dam.txt";
        String archivoBinario = "calificaciones.dat";
        String archivoAleatorio = "empleados_fijos.dat";

        System.out.println("==================================================");
        System.out.println(" 1. FASE DE ESCRITURA (Montando los cables de salida)");
        System.out.println("==================================================");
        escribirTextoConBufferYTraductor(archivoTexto);
        escribirDatosPrimitivos(archivoBinario);

        System.out.println("\n==================================================");
        System.out.println(" 2. FASE DE LECTURA (Comparando eficiencia y adaptadores)");
        System.out.println("==================================================");
        leerSinBufferNiFiltros(archivoTexto);       // El cable pelado (Lento)
        leerConTraductorDeCaracteres(archivoTexto);  // Cable + Adaptador de texto (Mejor, pero sin buffer)
        leerConBufferYTraductor(archivoTexto);       // Cable + Adaptador + Depósito (El profesional)
        leerDatosPrimitivos(archivoBinario);         // Leyendo tipos de datos de Java

        System.out.println("\n==================================================");
        System.out.println(" 3. ACCESO ALEATORIO (El brazo del vinilo)");
        System.out.println("==================================================");
        probarAccesoAleatorio(archivoAleatorio);
    }

    // =========================================================================
    // BLOQUE 1: ESCRITURAS
    // =========================================================================

    public static void escribirTextoConBufferYTraductor(String ruta) {
        // MONTAJE DE LA TORRE DE ESCRITURA (De fuera a dentro):
        // 1. BufferedWriter: El tanque/depósito intermedio en RAM.
        // 2. OutputStreamWriter: El traductor que pasa caracteres a bytes UTF-8.
        // 3. FileOutputStream: El cable físico que toca el disco duro.
        try (BufferedWriter bw = new BufferedWriter(
                                    new OutputStreamWriter(
                                        new FileOutputStream(ruta), StandardCharsets.UTF_8
                                    )
                                )) {
            bw.write("Módulo: Acceso a Datos (DAM)");
            bw.newLine(); // Salto de línea limpio y multiplataforma gracias al buffer
            bw.write("Tema: Flujos de E/S con java.io y tildes (á, é, í, ó, ú, ñ).");
            System.out.println("[Escritura] Archivo de texto guardado correctamente usando Buffer + Traductor.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escribirDatosPrimitivos(String ruta) {
        // MONTAJE PARA BINARIO:
        // DataOutputStream (Escribe ints, doubles...) envuelto en FileOutputStream (Bytes puros)
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta))) {
            dos.writeInt(2026);          // Escribe un entero exacto (4 bytes)
            dos.writeDouble(9.5);        // Escribe un decimal (8 bytes)
            dos.writeUTF("Convocatoria Ordinaria"); // Escribe una cadena formateada
            System.out.println("[Escritura Binaria] Datos primitivos guardados en disco.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // BLOQUE 2: LECTURAS (Comparativa de "Cables")
    // =========================================================================

    public static void leerSinBufferNiFiltros(String ruta) {
        // CABLE BÁSICO: FileInputStream puro.
        // Analogía: Un cable eléctrico pelado que solo lee 1 byte (un número del 0 al 255) cada vez.
        // Problema: Si el archivo tiene tildes, las rompe porque corta los caracteres a la mitad.
        try (InputStream cableBytes = new FileInputStream(ruta)) {
            int byteLeido;
            long inicio = System.nanoTime();
            
            while ((byteLeido = cableBytes.read()) != -1) {
                // Va al disco duro CADA VEZ que lee un byte (fatiga el sistema y es lentísimo)
            }
            
            long fin = System.nanoTime();
            System.out.println("-> 1. Lectura SIN Buffer (byte a byte): " + (fin - inicio) + " ns");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void leerConTraductorDeCaracteres(String ruta) {
        // ADAPTADOR + CABLE: InputStreamReader envuelto en FileInputStream.
        // Analogía: Al cable de bytes le ponemos un adaptador de clavija para traducir
        // los electrones en letras legibles (caracteres), forzando el idioma UTF-8.
        // Problema: Sigue sin buffer, va al disco carácter a carácter.
        try (Reader traductorCaracteres = new InputStreamReader(new FileInputStream(ruta), StandardCharsets.UTF_8)) {
            int caracter;
            long inicio = System.nanoTime();
            
            while ((caracter = traductorCaracteres.read()) != -1) {
                // Lee carácter a carácter
            }
            
            long fin = System.nanoTime();
            System.out.println("-> 2. Lectura CON Traductor (sin buffer): " + (fin - inicio) + " ns");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void leerConBufferYTraductor(String ruta) {
        // LA TORRE PROFESIONAL: BufferedReader -> InputStreamReader -> FileInputStream
        // Analogía: La manguera completa. El Buffer crea un depósito intermedio en la RAM. 
        // En vez de ir al disco por cada letra, carga bloques enteros de golpe y te permite
        // leer líneas enteras con `readLine()`. Es infinitamente más rápido.
        try (BufferedReader tanqueBuffer = new BufferedReader(
                                            new InputStreamReader(
                                                new FileInputStream(ruta), StandardCharsets.UTF_8
                                            )
                                        )) {
            String linea;
            long inicio = System.nanoTime();
            
            while ((linea = tanqueBuffer.readLine()) != null) {
                // Procesamos la línea completa que viene del depósito de la RAM
            }
            
            long fin = System.nanoTime();
            System.out.println("-> 3. Lectura CON Buffer + Traductor (Línea a línea): " + (fin - inicio) + " ns");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void leerDatosPrimitivos(String ruta) {
        // LECTURA BINARIA INTELIGENTE: DataInputStream envuelto en FileInputStream
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ruta))) {
            int anio = dis.readInt();
            double nota = dis.readDouble();
            String texto = dis.readUTF();
            System.out.println("[Lectura Binaria] Recuperado -> Año: " + anio + ", Nota: " + nota + ", Texto: " + texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // BLOQUE 3: ACCESO ALEATORIO (Fuera de los flujos secuenciales)
    // Esto realmente es el siguiente bloque p4accesos
    // =========================================================================

    public static void probarAccesoAleatorio(String ruta) {
        // RandomAccessFile: El brazo del vinilo / La aguja.
        // No es un Stream, es una clase independiente que te permite moverte 
        // libremente por el archivo hacia adelante o hacia atrás usando `seek()`.
        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            
            // Escribimos dos registros de empleados en posiciones concretas
            raf.writeInt(101);       // ID (4 bytes)
            raf.writeUTF("Ana");     // Nombre
            
            raf.writeInt(102);       // ID (4 bytes)
            raf.writeUTF("Luis");    // Nombre

            // SUPONGAMOS QUE QUEREMOS LEER DIRECTAMENTE EL SEGUNDO EMPLEADO
            // Sabiendo lo que ocupa el primer registro en bytes, movemos la "aguja" (seek):
            // 4 bytes (del int 101) + 2 bytes (longitud del UTF "Ana") + 3 bytes (las letras "Ana") = 9 bytes.
            long posicionSegundoRegistro = 4 + 2 + 3;
            
            raf.seek(posicionSegundoRegistro); // ¡Salto directo sin leer lo anterior!
            
            int idLeido = raf.readInt();
            String nombreLeido = raf.readUTF();
            
            System.out.println("[Acceso Aleatorio] Salto directo al registro 2 -> ID: " + idLeido + ", Nombre: " + nombreLeido);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}