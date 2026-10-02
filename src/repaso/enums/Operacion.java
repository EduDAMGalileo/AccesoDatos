package repaso.enums;

public enum Operacion {

	// Cada constante define un cuerpo de clase anónimo con su propia implementación
	SUMA("+") {
		@Override
		public double aplicar(double a, double b) {
			return a + b;
		}
	},

	RESTA("-") {
		@Override
		public double aplicar(double a, double b) {
			return a - b;
		}
	},

	MULTIPLICACION("*") {
		@Override
		public double aplicar(double a, double b) {
			return a * b;
		}
	},

	DIVISION("/") {
		@Override
		public double aplicar(double a, double b) {
			if (b == 0) {
				throw new ArithmeticException("División por cero no permitida");
			}
			return a / b;
		}
	}; // <-- Punto y coma obligatorio tras las constantes

	// Atributo que guarda el símbolo de la operación
	private final String simbolo;

	// Constructor privado del enum
	Operacion(String simbolo) {
		this.simbolo = simbolo;
	}

	/**
	 * MÉTODO ABSTRACTO:
	 * Al declararlo aquí, obligamos al compilador a verificar que TODAS y cada una
	 * de las constantes de arriba implementen obligatoriamente este método.
	 */
	public abstract double aplicar(double a, double b);

	// Muestra el símbolo en lugar del nombre (ej: "+" en lugar de "SUMA")
	@Override
	public String toString() {
		return simbolo;
	}
}