package esqueletos;

import java.util.Random;

//No es necesario cambiar nada de esta clase en las distintas versiones del ejercicio
public class Jugador extends Thread {
	
	private int id;
	private boolean descanso;
	private TableroJuego tablero;
	private static Random rnd = new Random();
	
	public Jugador(int id, TableroJuego tablero, boolean descanso) {
		this.id = id;
		this.descanso = descanso; //true: puede descansar, false: no solicita descanso
		this.tablero = tablero;
		setName("Jugador "+id);
	}

	@Override
	public void run() {
		boolean playFinished = false;
		
		//El jugador sigue jugando mientras no le indiquen que la partida ha terminado
		while(!playFinished) 
			try {
				//Este if solo se ejecuta si se ha activado la opcion de descansar
				if (descanso && rnd.nextInt(2) == 1) {
					//El jugador inicia un descanso
					tablero.iniciaDescanso(id);
					Thread.sleep(rnd.nextInt(10)*100);
					//El jugador indica que su descanso ha finalizado
					tablero.finDescanso(id);
				}
				
				//El jugador hace su jugada
				tablero.mueveJugador(id);
				//El jugador espera a que el maestro termine de hacer su jugada
				playFinished = tablero.esperaAlMaestro(id);
				
				sleep(rnd.nextInt(300)+200);
			} catch (InterruptedException x) {
				x.printStackTrace();
			}
	}

}
