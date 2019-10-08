
package cuborubic;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class Cuborubic {

 
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Cubo c=new Cubo(3);
        // lectura JSon
        c.InicializarJson("cube.json");
        c.mostrarCara("LEFT");
        System.out.println();
        c.mostrarCara("RIGHT");
        // id en MD5
        System.out.println(c.getID());
        
        // CLONAR OBJETO EN MEMORIA
        Cubo clonado=(Cubo)c.clone();
        clonado.mostrarCara("LEFT");
        System.out.println();
        clonado.mostrarCara("RIGHT");
        System.out.println(c.getID());
        
        // MOVER A LA IZDA
        c.moveL(1);
        c.mostrarCara("UP");
         System.out.println(c.getID());
    }
    
}
