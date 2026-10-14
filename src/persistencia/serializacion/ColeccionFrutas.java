package persistencia.serializacion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


class Fruta implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private double precioKilo;
	private boolean deTemporada;

	public Fruta(String nombre, double precioKilo, boolean deTemporada) {
		this.nombre = nombre;
		this.precioKilo = precioKilo;
		this.deTemporada = deTemporada;
	}

	@Override
	public String toString() {
		return String.format("%-10s | %5.2f €/kg | Temporada: %s", 
				nombre, precioKilo, (deTemporada ? "Sí" : "No"));
	}
}

//Programa de prueba
public class ColeccionFrutas{

	public static void main(String[] args) {
		Path ruta = Paths.get("frutas.dat");

		// Creamos la lista de frutas
		List<Fruta> frutasOriginales = new ArrayList<>();
		frutasOriginales.add(new Fruta("Manzana", 1.85, true));
		frutasOriginales.add(new Fruta("Plátano", 1.40, true));
		frutasOriginales.add(new Fruta("Cereza", 5.20, false));
		frutasOriginales.add(new Fruta("Naranja", 1.15, true));

		// ==========================================
		// GUARDAR LA COLECCIÓN COMPLETA
		// ==========================================
		System.out.println("--- GUARDANDO LISTA DE FRUTAS ---");
		try (ObjectOutputStream salida = new ObjectOutputStream(
				new BufferedOutputStream(Files.newOutputStream(ruta)))) {

			// Se pasa la lista entera directamente
			salida.writeObject(frutasOriginales);
			System.out.println("Lista de " + frutasOriginales.size() + " frutas guardada en disco.");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// ==========================================
		// RECUPERAR LA COLECCIÓN COMPLETA
		// ==========================================
		System.out.println("\n--- RECUPERANDO LISTA DE FRUTAS ---");
		try (ObjectInputStream entrada = new ObjectInputStream(
				new BufferedInputStream(Files.newInputStream(ruta)))) {

			// Recuperamos el objeto y hacemos el cast a List<Fruta>
			@SuppressWarnings("unchecked")
			List<Fruta> frutasRecuperadas = (List<Fruta>) entrada.readObject();

			System.out.println("Se han recuperado " + frutasRecuperadas.size() + " frutas:");
			for (Fruta f : frutasRecuperadas) {
				System.out.println(" - " + f);
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// Limpieza del archivo
			try {
				Files.deleteIfExists(ruta);
			} catch (IOException ignored) {}
		}
	}
}
