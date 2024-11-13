package impresoras;

import java.util.ArrayList;
import java.util.List;

public class SalaImpresoras {
    private int NUM_PRINTER, impresoraAsignada;
    private List<Integer> esperaClientes, impresorasDisponibles;
   
    
    public SalaImpresoras(int n) {
        NUM_PRINTER=n;
        esperaClientes = new ArrayList<>();
        impresorasDisponibles = new ArrayList<>();
        for(int i = 0; i < NUM_PRINTER; i++){
            impresorasDisponibles.add(i);
        }

    }

    public synchronized int quieroImpresora(int id) throws InterruptedException{
        System.out.println("Cliente " + id + " entra en la cola de espera");
        esperaClientes.add(id);
        while(impresorasDisponibles.size() <= 0 || id != esperaClientes.get(0))
            wait();

        impresoraAsignada = impresorasDisponibles.get(0);
        System.out.println("    El cliente " + id + " sale de la cola y se le asigna la impresora " + impresoraAsignada);
        esperaClientes.remove(0);
        impresorasDisponibles.remove(0);
        return (impresoraAsignada);
    }

    public synchronized void devuelvoImpresora(int id, int n) throws InterruptedException{
        System.out.println("    Devuelta la impresora " + n + " por el cliente " + id);
        impresorasDisponibles.add(n);
        notifyAll();
    }
}