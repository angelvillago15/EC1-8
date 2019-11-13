package cuboderubic;
import java.util.PriorityQueue;

public class Frontera {
    
    private PriorityQueue<NodoArbol> colaNodos = new PriorityQueue<NodoArbol>();
    
    public void CrearFrontera (){
        colaNodos.clear();
    }   
    public void Insertar (NodoArbol n){
        colaNodos.add(n);
    }   
     public NodoArbol Eliminar() {
        return colaNodos.remove();
    }
    public boolean EstaVacia(){
        return colaNodos.isEmpty();
    }
    
}
