package mrusa;

import java.util.concurrent.Semaphore;

public class Vagon {
    private volatile int C;
    private int numPas;

    Semaphore puedeBajar[];
    Semaphore puedeSubir[];
    Semaphore puedeViajar;

    public Vagon(int capacidad){
        C=capacidad;
        numPas = 0;
        puedeSubir = new Semaphore[C];
        for(int i = 0; i < C; i++)
            puedeSubir[i] = new Semaphore(1, true);
        puedeBajar = new Semaphore[C];
        for(int i = 0; i < C; i++)
            puedeBajar[i] = new Semaphore(0, true);
        puedeViajar = new Semaphore(0, true);
    }
    
    public void subir(int id) throws InterruptedException{
        System.out.println("[ESPERA] Pasajero "+ id + " esperando para subir");
        if(numPas < C){
            puedeSubir[numPas].acquire();
            System.out.println("[SUBE] Pasajero "+ id + " sube al vagon");
        } else if(numPas == C && puedeViajar.availablePermits() == 0){
            puedeViajar.release();
        }
    }

    public void bajar(int id) throws InterruptedException{
        puedeBajar[numPas].acquire();
        numPas--;
        System.out.println("[BAJA] Pasajero "+ id + " baja al vagon");
       
    }


    public void esperaLleno() throws InterruptedException{
        puedeViajar.acquire();
        System.out.println("    Viaje en la montaña rusa.");
    }

    public void finViaje(){
        System.out.println("    El viaje ha terminado.");
        while(numPas > 0){
            puedeBajar[numPas].release();
            numPas--;
        }
        numPas = C;
    }
    
}
