package esqueleto;

public class Pasajero extends Thread {
	
	boolean esPrimera;
	Cinta2 cinta;
	int id;

	
	public Pasajero(int id,Cinta2 cinta,boolean esPrimera) {
		this.id = id;
		this.cinta = cinta;
		this.esPrimera = esPrimera;

	}
	
	
	public void run() {
		try {
		while(true) {
			
			
			if(esPrimera) {
				//retira una maleta cada 3 seg
				Thread.sleep(5000);
				cinta.qRetirarPrimera(id); 				
				Thread.sleep(1000);
				cinta.fRetirarPrimera(id);
			}else {
				//retira una maleta cada 1 seg
				Thread.sleep(1000);
				cinta.qRetirarTurista(id);
				Thread.sleep(1000);
				cinta.fRetirarTurista(id);
			}
			
			
		}
		}catch(InterruptedException e) {e.printStackTrace();}
		
	}
}
