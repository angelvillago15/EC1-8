package cuborubic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Cubo implements Cloneable{    
    private int N;
    private int LEFT [][];
    private int DOWN [][];
    private int RIGHT [][];
    private int UP [][];
    private int BACK [][];
    private int FRONT [][];
    
    public Cubo (int N) throws IOException, FileNotFoundException, ParseException {
        this.LEFT=new int[N][N];
        this.DOWN=new int[N][N];
        this.RIGHT=new int[N][N];
        this.UP=new int[N][N];
        this.BACK=new int[N][N];
        this.FRONT=new int[N][N];
        this.N=N;        
    }
    
     public Object clone(){
        Cubo obj=null;
        try{
            obj=(Cubo)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        obj.LEFT=(int[][])obj.LEFT.clone();
        for(int i=0; i<obj.LEFT.length; i++){
            obj.LEFT[i]=(int[])obj.LEFT[i].clone();
        }
        obj.DOWN=(int[][])obj.DOWN.clone();
        for(int i=0; i<obj.DOWN.length; i++){
            obj.DOWN[i]=(int[])obj.DOWN[i].clone();
        }
        obj.RIGHT=(int[][])obj.RIGHT.clone();
        for(int i=0; i<obj.RIGHT.length; i++){
            obj.RIGHT[i]=(int[])obj.RIGHT[i].clone();
        }
        obj.UP=(int[][])obj.UP.clone();
        for(int i=0; i<obj.UP.length; i++){
            obj.UP[i]=(int[])obj.UP[i].clone();
        }
        obj.BACK=(int[][])obj.BACK.clone();
        for(int i=0; i<obj.BACK.length; i++){
            obj.BACK[i]=(int[])obj.BACK[i].clone();
        }
        obj.FRONT=(int[][])obj.FRONT.clone();
        for(int i=0; i<obj.FRONT.length; i++){
            obj.FRONT[i]=(int[])obj.FRONT[i].clone();
        }
        return obj;
    }
    
    
    public void InicializarJson(String fileJSON) throws FileNotFoundException, IOException, ParseException {
        String caras[]={"LEFT","DOWN","RIGHT","UP","BACK","FRONT"};
        int fila=0;
        
        JSONArray lista=null;
        Object obj = new JSONParser().parse(new FileReader(fileJSON));          
        JSONObject jo = (JSONObject) obj;         
        
        for (int k=0;k<caras.length;k++){
            JSONArray general = ((JSONArray)jo.get(caras[k]));        
            fila =0;            
            Iterator iterator = general.iterator();
            while (iterator.hasNext()) {
                lista=(JSONArray)iterator.next();
                for (int i=0;i<lista.size();i++) {  
                    
                    switch (caras[k]) {
                        case "LEFT":
                            this.LEFT[fila][i]=(int)((long) lista.get(i));
                            break;
                        case "DOWN":
                            this.DOWN[fila][i]=(int)((long) lista.get(i));
                            break;
                        case "RIGHT":
                            this.RIGHT[fila][i]=(int)((long) lista.get(i));
                            break;
                        case "UP":
                            this.UP[fila][i]=(int)((long) lista.get(i));
                            break;
                        case "BACK":
                            this.BACK[fila][i]=(int)((long) lista.get(i));
                            break;
                        case "FRONT":
                            this.FRONT[fila][i]=(int)((long) lista.get(i));
                            break;                           
                    }                    
                } 
                fila++;
            }
            
        }        
    }    
    
    public String getID() {
        String ID="";
        String caras[]={"LEFT","DOWN","RIGHT","UP","BACK","FRONT"};
        for (int k=0;k<caras.length;k++){
            for (int i=0;i<N;i++) {  
                for (int j=0;j<N;j++) { 
                    switch (caras[i]) {
                        case "LEFT":
                            ID+=LEFT[i][j];
                            break;
                        case "DOWN":
                            ID+=DOWN[i][j];
                            break;
                        case "RIGHT":
                            ID+=RIGHT[i][j];
                            break;
                        case "UP":
                            ID+=UP[i][j];
                            break;
                        case "BACK":
                            ID+=BACK[i][j];
                            break;
                        case "FRONT":
                            ID+=FRONT[i][j];
                            break;        
                    }          
                }    
            }
        }
        return getMD5(ID);
    }
    
    public void mostrarCara(String cara) {
        for (int i=0;i<N;i++) {  
            for (int j=0;j<N;j++) { 
                switch (cara) {
                    case "LEFT":
                        System.out.print(LEFT[i][j]);
                        break;
                    case "DOWN":
                        System.out.print(DOWN[i][j]);
                        break;
                    case "RIGHT":
                        System.out.print(RIGHT[i][j]);
                        break;
                    case "UP":
                        System.out.print(UP[i][j]);
                        break;
                    case "BACK":
                        System.out.print(BACK[i][j]);
                        break;
                    case "FRONT":
                        System.out.print(FRONT[i][j]);
                        break;        
                }          
            }
            System.out.println();
        }
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
 
            while(hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }    
    
    public void moveL (int col) {
        int aux[]=new int[N];
        for (int i=0;i<N;i++) {       
            aux[i]=FRONT[i][col];
            FRONT[i][col]= UP[N-i-1][N-col-1];
            UP[N-i-1][N-col-1]= BACK[i][col];
            BACK[i][col]= DOWN[i][col];
            DOWN[i][col]=aux[i];
        }
    }
    
    public void movel (int col) {
        int aux[]=new int[N];
        for (int i=0;i<N;i++) {       
            aux[i]=BACK[i][col];
            BACK[i][col]= UP[N-i-1][N-col-1];
            UP[N-i-1][N-col-1]= FRONT[i][col];
            FRONT[i][col]= DOWN[i][col];
            DOWN[i][col]=aux[i];
        }
    }
    
    public void moveD (int dentro) {
        int aux[]=new int[N];
        for (int i=0;i<N;i++) {  
            aux[i]=FRONT[i][dentro];
            FRONT[0][i]=RIGHT[N-1-i][dentro];
            RIGHT[i][dentro]=BACK[N-1][i];
            BACK[N-1][i]=LEFT[N-1-i][N-1];
            LEFT[i][N-1]=aux[i];
        }
    }
    
    public void moved (int dentro) {
        int aux[]=new int[N];
        for (int i=0;i<N;i++) {  
            aux[i]=FRONT[i][dentro];
            FRONT[dentro][i]=LEFT[i][N-1];
            LEFT[N-1][i]=BACK[N-1][N-1-i];
            BACK[N-1][i]=RIGHT[i][dentro];
            RIGHT[N-1-i][dentro]=aux[i];
        }
    }
    
    public void moveB (int fil) {
        int aux[]=new int[N];
        for (int i=0;i<N;i++) {       
            aux[i]=FRONT[fil][i];
            FRONT[fil][i]= UP[N-fil-1][N-i-1];
            UP[N-fil-1][N-i-1]= BACK[fil][i];
            BACK[fil][i]= DOWN[fil][i];
            DOWN[fil][i]=aux[i];
        }
    }
    
    public void moveb (int fil) {
        int aux[]=new int[N];
        for (int i=0;i<N;i++) {       
            aux[i]=BACK[fil][i];
            BACK[fil][i]= UP[N-fil-1][N-i-1];
            UP[N-fil-1][N-i-1]= FRONT[fil][i];
            FRONT[fil][i]= DOWN[fil][i];
            DOWN[fil][i]=aux[i];
        }
    }
}
