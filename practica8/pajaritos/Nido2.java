package pajaritos;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Nido2 {
    private int B = 10; // Número máximo de bichos
    private int bichitos = 0; // puede tener de 0 a B bichitos
    Lock l = new ReentrantLock(true);
    Condition colaPadres = l.newCondition();
    Condition colaBebes = l.newCondition();
	private boolean puedeComer = false;
	private boolean puedePoner = true;

    public void come(int id) throws InterruptedException {
        l.lock();
        try{
            while(!puedeComer)
                colaBebes.await();
            bichitos--;
            System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
            
            if(bichitos == 0)
                puedeComer = false;
            
            puedePoner = true;
            colaPadres.signalAll();
        } finally {
            l.unlock();
        }
    }

    public void nuevoBichito(int id) throws InterruptedException {
    	l.lock();
        try{
            while(!puedePoner || bichitos == B)
                colaPadres.await();
            bichitos++;
            System.out.println("	El papá " + id + " ha añadido un bichito. Hay " + bichitos);
            
            if(bichitos == B){
                puedePoner = false;
            }
    
            puedeComer = true;
            colaBebes.signalAll();
        } finally {
            l.unlock();
        }
    }
}
