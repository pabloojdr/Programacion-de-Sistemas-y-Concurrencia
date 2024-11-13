package esqueleto;

import java.util.Random;

public class Generador extends Thread {
	Cinta2 c;
	Random r;
	
	public Generador(Cinta2 c) {
		this.c=c;
		r = new Random();
	}
	
	
	public void run() {


		try {
			while (true) {
				c.poner(r.nextBoolean());
				Thread.sleep(500);
			}
		} catch (Exception ex) {

		}
	}


	
}
