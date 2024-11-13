package mrusa;
import java.util.*;
public class Pasajero extends Thread{
	
	private int id;
	private Vagon v;
	private static Random r = new Random();
	
	public Pasajero(int id,Vagon v){
		this.id = id;
		this.v = v;
	}
	
	
	public void run(){
		while (true){
			try{
				v.subir(id);
				v.bajar(id);
				Thread.sleep(r.nextInt(500));
			}catch(InterruptedException ie){
				
			}
			
		}
	}

}
