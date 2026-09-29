package repaso.docs;

import java.math.BigDecimal;

public class ErrorDecimal {

	public static void main(String[] args) {
		System.out.println(0.1 + 0.2);      // contra todo pronóstico 0.30000000000000004

		// Con el tipo adecuado:
		BigDecimal a = new BigDecimal("0.1");
		BigDecimal b = new BigDecimal("0.2");
		System.out.println(a.add(b));       // 0.3


	}

}
