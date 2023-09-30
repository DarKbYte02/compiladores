package compiladores;
import java.util.*;
public class ClaseEvalGramaticas {
        public String Expresion;
        public AnalizadorLexico L;
        public ArregloNodos Reglas[] = new ArregloNodos[1000];
        public int NumReglas = 0;
        
        public HashSet<String> Vn = new HashSet<>(); //No terminales
        public HashSet<String> Vt = new HashSet<>(); //Terminales
        
        public ClaseEvalGramaticas(AnalizadorLexico analizador){
        Expresion = analizador.getLexema();
        L = analizador;
    }
    
    public ClaseEvalGramaticas(String sigma, AFD afd){
        Expresion = sigma;
        L = new AnalizadorLexico(afd,Expresion);
    }
    public void setExpresion(String sigma){
        this.Expresion = sigma;
        L.setSigma(sigma);
    }
    
    public boolean iniEvalGramaticas(){
           int token;
           if(G()){
               token = L.yylex();
               System.out.println("Regreso true");
               if(token == 0){
                  IdentificarTerminales();
                  
                  System.out.println("Numero de reglas: "+NumReglas);
                  /*
                  System.out.println("No terminales: ");
                  for(String s: Vn) System.out.print(s+ " ");
                  System.out.println("\nTerminales: ");
                  for(String s: Vt) System.out.print(s+ " ");
                  */
                  
                  for(int i=0;i<NumReglas;i++){
                      System.out.print(Reglas[i].s.c+":("+Reglas[i].lista.lista.size()+") ");
                      for(NodoArreglo nd : Reglas[i].lista.lista){
                          System.out.print(nd.c+ " ");
                      }
                      System.out.print("\n");
                  }
                  
                   return true;
               }
           }
           return false;
    }
    
    public boolean G(){
        return ListaReglas();
    }
    
    public boolean ListaReglas(){
        if(Reglas()){
            int Token = L.yylex();
                if(Token == 10){
                    if(ListaReglasP()){
                        return true;
                    }
                }
        }
        return false;
    }
        
    public boolean ListaReglasP(){
        int Token;
        ClassEstadoAnalizLexico EdoAnalizadorLexico = new ClassEstadoAnalizLexico(); //IMPORTANTE
        EdoAnalizadorLexico = L.GetEdoAnalizLexio();
        if(Reglas()){
            Token = L.yylex();
                if(Token == 10){            //PUNTO Y COMA
                    if(ListaReglasP()){
                        return true;
                    }
                }
            return false;
        }
        L.SetEdoAnalizLexico(EdoAnalizadorLexico);
        return true;
    }
    
    public boolean Reglas(){
        Cadena simbolo = new Cadena();
        
        if(LadoIzquierdo(simbolo)){
            Vn.add(simbolo.s);
            int Token = L.yylex();
            if(Token == 20){                //FLECHA
                //System.out.println(L.CadenaXAnalizar());
                if(LadosDerechos(simbolo.s)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean LadoIzquierdo(Cadena simbolo){
        int Token = L.yylex();
        if(Token == 40){
            simbolo.s = L.getLexema();
            return true;
        }
        return false;
    }
    
    public boolean LadosDerechos(String simbolo){
        if(LadoDerecho(simbolo)){
            if(LadosDerechosP(simbolo)){
                return true;
            }
        }
        return false;
    }
    
    public boolean LadosDerechosP(String simbolo){
        int Token = L.yylex();
        if(Token == 30){
            if(LadoDerecho(simbolo)){
                if(LadosDerechosP(simbolo)){
                    return true;
                }
            }
            return false;
        }
        L.undoToken();
        return true;
    }
    
    public boolean LadoDerecho(String simbolo){
        ListaDeNodos lista = new ListaDeNodos();
        lista.lista.clear();
        if(SecSimbolos(lista)){
            Reglas[NumReglas] = new ArregloNodos();
            Reglas[NumReglas].s = new NodoArreglo(simbolo,false);
            Reglas[NumReglas++].lista = lista;
            return true;
        }
        return false;
    }
    
    public boolean SecSimbolos(ListaDeNodos lista){
        int Token = L.yylex();
        NodoArreglo N;
        if(Token == 40){
            N = new NodoArreglo(L.getLexema(),false);
            if(SecSimbolosP(lista)){
                lista.lista.add(0, N);
                return true;
            }
            
        }
        return false;
    }
    
    public boolean SecSimbolosP(ListaDeNodos lista){
        int Token = L.yylex();
        NodoArreglo N;
        if(Token == 40){
            N = new NodoArreglo(L.getLexema(),false);
            if(SecSimbolosP(lista)){
                lista.lista.add(0, N);
                return true;
            }
            return false;
            
        }
        
        L.undoToken();
        return true;
    }
    
    public void IdentificarTerminales(){
        int i;
        for(i=0;i<NumReglas;i++){
            for(NodoArreglo N : Reglas[i].lista.lista){
                if(!Vn.contains(N.c) && !N.c.equals("epsilon")){
                    N.esTerminal = true;
                    Vt.add(N.c);
                }
            }
        }

    }
    
public HashSet<String> First(ListaDeNodos lista){
        int i,j;
        NodoArreglo N;
        HashSet<String> R = new HashSet<>();
        R.clear();
        
        if(lista.lista.isEmpty()) return R;
        
        for(j=0;j<lista.lista.size();j++){
        
        N = lista.lista.get(j);
        if(N.esTerminal || N.c.equals("epsilon")){
            R.add(N.c);
            return R;
        }
        for(i=0;i<NumReglas;i++){
            if(Reglas[i].lista.lista.get(0).c.equals(N.c)) continue;
            if(Reglas[i].s.c.equals(N.c)) R.addAll(First(Reglas[i].lista));
        }
        if(R.contains("epsilon")){
            if(j ==lista.lista.size()-1) continue;
            R.remove("epsilon");
        }
        else break;
    }   
  return R;      
}    
    public HashSet<String> Follow(String SimbNoTerm){
        HashSet<String> R = new HashSet<>();
        HashSet<String> Aux = new HashSet<>();
        ListaDeNodos ListaPost = new ListaDeNodos();
        R.clear();
        
        int i,j,k;
        if(Reglas[0].s.c.equals(SimbNoTerm)) R.add("$");
        for(i=0;i<NumReglas;i++){
            for(j=0;j<Reglas[i].lista.lista.size();j++){
                if(Reglas[i].lista.lista.get(j).c.equals(SimbNoTerm)){
                    ListaPost.lista.clear();
                    for(k=j+1;k<Reglas[i].lista.lista.size();k++) ListaPost.lista.add(Reglas[i].lista.lista.get(k));
                    if(ListaPost.lista.isEmpty()){
                        if(!Reglas[i].s.c.equals(SimbNoTerm)) R.addAll(Follow(Reglas[i].s.c));
                        break;
                    }
                    Aux.clear();
                    Aux = First(ListaPost);
                    if(Aux.contains("epsilon")){
                        Aux.remove("epsilon");
                        R.addAll(Aux);
                        if(!Reglas[i].s.c.equals(SimbNoTerm)) R.addAll(Follow(Reglas[i].s.c));
                    }
                    else R.addAll(Aux);
                }
            }
        }
        return R;
    }
    
    
}
