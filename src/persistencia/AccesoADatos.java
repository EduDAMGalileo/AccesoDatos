package persistencia;

public class AccesoADatos {
	//Clase tonta que simula guardar al alumno
    public void guardar(Alumno alumno) {
        System.out.println("Guardando al alumno: " + alumno.getNombre());
        System.out.println("Fecha de nacimiento: " + alumno.getFechaNacimiento());
        System.out.println("Dirección: "+ alumno.getDireccion().getCalle() + ", " + alumno.getDireccion().getCiudad());
        System.out.println("Matrícula: " + alumno.getMatricula().getNumero());
    }
}