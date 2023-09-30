package compiladores;
public class Transicion {
       private char SimbInf;
       private char SimbSup;
       private Estado Edo;
       
       public Transicion(char simb, Estado e){
           SimbInf = SimbSup = simb;
           Edo = e;
       }
       public Transicion(char siminf, char simbsup, Estado e){
           SimbInf = siminf;
           SimbSup = simbsup;
           Edo = e;
       }
       public Transicion(){
           Edo = null;
       }
       public void setTransicion(char simbolo1, char simbolo2, Estado e){
           SimbInf = simbolo1; 
           SimbSup = simbolo2;
           Edo = e;
       }
       public void setTransicion(char simbolo1, Estado e){
           SimbInf = SimbSup = simbolo1;
           Edo = e;
       }

    public char getSimbInf() {
        return SimbInf;
    }

    public char getSimbSup() {
        return SimbSup;
    }

    public void setSimbInf(char SimbInf) {
        this.SimbInf = SimbInf;
    }

    public void setSimbSup(char SimbSup) {
        this.SimbSup = SimbSup;
    }
    public Estado GetEdoTrans(char c){
        return (SimbInf <= c && c <= SimbSup) ? Edo : null;
    }
}
