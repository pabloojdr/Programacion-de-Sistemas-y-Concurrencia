package distancia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class PrimosCousinWorker extends SwingWorker<List<Primos>, Void> {
    private Panel panel;
    private int numPrimos;

    public PrimosCousinWorker(Panel p, int num) {
        this.panel = p;
        this.numPrimos = num;
    }

    @Override
    protected List<Primos> doInBackground() throws Exception {
        // TODO Auto-generated method stub
        List<Primos> listaPrimos = new ArrayList<>();
        int primo = 2;
        int pos = 0;
        while(listaPrimos.size() < numPrimos && !isCancelled()) {
            if(numeroPrimo(primo) && numeroPrimo(primo+4)) {
                Primos nuevoPrimo = new Primos(primo, primo+4, pos);
                listaPrimos.add(pos, nuevoPrimo);
                pos++;
            }
            primo++;
        }
        return listaPrimos;
    }
    
    private boolean numeroPrimo(int n) {
        if(n < 2) {
            return false;
        }
        int i = 2;
        while(i < n) {
            if(n % i == 0) 
                return false;
            i++;
        }
        return true;
    }

    protected void done() {
        try {
            panel.escribePrimosCousin(get());
            panel.mensajeCousin("Primos cousin escritos con exito");
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CancellationException ce) {
            System.out.println("Cancelacion Registrada, pierdo todo...");
        }
    }
}
