package pajaritosbinarios;

import java.util.Random;

public class Bebe implements Runnable {

	private static Random r = new Random();
	private Nido n;
	private int id;

	public Bebe(Nido n, int id) {
		this.id = id;
		this.n = n;
	}

	public void run() {
		while (true) {
			try {
				//System.out.println("El pajarito " + id + " pía un ratito");
				Thread.sleep(r.nextInt(1000));
				n.come(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
