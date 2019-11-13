package cuboderubic;

import java.util.ArrayList;


public class FronteraList {
    
    private ArrayList<NodoArbol> arrayList = new ArrayList<NodoArbol>();
    
    public void CrearFrontera (){
        arrayList.clear();
    }   
    public void Insertar (NodoArbol n){
        arrayList.add(n);
    }   
    public void Eliminar (){
        arrayList.remove(0);
    }
    public boolean EstaVacia(){
        return arrayList.isEmpty();
    }
    
}
