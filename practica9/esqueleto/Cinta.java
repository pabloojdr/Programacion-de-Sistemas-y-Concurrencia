package esqueleto;

import java.util.ArrayList;
import java.util.List;

public class Cinta {

	private int maletasPrimera, maletasTurista;
	private boolean cintaOcupada;
	private List<Integer> listaPrimera;

	
	public Cinta() {
		listaPrimera = new ArrayList<>();
		maletasPrimera = 0;
		maletasTurista = 0;
		cintaOcupada = false;
	}

	//El reponedor pone maleta
	public synchronized void poner(boolean primeraClase) throws InterruptedException {
		if(primeraClase){
			maletasPrimera++;
			System.out.println("*** Sale maleta de primera clase. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
		} else {
			maletasTurista++;
			System.out.println("	Sale maleta de clase turista. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
		}
		notifyAll();
	}
	
	//El de primera indica que quiere retirar, se bloquea se no puede
	public synchronized void qRetirarPrimera(int pasajeroId) throws InterruptedException {
		System.out.println("*** El pasajero de primera " + pasajeroId + " quiere retirar su maleta");
		listaPrimera.add(pasajeroId);
		while (cintaOcupada || maletasPrimera <= 0 || listaPrimera.get(0) != pasajeroId)
			wait();
		cintaOcupada = true;
		System.out.println("***		El pasajero de primera " + pasajeroId + " pasa a la cinta");
	}
	
	//El de primera retira, si llega a llamar aquí es porque puede retirar
	public synchronized void fRetirarPrimera(int pasajeroId) throws InterruptedException {
		maletasPrimera--;
		System.out.println("***		El pasajero de primera " + pasajeroId + " retira su maleta. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
		listaPrimera.remove(0);
		cintaOcupada = false;
		notifyAll();
	}
	
	//El turista indica que quiere retirar, se bloquea se no puede
	public synchronized void qRetirarTurista(int pasajeroId) throws InterruptedException {
		while(listaPrimera.size() != 0 || cintaOcupada || maletasTurista <= 0)
			wait();
		cintaOcupada = true;
		System.out.println("El pasajero de clase turista " + pasajeroId + " pasa a la cinta");
	}
	
	//El turista retira, si llega a llamar aquí es porque puede retirar
	public synchronized void fRetirarTurista(int pasajeroId) throws InterruptedException {
		maletasTurista--;
		System.out.println("	El pasajero de clase turista " + pasajeroId + " retira su maleta. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
		cintaOcupada = false;
		notifyAll();
	}
}
