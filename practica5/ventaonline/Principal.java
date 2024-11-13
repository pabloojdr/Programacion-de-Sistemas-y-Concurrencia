package ventaonline;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int NUM_CLIENTES = 20;
		
		Web w = new Web();
		Cliente[] cliente = new Cliente[NUM_CLIENTES];
		for (int i = 0; i<cliente.length; i++){
			cliente[i] = new Cliente(i,w);
		}
		Reponedor r = new Reponedor(w);
		r.start();
		for (int i = 0; i<cliente.length; i++){
			cliente[i].start();
		}
	}

}
