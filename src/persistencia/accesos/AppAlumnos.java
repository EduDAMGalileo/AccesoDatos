package persistencia.accesos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

public class AppAlumnos {

	private static final String FICHERO = "alumnos.dat";

	public static void main(String[] args) {
		AlumnoFicheroDAO dao = new AlumnoFicheroDAO();
		File file = new File(FICHERO);

		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {

			Alumno a1 = new Alumno(	1, "12345678", "Ada", "Lovelace", LocalDate.of(1995, 12, 10), "ada@correo.com");
			Alumno a2 = new Alumno(2, "87654321", "Alan", "Turing",	LocalDate.of(1998, 6, 23), null);

			System.out.println("Tamaño de registro: " + FormatoAlumno.TAMANO + " bytes");

			// Escribir alumno 1 en la posición 0
			raf.seek(FormatoAlumno.posicionDe(0));
			dao.escribir(raf, a1);

			//Escribir alumno 2 en la posición 1
			raf.seek(FormatoAlumno.posicionDe(1));
			dao.escribir(raf, a2);

			System.out.println("Bytes totales tras escribir 2 registros: " + raf.length());

			//Comprobar lecturas saltando directamente a cada registro
			System.out.println("\n--- Comprobando lectura ---");
			raf.seek(FormatoAlumno.posicionDe(0));
			System.out.println("Registro 0: " + dao.leer(raf));

			raf.seek(FormatoAlumno.posicionDe(1));
			System.out.println("Registro 1: " + dao.leer(raf));

			//Probar que la validación estricta funciona
			System.out.println("\n--- Probando texto demasiado largo ---");
			Alumno alumnoInvalido = new Alumno(3,"NIA_DEMASIADO_LARGO_PARA_8_CHARS","Nombre","Apellidos",LocalDate.now(),"");

			// Provoca DatosInvalidosException
			dao.escribir(raf, alumnoInvalido); 

		} catch (DatosInvalidosException e) {
			System.out.println("(!) Error capturado con éxito: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}
	}
}