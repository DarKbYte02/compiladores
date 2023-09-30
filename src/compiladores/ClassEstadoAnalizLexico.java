
package compiladores;

import java.util.*;

public class ClassEstadoAnalizLexico {
    private char caracterActual;
    private int token,EdoActual,EdoTransion,iniLexema,finLexema,indiceActual;
    private boolean pasoPorEdoAcept;
    private String Lexema;
    private Stack<Integer> pila = new Stack<>();
    
    public ClassEstadoAnalizLexico(){
        this.token = this.EdoActual = this.EdoTransion = this.iniLexema = this.finLexema = this.indiceActual = -1;
        this.pasoPorEdoAcept = false;
        this.Lexema = "";
    }
    
    public char getCaracterActual() {
        return caracterActual;
    }

    public int getToken() {
        return token;
    }

    public int getEdoActual() {
        return EdoActual;
    }

    public int getEdoTransion() {
        return EdoTransion;
    }

    public int getIniLexema() {
        return iniLexema;
    }

    public int getFinLexema() {
        return finLexema;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public boolean isPasoPorEdoAcept() {
        return pasoPorEdoAcept;
    }

    public String getLexema() {
        return Lexema;
    }

    public Stack<Integer> getPila() {
        return pila;
    }

    public void setCaracterActual(char caracterActual) {
        this.caracterActual = caracterActual;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void setEdoActual(int EdoActual) {
        this.EdoActual = EdoActual;
    }

    public void setEdoTransion(int EdoTransion) {
        this.EdoTransion = EdoTransion;
    }

    public void setIniLexema(int iniLexema) {
        this.iniLexema = iniLexema;
    }

    public void setFinLexema(int finLexema) {
        this.finLexema = finLexema;
    }

    public void setIndiceActual(int indiceActual) {
        this.indiceActual = indiceActual;
    }

    public void setPasoPorEdoAcept(boolean pasoPorEdoAcept) {
        this.pasoPorEdoAcept = pasoPorEdoAcept;
    }

    public void setLexema(String Lexema) {
        this.Lexema = Lexema;
    }

    public void setPila(Stack<Integer> pila) {
        this.pila = pila;
    }
    
}
