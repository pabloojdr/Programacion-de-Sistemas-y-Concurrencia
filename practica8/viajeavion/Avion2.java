package viajeavion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Avion2 {
	
	private int N, numFrontal, numCola;
	private boolean comienzaViaje, finViaje, puedenSubir;
	private List<Integer> listaFrontal;
    private Lock l;
    private Condition colaViajeros, colaPiloto, colaViaje, colaBajadaFinal;
	

	public Avion2(int capacidad_area){
		N = capacidad_area;
		numFrontal = 0;
		numCola = 0;
		comienzaViaje = false;
		finViaje = false;
		puedenSubir = true;
		listaFrontal = new ArrayList<>();
        l = new ReentrantLock(true);
        colaViajeros = l.newCondition();
        colaPiloto = l.newCondition();
        colaViaje = l.newCondition();
        colaBajadaFinal = l.newCondition();
	}

	public void viaje(int id) throws InterruptedException {
		l.lock();
        try{

            while(!puedenSubir || comienzaViaje || (numCola + numFrontal) >= 2*N)
                colaViajeros.await();
            
            System.out.println("[El pasajero "+id+" esperando subir]");
            
            if(numFrontal < N){
                numFrontal++;
                System.out.println("El pasajero "+id+" ha subido a la parte frontal");
                listaFrontal.add(id);
            } else if(numFrontal == N) {
                numCola++;
                System.out.println("El pasajero " + id + " ha subido a la cola");
            }
    
    
            if(numCola + numFrontal == 2*N){
                comienzaViaje = true;
                puedenSubir = false;
                colaPiloto.signal();
            }
             
            while(!finViaje)
                colaViaje.await();
            
            while(!listaFrontal.contains(id) && numFrontal != 0)
                colaBajadaFinal.await();
            
            if(listaFrontal.contains(id)){
                System.out.println("	El pasajero "+id+" ha bajado de la parte frontal");
                numFrontal--;
            } else {
                System.out.println("	El pasajero " + id + " ha bajado de la cola");
                numCola--;
            }
    
            if(numFrontal == 0){
                listaFrontal.removeAll(listaFrontal);
                colaBajadaFinal.signalAll();
            }
    
            if(numCola + numFrontal == 0){
                puedenSubir = true;
                finViaje = false;
                colaViajeros.signalAll();
            }
        } finally {
            l.unlock();
        }
	}

	public void empiezaViaje() throws InterruptedException {
		l.lock();
        try{
            while(!comienzaViaje)
                colaPiloto.await();
            System.out.println("*** Empieza el viaje!!!");
        }finally{
            l.unlock();
        }
	}

	public void finViaje() throws InterruptedException  {
		l.lock();
        try{
            System.out.println("*** Fin del viaje!!!");	
            comienzaViaje = false;
            finViaje = true;
            colaViaje.signalAll();	
        } finally {
            l.unlock();
        }
	}
}