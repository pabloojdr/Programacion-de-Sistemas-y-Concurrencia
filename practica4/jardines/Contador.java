package jardines;

import java.util.concurrent.Semaphore;

public class Contador {
    private volatile int cont;
    Semaphore contPuerta = new Semaphore(1, true);

    public Contador() {
        cont = 0;
    }

    public void entraPersona() throws InterruptedException {
        contPuerta.acquire();
        cont++;
        contPuerta.release();
    }

    public int valor() {
        return cont;
    }

}
