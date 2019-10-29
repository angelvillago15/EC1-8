package cuboderubic;

import java.util.ArrayList;
import java.util.List;


public class EspacioDeEstados {
    
    public EspacioDeEstados(){}
    
    public List<Sucesor> getSucesores(Estado estado){ 
        List<Sucesor> sucesores = new ArrayList();
        List<Accion> acciones = new ArrayList(estado.getAcciones());
        for(Accion accion : acciones){
            sucesores.add(new Sucesor (accion,estado.getEstado(accion),1));
        }
        return sucesores;    
    }

}
