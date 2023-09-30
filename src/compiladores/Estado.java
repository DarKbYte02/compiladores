package compiladores;
import java.util.HashSet;

public class Estado {
       static int ContadorIdEstado =0;
       private int idEstado;
       private boolean EdoAcept;
       private int Token;
       private HashSet<Transicion> Trans =  new HashSet<>();
       
       public Estado(){
           EdoAcept = true;
           Token = -1;
           idEstado = ContadorIdEstado++;
           Trans.clear();
       }

    public int getIdEstado() {
        return idEstado;
    }

    public boolean isEdoAcept() {
        return EdoAcept;
    }

    public int getToken() {
        return Token;
    }

    public HashSet<Transicion> getTrans() {
        return Trans;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public void setEdoAcept(boolean EdoAcept) {
        this.EdoAcept = EdoAcept;
    }

    public void setToken(int Token) {
        this.Token = Token;
    }

    public void setTrans(Transicion Trans) {
        this.Trans.add(Trans);
    }
       
    
}
