package repaso.clasesInternas;

public class Banco {
    private String nombre;
    //Dinero total del banco
    private double reservas;

    public Banco(String nombre, double reservas) {
        this.nombre   = nombre;
        this.reservas = reservas;
    }

    public String getNombre() {
		return nombre;
	}

	public double getReservas() {
		return reservas;
	}

	// Clase interna de instancia: tiene acceso a 'nombre' y 'reservas'
    public class Cuenta {
        private String titular;
        private double saldo;

        public Cuenta(String titular, double saldoInicial) {
            this.titular = titular;
            this.saldo   = saldoInicial;
        }

        public void depositar(double cantidad) {
            saldo   += cantidad;
            //accede al campo privado de Banco
            reservas += cantidad;  
        }

        public String resumen() {
            // Accede a 'nombre' de la instancia de Banco que la contiene
            return titular + " en " + nombre + ": " + saldo + "€";
        }
    }
}

