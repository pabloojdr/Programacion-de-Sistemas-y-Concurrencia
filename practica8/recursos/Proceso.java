package recursos;

import java.util.*;
public class Proceso extends Thread{
	private static Random r = new Random();
	private int id;
	private Control2 c;
	private int NUM;
	public Proceso(int id,Control2 c,int num){
		this.id = id;
		this.c = c;
		this.NUM = num;
	}
	
	
	public void run(){
		int rec;
		while (true){
			
			try {
				Thread.sleep(r.nextInt(200));
				rec = r.nextInt(this.NUM-1)+1;
				c.qRecursos(id, rec);
				Thread.sleep(r.nextInt(200));
				c.libRecursos(id, rec);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
