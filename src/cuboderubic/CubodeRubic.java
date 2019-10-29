package cuboderubic;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.List;


public class CubodeRubic {

    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Estado estado=new Estado();
        // MOSTRAR EL ESTADO INCIAL
        System.out.println(estado);
        
        // MOSTRAR SUCESORES
        EspacioDeEstados espacio = new EspacioDeEstados();
        List<Sucesor> sucesores=new ArrayList(espacio.getSucesores(estado));
        for (Sucesor sucesor : sucesores) {
            System.out.println(sucesor.getEstado());
            System.out.println();
        }    
        
        // COMPROBAR DISTINTAS ESTRUCTURAS PILA, LISTA Y PRIORITYQUEUE PARA 1000000 10000000 y 100000000 DE Nodos Arbol
        
         
        Frontera frontera = new Frontera();        
        FronteraList fronteral = new FronteraList();        
        Frontera fronteras = new Frontera();        
        
        for (int k=1000000;k<=100000000;k*=10) {
            frontera.CrearFrontera();
            fronteral.CrearFrontera(); 
            fronteras.CrearFrontera(); 
            long icola,fcola;
            icola=System.nanoTime();
            for (int i=0;i<k;i++)
                frontera.Insertar(new NodoArbol(null, null, 0, null, 0, (double) (Math.random() * 50 + 1)));
            fcola=System.nanoTime();

            long ilista,flista;
            ilista=System.nanoTime();
            for (int i=0;i<k;i++)
                fronteral.Insertar(new NodoArbol(null, null, 0, null, 0, (double) (Math.random() * 50 + 1)));
            flista=System.nanoTime();

            long ipila,fpila;
            ipila=System.nanoTime();
            for (int i=0;i<k;i++)
                fronteras.Insertar(new NodoArbol(null, null, 0, null, 0, (double) (Math.random() * 50 + 1)));
            fpila=System.nanoTime();        

            System.out.println("Cola:" + (fcola-icola));
            System.out.println("Lista:" + (flista-ilista)); 
            System.out.println("Pila:" + (fpila-ipila)); 
            System.out.println();
        }
        
    }
    
}
