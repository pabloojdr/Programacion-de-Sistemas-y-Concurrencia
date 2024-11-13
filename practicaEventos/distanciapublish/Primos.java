package distanciapublish;

public class Primos {
    private int x, y, pos;
    
    public Primos(int numX, int numY, int posicion) {
        this.x = numX;
        this.y = numY;
        this.pos = posicion;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getPos() {
        return this.pos;
    }

    @Override
    public String toString() {
        return String.format("%d:(%d,%d)", this.getPos(), this.getX(), this.getY());
    }

    /* public static void main(String[] args) {
        Primos primos = new Primos(3, 5, 1);
        System.out.println(primos); // Salida: 1:(3,5)
    } */
}
