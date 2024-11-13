package esqueletos;

import java.util.ArrayList;
import java.util.List;

public class TableroJuegoSemaforosV1 extends TableroJuego{
	private int jugadoresDescansando, jugadoresListos;
	private boolean juegaMaestro;
	List<Integer> listaOrden;

	/*
	 * RECORDATORIO: Esta clase tiene acceso a los atributos 
	 * numJugadores y ganador de la clase TableroJuego
	 * 
	 * Tambien tiene acceso a los metodos movimiento(), 
	 * getMovimientos() y hayGanador() de la clase TableroJuego
	 */
	
	public TableroJuegoSemaforosV1(int numJugadores) {
		super(numJugadores);
		jugadoresDescansando = 0;
		jugadoresListos = 0;
		juegaMaestro = false;
		listaOrden = new ArrayList<>(); 
		for(int i = 0; i < numJugadores; i++)
			listaOrden.add(i);
	}
	
	@Override
	public synchronized void mueveJugador(int id) throws InterruptedException {
		while(jugadoresDescansando != 0 || juegaMaestro || listaOrden.get(0) != id){
			if(jugadoresDescansando != 0)
				System.out.println("	Jugador " + id + " no puede jugar. Hay otros descansando");
			wait();
		}
		movimiento();
		System.out.println("El jugador " + id + " hace su movimiento");	
		jugadoresListos++;

		if(jugadoresListos == numJugadores){
			juegaMaestro = true;
		}
	}

	@Override
	public synchronized boolean esperaAlMaestro(int id) throws InterruptedException {
		System.out.println("El jugador " + id + " espera al maestro");
		listaOrden.remove(0);
		notifyAll();
		while(juegaMaestro || jugadoresListos != numJugadores)
			wait();
		if (ganador == 0) {
			System.out.println("El jugador " + id + " dice: Hemos ganado al maestro!!");
		} else if (ganador == 1) {
			System.out.println("El jugador " + id +  " dice: Nos has ganado pero volveremos a intentarlo");
		} else if (ganador == -1) {
			notifyAll();
			if(id == numJugadores - 1)
				jugadoresListos = 0;
			return false;
		}
		return true; //Incluido para que compile
	}

	@Override
	public synchronized boolean mueveMaestro() throws InterruptedException {
		while(!juegaMaestro)
			wait();

		if(hayGanador()) {
			ganador = 0;
			System.out.println("---------------------------------------------------------");
			System.out.println("El maestro dice: Los jugadores me han ganado en " + getMovimientos() + " movimientos");
			System.out.println("---------------------------------------------------------");
			juegaMaestro = false;
			notifyAll();
			return true;
		}

		movimiento();

		if(hayGanador()){
			ganador = 1;
			System.out.println("---------------------------------------------------------");
			System.out.println("El maestro dice: He ganado en " + getMovimientos() + " movimientos");
			System.out.println("---------------------------------------------------------");
			juegaMaestro = false;
			notifyAll();
		} else {
			System.out.println("---------------------------------------------------------");
			System.out.println("El maestro dice: No ha ganado nadie. Seguimos jugando");
			System.out.println("---------------------------------------------------------");
			juegaMaestro = false;
			listaOrden.clear();
			for(int i = 0; i < numJugadores; i++){
				listaOrden.add(i);
			}
			notifyAll();
			return false;
		}
		return true; 
	}

	@Override
	public synchronized void iniciaDescanso(int id) throws InterruptedException{
		jugadoresDescansando++;
		System.out.println("El jugador " + id + " incia un descanso. Descansando = " + jugadoresDescansando);
	}
	
	@Override
	public synchronized void finDescanso(int id) throws InterruptedException{
		jugadoresDescansando--;
		System.out.println("Jugador " + id + " finaliza su descanso. Descansando = " + jugadoresDescansando);
		if (jugadoresDescansando == 0)
			notifyAll();
	}
}