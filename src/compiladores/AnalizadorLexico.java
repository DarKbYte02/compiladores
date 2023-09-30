package compiladores;

import java.util.*;

public class AnalizadorLexico {
    private AFD afd;
    private String Lexema;
    private String Cadena;
    public List<Pair<String,Integer>> guardarLexemas= new ArrayList<>();
    //public HashMap<String,Integer> guardarLexemas = new HashMap<>();
    private int token,EdoActual,EdoTransicion,iniLexema,finLexema,indiceActual;
    private boolean pasoPorEdoAcept;
    private char caracterActual;
    private Stack<Integer> pila;
    
    public AnalizadorLexico(){
        this.afd = new AFD();
        this.Lexema = "";
        this.Cadena = "";
        pasoPorEdoAcept = false;
        token = indiceActual = iniLexema = finLexema = -1;
        pila = new Stack<>();
        pila.clear();
    }
    
    public AnalizadorLexico(AFD automatadeterminista,String sigma){
        this.afd = automatadeterminista;
        this.Cadena = sigma;
        pasoPorEdoAcept = false;
        iniLexema = indiceActual = 0;
        token = finLexema = -1;
        pila = new Stack<>();
        pila.clear();
        this.Lexema = "";
        this.EdoActual=0;
    }
    
    public ClassEstadoAnalizLexico GetEdoAnalizLexio(){
        ClassEstadoAnalizLexico EdoActualAnaliz = new ClassEstadoAnalizLexico();
        EdoActualAnaliz.setCaracterActual(caracterActual);
        EdoActualAnaliz.setEdoActual(EdoActual);
        EdoActualAnaliz.setEdoTransion(EdoTransicion);
        EdoActualAnaliz.setFinLexema(finLexema);
        EdoActualAnaliz.setIndiceActual(indiceActual);
        EdoActualAnaliz.setIniLexema(iniLexema);
        EdoActualAnaliz.setLexema(Lexema);
        EdoActualAnaliz.setPasoPorEdoAcept(pasoPorEdoAcept);
        EdoActualAnaliz.setToken(token);
        EdoActualAnaliz.setPila(pila);
        return EdoActualAnaliz;
    }
    
    public boolean SetEdoAnalizLexico(ClassEstadoAnalizLexico e){
        caracterActual = e.getCaracterActual();
        EdoActual = e.getEdoActual();
        EdoTransicion = e.getEdoTransion();
        finLexema = e.getFinLexema();
        indiceActual = e.getIndiceActual();
        iniLexema = e.getIniLexema();
        Lexema = e.getLexema();
        pasoPorEdoAcept = e.isPasoPorEdoAcept();
        token = e.getToken();
        pila = e.getPila();
    return true;
    }
    
    public void setSigma(String sigma){
        Cadena =sigma;
        pasoPorEdoAcept = false;
        iniLexema = 0;
        finLexema = -1;
        indiceActual = 0;
        token = -1;
        pila.clear();
    }
    
    public String CadenaXAnalizar(){
    
           return Cadena.substring(indiceActual, Cadena.length());
    }
    
    
    public int yylex(){
        while(true){
            pila.push(indiceActual);
            if(indiceActual >= Cadena.length()){
                Lexema = "";
                return SimbolosEspeciales.FIN;
            }
            iniLexema = indiceActual;
            EdoActual = 0;
            pasoPorEdoAcept = false;
            finLexema = -1;
            token = -1;
            
            while(indiceActual < Cadena.length()){
                caracterActual = Cadena.charAt(indiceActual);
                int[] temp = new int[260];
                temp = afd.getTabla().get(EdoActual);
                EdoTransicion = temp[caracterActual];
                
                if(EdoTransicion != -1){
                    //System.out.println("HOLAAAAA"+ " EDOACTUAL: "+EdoActual+temp[256]);
                    temp = afd.getTabla().get(EdoTransicion); // < - IMPORTANTE - >
                    if(temp[256] != -1){
                        //System.out.println("HOLAAAAA");
                        pasoPorEdoAcept = true;
                        token = temp[256];
                        finLexema = indiceActual;
                    }
                    indiceActual++;
                    EdoActual = EdoTransicion;
                    continue;
                }
                /*else if(temp[256] != -1){
                        //System.out.println("HOLAAAAA");
                        pasoPorEdoAcept = true;
                        token = temp[256];
                        finLexema = indiceActual;
                        //indiceActual++;
                        //EdoActual = EdoTransicion;
                        break;
                    }*/
                break;
            }
            
            if(!pasoPorEdoAcept){
                int[] temp = new int[260];
                temp = afd.getTabla().get(EdoActual);
                //System.out.println("Estado actual: " + EdoActual+ " "+ temp[256]);
                indiceActual = iniLexema +1;
                Lexema = Cadena.substring(iniLexema,indiceActual);
                token = SimbolosEspeciales.ERROR;
                return token;
            }
            int longitud = finLexema-iniLexema+1;
            Lexema = Cadena.substring(iniLexema,iniLexema+longitud);
            
            indiceActual = finLexema+1;
            if(token == SimbolosEspeciales.OMITIR) continue;
            else return token;
        }
    }
    
    public void setAFD(AFD automata_determinista){
        this.afd = automata_determinista;
    }

    public int getToken() {
        return token;
    }

    public String getLexema() {
        return Lexema;
    }

    public String getCadena() {
        return Cadena;
    }

    public int getIniLexema() {
        return iniLexema;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public char getCaracterActual() {
        return caracterActual;
    }
    
    
    
    public boolean undoToken(){
    if(pila.isEmpty()) return false;
        indiceActual = pila.pop();
    return true;
    }
    
}
