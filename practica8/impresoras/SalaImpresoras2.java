package impresoras;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalaImpresoras2 {
    private int NUM_PRINTER, impresoraAsignada;
    private List<Integer> esperaClientes, impresorasDisponibles;
    private Lock l;
    private Condition colaClientes;
   
    
    public SalaImpresoras2(int n) {
        NUM_PRINTER=n;
        l = new ReentrantLock(true);
        colaClientes = l.newCondition();
        esperaClientes = new ArrayList<>();
        impresorasDisponibles = new ArrayList<>();
        for(int i = 0; i < NUM_PRINTER; i++){
            impresorasDisponibles.add(i);
        }

    }

    public int quieroImpresora(int id) throws InterruptedException{
        l.lock();
        try{
            System.out.println("Cliente " + id + " entra en la cola de espera");
            esperaClientes.add(id);
            
            while(impresorasDisponibles.size() <= 0 || id != esperaClientes.get(0))
                colaClientes.await();
    
            impresoraAsignada = impresorasDisponibles.get(0);
            System.out.println("    El cliente " + id + " sale de la cola y se le asigna la impresora " + impresoraAsignada);
            esperaClientes.remove(0);
            impresorasDisponibles.remove(0);
            
            return (impresoraAsignada);
        }finally{
            l.unlock();
        }
    }

    public void devuelvoImpresora(int id, int n) throws InterruptedException{
        l.lock();
        try{
            System.out.println("    Devuelta la impresora " + n + " por el cliente " + id);
            impresorasDisponibles.add(n);
            colaClientes.signalAll();
        }finally{
            l.unlock();
        }
    }
}