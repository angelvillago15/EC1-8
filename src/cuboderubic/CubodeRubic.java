package cuboderubic;

import Util.Utilidades;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.parser.ParseException;


public class CubodeRubic {

    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        //Estado estado=new Estado();
        // MOSTRAR EL ESTADO INCIAL
        //System.out.println(estado);
        
//        // MOSTRAR SUCESORES
//        EspacioDeEstados espacio = new EspacioDeEstados();
//        List<Sucesor> sucesores=new ArrayList(espacio.getSucesores(estado));
//        for (Sucesor sucesor : sucesores) {
//            System.out.println(sucesor.getEstado());
//            System.out.println();
//        }    
        
        // COMPROBAR DISTINTAS ESTRUCTURAS PILA, LISTA Y PRIORITYQUEUE PARA 1000000 10000000 y 100000000 DE Nodos Arbol
                 
//        Frontera frontera = new Frontera();        
//        FronteraList fronteral = new FronteraList();        
//        Frontera fronteras = new Frontera();        
//        
//        for (int k=1000000;k<=100000000;k*=10) {
//            frontera.CrearFrontera();
//            fronteral.CrearFrontera(); 
//            fronteras.CrearFrontera(); 
//            long icola,fcola;
//            icola=System.nanoTime();
//            for (int i=0;i<k;i++)
//                frontera.Insertar(new NodoArbol(null, null, 0, null, 0, (double) (Math.random() * 50 + 1)));
//            fcola=System.nanoTime();
//
//            long ilista,flista;
//            ilista=System.nanoTime();
//            for (int i=0;i<k;i++)
//                fronteral.Insertar(new NodoArbol(null, null, 0, null, 0, (double) (Math.random() * 50 + 1)));
//            flista=System.nanoTime();
//
//            long ipila,fpila;
//            ipila=System.nanoTime();
//            for (int i=0;i<k;i++)
//                fronteras.Insertar(new NodoArbol(null, null, 0, null, 0, (double) (Math.random() * 50 + 1)));
//            fpila=System.nanoTime();        
//
//            System.out.println("Cola:" + (fcola-icola));
//            System.out.println("Lista:" + (flista-ilista)); 
//            System.out.println("Pila:" + (fpila-ipila)); 
//            System.out.println();
 //       }
  
    String opcion;
        boolean correcto, hayPoda, solucion = false;
        int Prof_Max = 100, Inc_Prof = 1;
        Scanner sc = new Scanner(System.in);
        Problema prob = new Problema();

        do {
            System.out.print("1. Con Poda \n2. Sin Poda\n Escribe Opcion 1 o 2: ");
            opcion = sc.next();
            correcto = opcion.equals("1") || opcion.equals("2");
            if (!correcto) {
                System.out.println("Opcion no Correcta.\n");
            }
        } while (!correcto);

        hayPoda = opcion.equals("1") ? true : false;

        // pedimos al usuario la estrategia de búsqueda
        do {
            System.out.print("\n1. Anchura\n2. Profundidad simple\n3. Profundidad Acotada\n4. Profundidad Iterativa"
                    + "\n5. Coste Uniforme\n6. Voraz\n7. A Asterisco\n Escribe opcion 1-7: ");
            opcion = sc.next();
            correcto = opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4") || opcion.equals("5")
                    || opcion.equals("6") || opcion.equals("7");
            if (!correcto) {
                System.out.println("Opcion no Correcta.\n");
            }
        } while (!correcto);

        // Para cualquier opcion distinta de la profundidad simple elegimos la profundidad máxima acotada
        if (!opcion.equals("2")) {
            do {
                correcto = true;
                System.out.print("Escribe profundida máxima: ");
                try {
                    Prof_Max = Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Valor no númerico");
                    correcto = false;
                }
            } while (!correcto);
        }

        // Si la opcion es la profuncidad iterativa pedimos al usuario el incremento por iteración
        if (opcion.equals("4")) {
            do {
                correcto = true;
                System.out.print("Escribe Incremento Profundidad Iterativa: ");
                try {
                    Inc_Prof = Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Valor no númerico");
                    correcto = false;
                }
            } while (!correcto);
        }

        switch (opcion) {
            case "1":
                solucion = Busqueda_Acotada(prob, "Anchura", Prof_Max, hayPoda); //anchura
                break;
            case "2":
                solucion = Busqueda_Acotada(prob, "ProfundidadSimple", 10000, hayPoda); //Profundidad simple, utilizada prof infinita
                break;
            case "3":
                solucion = Busqueda_Acotada(prob, "ProfundidadAcotada", Prof_Max, hayPoda); // Profundidad acotada
                break;
            case "4":
                solucion = Busqueda(prob, "ProfundidadIterativa", Prof_Max, Inc_Prof, hayPoda); // Profundidad iterativa
                break;
            case "5":
                solucion = Busqueda_Acotada(prob, "CosteUniforme", Prof_Max, hayPoda); // Coste uniforme
                break;
            case "6":
                solucion = Busqueda_Acotada(prob, "Voraz", Prof_Max, hayPoda); // Voraz
                break;
            case "7":
                solucion = Busqueda_Acotada(prob, "A", Prof_Max, hayPoda); // A asterisco
                break;

        }

        if (!solucion) {
            System.out.println("Solucion no encontrada en la profundidad maxima dada");
        } 
    }
     public static boolean Busqueda_Acotada(Problema prob, String estrategia, int Prof_Max, boolean conPoda) throws IOException {
        
        int total = 0;
        Frontera frontera = new Frontera();
        frontera.CrearFrontera();
        NodoArbol n_inicial = new NodoArbol(null, prob.getEstadoInicial(), 0, null, 0, 0);//he puesto null
        frontera.Insertar(n_inicial);
        boolean solucion = false;
        NodoArbol n_actual = null;
        Map<String, Double> nodosVisitados = new HashMap(); // HashMap para la poda de estados repetidos
        Deque<NodoArbol> nodosSolucion = new LinkedList(); // cola doble para almacenar la solución generada

        while (!solucion && !frontera.EstaVacia()) {
            
            n_actual = frontera.Eliminar();
            if (prob.esObjetivo(n_actual.getEstado())) {                
                solucion = true;
            } else {                
                List<Sucesor> LS = prob.getEspacioDeEstados().getSucesores(n_actual.getEstado());
                System.out.println(LS.size());
                List<NodoArbol> LN = CreaListaNodosArbol(LS, n_actual, Prof_Max, estrategia);
                for (NodoArbol nodo : LN) {
                    if (conPoda) { // si se ha elegido poda no se insertan en la frontera los estados repetidos                   
                        String nodoString = nodo.getEstado().getID(); //.getIncident
                        if (nodosVisitados.containsKey(nodoString)) {
                            if ((nodo.getF() < nodosVisitados.get(nodoString).intValue())) {
                                frontera.Insertar(nodo);
                                total++;
                                nodosVisitados.replace(nodoString, nodo.getF());//                                                          
                            }
                        } else {
                            nodosVisitados.put(nodoString, nodo.getF());
                            frontera.Insertar(nodo);
                            total++;
                        }
                    } else {
                        frontera.Insertar(nodo);
                    }
                }
            }
        }
        if (solucion) {
            // si encontramos solucion la introducimos en la cola doble nodosSolucion            
            while (n_actual.getPadre() != null) {
                nodosSolucion.addFirst(n_actual);
                n_actual = n_actual.getPadre();
            }
            // se inserta el nodo inicial y se genera el fichero en la carpeta de la solución
            nodosSolucion.addFirst(n_inicial);
            Utilidades.escribirConsola(nodosSolucion, estrategia, total);
            Utilidades.generarFichero(nodosSolucion, estrategia, total);
            System.out.println("\nSE HA ESCRITO EL FICHERO EN LA CARPETA DEL PROYECTO");

        }
        return solucion;
    }

    public static List<NodoArbol> CreaListaNodosArbol(List<Sucesor> LS, NodoArbol n_actual, int Prof_Max, String estrategia) {
        List<NodoArbol> LN = new ArrayList();
        if (n_actual.getP() < Prof_Max) { // si aún podemos seguir iterando por no alcanzar la profundidad máxima
            NodoArbol aux = null;
            for (Sucesor sucesor : LS) {
                //dependiendo de la estrategia generamos los nodos
                switch (estrategia) {
                    case "Anchura":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, n_actual.getP() + 1);
                        break;
                    case "ProfundidadSimple":
                    case "ProfundidadAcotada":
                    case "ProfundidadIterativa":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, 1/(n_actual.getP()) + 1);
                        break;
                    case "CosteUniforme":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, n_actual.getCoste() + sucesor.getCoste());
                        break;
                    case "Voraz":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, n_actual.getCoste() + sucesor.getEstado().getHeuristica());
                        break;
                    case "A":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, n_actual.getCoste() + sucesor.getCoste() + sucesor.getEstado().getHeuristica());   //falta por implementar la heuristica                      
                        break;
                }
                LN.add(aux);

            }
        }
        return LN;
    }
    // metodo utilizado para la busqueda de profundidad iterativa, que recibe ademas el incremento de profundidad 

    public static boolean Busqueda(Problema prob, String estrategia, int Prof_Max, int Inc_Prof, boolean conPoda) throws IOException {

        int Prof_Actual = Inc_Prof;
        boolean solucion = false;
        while (!solucion && Prof_Actual <= Prof_Max) {
            solucion = Busqueda_Acotada(prob, estrategia, Prof_Actual, conPoda);
            Prof_Actual += Inc_Prof;
        }
        return solucion;
    }


}
