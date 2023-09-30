
package compiladores;
public class ClaseEvalRegex {
        public String Expresion;
    public AnalizadorLexico L;
    
    public ClaseEvalRegex(AnalizadorLexico analizador){
        Expresion = analizador.getLexema();
        L = analizador;
    }
    
    public ClaseEvalRegex(String sigma, AFD afd){
        Expresion = sigma;
        L = new AnalizadorLexico(afd,Expresion);
    }
    public void setExpresion(String sigma){
        this.Expresion = sigma;
        L.setSigma(sigma);
    }
    
    public boolean iniEvalRegex(AFN f){
        return (E(f));
    }
    
    public boolean E(AFN f){
        if(T(f)){
            if(Ep(f)){
                return true;
            }
        }
        return false;
    }
    
    public boolean Ep(AFN f){
        int Token;
       AFN f2 = new AFN();
       Token = L.yylex();
       if(Token == 10){
           if(T(f2)){
               f.UnirAFN(f2);
               if(Ep(f)){
                   return true;
               }
           }
           return false;
       }
       L.undoToken();
       return true;
    }
    
    public boolean T(AFN f){
        if(C(f)){
            if(Tp(f)){
                return true;
            }
        }
        return false;
    }
    
    
    public boolean Tp(AFN f){
        int Token;
        AFN f2 = new AFN();
        Token = L.yylex();
        if(Token == 20){
            if(C(f2)){
                f.ConcAFN(f2);
                if(Tp(f)){
                    return true;
                }
            }
            return false;
        }
        L.undoToken();
        return true;
    }
    
    
    public boolean C(AFN f){
        if(F(f)){
            if(Cp(f)){
                return true;
            }
        }
        return false;
    }
    
    public boolean Cp(AFN f){
        int Token;
        Token = L.yylex();
        
        switch(Token){
            case 30:
                f.CerrPos();
                return Cp(f);
            case 40:
                f.CerrKleen();
                return Cp(f);
            case 50:
                f.CerrOpcional();
                return Cp(f);
        }
        L.undoToken();
        return true;
    }
    
    public boolean F(AFN f){
        int Token;
        String Lexema1,Lexema2;
        Token = L.yylex();
        switch(Token){
            case 60:
                if(E(f)){
                    return (L.yylex() == 70);
                }
                return false;
            case 80:
                Token = L.yylex();
                if(Token == 110){
                    Lexema1 = (L.getLexema().charAt(0) == '\\')?L.getLexema().substring(1, 2) : L.getLexema();
                    Token = L.yylex();
                    if(Token == 100){
                        Token = L.yylex();
                        if(Token == 110){
                            Lexema2 = (L.getLexema().charAt(0) == '\\')?L.getLexema().substring(1, 2) : L.getLexema();
                            Token = L.yylex();
                            if(Token == 90){
                                //f = new AFN();
                                //System.out.println(Lexema1.charAt(0));
                                //System.out.println(Lexema2.charAt(0));
                                f.crearAFNBasico(Lexema1.charAt(0), Lexema2.charAt(0));
                                return true;
                            }
                            return false;
                        }
                    }
                }
                return false;
            case 110:
                //f = new AFN();
                //System.out.println("HOLA");
                Lexema1 = (L.getLexema().charAt(0) == '\\')?L.getLexema().substring(1, 2) : L.getLexema();
                f.crearAFNBasico(Lexema1.charAt(0));
                return true;
        }
        return false;
    }
    
    
    
    
}
