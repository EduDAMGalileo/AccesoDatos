package repaso.docs;

public record Direction(String calle, String municipio, String codigoPostal) { 
	
    public Direction {
        if (codigoPostal == null || codigoPostal.length() != 5) {
            throw new IllegalArgumentException("Código postal inválido");
        }
    }

}
