package repaso.clasesInternas;


public class AppExterna {
	public static void main(String[] args) {
		Externa ext = new Externa();
		Externa.Interna in = ext.new Interna();

		in.probar();
	}

}
