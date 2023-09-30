package compiladores;
public class ArregloNodos {
    public NodoArreglo s;
    public ListaDeNodos lista = new ListaDeNodos();
    public ArregloNodos(){}
    
    public ArregloNodos(NodoArreglo simbolo, ListaDeNodos lista){
            this.s = simbolo;
            this.lista = lista;
    }
}
