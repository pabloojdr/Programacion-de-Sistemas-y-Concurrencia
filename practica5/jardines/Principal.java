package jardines;


public class Principal {
    public static void main(String[] args) throws InterruptedException {
        int N=10;
        Contador visitantes = new Contador();
        Thread[] puertas= new Thread[N]; //Declaramos una lista de futuras puertas, son referencias a puertas sin inicializar
        
       
        for (int i = 0; i < N; i++) {
            //Creamos los objetos puertas
            puertas[i] = new Thread(new Puerta(visitantes, 1000),"Puerta "+(i+1));
        }

        for (int i = 0; i < N; i++) {
            //Iniciamos las hebras de los objetos puertas
            puertas[i].start();
        }
        
        for (int i = 0; i < N; i++) {
            //Esperamos a que terminen todas las puertas
            puertas[i].join();
        }

        System.out.println("Hay " + visitantes.valor() + " visitantes");
    }
}