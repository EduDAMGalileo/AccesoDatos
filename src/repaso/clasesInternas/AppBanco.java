package repaso.clasesInternas;

import repaso.clasesInternas.Banco.Cuenta;

public class AppBanco {
	public static void main (String[] args) {
		// Para crear una Cuenta necesitamos primero una instancia de Banco:
		Banco miBanco = new Banco("CajaNueva", 1_000_000);
		//Ahora podemos crear cuentas dentro de ese Banco
		Banco.Cuenta cuenta1 = miBanco.new Cuenta("Ana", 500);
		Banco.Cuenta cuenta2 = miBanco.new Cuenta("Luis", 1200);

		cuenta1.depositar(200);
		System.out.println(cuenta1.resumen());
		System.out.println(miBanco.getReservas());
	}

}
