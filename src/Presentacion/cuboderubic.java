package Presentacion;

import Dominio.Frontera;
import Dominio.Sucesor;
import Dominio.NodoArbol;
import Dominio.Problema;
import Util.Utilidades;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class cuboderubic {
    public static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        String seleccionado;
        boolean ok,sol = false;
        int PMax = Integer.MAX_VALUE, Inc_P = 1;
        
        Problema prob = new Problema();
        
        // Pedir estrategia de busqueda
        do {
            System.out.print("1. Anchura\n2. Profundidad simple\n3. Profundidad Acotada\n4. Profundidad Iterativa"
                    + "\n5. Coste Uniforme\n6. Voraz\n7. A*\n\n(Selecciona una busqueda): ");
            seleccionado = sc.next();
            ok = seleccionado.equals("1") || seleccionado.equals("2") || seleccionado.equals("3") || seleccionado.equals("4") || seleccionado.equals("5")
                    || seleccionado.equals("6") || seleccionado.equals("7");
            if (!ok) {
                System.out.println("Opcion no existente.");
            }
        } while (!ok);

        // Pedir profundidad maxima para todas las opciones salvo la profundidad simple
        if (!seleccionado.equals("2")) {
            do {
                ok = true;
                System.out.print("Dame la profundida máxima: ");
                try {
                    PMax = Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Debe ser numerico. Error producido");
                    ok = false;
                }
            } while (!ok);
        }

        
        // pedir profundidad iterativa en el caso de que se elija esta opcion
        if (seleccionado.equals("4")) {
            do {
                ok = true;
                System.out.print("Dame el incremento de Profundidad Iterativa:(por defecto 1) ");
                try {
                    Inc_P = Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Debe ser numerico. Error producido");
                    ok = false;
                }
            } while (!ok);
        }

        switch (seleccionado) {
            case "1":
                sol = Busqueda_Acotada(prob, "Anchura", PMax); //anchura
                break;
            case "2":
                sol = Busqueda_Acotada(prob, "ProfSimple", Integer.MAX_VALUE); //Profundidad simple, utilizada prof infinita
                break;
            case "3":
                sol = Busqueda_Acotada(prob, "ProfAcotada", PMax); // Profundidad acotada
                break;
            case "4":
                sol = Busqueda(prob, "ProfIterativa", PMax, Inc_P); // Profundidad iterativa
                break;
            case "5":
                sol = Busqueda_Acotada(prob, "CosteUniforme", PMax); // Coste uniforme
                break;
            case "6":
                sol = Busqueda_Acotada(prob, "Voraz", PMax); // Voraz
                break;
            case "7":
                sol = Busqueda_Acotada(prob, "A", PMax); // A asterisco
                break;

        }

        if (!sol) {
            System.out.println("No se ha podido encontrar solucion");
        }
    }
    
    // estrutura de metodo de busqueda_Acotada para todas las busquedas dado en laboratorio
    public static boolean Busqueda_Acotada(Problema prob, String estrategia, int Prof_Max) throws IOException {

        int idsgenerados = 0;
        Frontera frontera = new Frontera();
        frontera.CrearFronteraVacia();
        NodoArbol n_inicial = new NodoArbol(null, prob.getEstadoInicial(), 0, null, 0, 0);
        frontera.Insertar(n_inicial);
        boolean solucion = false;
        NodoArbol n_actual = null;
        Map<String, Double> nodosVisitados = new HashMap(); // Estructura para gestionar los estados repetidos
        Deque<NodoArbol> nSol = new LinkedList(); // almacena la solucion

        while (!solucion && !frontera.EstaVacia()) {
            n_actual = frontera.Eliminar();
            if (prob.esObjetivo(n_actual.getEstado())) {
                solucion = true;
            } else {
                List<Sucesor> LS = prob.getEspacioDeEstados().getSucesores(n_actual.getEstado());
                List<NodoArbol> LN = CreaListaNodosArbol(LS, n_actual, Prof_Max, estrategia);

                for (NodoArbol nodo : LN) {                  
                    String nodoString = nodo.getEstado().getID();
                    if (nodosVisitados.containsKey(nodoString)) {
                        if ((Math.abs(nodo.getF()) < Math.abs(nodosVisitados.get(nodoString)))) {
                            nodo.setID(++idsgenerados);
                            frontera.Insertar(nodo);
                            nodosVisitados.replace(nodoString, nodo.getF());
                        }
                    } else {
                        nodo.setID(++idsgenerados);
                        nodosVisitados.put(nodoString, nodo.getF());
                        frontera.Insertar(nodo);
                    }                    
                }
            }
        }
        if (solucion) {
            //poner la solucion en la estructura de cola
            while (n_actual.getPadre() != null) {
                nSol.addFirst(n_actual);
                n_actual = n_actual.getPadre();
            }
            nSol.addFirst(n_inicial);            
            Utilidades.generarFichero(nSol, estrategia, idsgenerados);
            System.out.println("\nFICHERO GENERADO");

        }
        return solucion;
    }

    public static List<NodoArbol> CreaListaNodosArbol(List<Sucesor> LS, NodoArbol n_actual, int Prof_Max, String estrategia) {
        List<NodoArbol> LN = new ArrayList();
        if (n_actual.getP() < Prof_Max) { // si aun no hemos alcanzado la maxima profundidad
            NodoArbol aux = null;
            for (Sucesor sucesor : LS) {//                
                switch (estrategia) {
                    case "Anchura":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, n_actual.getP() + 1);
                        break;
                    case "ProfSimple":
                    case "ProfAcotada":
                    case "ProfIterativa":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, (-n_actual.getP() - 1));
                        break;
                    case "CosteUniforme":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, n_actual.getCoste() + sucesor.getCoste());
                        break;
                    case "Voraz":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, sucesor.getEstado().getHeuristica());
                        break;
                    case "A":
                        aux = new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste() + sucesor.getCoste(), sucesor.getAccion(),
                                n_actual.getP() + 1, n_actual.getCoste() + sucesor.getCoste() + sucesor.getEstado().getHeuristica());
                        break;
                }
                LN.add(aux);
            }
        }
        return LN;
    }
    // estrutura de metodo de busqueda para la profundidad iterativa dado en laboratorio
    public static boolean Busqueda(Problema prob, String estrategia, int Prof_Max, int Inc_Prof) throws IOException {

        int Prof_Actual = Inc_Prof;
        boolean solucion = false;
        while (!solucion && Prof_Actual <= Prof_Max) {
            solucion = Busqueda_Acotada(prob, estrategia, Prof_Actual);
            Prof_Actual += Inc_Prof;
        }
        return solucion;
    }


}
