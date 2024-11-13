package sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {

   
    Semaphore trabajador = new Semaphore(0, true);
    Semaphore sensores[] = new Semaphore[3];

    public Mediciones() {
        sensores[0] = new Semaphore(1, true);
        sensores[1] = new Semaphore(1, true);
        sensores[2] = new Semaphore(1, true);
    }

    /**
     * El sensor id deja su medición y espera hasta que el trabajador
     * ha terminado sus tareas
     * 
     * @param id
     * @throws InterruptedException
     */
    public void nuevaMedicion(int id) throws InterruptedException {
        sensores[id].acquire();
        System.out.println("Sensor " + id + " deja sus mediciones.");
        trabajador.release();
        //Hemos terminado todos

    }

    /***
     * El trabajador espera hasta que están las tres mediciones
     * 
     * @throws InterruptedException
     */
    public void leerMediciones() throws InterruptedException {
        trabajador.acquire(3);
        System.out.println("El trabajador tiene sus mediciones...y empieza sus tareas");

    }

    /**
     * El trabajador indica que ha terminado sus tareas
     */
    public void finTareas() {
        System.out.println("El trabajador ha terminado sus tareas");
        for(Semaphore s : sensores)
            s.release();
    }
}