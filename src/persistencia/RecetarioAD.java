package persistencia;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Recetario de envolturas de Acceso a Datos: java.io y NIO.
 * Cada receta es un metodo independiente: copia el que necesites.
 * Las pilas se leen de dentro (nodo) hacia fuera (lo que usas).
 */
public class RecetarioAD {

	// =================================================================
	// TEXTO
	// =================================================================

	// 1. Leer texto linea a linea (java.io)
	//    FileInputStream -> InputStreamReader (UTF-8) -> BufferedReader
	static void leerLineas(String fichero) throws IOException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(fichero), StandardCharsets.UTF_8))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}
		}
	}

	// 2. Lo mismo, mas corto: FileReader ya lleva el puente dentro
	static void leerLineasCorto(String fichero) throws IOException {
		try (BufferedReader br = new BufferedReader(
				new FileReader(fichero, StandardCharsets.UTF_8))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}
			// Con lambdas (Java 8): br.lines().forEach(System.out::println);
		}
	}

	// 3. Lo mismo con NIO (UTF-8 por defecto)
	static void leerLineasNio(Path ruta) throws IOException {
		try (BufferedReader br = Files.newBufferedReader(ruta)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}
			// Con lambdas (Java 8): br.lines().forEach(System.out::println);
		}
	}

	// 4. Leer un fichero de texto entero (NIO). Solo si cabe en memoria.
	//    Java 25: new FileReader(f, UTF_8).readAllAsString() hace lo mismo
	static String leerTodo(Path ruta) throws IOException {
		return Files.readString(ruta);
	}

	// 5. Recorrer un fichero grande sin cargarlo entero en memoria
	static long contarLineasCon(Path ruta, String texto) throws IOException {
		long n = 0;
		try (BufferedReader br = Files.newBufferedReader(ruta)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.contains(texto)) {
					n++;
				}
			}
		}
		return n;
		// Con lambdas (Java 8). Files.lines deja el fichero abierto hasta
		// que se cierra el Stream: por eso va en un try.
		// try (Stream<String> lineas = Files.lines(ruta)) {
		//     return lineas.filter(l -> l.contains(texto)).count();
		// }
	}

	// 6. Escribir texto con formato (CSV, informes)
	//    FileWriter (UTF-8) -> BufferedWriter -> PrintWriter
	static void escribirCsv(String fichero, List<String> nombres, List<Integer> notas)
			throws IOException {
		try (PrintWriter pw = new PrintWriter(
				new BufferedWriter(
						new FileWriter(fichero, StandardCharsets.UTF_8)))) {
			pw.println("nombre;nota");
			for (int i = 0; i < nombres.size(); i++) {
				pw.printf("%s;%d%n", nombres.get(i), notas.get(i));
			}
		}   // close() hace flush: sin el, el fichero queda vacio
	}

	// 7. Lo mismo con NIO por debajo
	static void escribirCsvNio(Path ruta, List<String> lineas) throws IOException {
		try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(ruta))) {
			for (String linea : lineas) {
				pw.println(linea);
			}
			// Con lambdas (Java 8): lineas.forEach(pw::println);
		}
	}

	// 8. Anadir al final de un fichero (java.io: el true del constructor)
	static void anadirLinea(String fichero, String linea) throws IOException {
		try (PrintWriter pw = new PrintWriter(
				new FileWriter(fichero, StandardCharsets.UTF_8, true))) {
			pw.println(linea);
		}
	}

	// 9. Anadir al final con NIO (creando el fichero si no existe)
	static void anadirLineaNio(Path ruta, String linea) throws IOException {
		Files.writeString(ruta, linea + System.lineSeparator(),
				StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}

	// 10. Leer un fichero que NO es UTF-8 (CSV antiguo de Windows, etc.)
	static List<String> leerLatin1(Path ruta) throws IOException {
		return Files.readAllLines(ruta, StandardCharsets.ISO_8859_1);
	}

	// =================================================================
	// BINARIO
	// =================================================================

	// 11. Copiar un fichero binario cualquiera (java.io)
	static void copiar(String origen, String destino) throws IOException {
		try (InputStream in = new FileInputStream(origen);
				OutputStream out = new FileOutputStream(destino)) {
			in.transferTo(out);
		}
	}

	// 12. Copiar con NIO
	static void copiarNio(Path origen, Path destino) throws IOException {
		Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
	}

	// 13. Guardar primitivos en binario
	//     FileOutputStream -> BufferedOutputStream -> DataOutputStream
	static void guardarNumeros(String fichero, int[] numeros) throws IOException {
		try (DataOutputStream out = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(fichero)))) {
			out.writeInt(numeros.length);           // primero cuantos hay
			for (int n : numeros) out.writeInt(n);
		}
	}

	// 14. Leerlos: mismo orden y mismos tipos con que se escribieron
	static int[] leerNumeros(String fichero) throws IOException {
		try (DataInputStream in = new DataInputStream(
				new BufferedInputStream(new FileInputStream(fichero)))) {
			int[] numeros = new int[in.readInt()];
			for (int i = 0; i < numeros.length; i++) numeros[i] = in.readInt();
			return numeros;
		}
	}

	// 15. Leer primitivos sin saber cuantos hay: el final es EOFException
	static List<Integer> leerHastaElFinal(String fichero) throws IOException {
		List<Integer> lista = new ArrayList<>();
		try (DataInputStream in = new DataInputStream(
				new BufferedInputStream(new FileInputStream(fichero)))) {
			while (true) lista.add(in.readInt());
		} catch (EOFException fin) {
			return lista;                           // no es un error
		}
	}

	// 16. Guardar objetos: la lista entera de una vez (no se puede anadir)
	record Alumno(String nombre, int nota) implements Serializable {}

	static void guardarObjetos(String fichero, List<Alumno> alumnos)
			throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(fichero)))) {
			oos.writeObject(new ArrayList<>(alumnos));
		}
	}

	// 17. Leer objetos
	@SuppressWarnings("unchecked")
	static List<Alumno> leerObjetos(String fichero)
			throws IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(fichero)))) {
			return (List<Alumno>) ois.readObject();
		}
	}

	// 18. Acceso directo: registro n de tamano fijo (aqui, un int = 4 bytes)
	static int leerRegistro(String fichero, int n) throws IOException {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
			raf.seek((long) n * Integer.BYTES);
			return raf.readInt();
		}
	}

	static void escribirRegistro(String fichero, int n, int valor) throws IOException {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
			raf.seek((long) n * Integer.BYTES);
			raf.writeInt(valor);
		}
	}

	// =================================================================
	// MEMORIA Y FLUJOS AJENOS
	// =================================================================

	// 19. Texto que llega como InputStream (recurso del proyecto, red, ZIP...)
	//     InputStream ajeno -> InputStreamReader (UTF-8) -> BufferedReader
	static List<String> leerInputStream(InputStream in) throws IOException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(in, StandardCharsets.UTF_8))) {
			List<String> lineas = new ArrayList<>();
			String linea;
			while ((linea = br.readLine()) != null) {
				lineas.add(linea);
			}
			return lineas;
			// Con lambdas (Java 16): return br.lines().toList();
		}
	}
	// Uso con un recurso del proyecto:
	//   leerInputStream(RecetarioAD.class.getResourceAsStream("/datos.txt"))

	// 20. Capturar en un String lo que un metodo escribe en un Writer
	static String capturarTexto() {
		StringWriter sw = new StringWriter();
		try (PrintWriter pw = new PrintWriter(sw)) {
			pw.printf("%d alumnos%n", 2);
		}
		return sw.toString();
	}

	// 21. Montar un mensaje binario en memoria y obtener sus bytes
	static byte[] montarMensaje(int id, String texto) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (DataOutputStream dos = new DataOutputStream(baos)) {
			dos.writeInt(id);
			dos.writeUTF(texto);
		}
		return baos.toByteArray();
	}

	// 22. Pasar un texto o unos bytes a un metodo que pide Reader / InputStream
	//     (Java 24: Reader.of(texto) en lugar de new StringReader(texto))
	static void adaptadores() throws IOException {
		Reader r = new StringReader("uno\ndos");
		InputStream in = new ByteArrayInputStream(new byte[] {1, 2, 3});
		r.close();
		in.close();
	}

	// =================================================================
	// TECLADO
	// =================================================================

	// 23. Leer lineas del teclado (no se cierra: cerraria System.in)
	//     Java 25: IO.readln("Nombre: ") hace lo mismo en una linea
	static String leerLineaTeclado(String mensaje) throws IOException {
		System.out.print(mensaje);
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		return teclado.readLine();
	}

	// 24. Leer numeros del teclado con Scanner (cuidado con nextInt + nextLine)
	static int leerEnteroTeclado(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		return Integer.parseInt(sc.nextLine().trim());   // evita el salto pendiente
	}

	// =================================================================
	// PRUEBA DE TODAS LAS RECETAS
	// =================================================================
	public static void main(String[] args) throws Exception {
		Path dir = Files.createTempDirectory("recetario");
		String txt = dir.resolve("datos.txt").toString();
		Files.writeString(Path.of(txt), "uno\ndos\ntres\n");

		//Comenta y descomenta lo que quieras
		
		leerLineas(txt);
		leerLineasCorto(txt);
		leerLineasNio(Path.of(txt));
		System.out.print(leerTodo(Path.of(txt)));
		System.out.println("con 't': " + contarLineasCon(Path.of(txt), "t"));

		String csv = dir.resolve("notas.csv").toString();
		escribirCsv(csv, List.of("Ana", "Luis"), List.of(8, 6));
		anadirLinea(csv, "Eva;9");
		anadirLineaNio(Path.of(csv), "Iker;7");
		System.out.println(Files.readAllLines(Path.of(csv)));
		escribirCsvNio(dir.resolve("b.csv"), List.of("a;1", "b;2"));

		copiar(csv, dir.resolve("copia.csv").toString());
		copiarNio(Path.of(csv), dir.resolve("copia2.csv"));

		String bin = dir.resolve("n.bin").toString();
		guardarNumeros(bin, new int[] {10, 20, 30});
		System.out.println(java.util.Arrays.toString(leerNumeros(bin)));
		System.out.println(leerHastaElFinal(bin));     // incluye el 3 inicial
		escribirRegistro(bin, 2, 99);
		System.out.println("registro 2: " + leerRegistro(bin, 2));

		String obj = dir.resolve("alumnos.dat").toString();
		guardarObjetos(obj, List.of(new Alumno("Ana", 8), new Alumno("Luis", 6)));
		System.out.println(leerObjetos(obj));

		System.out.println(leerInputStream(new FileInputStream(txt)));
		System.out.print(capturarTexto());
		System.out.println("mensaje: " + montarMensaje(1, "hola").length + " bytes");
		adaptadores();
	}
}