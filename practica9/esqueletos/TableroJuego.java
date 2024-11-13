package esqueletos;

import java.util.Random;

public abstract class TableroJuego {

	private Random rnd = new Random();
	
	private int mov; //Numero de movimientos realizados
	
	protected int numJugadores; //Numero de jugadores
	protected int ganador; //-1: no hay ganador; 0: han ganado los jugadores; 1: ha ganado el maestro
	
	public TableroJuego(int numJugadores) {
		mov = 0;
		this.numJugadores = numJugadores;
		this.ganador = -1;
	}
	
	protected void movimiento() {
		mov++;
	}

	protected int getMovimientos() {
		return mov;
	}
	
	protected boolean hayGanador() {
		double v = (mov - 5) / 3;
		v = v*v*v+3;
		return rnd.nextInt((int) v) > 3;
	}
	
	//Métodos a implementar en las clases que hereden de esta clase
	public abstract void mueveJugador(int id) throws InterruptedException;
	
	public abstract boolean esperaAlMaestro(int id) throws InterruptedException; 
	
	public abstract boolean mueveMaestro() throws InterruptedException;
	
	public abstract void iniciaDescanso(int id) throws InterruptedException;
	
	public abstract void finDescanso(int id) throws InterruptedException;
}