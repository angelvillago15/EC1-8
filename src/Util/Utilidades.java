
package Util;

import cuboderubic.NodoArbol;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Deque;

public class Utilidades {

    public static String getMD5(String input) {
        //metodo que cifra un String
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

    // método para mostrar la solución por consola
    public static void escribirConsola(Deque<NodoArbol> camino, String estrategia, int total) {
        System.out.println("\nLa soluci\u00f3n es: ");
        System.out.println("Estrategia: " + estrategia.toUpperCase());
        System.out.println("Total Nodos Generados: " + total);
        System.out.println("Profundidad: " + (camino.getLast().getP() + 1));
        System.out.println("Costo: " + camino.getLast().getCoste());
        System.out.println("");
        int i = 0;
        for (NodoArbol nodoarbol : camino) {
            if (i == 0) {
                if (estrategia.equals("Voraz") || estrategia.equals("A")) {
                    System.out.println("None " + nodoarbol.getEstado().getHeuristica() + " " + nodoarbol.getP() + " " + nodoarbol.getCoste());
                } else {
                    System.out.println("None " + nodoarbol.getF() + " " + nodoarbol.getP() + " " + nodoarbol.getCoste());
                }
                System.out.println(nodoarbol.getEstado());
            } else {
                System.out.println(nodoarbol.getAccion() + " " + nodoarbol.getF() + " " + nodoarbol.getP() + " " + nodoarbol.getCoste());
                System.out.println(nodoarbol.getEstado());
            }
            System.out.println("");
            i++;
        }
    }

    public static void generarFichero(Deque<NodoArbol> camino, String estrategia, int total) throws IOException {
        File archivo = new File("Path" + estrategia + ".txt");
        FileWriter file = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(file);
        pw.println("La soluci\u00f3n es: ");
        pw.println("Estrategia: " + estrategia.toUpperCase());
        pw.println("Total Nodos Generados: " + total);
        pw.println("Profundidad: " + (camino.getLast().getP() + 1));
        pw.println("Costo: " + (camino.getLast().getCoste()));
        pw.println("");
        int i = 0;
        for (NodoArbol nodoarbol : camino) {
            if (i == 0) {
                if (estrategia.equals("Voraz") || estrategia.equals("A")) {
                    pw.println("None " + nodoarbol.getEstado().getHeuristica() + " " + nodoarbol.getP() + " " + nodoarbol.getCoste());
                } else {
                    pw.println("None " + nodoarbol.getF() + " " + nodoarbol.getP() + " " + nodoarbol.getCoste());
                }
                pw.println(nodoarbol.getEstado());
            } else {
                pw.println(nodoarbol.getAccion() + " " + nodoarbol.getF() + " " + nodoarbol.getP() + " " + nodoarbol.getCoste());
                pw.println(nodoarbol.getEstado());
            }
            pw.println("");
            i++;
        }
        pw.close();
    }
    
}
