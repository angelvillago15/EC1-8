package Dominio;

public class Accion {
    
    private char tipomovimiento; //tipo de movimiento entre D,d,B,b,L,l
    private int filacolumna; // fila o columna en la que realizamos el movimiento

    public Accion (char movimiento, int posicion){
        this.tipomovimiento=movimiento;       
        this.filacolumna=posicion;
    } 

    public char getMovimiento() {
        return tipomovimiento;
    }

    public int getPosicion() {
        return filacolumna;
    }

    @Override
    public String toString() {
        return tipomovimiento+""+filacolumna;
    }
    
}
