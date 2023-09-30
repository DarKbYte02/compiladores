package compiladores;
//TODO: Optimizar multiplicacion de matrices con el método de Strassen. -> Ya funciona de manera normal, no importa que sea O(n^3)

import java.util.*; //como la librería <bits/stdc++.h> en C++

public class ClaseEvalMatrices {
    public String Expresion;
    public AnalizadorLexico L;
    public HashMap<String,Matriz> b = new HashMap<>();
    
    public ClaseEvalMatrices(AnalizadorLexico analizador){
        Expresion = analizador.getLexema();
        L = analizador;
    }
    
    public ClaseEvalMatrices(String sigma, AFD afd){
        Expresion = sigma;
        L = new AnalizadorLexico(afd,Expresion);
    }
    public void setExpresion(String sigma){
        this.Expresion = sigma;
        L.setSigma(sigma);
    }
    
    public boolean iniEvalMatrices(){
        int Token;
        Matriz nueva_matriz = new Matriz();
        if(Asig(nueva_matriz)){
            Token = L.yylex();
            if(Token == 0){
                Proyecto.a.put(nueva_matriz.ident, nueva_matriz); //Una cochinada pero si funciona ya no hay que moverlo
                System.out.println(nueva_matriz.ident+": ");
                for(ArrayList<Double> c : nueva_matriz.matriz){
                    for(Double a : c){
                        System.out.print("| "+a+" |"+"\t");
                    }
                    System.out.println("");
                }
                
                return true;
            }
        }
        return false;
    }
    
    public boolean Asig(Matriz nueva_matriz){
        int Token;
        Token = L.yylex();
        if(Token == 120){                   //IDENT    
           nueva_matriz.ident = L.getLexema(); // Guardamos el identificador de la matriz pa despues asignarlo al HashMap
            Token = L.yylex();
            if(Token == 10){                //EQ
                if(E(nueva_matriz)){
                    Token = L.yylex();
                    if(Token == 20){        //PC
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean E(Matriz nueva_matriz){
        if(T(nueva_matriz)){
            if(Ep(nueva_matriz)){
                return true;
            }
        }
        return false;
    }
    
    
    public boolean Ep(Matriz nueva_matriz){
        
        int Token;
        Token = L.yylex();
        if(Token == 30){
            Matriz nueva_matriz2 = new Matriz();
            //Tengo que enviar otra matriz, no puede ser la misma porque tengo que recuperar otra para hacer una operación.
            if(T(nueva_matriz2)){
                if(Ep(nueva_matriz2)){
                    if(!nueva_matriz2.matriz.isEmpty() && !nueva_matriz.matriz.isEmpty()){
                        if(nueva_matriz.matriz.size() == nueva_matriz2.matriz.size() && nueva_matriz.matriz.get(0).size() == nueva_matriz2.matriz.get(0).size()){
                            Double temp;
                            for(int i=0;i<nueva_matriz.matriz.size();i++){
                                Fila fila_aux = new Fila();
                                for(int j=0;j<nueva_matriz.matriz.get(0).size();j++){
                                    temp = nueva_matriz.matriz.get(i).get(j) + nueva_matriz2.matriz.get(i).get(j);
                                    fila_aux.fila.add(temp);
                                }
                                nueva_matriz.matriz.set(i, fila_aux.fila);
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        
        if(Token == 40){
            Matriz nueva_matriz2 = new Matriz();
            //Tengo que enviar otra matriz, no puede ser la misma porque es necesario realizar una operación.
            if(T(nueva_matriz2)){
                if(Ep(nueva_matriz2)){
                      if(!nueva_matriz2.matriz.isEmpty() && !nueva_matriz.matriz.isEmpty()){
                        if(nueva_matriz.matriz.size() == nueva_matriz2.matriz.size() && nueva_matriz.matriz.get(0).size() == nueva_matriz2.matriz.get(0).size()){
                            Double temp;
                            for(int i=0;i<nueva_matriz.matriz.size();i++){
                                Fila fila_aux = new Fila();
                                for(int j=0;j<nueva_matriz.matriz.get(0).size();j++){
                                    temp = nueva_matriz.matriz.get(i).get(j) - nueva_matriz2.matriz.get(i).get(j);
                                    fila_aux.fila.add(temp);
                                }
                                nueva_matriz.matriz.set(i, fila_aux.fila);
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        L.undoToken();
        return true;
    }
    
    
    public boolean T(Matriz nueva_matriz){
        if(F(nueva_matriz)){
            if(Tp(nueva_matriz)){
                return true;
            }
        }
        return false;
    }


    public boolean Tp(Matriz nueva_matriz){
        int Token;
        Token = L.yylex();
        if(Token == 50){
            Matriz nueva_matriz2 = new Matriz();
            //Tengo que enviar otra matriz, no puede ser la misma porque es necesario realizar una operación.
            if(F(nueva_matriz2)){
                if(Tp(nueva_matriz2)){
                    if(!nueva_matriz.matriz.isEmpty() && !nueva_matriz2.matriz.isEmpty()){
                        if(nueva_matriz.matriz.get(0).size() == nueva_matriz2.matriz.size()){
                            Double[][] asd = new Double[nueva_matriz.matriz.size()][nueva_matriz2.matriz.get(0).size()];
                            for(int i=0;i<nueva_matriz.matriz.size();i++) for(int j=0;j<nueva_matriz2.matriz.get(0).size();j++) asd[i][j]=0.0;
                            for(int i=0;i<nueva_matriz.matriz.size();i++){
                                for(int j=0;j<nueva_matriz2.matriz.get(0).size();j++){
                                    for(int k=0;k<nueva_matriz.matriz.get(0).size();k++){
                                        asd[i][j] = nueva_matriz.matriz.get(i).get(k)*nueva_matriz2.matriz.get(k).get(j)+asd[i][j];
                                        //hago esto para facilitar la multiplicación, ya luego copio lo de este array a un objeto matriz.
                                    }
                                }
                            }
                            
                            nueva_matriz.matriz.clear();
                            for(Double[] x : asd){
                                Fila auxiliar = new Fila();
                                for(Double y: x){
                                    auxiliar.fila.add(y);
                                }
                                nueva_matriz.matriz.add(auxiliar.fila);
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        L.undoToken();
        return true;
    }
    
    
    public boolean F(Matriz nueva_matriz){
        int Token;
        Token = L.yylex();
        switch(Token){
            case 60:
                if(E(nueva_matriz)){
                    Token = L.yylex();
                    if(Token == 70){
                        return true;
                    }
                }
                return false;
            case 120:
                String identificador = L.getLexema();
                if(Proyecto.a.containsKey(identificador)){
                    Matriz aux = new Matriz();
                    for(ArrayList<Double> c : Proyecto.a.get(identificador).matriz){
                        Fila fila_aux = new Fila();
                        for(Double x : c){
                            fila_aux.fila.add(x);
                        }
                        aux.matriz.add(fila_aux.fila);
                    }
                    nueva_matriz.matriz = aux.matriz; //Hay que copiar la matriz a mano pq si no se pasa por referencia y se muere.
                                                      //Es como pasar un puntero, si le pasamos el puntero se modifica la matriz original
                                                      //entonces hay que hacer una copia y pasarle esa copia para que no se modifiquen las
                                                      //matrices originales.

                }
                else{
                System.out.println("No tengo la llave "+identificador);
                        //En caso de que la matriz no exista
                }
                return true;
            case 80:
                if(Renglones(nueva_matriz)){
                    Token = L.yylex();
                    if(Token == 90){
                        return true;
                    }
                }
                return false;
        }
        return false;
    }
        
    
    public boolean Renglones(Matriz nueva_matriz){
        Fila fila = new Fila();
        if(Renglon(fila)){
            if(!fila.fila.isEmpty()){
                    nueva_matriz.matriz.add(fila.fila);
            }
            
            if(Renglonesp(nueva_matriz)){
                return true;
            }
        }
        return false;
    }
    
    public boolean Renglonesp(Matriz nueva_matriz){
        ClassEstadoAnalizLexico EdoAnalizadorLexico = new ClassEstadoAnalizLexico();
        EdoAnalizadorLexico = L.GetEdoAnalizLexio();
        Fila fila = new Fila();
        if(Renglon(fila)){
                        if(!fila.fila.isEmpty()){
                    nueva_matriz.matriz.add(fila.fila);
            }
            
            if(Renglonesp(nueva_matriz)){
                return true;
            }
            return false;
        }
        L.SetEdoAnalizLexico(EdoAnalizadorLexico);
        return true;
    }
    
    
    public boolean Renglon(Fila fila){
        int Token;
        Token = L.yylex();
        if(Token == 80){
            if(ListaNumeros(fila)){
                Token = L.yylex();
                if(Token == 90){
                    return true;
                }
            }
        }
        return false;
    }
    
    //debo recibir aquí una arraylist para ir guardando las cosas
    public boolean ListaNumeros(Fila fila){
        int Token;
        Token = L.yylex();
        if(Token == 110){
            fila.fila.add(Double.valueOf(L.getLexema()));
            if(ListaNumerosp(fila)){
                return true;
            }
        }
        return false;
    }
    
    public boolean ListaNumerosp(Fila fila){
        int Token;
        Token = L.yylex();
        if(Token == 100){
            Token = L.yylex();
            if(Token == 110){
                fila.fila.add(Double.valueOf(L.getLexema()));
                if(ListaNumerosp(fila)){
                    return true;
                }
            }
            return false;
        }
        L.undoToken();
        return true;
    }
    
    
}
