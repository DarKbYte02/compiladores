package compiladores;

public class NodoArreglo {
    public String c;
    public boolean esTerminal;
    public int token;
    
   public NodoArreglo(){
       this.c="";
       this.esTerminal=false;
       this.token=-1;
   }
   
   public NodoArreglo(String s, boolean i){
       this.c = s;
       this.esTerminal=i;
   }
}
