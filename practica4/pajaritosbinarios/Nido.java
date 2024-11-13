package pajaritosbinarios;

import java.util.concurrent.*;

public class Nido {
	private int B = 10; // Número máximo de bichos
	private int bichitos=0; // puede tener de 0 a B bichitos
	
	// Sugerencia, tener tres semáforos. 
	// Uno para acceder en exlcusion mutua a bichitos.
	// Otro para sincronizar cuando se puede comer.
	// Otro para sincronizar cuando se puede poner.
	Semaphore mutexBichitos = new Semaphore(1, true);
	Semaphore puedeComer = new Semaphore(0, true);
	Semaphore puedePoner = new Semaphore(0, true);

	public void come(int id) throws InterruptedException {
		mutexBichitos.acquire();
		if(bichitos <= 0 && puedePoner.availablePermits() == 0){
			puedePoner.release();
		} else if(puedeComer.availablePermits() != 0) {
			puedeComer.acquire();
			bichitos--;
			System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
			if(bichitos > 0)
				puedeComer.release();
			if(puedePoner.availablePermits() == 0)
				puedePoner.release();
		}
		mutexBichitos.release();
	}

	// el papa/mama id deja un nuevo bichito en el nido
	public void nuevoBichito(int id) throws InterruptedException {
		mutexBichitos.acquire();
		if(bichitos >= B && puedeComer.availablePermits() == 0){
			puedeComer.release();
		} else if(puedePoner.availablePermits() != 0){
			puedePoner.acquire();
			bichitos++;
			System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
			if(bichitos < B)
				puedePoner.release();
			if(puedeComer.availablePermits() == 0)
				puedeComer.release();
		}
		mutexBichitos.release();
	}
}

// CS-Bebe-i: No puede comer del nido si está vacío
// CS-Papa/Mama: No puede poner un bichito en el nido si está lleno