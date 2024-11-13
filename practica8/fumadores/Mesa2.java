package fumadores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa2 {

   	private Lock l;
	private Condition colaFumadores;
	private Condition colaCamellos;
	private boolean puedeFumar, puedePoner;
	private int ingrediente;

	public Mesa2() {
        l = new ReentrantLock(true);
		colaFumadores = l.newCondition();
		colaCamellos = l.newCondition();
		puedeFumar = false;
		puedePoner = true;
	}

	public void qFumar(int id) throws InterruptedException {
		l.lock();
		try {
			while(!puedeFumar || id != ingrediente)
				colaFumadores.await();
			
			System.out.println("Fumador " + id + " coge los ingredientes");
			puedeFumar = false;
		} finally {
			l.unlock();
		}
	}

	public void finFumar(int id) {
		l.lock();
		try{
			System.out.println("Fumador " + id + " ha terminado de fumar");
			puedePoner = true;
			colaCamellos.signal();
		} finally {
			l.unlock();
		}
	}

	// Recuerda que es un número de 0..2,
	// y que si es 0 se espera que fume el fumador 0
	// si es 1 se espera que fume el id 1
	// y si es 2, debe fumar el que tiene id 2
	public void nuevosIng(int ing) throws InterruptedException { // se pasa el ingrediente que no se pone
		l.lock();
		try {
			while(!puedePoner)
				colaCamellos.await();

			puedePoner = false;
			System.out.println("El agente ha puesto el ingrediente " + ing);
			ingrediente = ing;
			puedeFumar = true;
			colaFumadores.signalAll();
		} finally {
			l.unlock();
		}
	}

}
