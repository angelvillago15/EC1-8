package Dominio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;

public class EspacioDeEstados {
    private Estado estado;
    public EspacioDeEstados(String fileJSON) throws IOException, FileNotFoundException, ParseException{
        estado = new Estado(fileJSON);
    }
    
    public List<Sucesor> getSucesores(Estado estado){ //metodo que obtiene una lista de los sucesores a partir de cierto estado.
        List<Sucesor> suc = new ArrayList<>();
        List<Accion> acc = new ArrayList<>(estado.getAcciones());
        acc.forEach((accion) -> {
            suc.add(new Sucesor (accion,estado.getEstado(accion),1));
        });
        return suc;    
    }

    public Estado getEstado() {
        return estado;
    }    
}

