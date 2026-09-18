package persistencia;

import java.time.LocalDate;

public class AppAlumno {

    public static void main(String[] args) {
    	
    	matricular();
        
    }
    
    public static void matricular() {
    	AccesoADatos accesoADatos = new AccesoADatos();
        Alumno a = new Alumno("Eduardo", LocalDate.of(1984, 4, 12));
        a.setDireccion(new Direccion("Calle Larga 3", "Valladolid", "47001"));
        a.matricularEn(accesoADatos);
    }
}