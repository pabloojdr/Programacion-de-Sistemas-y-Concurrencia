package mrusa;

public class Principal {

	
	public static void main(String[] args){
		int N=3;
		Vagon v = new Vagon(N);
		Coche coche = new Coche(v);
		coche.setName("Coche");

		Pasajero[] pas = new Pasajero[10];
		for (int i = 0; i<pas.length; i++){
			pas[i] = new Pasajero(i,v);
			pas[i].setName("Pasajero " +i);
		}
		coche.start();
		for (int i = 0; i<pas.length; i++){
			pas[i].start();
		}
	}
}
