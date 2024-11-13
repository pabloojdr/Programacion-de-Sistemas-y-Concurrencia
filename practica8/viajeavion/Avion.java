package viajeavion;

import java.util.ArrayList;
import java.util.List;  

public class Avion {
	
	private int N, numFrontal, numCola;
	private boolean comienzaViaje, finViaje, puedenSubir;
	private List<Integer> listaFrontal;
	

	public Avion(int capacidad_area){
		N = capacidad_area;
		numFrontal = 0;
		numCola = 0;
		comienzaViaje = false;
		finViaje = false;
		puedenSubir = true;
		listaFrontal = new ArrayList<>();
	}

	public synchronized void viaje(int id) throws InterruptedException {
		while(!puedenSubir || comienzaViaje || (numCola + numFrontal) >= 2*N)
			wait();
		
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
			notifyAll();
		}
		 
		while(!finViaje)
			wait();
		
		while(!listaFrontal.contains(id) && numFrontal != 0)
			wait();
		
		if(listaFrontal.contains(id)){
			System.out.println("	El pasajero "+id+" ha bajado de la parte frontal");
			numFrontal--;
		} else {
			System.out.println("	El pasajero " + id + " ha bajado de la cola");
			numCola--;
		}

		if(numFrontal == 0){
			listaFrontal.removeAll(listaFrontal);
			notifyAll();
		}
		if(numCola + numFrontal == 0){
			puedenSubir = true;
			finViaje = false;
			notifyAll();
		}
	}

	public synchronized void empiezaViaje() throws InterruptedException {
		while(!comienzaViaje)
			wait();
		System.out.println("*** Empieza el viaje!!!");
	}
	
	public synchronized void finViaje() throws InterruptedException  {
		System.out.println("*** Fin del viaje!!!");	
		comienzaViaje = false;
		finViaje = true;
		notifyAll();	
	}
}
