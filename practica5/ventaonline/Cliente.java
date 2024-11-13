package ventaonline;

import java.util.Random;

public class Cliente extends Thread{
	
	private int id;
	private Web web;
	private static Random r = new Random();
	
	public Cliente(int id,Web w){
		this.id = id;
		this.web = w;
	}
	
	
	public void run(){
		while (true){
			
			try {
				Thread.sleep(r.nextInt(1000));
				web.entroweb(id);
				Thread.sleep(r.nextInt(500));
				web.salgoweb(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
