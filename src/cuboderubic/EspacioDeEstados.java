package cuboderubic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;


public class EspacioDeEstados {
    private Estado estado;
    
    public EspacioDeEstados(String ficheroJSON) throws IOException, FileNotFoundException, ParseException{        
        estado = new Estado(ficheroJSON);
    }
    
    public List<Sucesor> getSucesores(Estado estado){ 
        List<Sucesor> sucesores = new ArrayList();
        List<Accion> acciones = new ArrayList(estado.getAcciones());
        for(Accion accion : acciones){
            sucesores.add(new Sucesor (accion,estado.getEstado(accion),1));
        }
        return sucesores;    
    }

    public Estado getEstado() {
        return estado;
    }
    
}
