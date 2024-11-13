package esqueletos;

public class Driver {
	//TEST = 0 test semaforos sin descanso
	//TEST = 1 test semaforos con descanso
	
	private static final int TEST = 1;  //Cambia el valor de esta variable para probar las distintas versiones
	private static final int NUM_JUGADORES = 5;
	
	public static void main(String[] args) {
		//Creamos el tablero de juego
		TableroJuego tablero;
		
		//Creamos la instancia del recurso compartido que queremos probar
		boolean descanso = (TEST > 0);
		if (TEST == 0) tablero = new TableroJuegoSemaforosV1(NUM_JUGADORES);
		if (TEST == 1) tablero = new TableroJuegoSemaforosV2(NUM_JUGADORES);
		
		//Creamos los jugadores
		for(int id=0; id<NUM_JUGADORES; id++)
			new Jugador(id,tablero,descanso).start();
		
		//Creamos al maestro
		new Maestro(tablero).start();
	}
}
