package recursos;

import java.util.ArrayList;
import java.util.List;

public class Control {
	private int numRec;
	List<Integer> colaEspera; 
	
	public Control(int num){
		this.numRec = num;
		colaEspera = new ArrayList<>();
	}

	public synchronized void qRecursos(int id, int num) throws InterruptedException{
		colaEspera.add(id);
		System.out.println("Llega el proceso " + id + ".");
		while (id != colaEspera.get(0) || (numRec - num) < 0)
			wait();
		System.out.println("	Entra el proceso " + id + ".");
		colaEspera.remove(0);
		numRec -= num;
		System.out.println("	El proceso " + id + " utiliza " + num + " recursos. Quedan " + numRec + ".");
		notifyAll();
	}


	public synchronized void libRecursos(int id, int num) throws InterruptedException{
		numRec+=num;
		System.out.println("	El proceso " + id + " ha liberado " + num + " recursos. Hay " + numRec + ".");
		notifyAll();
	}
}
//CS-1: un proceso tiene que esperar su turno para coger los recursos
//CS-2: cuando es su turno el proceso debe esperar hasta haya recursos suficiente
//para él 
