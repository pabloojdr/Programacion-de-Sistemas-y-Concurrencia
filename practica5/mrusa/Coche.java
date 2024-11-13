
package mrusa;

import java.util.concurrent.*;


public class Coche extends Thread{
   
    private Vagon v;

    public Coche(Vagon v){
		this.v = v;
	}
	
    
    public void run(){
        while (true){
            try {
                v.esperaLleno();
                Thread.sleep(200);
                v.finViaje();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
} 