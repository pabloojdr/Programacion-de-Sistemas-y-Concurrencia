package recursos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Control2 {
	private int numRec;
    Lock l;
    Condition colaCoger;
	List<Integer> colaEspera; 
	
	public Control2(int num){
		this.numRec = num;
        l = new ReentrantLock(true);
        colaCoger = l.newCondition();
		colaEspera = new ArrayList<>();
	}

	public void qRecursos(int id, int num) throws InterruptedException{
		l.lock();
        try{
            colaEspera.add(id);
            System.out.println("Control2: Llega el proceso " + id + ".");
            while (id != colaEspera.get(0) || (numRec - num) < 0)
                colaCoger.await();
            System.out.println("	Entra el proceso " + id + ".");
            colaEspera.remove(0);
            numRec -= num;
            System.out.println("	El proceso " + id + " utiliza " + num + " recursos. Quedan " + numRec + ".");
            colaCoger.signalAll();
        }finally{
            l.unlock();
        }
	}


	public void libRecursos(int id, int num) throws InterruptedException{
		l.lock();
        try{
            numRec+=num;
            System.out.println("	El proceso " + id + " ha liberado " + num + " recursos. Hay " + numRec + ".");
            colaCoger.signalAll();
        } finally {
            l.unlock();
        }
	}
}
//CS-1: un proceso tiene que esperar su turno para coger los recursos
//CS-2: cuando es su turno el proceso debe esperar hasta haya recursos suficiente
//para él 
