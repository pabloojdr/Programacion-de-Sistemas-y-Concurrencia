package viajeavion;

public class Principal {
	private static int CAPACIDAD_AREA = 5;
	public static void main(String[] args) {
		Avion2 avi = new Avion2(CAPACIDAD_AREA);
		Piloto m = new Piloto(avi);
		Viajero[] pas = new Viajero[20];
		for (int i=0; i<pas.length; i++){
			pas[i] = new Viajero(avi,i);
			pas[i].setName("Pas "+ i);
		}
		m.setName("Piloto");
		m.start();
		for (int i=0; i<pas.length; i++)
			pas[i].start();
	}
}
