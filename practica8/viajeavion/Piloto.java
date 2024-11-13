package viajeavion;

import java.util.*;

public class Piloto extends Thread{

	private Avion2 avi;
	public Piloto(Avion2 av) {
		this.avi = av;
	}
	private Random r = new Random();
	public void run() {
		while (true) {
			try {
				avi.empiezaViaje();
				Thread.sleep(r.nextInt(1000));
				avi.finViaje();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
