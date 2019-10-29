package cuboderubic;


public class Problema {   
    private EspacioDeEstados espacioDeEstados;
    private Estado estadoInicial;

    public Problema(EspacioDeEstados espacioDeEstados, Estado estadoInicial) {
        this.espacioDeEstados = espacioDeEstados;
        this.estadoInicial = estadoInicial;
    }
    
    public boolean esObjetivo (Estado estado) {
        return estadoInicial.esObjetivo();
    }

    public EspacioDeEstados getEspacioDeEstados() {
        return espacioDeEstados;
    }

    public void setEspacioDeEstados(EspacioDeEstados espacioDeEstados) {
        this.espacioDeEstados = espacioDeEstados;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    @Override
    public String toString() {
        return "Problema{" + "espacioDeEstados=" + espacioDeEstados + ", estadoInicial=" + estadoInicial + '}';
    }
    
    
}
