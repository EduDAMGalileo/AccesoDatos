package ejercicios.tema4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppAlumno {

	
	public static void alta10Alumnos(RandomAccessFile raf,AlumnoFicheroDAO dao ) throws IOException {
		//Vamos a crear una lista, por trabajar con Listas
		List<Alumno> alumnos = new ArrayList<>();
		for (int i=1; i<=10;i++) {
			alumnos.add(new Alumno(i, "A1234"+i, "Juan"+i, "Gómez Puente", 
					LocalDate.of(1984, 4, 14), "correo@edu.com"));
		}
		
		//Para meterlos en el fichero recorremos la lista y repasamos la lista
		for(Alumno a: alumnos) {
			dao.escribir(raf, a);
		}
		
		
		 
		  for (int i=1; i<=10;i++) {
			dao.escribir(raf, new Alumno(i, "A1234"+i, "Juan"+i, "Gómez Puente", 
					LocalDate.of(1984, 4, 14), "correo@edu.com"));
		}
		 
		
		
	}
	
	public static void consultarEstadisticas(RandomAccessFile raf) throws IOException{
		System.out.println ("Un registro ocupa: " + FormatoAlumno.TAMANO);
		System.out.println ("El fichero ocupa: " + raf.length());
		System.out.println ("Estamos en la posición: " + raf.getFilePointer());
	}
	
	
	public static void main(String[] args){
		//Ampliamos el ejercicio, vamos a poder:
			//1."Borrar" alumnos del fichero
			//2.Mostrar todos los alumnos (que no estén borrados)
			//3.Ahora los alumnos tienen un campo nuevo nacionalidad (20 caracteres)
		
			//Bonus, mejorar el escribir alumno, busca el primer hueco que esté borrado
			//Bonus 2, compactar, reorganiza el fichero para que no haya borrados.

		Scanner sc =new Scanner(System.in);
		AlumnoFicheroDAO dao = new AlumnoFicheroDAO();
		try(RandomAccessFile raf = new RandomAccessFile("fichero_alumnos.dat", "rw")){
			int opcion;
			do {
				System.out.println("Menú");
				System.out.println("1.Dar de alta 10 alumnos");
				System.out.println("2.Dar de baja 1 alumno");
				System.out.println("3.Dar de alta 1 alumno");
				System.out.println("4.Listar todos los alumnos");
				System.out.println("5.Consultar estadisticas");
				System.out.println("6.Mover puntero");
				System.out.println("7.Salir");
				
				opcion=sc.nextInt();
				switch(opcion) {
				case 1 ->alta10Alumnos(raf, dao);
				case 3 ->dao.escribir(raf, new Alumno(50, "B1222", "Alumno", "Creado a mano", 
						LocalDate.of(1980, 5, 6), null));
				case 5 ->consultarEstadisticas(raf);
				default -> System.out.println("Opción no válida");
				}
				
			} while (opcion!=7);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
}
