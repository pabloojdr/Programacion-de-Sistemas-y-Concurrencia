package pajaritos;


public class Nido {
    private int B = 10; // Número máximo de bichos
    private int bichitos = 0; // puede tener de 0 a B bichitos
	private boolean puedeComer = false;
	private boolean puedePoner = true;

    public synchronized void come(int id) throws InterruptedException {
        while(!puedeComer)
            wait();
		bichitos--;
        System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
		
		if(bichitos == 0)
			puedeComer = false;
		
		puedePoner = true;
		notifyAll();
    }

    public synchronized void nuevoBichito(int id) throws InterruptedException {
    	while(!puedePoner || bichitos == B)
			wait();
		bichitos++;
        System.out.println("	El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		
		if(bichitos == B){
			puedePoner = false;
		}

		puedeComer = true;
		notifyAll();
      
    }
}
