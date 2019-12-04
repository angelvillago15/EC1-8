package Dominio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Estado implements Cloneable {

    private int N; // numero filas del cubo
    private int LEFT[][]; //Cara izquierda del cubo
    private int DOWN[][]; //Cara inferior
    private int RIGHT[][]; //Cara derecha
    private int UP[][]; // cara superior
    private int BACK[][];// cara trasera
    private int FRONT[][];// cara inferior

    public Estado(String fileJSON) throws IOException, FileNotFoundException, ParseException {
        this.N = getN(fileJSON);
        this.LEFT = new int[N][N];
        this.DOWN = new int[N][N];
        this.RIGHT = new int[N][N];
        this.UP = new int[N][N];
        this.BACK = new int[N][N];
        this.FRONT = new int[N][N];
        leerJSON(fileJSON); //leemos el fichero json pasandole el nombre del fichero o la cadena
    }

    // metodo para clonar objetos Estado
    @Override
    public Object clone() {
        Estado obj = null;
        try {
            obj = (Estado) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println("El cubo no se ha podido duplicar");
        }
        //copiamos la cara izquierda
        obj.LEFT = obj.LEFT.clone();
        for (int i = 0; i < obj.LEFT.length; i++) {
            obj.LEFT[i] = obj.LEFT[i].clone();
        }
        //copiamos la cara de abajo
        obj.DOWN = obj.DOWN.clone();
        for (int i = 0; i < obj.DOWN.length; i++) {
            obj.DOWN[i] = obj.DOWN[i].clone();
        }
        //copiamos la cara derecha
        obj.RIGHT = obj.RIGHT.clone();
        for (int i = 0; i < obj.RIGHT.length; i++) {
            obj.RIGHT[i] = obj.RIGHT[i].clone();
        }
        //copiamos la cara de arriba
        obj.UP = obj.UP.clone();
        for (int i = 0; i < obj.UP.length; i++) {
            obj.UP[i] = obj.UP[i].clone();
        }
        //copiamos la cara de atras
        obj.BACK = obj.BACK.clone();
        for (int i = 0; i < obj.BACK.length; i++) {
            obj.BACK[i] = obj.BACK[i].clone();
        }
        //copiamos la cara de enfrente
        obj.FRONT = obj.FRONT.clone();
        for (int i = 0; i < obj.FRONT.length; i++) {
            obj.FRONT[i] = obj.FRONT[i].clone();
        }
        return obj;
    }

    // metodo para obtener la dimension N del cubo
    public int getN(String fileJSON) throws FileNotFoundException, IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(fileJSON));
        JSONObject jo = (JSONObject) obj;
        JSONArray general = ((JSONArray) jo.get("LEFT"));
        return general.size();
    }

    //metodo que lee el fichero json proporcionado.
    public void leerJSON(String fileJSON) throws FileNotFoundException, IOException, ParseException {
        String caras[] = {"LEFT", "DOWN", "RIGHT", "UP", "BACK", "FRONT"};
        int fila;

        JSONArray lista;
        Object obj = new JSONParser().parse(new FileReader(fileJSON));
        JSONObject jo = (JSONObject) obj;

        for (String cara : caras) {
            JSONArray general = (JSONArray) jo.get(cara);
            fila = 0;
            Iterator iterator = general.iterator();
            while (iterator.hasNext()) {
                lista = (JSONArray) iterator.next();
                for (int i = 0; i < lista.size(); i++) {
                    //generar cara a cara los valores para las celdas del cubo
                    switch (cara) {
                        case "LEFT":
                            this.LEFT[fila][i] = (int) ((long) lista.get(i));
                            break;
                        case "DOWN":
                            this.DOWN[fila][i] = (int) ((long) lista.get(i));
                            break;
                        case "RIGHT":
                            this.RIGHT[fila][i] = (int) ((long) lista.get(i));
                            break;
                        case "UP":
                            this.UP[fila][i] = (int) ((long) lista.get(i));
                            break;
                        case "BACK":
                            this.BACK[fila][i] = (int) ((long) lista.get(i));
                            break;
                        case "FRONT":
                            this.FRONT[fila][i] = (int) ((long) lista.get(i));
                            break;
                    }
                }
                fila++;
            }
        }
    }

    // metodo para generar el id del cubo cifrado en MD5
    public String getID() {
        String ID = "";
        String caras[] = {"BACK", "DOWN", "FRONT", "LEFT", "RIGHT", "UP"};
        for (String cara : caras) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    switch (cara) {
                        case "LEFT":
                            ID += LEFT[i][j];
                            break;
                        case "DOWN":
                            ID += DOWN[i][j];
                            break;
                        case "RIGHT":
                            ID += RIGHT[i][j];
                            break;
                        case "UP":
                            ID += UP[i][j];
                            break;
                        case "BACK":
                            ID += BACK[i][j];
                            break;
                        case "FRONT":
                            ID += FRONT[i][j];
                            break;
                    }
                }
            }
        }
        return Util.Utilidades.getMD5(ID);
    }

    //metodo para realizar el movimiento L
    public void moveL(int col) {
        int aux[] = new int[N];
        for (int i = 0; i < N; i++) {
            aux[i] = FRONT[i][col];
            FRONT[i][col] = UP[N - i - 1][N - col - 1];
            UP[N - i - 1][N - col - 1] = BACK[i][col];
            BACK[i][col] = DOWN[i][col];
            DOWN[i][col] = aux[i];
        }
        //ROTACION
        if (col == 0) {
            copiarCara(girarDerecha(LEFT), LEFT);
        }
        if (col == N - 1) {
            copiarCara(girarDerecha(RIGHT), RIGHT);
        }
    }

    //metodo para realizar el movimiento l
    public void movel(int col) {
        int aux[] = new int[N];
        for (int i = 0; i < N; i++) {
            aux[i] = BACK[i][col];
            BACK[i][col] = UP[N - i - 1][N - col - 1];
            UP[N - i - 1][N - col - 1] = FRONT[i][col];
            FRONT[i][col] = DOWN[i][col];
            DOWN[i][col] = aux[i];
        }
        //ROTADO
        if (col == 0) {
            copiarCara(girarIzquierda(LEFT), LEFT);
        }
        if (col == N - 1) {
            copiarCara(girarIzquierda(RIGHT), RIGHT);
        }
    }

    //metodo para realizar el movimiento D
    public void moveD(int dentro) {
        int aux[] = new int[N];
        for (int i = 0; i < N; i++) {
            aux[i] = FRONT[dentro][i];
            FRONT[dentro][i] = RIGHT[N - 1 - i][dentro];
            RIGHT[N - 1 - i][dentro] = BACK[N - 1 - dentro][N - 1 - i];
            BACK[N - 1 - dentro][N - 1 - i] = LEFT[i][N - 1 - dentro];
            LEFT[i][N - 1 - dentro] = aux[i];
        }
        //ROTADO
        if (dentro == 0) {
            copiarCara(girarDerecha(DOWN), DOWN);
        }
        if (dentro == N - 1) {
            copiarCara(girarDerecha(UP), UP);
        }
    }

    //metodo para realizar el movimiento L
    public void moved(int dentro) {
        int aux[] = new int[N];
        for (int i = 0; i < N; i++) {
            aux[i] = FRONT[dentro][i];
            FRONT[dentro][i] = LEFT[i][N - 1 - dentro];
            LEFT[i][N - 1 - dentro] = BACK[N - 1 - dentro][N - 1 - i];
            BACK[N - 1 - dentro][N - 1 - i] = RIGHT[N - 1 - i][dentro];
            RIGHT[N - 1 - i][dentro] = aux[i];
        }
        //ROTADO
        if (dentro == 0) {
            copiarCara(girarIzquierda(DOWN), DOWN);
        }
        if (dentro == N - 1) {
            copiarCara(girarIzquierda(UP), UP);
        }
    }

    //metodo para realizar el movimiento B
    public void moveB(int fil) {
        int aux[] = new int[N];
        for (int i = 0; i < N; i++) {
            aux[i] = DOWN[fil][i];
            DOWN[fil][i] = LEFT[fil][i];
            LEFT[fil][i] = UP[fil][i];
            UP[fil][i] = RIGHT[fil][i];
            RIGHT[fil][i] = aux[i];
        }
        //ROTADO
        if (fil == 0) {
            copiarCara(girarDerecha(BACK), BACK);
        }
        if (fil == N - 1) {
            copiarCara(girarDerecha(FRONT), FRONT);
        }
    }

    //metodo para realizar el movimiento b
    public void moveb(int fil) {
        int aux[] = new int[N];
        for (int i = 0; i < N; i++) {
            aux[i] = DOWN[fil][i];
            DOWN[fil][i] = RIGHT[fil][i];
            RIGHT[fil][i] = UP[fil][i];
            UP[fil][i] = LEFT[fil][i];
            LEFT[fil][i] = aux[i];
        }
        //ROTADO
        if (fil == 0) {
            copiarCara(girarIzquierda(BACK), BACK);
        }
        if (fil == N - 1) {
            copiarCara(girarIzquierda(FRONT), FRONT);
        }
    }

    //metodo que gira hacia la izquierda una de las caras del cubo
    public int[][] girarIzquierda(int matriz[][]) {
        int[][] otherMatriz = new int[matriz[0].length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            int k = 0;
            for (int j = matriz[0].length - 1; j >= 0; j--) {
                otherMatriz[j][i] = matriz[i][k];
                k++;
            }
        }
        return otherMatriz;
    }

    //metodo que gira hacia la derecha una de las caras del cubo
    public int[][] girarDerecha(int matriz[][]) {
        int[][] otherMatriz = new int[matriz[0].length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            int k = 0;
            for (int j = matriz[0].length - 1; j >= 0; j--) {
                otherMatriz[i][k] = matriz[j][i];
                k++;
            }
        }
        return otherMatriz;
    }

    // metodo para copiar una cara en otra
    public void copiarCara(int origen[][], int destino[][]) {
        for (int i = 0; i < destino.length; i++) {
            System.arraycopy(origen[i], 0, destino[i], 0, destino[i].length);
        }
    }

    // metodo para obtener el Cubo tras realizarle la accion A
    public Estado getEstado(Accion accion) {
        Estado estado = (Estado) this.clone();
        switch (accion.getMovimiento()) {
            case 'L':
                estado.moveL(accion.getPosicion());
                break;
            case 'B':
                estado.moveB(accion.getPosicion());
                break;
            case 'D':
                estado.moveD(accion.getPosicion());
                break;
            case 'l':
                estado.movel(accion.getPosicion());
                break;
            case 'b':
                estado.moveb(accion.getPosicion());
                break;
            case 'd':
                estado.moved(accion.getPosicion());
                break;
        }
        return estado;
    }

    public List<Accion> getAcciones() {
        List<Accion> acciones = new ArrayList<>();
        char[] movimientos = {'B', 'b', 'D', 'd', 'L', 'l'};
        for (int i = 0; i < movimientos.length; i++) {
            for (int k = 0; k < N; k++) {
                acciones.add(new Accion(movimientos[i], k));
            }
        }
        return acciones;
    }

    // función que nos dice si hemos llegado a un cubo que es objetivo
    public boolean esObjetivo() {

        boolean correcto = true;
        String caras[] = {"LEFT", "DOWN", "RIGHT", "UP", "BACK", "FRONT"};
        for (int k = 0; k < caras.length && correcto; k++) {
            for (int i = 0; i < LEFT.length && correcto; i++) {
                for (int j = 0; j < LEFT.length && correcto; j++) {
                    switch (caras[k]) {
                        case "LEFT":
                            if (LEFT[i][j] != LEFT[0][0]) {
                                correcto = false;
                            }
                            break;
                        case "DOWN":
                            if (DOWN[i][j] != DOWN[0][0]) {
                                correcto = false;
                            }
                            break;
                        case "RIGHT":
                            if (RIGHT[i][j] != RIGHT[0][0]) {
                                correcto = false;
                            }
                            break;
                        case "UP":
                            if (UP[i][j] != UP[0][0]) {
                                correcto = false;
                            }
                            break;
                        case "BACK":
                            if (BACK[i][j] != BACK[0][0]) {
                                correcto = false;
                            }
                            break;
                        case "FRONT":
                            if (FRONT[i][j] != FRONT[0][0]) {
                                correcto = false;
                            }
                            break;
                    }
                }
            }
        }
        return correcto;
    }

    //logaritmo en base n calculado con respecto al logaritmo neperiano
    public double log(double num, int base) {
        return (Math.log(num) / Math.log(base));
    }

    //heuristica dada en el laboratorio 
    public double getHeuristica() {
        double entropia = 0;
        String caras[] = {"LEFT", "DOWN", "RIGHT", "UP", "BACK", "FRONT"};
        double contador[] = new double[caras.length];
        for (String cara : caras) {
            contador = contarColoresCara(cara, contador);
            for (int i = 0; i < caras.length; i++) {
                if (contador[i] > 0.0) {
                    entropia += contador[i] / (N * N) * log(contador[i] / (N * N), 6);
                }
            }
            for (int i = 0; i < contador.length; i++) 
                contador[i] = 0.0;
        }
        return Math.abs(entropia);
    }

    //contar la cantidad de colores por cara para la heuristica
    public double[] contarColoresCara(String cara, double[] contador) {
        for (int k = 0; k < contador.length; k++) {
            for (int i = 0; i < LEFT.length; i++) {
                for (int j = 0; j < LEFT.length; j++) {
                    switch (cara) {
                        case "LEFT":
                            if (LEFT[i][j] == k) {
                                contador[k]++;
                            }
                            break;
                        case "DOWN":
                            if (DOWN[i][j] == k) {
                                contador[k]++;
                            }
                            break;
                        case "RIGHT":
                            if (RIGHT[i][j] == k) {
                                contador[k]++;
                            }
                            break;
                        case "UP":
                            if (UP[i][j] == k) {
                                contador[k]++;
                            }
                            break;
                        case "BACK":
                            if (BACK[i][j] == k) {
                                contador[k]++;
                            }
                            break;
                        case "FRONT":
                            if (FRONT[i][j] == k) {
                                contador[k]++;
                            }
                            break;
                    }
                }
            }
        }
        return contador;
    }
    
    @Override
    public String toString() {
        String s = "";
        s += "LEFT                    DOWN                    "
                + "RIGHT                   UP                       BACK                    FRONT\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s += LEFT[i][j] + " ";
            }
            s += "\t";
            for (int j = 0; j < N; j++) {
                s += DOWN[i][j] + " ";
            }
            s += "\t";
            for (int j = 0; j < N; j++) {
                s += RIGHT[i][j] + " ";
            }
            s += "\t";
            for (int j = 0; j < N; j++) {
                s += UP[i][j] + " ";
            }
            s += "\t";
            for (int j = 0; j < N; j++) {
                s += BACK[i][j] + " ";
            }
            s += "\t";
            for (int j = 0; j < N; j++) {
                s += FRONT[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}
