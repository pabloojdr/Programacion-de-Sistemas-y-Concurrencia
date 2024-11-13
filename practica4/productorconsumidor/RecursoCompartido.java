package productorconsumidor;

import java.util.concurrent.Semaphore;

//Condición Consumidor:
// No puedo leer un dato hasta que no se ha almacenado uno nuevo.
//Condición Productor:
//  No puedo escribir un dato hasta que no se ha leido el anterior
//prod(0)->cons(0)->prod(1)->cons(1)

public class RecursoCompartido {
    private volatile int recurso;
    Semaphore productor = new Semaphore(1, true);
    Semaphore consumidor = new Semaphore(0, true);

    public int consumir() throws InterruptedException{
        consumidor.acquire();
        int a = recurso;
        System.out.println("he leido " + a);
        productor.release();
        return a;
    }

    public void producir(int r) throws InterruptedException{
        productor.acquire();
        recurso = r;
        System.out.println("he escrito " + r);
        consumidor.release();
    }
}
