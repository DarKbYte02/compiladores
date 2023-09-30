package compiladores;

import java.util.*;

public class AFN {
       public static HashSet<AFN> ConjDeAFNs = new HashSet<>(); //Aquí se van a guardar todos los AFNs
       Estado EdoIni;
       HashSet<Estado> EdosAFN = new HashSet<>();
       HashSet<Estado> EdosAcept = new HashSet<>();;
       HashSet<Character> Alfabeto = new HashSet<>();
       boolean SeAgregoAFNUnionLexico;
       public int idAFN;
       
       public AFN(){
           idAFN = 0;
           EdoIni = null;
           EdosAFN.clear();
           EdosAcept.clear();
           Alfabeto.clear();
           SeAgregoAFNUnionLexico = false;
       }
       public AFN crearAFNBasico(char c){
           Transicion t;
           Estado e1,e2;
           e1 = new Estado(); e2 = new Estado();
           t = new Transicion(c,e2);
           e1.setTrans(t);
           e2.setEdoAcept(true);
           Alfabeto.add(c);
           EdoIni = e1;
           EdosAFN.add(e1);
           EdosAFN.add(e2);
           EdosAcept.add(e2);
           SeAgregoAFNUnionLexico = false;
           return this;
       }
       
       public AFN crearAFNBasico(char c1, char c2){
           if(c1>c2){
             char i;
             i = c2;
             c2 = c1;
             c1 = i;
           }
           Transicion t;
           Estado e1,e2;
           e1 = new Estado();
           e2 = new Estado();
           t = new Transicion(c1,c2,e2);
           e1.setTrans(t);
           e2.setEdoAcept(true);
           for(char i=c1;i<=c2;i++) Alfabeto.add(i);
           EdoIni = e1;
           EdosAFN.add(e1);
           EdosAFN.add(e2);
           EdosAcept.add(e2);
           SeAgregoAFNUnionLexico = false;
           return this;   
       }
       public AFN UnirAFN(AFN af2){
           Estado e1 = new Estado();
           Estado e2 = new Estado();
           Transicion t1 = new Transicion(SimbolosEspeciales.EPSILON,this.EdoIni);
           Transicion t2 = new Transicion(SimbolosEspeciales.EPSILON,af2.EdoIni);
           e1.setTrans(t1);
           e1.setTrans(t2);
           for(Estado e:this.EdosAcept){
                e.setTrans(new Transicion(SimbolosEspeciales.EPSILON,e2));
                e.setEdoAcept(false);
           }
           for(Estado e: af2.EdosAcept){
                e.setTrans(new Transicion(SimbolosEspeciales.EPSILON,e2));
                e.setEdoAcept(false);
           }
           this.EdosAcept.clear();
           af2.EdosAcept.clear();
           this.EdoIni = e1;
           e2.setEdoAcept(true);
           this.EdosAcept.add(e2);
           this.EdosAFN.addAll(af2.EdosAFN);
           this.EdosAFN.add(e1);
           this.EdosAFN.add(e2);
           this.Alfabeto.addAll(af2.Alfabeto);
           return this;
       }
       
       public AFN ConcAFN(AFN af2){
           for(Transicion t: af2.EdoIni.getTrans()){
               for(Estado e: this.EdosAcept){
                   e.setTrans(t);
                   e.setEdoAcept(false);
               }
           }
           af2.EdosAFN.remove(af2.EdoIni);
           this.EdosAcept = af2.EdosAcept;
           this.EdosAFN.addAll(af2.EdosAFN);
           this.Alfabeto.addAll(af2.Alfabeto);
           return this;
       }
       
       public AFN CerrPos(){
           Estado start = new Estado();
           Estado end = new Estado();
           start.setTrans(new Transicion(SimbolosEspeciales.EPSILON,EdoIni));
           for(Estado s: EdosAcept){
               s.setTrans(new Transicion(SimbolosEspeciales.EPSILON,end));
               s.setTrans(new Transicion(SimbolosEspeciales.EPSILON,EdoIni));
               s.setEdoAcept(false);
           }
           EdoIni = start;
           end.setEdoAcept(true);
           EdosAcept.clear();
           EdosAcept.add(end);
           EdosAFN.add(start);
           EdosAFN.add(end);
           return this;
       }
       
       public AFN CerrKleen(){
            Estado start = new Estado();
            Estado end = new Estado();
            start.setTrans(new Transicion(SimbolosEspeciales.EPSILON,EdoIni));
            start.setTrans(new Transicion(SimbolosEspeciales.EPSILON,end));
            for(Estado s: EdosAcept){
                s.setTrans(new Transicion(SimbolosEspeciales.EPSILON,end));
                s.setTrans(new Transicion(SimbolosEspeciales.EPSILON,EdoIni));
                s.setEdoAcept(false);
            }
            EdoIni = start;
            end.setEdoAcept(true);
            EdosAcept.clear();
            EdosAcept.add(end);
            EdosAFN.add(start);
            EdosAFN.add(end);
            return this;
       }
       
       public AFN CerrOpcional(){
           Estado start = new Estado();
           Estado end = new Estado();
           start.setTrans(new Transicion(SimbolosEspeciales.EPSILON,EdoIni));
           start.setTrans(new Transicion(SimbolosEspeciales.EPSILON,end));
           for(Estado s: EdosAcept){
               s.setTrans(new Transicion(SimbolosEspeciales.EPSILON,end));
               s.setEdoAcept(false);
           }
           EdoIni = start;
           start.setEdoAcept(false);
           EdosAcept.clear();
           EdosAcept.add(end);
           EdosAFN.add(start);
           EdosAFN.add(end);
           return this;
       }
       //Si vemos los automatas como grafos, esto es simplemente una DFS iterativa sobre un grafo.
       public HashSet<Estado> CerraduraEpsilon(Estado e){
       HashSet<Estado> R = new HashSet<>();
       Estado aux,Edo;
       Stack<Estado> S = new Stack<>();
       R.clear(); S.clear();
       
       S.push(e);
       
       while(!S.empty()){
           aux = S.pop();
           R.add(aux);
           //Has la DFS y si encuentras algún estado no visitado (nuevo) mételo a la respuesta
           for(Transicion t:aux.getTrans()) if((Edo = t.GetEdoTrans(SimbolosEspeciales.EPSILON)) != null && !R.contains(Edo)) S.push(Edo);
       }
       return R;
       }
       
       public HashSet<Estado> CerraduraEpsilon(HashSet<Estado> ConjEdos){
           HashSet<Estado> R = new HashSet<>();
           Stack<Estado> S = new Stack<>();
           Estado aux,Edo;
           R.clear(); S.clear();
           for(Estado s: ConjEdos) S.push(s);
           while(!S.empty()){
               aux = S.pop();
               R.add(aux);
               for(Transicion t: aux.getTrans()) if((Edo = t.GetEdoTrans(SimbolosEspeciales.EPSILON)) != null && !R.contains(Edo)) S.push(Edo);
           }
           return R;
       }
       
       public HashSet<Estado> Mover(Estado s,char Simb){
           HashSet<Estado> C = new HashSet<>();
           Estado aux;
           C.clear();
           for(Transicion t:s.getTrans()){
               aux = t.GetEdoTrans(Simb);
               if(aux != null) C.add(aux);
           }
           return C;
       }
       
       public HashSet<Estado> Mover(HashSet<Estado> Edos,char Simb){
           HashSet<Estado> C = new HashSet<>();
           Estado aux;
           C.clear();
           for(Estado Edo: Edos){
               for(Transicion t: Edo.getTrans()){
                   aux = t.GetEdoTrans(Simb);
                   if(aux != null) C.add(aux);
               }
           }
           return C;
       }
       //Para ahorrarse escribir el cerraduraepsilon(Mover(...,...));
       public HashSet<Estado> Ir_A(HashSet<Estado> Edos, char simb){
           HashSet<Estado> C = new HashSet<>();
           C.clear();
           C = CerraduraEpsilon(Mover(Edos,simb));
           return C;
       }
       
       //Esta parte del código es importante, sirve para que en la interfaz gráfica se muestren
       //las IDs de los AFNs y cuando se seleccione uno, el JComboBox retorne el objeto completo
       //en lugar de solo la id. https://stackoverflow.com/questions/45313023/return-object-from-jcombobox
       
       @Override
       public String toString(){
       return Integer.toString(idAFN);
       }
       //Creo un estado inicial nuevo que transiciona a los demás afns, luego les asigno los tokens a los edos
       //de aceptación correspondientes a cada afn
       public void UnionEspecialAFNs(AFN f, int Token){
           Estado e;
           if(!this.SeAgregoAFNUnionLexico){
                this.EdosAFN.clear();
                this.Alfabeto.clear();
                e = new Estado();
                e.setTrans(new Transicion(SimbolosEspeciales.EPSILON,f.EdoIni));
                this.EdoIni = e;
                this.EdosAFN.add(e);
                this.SeAgregoAFNUnionLexico = true;
           }
           else this.EdoIni.setTrans(new Transicion(SimbolosEspeciales.EPSILON,f.EdoIni));
           
           for(Estado EdoAcep : f.EdosAcept) EdoAcep.setToken(Token);
           this.EdosAcept.addAll(f.EdosAcept);
           this.EdosAFN.addAll(f.EdosAFN);
           this.Alfabeto.addAll(f.Alfabeto);
       }
       
       public AFD ConvAFNaAFD(){
           //Tipo BFS modificada, método de subconjuntos, dificil de implementar
           //Ver AFN como si fuese grafo dirigido para entenderle mejor
           
           
           AFD ans = new AFD(); // Lo que voy a regresar
           Sj S0 = new Sj();
           int i=0,j=0;
           boolean transicion=false; //Nunca usé esto al final
           Queue<HashSet<Estado>> cola = new LinkedList<>(); //Necesario para la BFS
           HashSet<Sj> conjunto_nodos_sj = new HashSet<>(); //Guardar los Sj para la conversión
           HashSet<Estado> ya_lo_visite = new HashSet<>(); //Para ver si ya tengo este conjunto de estados
           HashSet<HashSet<Estado>> ConjDeEdosVisitados = new HashSet<>(); //Conjunto de conjutos para ver si un conjunto ya lo tengo
           S0.setEstados(this.CerraduraEpsilon(this.EdoIni)); //Agrego en s0 el conjunto de aplicar la cerradura epsilon
           //Hago clear a todo csm
           cola.clear(); conjunto_nodos_sj.clear(); ya_lo_visite.clear();
           ConjDeEdosVisitados.clear();
           
           //Ahora para S0 reviso si tiene estados de aceptacion, y si tiene le pongo a S0 el token correspondiente de esto
           for(Estado e:this.EdosAcept){
               if(S0.getEstados().contains(e)){
                   S0.setToken(e.getToken());
               }
           }
           
           S0.setId(i); //Le agrego la id para despues ocuparla
           
           
           i++; //Aumento la id que será la llave en mi unordered_map
           
           conjunto_nodos_sj.add(S0);   //agrego a la respuesta el estado S0
           
           //Agrego a la cola para hacer la BFS el S0.estados
           
           cola.add(S0.getEstados());
           
           //Empieza BFS
           
           ConjDeEdosVisitados.add(S0.getEstados());
           
           j=i; //Esto nunca lo usé tampoco al final
           while(!cola.isEmpty()){
               //Saco el conjunto de la cola para analizarlo y ver si tiene transiciones
               HashSet<Estado> aux_Estados = cola.poll();
               //para cada caracter reviso si hay transicion
               for(char c : this.Alfabeto){
                   //me muevo
                   ya_lo_visite = (this.Ir_A(aux_Estados, c));
                   //Me regresa un conjunto de nodos, ahora tengo que ver lo siguiente:
                   //1.- Si no están en mi conjunto de conjunto, significa que es un estado nuevo, por lo que tengo que
                   //guardar de donde vengo (buscar el origen en el conjunto en el conjunto_de_nodos_Sj), almacenar a donde voy
                   //agregar nueva fila a la tabla y revisar si es edo de aceptacion, finalmente agregar este estado a la cola
                   
                   //2.- Si ya lo visite, agregar la transicion nada más
                   
                   //3.- Caso especial, si es vacío, entonces agregar una fila de puros -1 a la respuesta final  
                   if(!ConjDeEdosVisitados.contains(ya_lo_visite) && !ya_lo_visite.isEmpty()){
                       //Si este estado es nuevo entonces crea un Saux y agregalo al conjunto de Sj's
                       //System.out.println("Prueba"+c);
                       //Le agrego id, como es nuevo le aumento el i
                       Sj Saux = new Sj();
                       
                       Saux.setId(i++);
                       
                       Saux.setEstados(ya_lo_visite);
                       
                       //Hay que revisar si este conjunto contiene estados de aceptación y en caso de que tenga poner el token correspondiente
                       for(Estado e:this.EdosAcept){
                            if(Saux.getEstados().contains(e)){
                                Saux.setToken(e.getToken());
                            }
                        }
                       //Agrego a mi conjunto Sj el Saux para la conversión
                       conjunto_nodos_sj.add(Saux);
                       
                       //Meto a la queue para continuar con la BFS, ya que este es el caso donde es conjunto nuevo
                       cola.add(Saux.getEstados());
                       
                       //Variable auxiliar, el mismo nombre es descriptivo
                       int de_donde_vengo=1; 
                       
                       //Busco en mi conjunto de donde vngo y regreso la id para armar la tabla
                       for(Sj aux1 : conjunto_nodos_sj){
                           if(aux1.getEstados().equals(aux_Estados)){
                               de_donde_vengo = aux1.getId();
                           }
                       }
                       
                       //agrego al conjunto de conjuntos el conjunto resultado de moverme con este caracter
                       ConjDeEdosVisitados.add(ya_lo_visite);
                       
                       
                       //Inserto en la tabla de conversión la transición hacia el nuevo estado creado con el caracter correspondiente
                       ans.insertaAFDenTabla(de_donde_vengo, c, Saux.getId());
                       
                       transicion = true;
                       
                   }
                   else if(ConjDeEdosVisitados.contains(ya_lo_visite) && !ya_lo_visite.isEmpty()){
                       //si ya tengo este estado
                       int de_donde_vengo=1;
                       
                       //Busco desde qué conjunto vengo para insertar la transición
                       for(Sj aux1 : conjunto_nodos_sj){
                           if(aux1.getEstados().equals(aux_Estados)){
                               de_donde_vengo = aux1.getId();
                           }
                       }
                       
                       int a_donde_voy=1;
                       //Busco hacia donde tengo que moverme para insertar la transición en la tabla de conversión
                       for(Sj aux1 : conjunto_nodos_sj){
                           if(aux1.getEstados().equals(ya_lo_visite)){
                               a_donde_voy = aux1.getId();
                           }
                       }
                       
                       //Inserto la transición
                        ans.insertaAFDenTabla(de_donde_vengo, c, a_donde_voy);
                        transicion = true;
                   }
                   
               }
           }
           
           //Este es el caso de cuando llego a un estado que es de aceptación y no tiene transiciones, para esto
           //creo un arreglo de puros -1 y agrego el token correspondiente a cada estado de aceptación.
           for(Sj aux1 : conjunto_nodos_sj){
                    //Si no estás en mi unordered_map es porque caes en el caso de que no tienes transiciones y todos tus 255 caracteres
                    //ascii no tienen transición, por lo que hay que crear un array de puros -1 y agregarlo al unordered_map con la id
                    //correspondiente
                   if(!ans.getTabla().containsKey(aux1.getId())) ans.insertaAFDenTabla(aux1.getId());
                   //if(aux1.getToken() != -1) System.out.println(aux1.getId() + " " + aux1.getToken());
                   //reviso si este conjunto es de aceptación y en caso de serlo pongo el token correspondiente
                   if(aux1.getToken() != -1){
                       ans.setTokenTabla(aux1.getId(),aux1.getToken());
                   }
           }
           //System.out.println("Numero de conjuntos Sj: "+conjunto_nodos_sj.size());
           
           
           //System.out.println("Tamaño de el hashmap: "+ya_lo_tengo1.size());
           //Regreso la respuesta después de terminar la transformación
           return ans;
       }
       
}
    

