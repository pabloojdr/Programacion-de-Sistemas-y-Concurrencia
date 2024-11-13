package distanciapublishprogress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class PrimosTwinWorker extends SwingWorker<List<Primos>, Primos> {
    private Panel panel;
    private int numPrimos;

    public PrimosTwinWorker(Panel p, int num) {
        this.panel = p;
        this.numPrimos = num;
    }

    @Override
    protected List<Primos> doInBackground() throws Exception {
        // TODO Auto-generated method stub
        List<Primos> listaPrimos = new ArrayList<>();
        int primo = 2;
        int pos = 0;
        int progress = 0;

        while(listaPrimos.size() < numPrimos && !isCancelled()) {
            if(numeroPrimo(primo) && numeroPrimo(primo+2)) {
                Primos nuevoPrimo = new Primos(primo, primo+2, pos);
                publish(nuevoPrimo);
                listaPrimos.add(pos, nuevoPrimo);
                pos++;
                progress = Math.min(100, 100*pos/this.numPrimos);
                setProgress(progress);
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

    public void process(List<Primos> lista) {
        panel.escribePrimosTwin(lista);
    }
    
    protected void done() {
        try {
            panel.limpiaAreaTwin();
            panel.escribePrimosTwin(get());
            panel.mensajeTwin("Primos twin escritos con exito");
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CancellationException ce) {
            System.out.println("Cancelacion Registrada, pierdo todo...");
        }
    }
    
}
