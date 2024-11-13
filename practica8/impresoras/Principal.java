package impresoras;

public class Principal {

    public static void main(String[] args) {

        int N = 3;
        int NC = 20;
        SalaImpresoras2 sala = new SalaImpresoras2(N); 
        Thread[] cliente = new Thread[NC];
        for (int i = 0; i < cliente.length; i++) {
            cliente[i] = new Thread(new Cliente(sala, i));
            cliente[i].setName("Cliente " + i);
        }
        for (int i = 0; i < cliente.length; i++) {
            cliente[i].start();
        }
    }

}
