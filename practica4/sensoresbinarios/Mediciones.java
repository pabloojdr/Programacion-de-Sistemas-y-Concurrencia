package sensoresbinarios;

import java.util.concurrent.Semaphore;

public class Mediciones {
    private volatile int num_med=0;
   
    Semaphore sensores[];
    Semaphore trabajador;
    public Mediciones() {
        sensores = new Semaphore[3];
        for(int i = 0; i < sensores.length; i++)
            sensores[i] = new Semaphore(1, true);
        trabajador = new Semaphore(0, true);
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
        num_med++;
        if(num_med == 3)
            trabajador.release();
        //Hemos terminado todos
    }

    /***
     * El trabajador espera hasta que están las tres mediciones
     * 
     * @throws InterruptedException
     */
    public void leerMediciones() throws InterruptedException {
        trabajador.acquire();
        System.out.println("El trabajador tiene sus mediciones...y empieza sus tareas");

    }

    /**
     * El trabajador indica que ha terminado sus tareas
     */
    public void finTareas() {
        System.out.println("El trabajador ha terminado sus tareas");
        while(num_med != 0)
            num_med--;
        for(Semaphore s : sensores)
            s.release();
    }
}