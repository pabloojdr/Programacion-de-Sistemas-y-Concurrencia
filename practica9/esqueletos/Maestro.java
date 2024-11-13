package esqueletos;

import java.util.Random;

public class Maestro extends Thread {
	
	private TableroJuego tablero;
	private static Random rnd = new Random();

	public Maestro(TableroJuego tablero) {
		this.tablero = tablero;
	}
	
	@Override
	public void run() {
		boolean playFinished = false;
		
		//El maestro sigue jugando mientras que la partida no haya terminado
		while(!playFinished) 
			try {
				sleep(rnd.nextInt(50)+100);
				playFinished = tablero.mueveMaestro();
			} catch (InterruptedException x) {
				x.printStackTrace();
			}
	}
}
