package sensores;

public class Principal {

    public static void main(String[] args) {
        Mediciones m = new Mediciones();
        Thread t = new Thread(new Trabajador(m),"Trabajador");
        Thread[] s = new Thread[3];
        for (int i = 0; i < s.length; i++) {
            s[i] = new Thread(new Sensor(i, m),"Sensor" + (i+1));
        }
        t.start();
        for (int i = 0; i < s.length; i++) {
            s[i].start();
        }
    }

}
