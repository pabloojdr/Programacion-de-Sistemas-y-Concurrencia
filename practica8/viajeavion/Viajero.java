package viajeavion;

public class Viajero extends Thread{

	private int id;
	private Avion2 avion;
	
	public Viajero(Avion2 b, int id) {
		this.avion = b;
		this.id = id;
	}
	
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(2000);
				avion.viaje(id);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
