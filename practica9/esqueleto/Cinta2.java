package esqueleto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cinta2 {

	private int maletasPrimera, maletasTurista;
	private boolean cintaOcupada;
	private List<Integer> listaPrimera;
    Lock l;
    Condition colaPrimera;
    Condition colaTurista;
	
	public Cinta2() {
		listaPrimera = new ArrayList<>();
		maletasPrimera = 0;
		maletasTurista = 0;
		cintaOcupada = false;
        l = new ReentrantLock(true);
        colaPrimera = l.newCondition();
        colaTurista = l.newCondition();
	}

	//El reponedor pone maleta
	public void poner(boolean primeraClase) throws InterruptedException {
		l.lock();
        try {
            if(primeraClase){
                maletasPrimera++;
                System.out.println("*** Sale maleta de primera clase. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
            } else {
                maletasTurista++;
                System.out.println("	Sale maleta de clase turista. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
            }
            colaPrimera.signalAll();
            colaTurista.signalAll();
        } finally {
            l.unlock();
        }
	}
	
	//El de primera indica que quiere retirar, se bloquea se no puede
	public void qRetirarPrimera(int pasajeroId) throws InterruptedException {
		l.lock();
        try {
            System.out.println("*** El pasajero de primera " + pasajeroId + " quiere retirar su maleta");
            listaPrimera.add(pasajeroId);
            while (cintaOcupada || maletasPrimera <= 0 || listaPrimera.get(0) != pasajeroId)
                colaPrimera.await();
            cintaOcupada = true;
            System.out.println("***		El pasajero de primera " + pasajeroId + " pasa a la cinta");
        } finally {
            l.unlock();
        }
	}
	
	//El de primera retira, si llega a llamar aquí es porque puede retirar
	public void fRetirarPrimera(int pasajeroId) throws InterruptedException {
		l.lock();
        try {
            maletasPrimera--;
            System.out.println("***		El pasajero de primera " + pasajeroId + " retira su maleta. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
            listaPrimera.remove(0);
            cintaOcupada = false;
    
            if(listaPrimera.isEmpty())
                colaTurista.signalAll();
            else
                colaPrimera.signalAll();  
        } finally {
            l.unlock();
        }
	}
	
	//El turista indica que quiere retirar, se bloquea se no puede
	public void qRetirarTurista(int pasajeroId) throws InterruptedException {
		l.lock();
        try {
            while(listaPrimera.size() != 0 || cintaOcupada || maletasTurista <= 0)
                colaTurista.await();
            cintaOcupada = true;
            System.out.println("El pasajero de clase turista " + pasajeroId + " pasa a la cinta");
        } finally {
            l.unlock();
        }
	}
	
	//El turista retira, si llega a llamar aquí es porque puede retirar
	public void fRetirarTurista(int pasajeroId) throws InterruptedException {
		l.lock();
        try {
            maletasTurista--;
            System.out.println("	El pasajero de clase turista " + pasajeroId + " retira su maleta. maletasP: " + maletasPrimera + ". maletasT: " + maletasTurista);
            cintaOcupada = false;
            if(listaPrimera.isEmpty())
                colaTurista.signalAll();
            else
                colaPrimera.signalAll();
        } finally {
            l.unlock();
        }
	}
}