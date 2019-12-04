
package Util;

import Dominio.NodoArbol;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Deque;

public class Utilidades {

    // metodo para generar el fichero con el formato dado en el laboratorio
    public static void generarFichero(Deque<NodoArbol> camino, String estrategia, int total) throws IOException {
        DecimalFormat df = new DecimalFormat("#0.00");
        File archivo = new File("Estrategia" + estrategia + ".txt");
        FileWriter file = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(file);
        
        pw.println(estrategia.toUpperCase());
        pw.print("Nodos Generados: " + total);
        pw.print(" Profundidad: " + (camino.getLast().getP() + 1));
        pw.print(" Costo: " + (int) (camino.getLast().getCoste()) + "\n\n");        
        int i = 0;
        for (NodoArbol nodoarbol : camino) {
            if (i == 0) {
                pw.println("[" + nodoarbol.getID() + "]" + "([None]" + nodoarbol.getEstado().getID() + ",c=" + (int) nodoarbol.getCoste() + ",p=" + nodoarbol.getP() + ",h=" + df.format(nodoarbol.getEstado().getHeuristica()) + ",f=" + nodoarbol.getF() + ")");
            } else {
                pw.println("[" + nodoarbol.getID() + "]" + "([" + nodoarbol.getAccion() + "]" + nodoarbol.getEstado().getID() + ",c=" + (int) nodoarbol.getCoste() + ",p=" + nodoarbol.getP() + ",h=" + df.format(Math.abs(nodoarbol.getEstado().getHeuristica())) + ",f=" + df.format(Math.abs(nodoarbol.getF())) + ")");
            }
            pw.println("");
            i++;
        }
        pw.close();
    }
   
    // metodo para el cifrado MD5
    public static String getMD5(String input) { 
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
