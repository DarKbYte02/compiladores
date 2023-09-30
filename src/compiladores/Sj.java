
package compiladores;

import java.util.HashSet;

public class Sj {
    private HashSet<Estado> estados;
    private int id;
    private int token;

    public void setEstados(HashSet<Estado> estados) {
        this.estados = estados;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    
    public void setToken(int token) {
        this.token = token;
    }

    public HashSet<Estado> getEstados() {
        return estados;
    }


    public int getToken() {
        return token;
    }
    
    public Sj(){
        estados = new HashSet<>();
        token = -1;
        id = -1;
    }
    
    
    
}
