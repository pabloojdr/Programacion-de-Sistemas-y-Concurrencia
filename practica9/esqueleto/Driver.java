package esqueleto;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Cinta2 cinta=new Cinta2();

		Generador g=new Generador(cinta);
		g.setName("Generador");
		g.start();


		Pasajero pasajeros[] = new Pasajero[10]; 
		for(int i = 0; i< 10; i++) {
			pasajeros[i] = new Pasajero(i,cinta, i%2==0);
			pasajeros[i].setName("Pasajero " + (i%2==0?"Primera " + i:"Turista " + i));
		}
		
		for(int i = 0; i< 10; i++) {
			pasajeros[i].start();
		}
	}
}


