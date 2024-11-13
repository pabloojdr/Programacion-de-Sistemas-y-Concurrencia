package productorconsumidor;

public class Principal {
    public static void main(String[] args) {
        RecursoCompartido rc = new RecursoCompartido();
        Thread productor = new Thread(new Productor(rc),"Productor");
        Thread consumidor = new Thread(new Consumidor(rc),"Consumidor");
        productor.setName("Productor");
        consumidor.setName("Consumidor");

        productor.start();
        consumidor.start();

    }
}
