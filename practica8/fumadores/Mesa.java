package fumadores;

public class Mesa {
	boolean puedeFumar, puedePoner;
	int	ingrediente;

	public Mesa() {
		puedeFumar = false;
		puedePoner = true;
	}

	public synchronized void qFumar(int id) throws InterruptedException {
		while(!puedeFumar || id != ingrediente) // Podríamos usar solo ingrediente y cuando acabe de fumar, asignarle el valor -1
			wait();
			
		System.out.println("Fumador " + id + " coge los ingredientes");
		puedeFumar = false; // ingrediente = -1;
	}

	public synchronized void finFumar(int id) {
		System.out.println("Fumador " + id + " ha terminado de fumar");
		puedePoner = true;
		notifyAll();
	}

	// Recuerda que es un número de 0..2,
	// y que si es 0 se espera que fume el fumador 0
	// si es 1 se espera que fume el id 1
	// y si es 2, debe fumar el que tiene id 2
	public synchronized void nuevosIng(int ing) throws InterruptedException { // se pasa el ingrediente que no se pone
		while(!puedePoner) // podríamos usar como condición ingrediente != -1
			wait();
		puedePoner = false;
		System.out.println("El agente ha puesto el ingrediente " + ing);
		ingrediente = ing;
		puedeFumar = true;
		notifyAll();
	}
}
