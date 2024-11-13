package ventaonline;

import java.util.Random;

public class Reponedor extends Thread{
	
	private Web web;
	private static Random r = new Random();
	
	public Reponedor(Web w){
		this.web = w;
	}
	
	
	public void run(){
		while (true){
			
			try {
				Thread.sleep(r.nextInt(3000));
				web.reponerEntra();
				Thread.sleep(r.nextInt(1000));
				web.reponerSale();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
