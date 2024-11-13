package fumadores;

import java.util.concurrent.*;

public class Mesa {

	// esta es una implementación pasiva para los fumadores
	// los van a despertar cuando tengan que fumar.
	Semaphore agent;
	Semaphore puedeFumar[];

	public Mesa() {
		agent = new Semaphore(1, true);
		puedeFumar = new Semaphore[3];
		puedeFumar[0] = new Semaphore(0, true);
		puedeFumar[1] = new Semaphore(0, true);
		puedeFumar[2] = new Semaphore(0, true);
 	}

	public void qFumar(int id) throws InterruptedException {
		puedeFumar[id].acquire();
		System.out.println("Fumador " + id + " coge los ingredientes");

	}

	public void finFumar(int id) {
		System.out.println("Fumador " + id + " ha terminado de fumar");
		agent.release();
	}

	public void nuevosIng(int ing) throws InterruptedException { // se pasa el ingrediente que no se pone
		agent.acquire();
		System.out.println("El agente ha puesto los ingredientes menos " + ing);
		puedeFumar[ing].release();
	}

}

// CS-Fumador i: No puede fumar hasta que el fumador anterior no ha terminado
// de fumar y sus ingredientes están sobre la mesa
// CS-Agente: no puede poner nuevos ingredientes hasta que el fumador anterior
// no ha terminado de fumar
