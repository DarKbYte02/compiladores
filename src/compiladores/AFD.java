
package compiladores;

import java.util.*;

public class AFD {
    public static HashSet<AFD> ConjDeAFDs = new HashSet<>(); // Aqui se guardan todos los AFDs
    private HashMap<Integer,int[]> Tabla; // Voy a hashear la id del Sj a un renglón para armar mi tabla;
    private int[] renglon;
    private int idAFD;

    public int getIdAFD() {
        return idAFD;
    }

    public void setIdAFD(int idAFD) {
        this.idAFD = idAFD;
    }
    
    public AFD(){
    
        Tabla = new HashMap<>();
        Tabla.clear();
        int[] renglon = new int[260];
        for(int i=0;i<260;i++) renglon[i] = -1;
    
    }                          //Constructor para el AFD que va a guardar los demás
    
    public AFD(int key){
        Tabla = new HashMap<>();
        Tabla.clear();
        renglon = new int[260];
        for(int i=0;i<260;i++) renglon[i] = -1;
        this.Tabla.put(key,this.renglon);
    }
    //Recibo una llave, de donde vengo y a donde voy (como si fuera armando un grafo)
    public void insertaAFDenTabla(int key, int car1, int j){
        //Si mando a llamar esto desde un AFD lo que hace es va a insertar un AFD al map.
        if(this.Tabla.containsKey(key)){ //Si ya existe modifica la tabla.
            int[] renglon_nuevo = this.Tabla.get(key);
            renglon_nuevo[car1] = j;
            this.Tabla.put(key,renglon_nuevo); //actualizamos el valor
            
        }
        else{
            int[] renglon_tabla = new int[260];
            for(int i=0;i<260;i++) renglon_tabla[i] = -1;
            renglon_tabla[car1] = j;
            this.Tabla.put(key,renglon_tabla);
        }
    }
    
    public void setTokenTabla(int key, int token){
        int[] temp = this.Tabla.get(key);
        temp[256] = token;
        this.Tabla.put(key,temp);
    }
    
    public void insertaAFDenTabla(int key){
        //Si mando a llamar esto desde un AFD lo que hace es va a insertar un AFD al map.
        int[] renglon_nuevo = new int[260];
        for(int i=0;i<260;i++) renglon_nuevo[i] = -1;
        this.Tabla.put(key, renglon_nuevo);
                }

    public static HashSet<AFD> getConjDeAFDs() {
        return ConjDeAFDs;
    }

    public HashMap<Integer, int[]> getTabla() {
        return Tabla;
    }

    public int[] getRenglon() {
        return renglon;
    }
    
    @Override
       public String toString(){
       return Integer.toString(idAFD);
       }
    
}
