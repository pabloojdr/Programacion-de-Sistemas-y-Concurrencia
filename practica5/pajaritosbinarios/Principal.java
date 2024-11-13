package pajaritosbinarios;

public class Principal {

	public static void main(String[] args) {
		Nido n = new Nido();
		Thread p = new Thread(new Adulto(n, 0),"Macho");
		Thread m = new Thread(new Adulto(n, 1),"Hembra");
		Thread[] b = new Thread[10];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Thread(new Bebe(n, i),"Pajaro " + i);
		}
		p.start();
		m.start();
		for (int i = 0; i < b.length; i++) {
			b[i].start();
		}
	}
}
