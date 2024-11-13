package ventaonline;

import java.util.concurrent.Semaphore;

public class Web {

Semaphore clientes = new Semaphore(20, true);


//Utilizado por el cliente id para relaizar el prepago y comprar.
	public void entroweb(int id) throws InterruptedException {
		clientes.acquire();
		System.out.println("El cliente "+id+" ha prepagado.");
		System.out.println("El cliente "+id+" esta mirando el stock.");
	}

	/**
	 * Utilizado por el cliente id cuando finaliza su compra
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void salgoweb(int id) throws InterruptedException {
		System.out.println("El cliente "+id+" ha comprado.");
		clientes.release();
	}

	/**
	 * Utilizado por el reponedor cuando quiere entrar en la web
	 * CS: El reponedor esta solo en la web, es decir, espera hasta que
	 * no haya ningun cliente.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void reponerEntra() throws InterruptedException {
		clientes.acquire(20);
		System.out.println("El reponedor entra en la web.");
	}

	/**
	 * Utilizado por el reponedor cuando sale de la web
	 * 
	 * 
	 */
	public void reponerSale() {
		System.out.println("El reponedor ha terminado su tarea.");
		clientes.release(20);
	}
}