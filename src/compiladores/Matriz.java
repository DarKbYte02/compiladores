
package compiladores;

import java.util.*;

public class Matriz {
    public static HashMap<String,ArrayList<ArrayList<Double>>> matrices;
    public ArrayList<Double> fila;
    public ArrayList<ArrayList<Double>> matriz;
    public Integer num_filas,num_columnas;
    public String ident;
    
    public Matriz(){
        matrices = new HashMap<>();
        fila = new ArrayList<>();
        matriz = new ArrayList<>();
        ident = "";
        num_filas = num_columnas = 0;
    }
    
    
}
