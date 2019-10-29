package cuboderubic;

import java.util.Stack;


public class FronteraPila {
    
    private Stack<NodoArbol> stack = new Stack<NodoArbol>();
    
    public void CrearFrontera (){
        stack.clear();
    }   
    public void Insertar (NodoArbol n){
        stack.push(n);
    }   
    public void Eliminar (){
        stack.pop();
    }
    public boolean EstaVacia(){
        return stack.isEmpty();
    }
    
}
