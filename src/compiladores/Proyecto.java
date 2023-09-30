package compiladores;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableModel;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
public class Proyecto {
    public static HashMap<String,Matriz> a = new HashMap<>(); //Para las matrices, no la mejor práctica de programación, pero funciona
    public static void main(String[] args){
 /*
Ya jala entonces se queda así, todo en el main
 */
//-----------------------------------------------------------------------------------------------
                        //Frame principal para el menú
        AFN afn = new AFN();
        AFD afd = new AFD();
        JFrame frame = new JFrame("Proyecto primer parcial");       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        frame.setSize(600, 600);         
        frame.setLocationRelativeTo(null);
        JMenuBar mb = new JMenuBar();       
        JMenu m1 = new JMenu("AFN's");       
        JMenu m2 = new JMenu("Análisis sintáctico");       
        mb.add(m1);       
        mb.add(m2);       
        JMenuItem m11 = new JMenuItem("Básico");       
        JMenuItem m22 = new JMenuItem("Unir");
        JMenuItem m33 = new JMenuItem("Concatenar");
        JMenuItem m44 = new JMenuItem("Cerradura +");
        JMenuItem m55 = new JMenuItem("Cerradura *");
        JMenuItem m66 = new JMenuItem("Opcional");
        JMenuItem m77 = new JMenuItem("ER -> AFN");
        JMenuItem m88 = new JMenuItem("Union para analizador léxico");
        JMenuItem m99 = new JMenuItem("AFN -> AFD");
        JMenuItem m111 = new JMenuItem("Probar analizador léxico");
        JMenuItem m122 = new JMenuItem("Probar analizador sintáctico para matrices");
        JMenuItem m133 = new JMenuItem("Probar analizador sintáctico para gramáticas");
        JMenuItem m144 = new JMenuItem("Probar analizador LL(1)");
        m1.add(m11);       
        m1.add(m22);
        m1.add(m33); 
        m1.add(m44); 
        m1.add(m55); 
        m1.add(m66); 
        m1.add(m77); 
        m1.add(m88); 
        m1.add(m99); 
        m1.add(m111);  
        m2.add(m122);  
        m2.add(m133);
        m2.add(m144);

        // Área de texto en el centro    
        //JTextArea ta = new JTextArea();        

        // Agregar componentes al marco.      
        //frame.getContentPane().add(BorderLayout.SOUTH, panel);       
        frame.getContentPane().add(BorderLayout.NORTH, mb);       
        //frame.getContentPane().add(BorderLayout.CENTER, ta);       
        frame.setVisible(true); 
//-----------------------------------------------------------------------------------------------
        //Boton de crear nuevo AFN básico
        m11.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frameAFNBasico = new JFrame("Creación de un AFN básico");
                JLabel ci = new JLabel("Caracter inferior");
                JLabel cs = new JLabel("Caracter superior");
                JLabel nm = new JLabel("Id AFN");
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.5);
                int h = (int)((double)r.width*0.3);
                frameAFNBasico.setSize(w, h);
                frameAFNBasico.setLocationRelativeTo(null);
                JTextField inf = new JTextField(1);
                JTextField sup = new JTextField(1);
                JTextField num = new JTextField(1);
                JCheckBox check1 =new JCheckBox("Valor en decimal");
                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();
                JPanel panel4 = new JPanel();
                JPanel panel5 = new JPanel();
                JButton Boton = new JButton("Crear");
                panel1.add(ci);
                panel1.add(inf);
                panel2.add(cs);
                panel2.add(sup);
                panel3.add(nm);
                panel3.add(num);
                panel4.add(Boton);
                panel5.add(check1);
                //frameAFNBasico.add(cs);
                //frameAFNBasico.add(sup);
                //frameAFNBasico.add(Boton);
                //panel1.setSize(250, 20);
                frameAFNBasico.add(panel1);
                frameAFNBasico.add(panel2);
                frameAFNBasico.add(panel3);
                frameAFNBasico.add(panel5);
                frameAFNBasico.add(panel4);
                //inf.setSize(399, 400);
                //Hacemos un grid para que se vea mejor el menú
                frameAFNBasico.setLayout(new GridLayout(5,1));
                frameAFNBasico.setVisible(true);
                //Para cuando se presione el boton de crear
                Boton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                String s = e.getActionCommand();
                if(s.equals("Crear")){
                    if(check1.isSelected()==false){
                        String inf1 = inf.getText();
                        String sup1 = sup.getText();
                        String id = num.getText();
                        if(!inf1.isEmpty() && !sup1.isEmpty() && !id.isEmpty()){
                            AFN temp = new AFN();
                            temp.idAFN = Integer.parseInt(id);
                            temp.crearAFNBasico(inf1.charAt(0), sup1.charAt(0));
                            afn.ConjDeAFNs.add(temp);
                            JOptionPane.showMessageDialog(frameAFNBasico, "AFN creado");
                            frameAFNBasico.dispatchEvent(new WindowEvent(frameAFNBasico,WindowEvent.WINDOW_CLOSING));
                            //Cierra después de crear el AFN
                        }
                        else if((!inf1.isEmpty() || !sup1.isEmpty()) && !id.isEmpty()){
                            //Creo uno temporal para asignarle la id y luego meterlo a el conjunto
                            AFN temp = new AFN();
                            temp.idAFN = Integer.parseInt(id);
                            //Operador ternario, pregunta si el inf es null, si sí entonces el otro no es null
                            temp.crearAFNBasico((inf1.isEmpty()) ? sup.getText().charAt(0) : inf.getText().charAt(0));
                            afn.ConjDeAFNs.add(temp);
                            JOptionPane.showMessageDialog(frameAFNBasico, "AFN creado");
                            frameAFNBasico.dispatchEvent(new WindowEvent(frameAFNBasico,WindowEvent.WINDOW_CLOSING));
                            //Cierra después de crear el AFN
                        }
                        else{
                        JOptionPane.showMessageDialog(frameAFNBasico, "AFN NO creado","Error",JOptionPane.ERROR_MESSAGE);
                        //Muestra error si no hay nada a la entrada
                        }
                }
                    else{
                            String id = num.getText();
                            String inf1 = inf.getText();
                            AFN temp = new AFN();
                            temp.idAFN = Integer.parseInt(id);
                            temp.crearAFNBasico((char)Integer.parseInt(inf1));
                            afn.ConjDeAFNs.add(temp);
                            JOptionPane.showMessageDialog(frameAFNBasico, "AFN creado");
                            frameAFNBasico.dispatchEvent(new WindowEvent(frameAFNBasico,WindowEvent.WINDOW_CLOSING));
                    }
                }
                }
                });
            }
        });
//-----------------------------------------------------------------------------------------------
//              Boton para unir 2 AFNs
        m22.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame Unir = new JFrame("Unir");
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.5);
                int h = (int)((double)r.width*0.3);
                Unir.setSize(w,h);
                Unir.setLocationRelativeTo(null);
                JLabel l1 = new JLabel("Seleccione un AFN");
                JLabel l2 = new JLabel("Seleccione un AFN");

                JComboBox lista1 = new JComboBox();
                JComboBox lista2 = new JComboBox();
                for(AFN a : afn.ConjDeAFNs){ 
                    lista1.addItem(a);
                    lista2.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Unir");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                p1.add(l1); p1.add(lista1);
                p2.add(l2); p2.add(lista2);
                p3.add(boton);
                Unir.add(p1); Unir.add(p2); Unir.add(p3);
                Unir.setLayout(new GridLayout(5,1));
                Unir.setVisible(true);
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        //Si se presionó el botón, entonces has la unión, para eso crea un AFN nuevo de la union y borra los otros
                        for(AFN a: afn.ConjDeAFNs){
                            AFN test = (AFN)lista1.getSelectedItem();
                            if(a.idAFN == test.idAFN){
                                //System.out.println(a);
                                AFN temp2 = a.UnirAFN((AFN)lista2.getSelectedItem());
                                temp2.idAFN = a.idAFN;
                                afn.ConjDeAFNs.remove((AFN)lista1.getSelectedItem());
                                afn.ConjDeAFNs.remove((AFN)lista2.getSelectedItem());
                                afn.ConjDeAFNs.add(temp2);
                                JOptionPane.showMessageDialog(Unir, "Unión exitosa");
                                break;
                            }
                        }
                        Unir.dispatchEvent(new WindowEvent(Unir,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });
 //-----------------------------------------------------------------------------------------------       
        //Boton para concatenar 2 AFN
        m33.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame Concatenar = new JFrame("Concatenar");
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.5);
                int h = (int)((double)r.width*0.3);
                Concatenar.setSize(w,h);
                Concatenar.setLocationRelativeTo(null);
                JLabel l1 = new JLabel("Seleccione un AFN");
                JLabel l2 = new JLabel("Seleccione un AFN");

                JComboBox lista1 = new JComboBox();
                JComboBox lista2 = new JComboBox();
                for(AFN a : afn.ConjDeAFNs){ 
                    lista1.addItem(a);
                    lista2.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Concatenar");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                p1.add(l1); p1.add(lista1);
                p2.add(l2); p2.add(lista2);
                p3.add(boton);
                Concatenar.add(p1); Concatenar.add(p2); Concatenar.add(p3);
                Concatenar.setLayout(new GridLayout(5,1));
                Concatenar.setVisible(true);
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        //Si se presionó el botón, entonces has la unión, para eso crea un AFN nuevo de la union y borra los otros
                        for(AFN a: afn.ConjDeAFNs){
                            AFN test = (AFN)lista1.getSelectedItem();
                            if(a.idAFN == test.idAFN){
                                //System.out.println(a);
                                AFN temp2 = a.ConcAFN((AFN)lista2.getSelectedItem());
                                temp2.idAFN = a.idAFN;
                                afn.ConjDeAFNs.remove((AFN)lista1.getSelectedItem());
                                afn.ConjDeAFNs.remove((AFN)lista2.getSelectedItem());
                                afn.ConjDeAFNs.add(temp2);
                                JOptionPane.showMessageDialog(Concatenar, "Concatenación exitosa");
                                break;
                            }
                        }
                        Concatenar.dispatchEvent(new WindowEvent(Concatenar,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });
//-----------------------------------------------------------------------------------------------
        //Boton para cerradura positiva 
        m44.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame CerPos = new JFrame("Cerradura positiva");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.5);
                int h = (int)((double)r.width*0.3);
                CerPos.setSize(w,h);
                CerPos.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Seleccione un AFN");

                JComboBox lista1 = new JComboBox();
                for(AFN a : afn.ConjDeAFNs){ 
                    lista1.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Aceptar");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                p2.add(l2);
                p1.add(lista1);
                p3.add(boton);
                CerPos.add(p2); CerPos.add(p1); CerPos.add(p3);
                CerPos.setLayout(new GridLayout(5,1));
                CerPos.setVisible(true);
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        for(AFN a: afn.ConjDeAFNs){
                            AFN test = (AFN)lista1.getSelectedItem();
                            if(a.idAFN == test.idAFN){
                                //System.out.println(a);
                                AFN temp2 = a.CerrPos();
                                temp2.idAFN = a.idAFN;
                                afn.ConjDeAFNs.remove((AFN)lista1.getSelectedItem());
                                afn.ConjDeAFNs.add(temp2);
                                JOptionPane.showMessageDialog(CerPos, "Cerradura positiva exitosa");
                                break;
                            }
                        }
                        CerPos.dispatchEvent(new WindowEvent(CerPos,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });
//-----------------------------------------------------------------------------------------------
        //Boton para cerradura de kleen 
        m55.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame CerKleen = new JFrame("Cerradura de Kleen");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.5);
                int h = (int)((double)r.width*0.3);
                CerKleen.setSize(w,h);
                CerKleen.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Seleccione un AFN");

                JComboBox lista1 = new JComboBox();
                for(AFN a : afn.ConjDeAFNs){ 
                    lista1.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Aceptar");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                p2.add(l2);
                p1.add(lista1);
                p3.add(boton);
                CerKleen.add(p2); CerKleen.add(p1); CerKleen.add(p3);
                CerKleen.setLayout(new GridLayout(5,1));
                CerKleen.setVisible(true);
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        for(AFN a: afn.ConjDeAFNs){
                            AFN test = (AFN)lista1.getSelectedItem();
                            if(a.idAFN == test.idAFN){
                                //System.out.println(a);
                                AFN temp2 = a.CerrKleen();
                                temp2.idAFN = a.idAFN;
                                afn.ConjDeAFNs.remove((AFN)lista1.getSelectedItem());
                                afn.ConjDeAFNs.add(temp2);
                                JOptionPane.showMessageDialog(CerKleen, "Cerradura de Kleen aplicada con éxito");
                                break;
                            }
                        }
                        CerKleen.dispatchEvent(new WindowEvent(CerKleen,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });     
 
 //-----------------------------------------------------------------------------------------------
        //Boton para cerradura opcional
        m66.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame CerOp = new JFrame("Cerradura opcional");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.5);
                int h = (int)((double)r.width*0.3);
                CerOp.setSize(w,h);
                CerOp.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Seleccione un AFN");

                JComboBox lista1 = new JComboBox();
                for(AFN a : afn.ConjDeAFNs){ 
                    lista1.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Aceptar");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                p2.add(l2);
                p1.add(lista1);
                p3.add(boton);
                CerOp.add(p2); CerOp.add(p1); CerOp.add(p3);
                CerOp.setLayout(new GridLayout(5,1));
                CerOp.setVisible(true);
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        for(AFN a: afn.ConjDeAFNs){
                            AFN test = (AFN)lista1.getSelectedItem();
                            if(a.idAFN == test.idAFN){
                                //System.out.println(a);
                                AFN temp2 = a.CerrOpcional();
                                temp2.idAFN = a.idAFN;
                                afn.ConjDeAFNs.remove((AFN)lista1.getSelectedItem());
                                afn.ConjDeAFNs.add(temp2);
                                JOptionPane.showMessageDialog(CerOp, "Cerradura opcional aplicada con éxito");
                                break;
                            }
                        }
                        CerOp.dispatchEvent(new WindowEvent(CerOp,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });
        
 //-----------------------------------------------------------------------------------------------     
        //Boton para obtener el AFN de una ER 
        m77.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame ExpReg = new JFrame("Obtener AFN de una expresión regular");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.8);
                int h = (int)((double)r.width*0.3);
                ExpReg.setSize(w,h);
                ExpReg.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Ingrese una expresión regular");

                //JComboBox lista1 = new JComboBox();
                /*for(AFN a : afn.ConjDeAFNs){ 
                    lista1.addItem(a);
                }*/
                JTextField expreg = new JTextField(35);
                JLabel nm = new JLabel("Id AFN");
                JTextField num = new JTextField(3);
                //lista.addItemListener(this);
                JButton boton = new JButton("Aceptar");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p4 = new JPanel();
                JPanel p3 = new JPanel();
                p2.add(l2);
                p1.add(expreg);
                p4.add(nm); p4.add(num);
                p3.add(boton);
                ExpReg.add(p2); ExpReg.add(p1); ExpReg.add(p4); ExpReg.add(p3);
                ExpReg.setLayout(new GridLayout(5,1));
                ExpReg.setVisible(true);
                
                
                AFD nuevo_afd = new AFD();
                        int idAFDs = afd.ConjDeAFDs.size()+1;
                        String ruta_archivo = System.getProperty("user.dir")+"/AFD_Regex.txt";      //Para que jale en cualquier PC.
                        File fichero = new File(ruta_archivo);                                  //Cargamos archivo de forma automatica al iniciar
                        
                        try(FileReader fr = new FileReader(fichero)){
                                        int idSj; int[] arregloX = new int[258];
                                        
                                        String cadena = "";
                                        String buffer = "";
                                        int contador = 0;
                                        int valor = fr.read();
                                        //Scanner scan = new Scanner(System.in);
                                        while(valor != -1){
                                            cadena+= (char) valor;
                                            
                                            
                                            if((char)valor == '\n'){
                                                //System.out.println(buffer);
                                                for(int i =0;i<cadena.length();i++){
                                          
                                                    if(cadena.charAt(i) == ' ' || cadena.charAt(i) == '\n' ||(int) cadena.charAt(i) == 13){
                                                        if(!buffer.equals("")) arregloX[contador] = Integer.parseInt(buffer);
                                                        //System.out.print(i+" ");
                                                        contador++;
                                                        buffer="";
                                                    }
                                                    else if(((cadena.charAt(i)-'0') >= 0) || ((cadena.charAt(i)-'0') <= 9) || (cadena.charAt(i) == '-')) buffer+=cadena.charAt(i);
                                                    
                                                }
                                                nuevo_afd.insertaAFDenTabla(arregloX[0]);
                                                for(int i=1;i<=256;i++) nuevo_afd.insertaAFDenTabla(arregloX[0],i-1,arregloX[i]);
                                                nuevo_afd.setTokenTabla(arregloX[0], arregloX[257]);
                                                //System.out.println(Arrays.toString(arregloX)); System.out.println("Final:  "+arregloX[257]+" ");
                                                cadena="";
                                                contador = 0;
                                                
                                                //System.out.println("HOla");
                                            }
                                            
                                            valor = fr.read();
                                        }
                                        //System.out.println(cadena);
                                        
                                        nuevo_afd.setIdAFD(100);
                                        //afd.ConjDeAFDs.add(nuevo_afd);
                                        
                                            /*for (HashMap.Entry<Integer, int[]> set : nuevo_afd.getTabla().entrySet()) {
                                                    int [] temp1 = new int[260];
                                                    temp1 = set.getValue();
                                                    System.out.print(set.getKey()+ " ");
                                                    for(int i=0;i<257;i++){ 
                                                        System.out.print(temp1[i]+" ");
                                                    }
                                                    System.out.println("");
                                                }
                                            */
                                       //System.out.println(cadena);
                                    } catch(IOException el){
                                        el.printStackTrace();
                                    }
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        //afd.ConjDeAFDs.remove(nuevo_afd);                       //Una vez creado bórralo para que no haya problemas
                        //System.out.println(afd.ConjDeAFDs.size());
                        AnalizadorLexico temp = new AnalizadorLexico(nuevo_afd,expreg.getText());
                        ClaseEvalRegex nuevoAFN = new ClaseEvalRegex(temp);
                        AFN ans = new AFN();
                        String id_delAFN = num.getText();
                        ans.idAFN = Integer.parseInt(id_delAFN);
                        
                        if(nuevoAFN.iniEvalRegex(ans)){
                            afn.ConjDeAFNs.add(ans);
                            JOptionPane.showMessageDialog(ExpReg, "AFN creado con éxito en base a una expresión regular");

                        }
                        else JOptionPane.showMessageDialog(ExpReg, "AFN NO creado","Error",JOptionPane.ERROR_MESSAGE);
                        ExpReg.dispatchEvent(new WindowEvent(ExpReg,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });     
 //----------------------------------------------------------------------------------------------- 
        //Menu de union para analizador lexico
        m88.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame UniLex = new JFrame("Union para analizador lexico");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int numAFNs = afn.ConjDeAFNs.size()/10+1;
                int w = (int)((double)r.height*0.8);
                int h = (int)((double)r.width*0.8*numAFNs);
                UniLex.setSize(w,h);
                UniLex.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Seleccione los AFNs a unir");
                JLabel nm = new JLabel("Id del nuevo AFN");
                JTextField num = new JTextField(1);
               // JComboBox lista1 = new JComboBox();
                
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                JPanel p1 = new JPanel();
                JPanel p4 = new JPanel();
                p1.add(new JLabel("Id AFN\t\t\t\t"));
                p1.add(new JLabel("Token\t"));
                UniLex.add(p1);
                HashMap<Integer,JCheckBox> checkboxes = new HashMap<>();
                HashMap<Integer,JTextField> tokens = new HashMap<>();
                for(AFN a : afn.ConjDeAFNs){ 
                    JPanel tem = new JPanel();
                    JCheckBox item = new JCheckBox(Integer.toString(a.idAFN),false);
                    JTextField tok = new JTextField(5);
                    checkboxes.put(a.idAFN, item);
                    tokens.put(a.idAFN, tok);
                    tem.add(item);
                    tem.add(tok);
                     UniLex.add(tem);
                }
                JButton boton = new JButton("Unir");
                p2.add(l2);
                p4.add(nm);
                p4.add(num);
                p3.add(boton);
                UniLex.add(p2); UniLex.add(p4); UniLex.add(p3);
                UniLex.setLayout(new GridLayout(15,20));
                UniLex.setVisible(true);
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        ArrayList<Integer> AFNs = new ArrayList<>();
                        HashMap<Integer,Integer> Tokens = new HashMap<>();
                        for(AFN a : afn.ConjDeAFNs){
                            if(checkboxes.containsKey(a.idAFN)){
                                if(checkboxes.get(a.idAFN).isSelected()){
                                    //System.out.println(Integer.toString(a.idAFN));
                                    AFNs.add(a.idAFN);
                                    Tokens.put(a.idAFN, Integer.parseInt(tokens.get(a.idAFN).getText())); //Meto los tokens a un hasmap
                                }
                                //else System.out.println(Integer.toString(a.idAFN));
                            }
                        }
                        String id = num.getText();
                        AFN base = new AFN();
                        
                            for(int i : AFNs){
                                for(AFN a : afn.ConjDeAFNs){
                                    if(i == a.idAFN){
                                        base.UnionEspecialAFNs(a, Tokens.get(a.idAFN));
                                        afn.ConjDeAFNs.remove(a);
                                        break;
                                    }
                                }
                            }
                        base.idAFN=Integer.parseInt(id);
                        afn.ConjDeAFNs.add(base);
                        //System.out.println(base.Alfabeto);
                        //AFD convertido = new AFD();
                        //convertido = base.ConvAFNaAFD();
                        
                        //afd.ConjDeAFDs.add(convertido);
                        
                        //for(char g = 0;g<256;g++) System.out.print(g+" ");
                        
                        //System.out.println("\nTamaño");
                        //System.out.println(convertido.getTabla().size());
                        /*
                        int cnt = afd.ConjDeAFDs.size();
                        try {
                            PrintWriter out = new PrintWriter("AFD"+ cnt+".txt");
                            
                            for (HashMap.Entry<Integer, int[]> set : convertido.getTabla().entrySet()) {
                            int [] temp1 = new int[260];
                            temp1 = set.getValue();
                            out.print(set.getKey()+ " ");
                            for(int i=0;i<256;i++) out.print(temp1[i]+", ");
                            out.print(temp1[256]);
                            out.println("");
                            
                        }
                            out.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        
                        for (HashMap.Entry<Integer, int[]> set : convertido.getTabla().entrySet()) {
                            int [] temp1 = new int[260];
                            temp1 = set.getValue();
                            System.out.print(set.getKey()+ " ");
                            for(int i=0;i<257;i++) System.out.print(temp1[i]+" ");
                            System.out.println("");
                        }
                        
                        */
                        JOptionPane.showMessageDialog(UniLex, "Union Especial realizada con éxito");
                        
                        UniLex.dispatchEvent(new WindowEvent(UniLex,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });
 //-----------------------------------------------------------------------------------------------   
 //Boton para transformar de AFN a AFD
        m99.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame Conversion = new JFrame("Conversión AFN -> AFD");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height);
                int h = (int)((double)r.width);
                Conversion.setSize(w,h);
                Conversion.setLocationRelativeTo(null);
                JLabel l1 = new JLabel("Seleccione un AFN");
                JLabel l2 = new JLabel("Ingrese la id del AFD");
                JComboBox lista1 = new JComboBox();
                for(AFN a : afn.ConjDeAFNs){ 
                    lista1.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Convertir");
                JPanel p1 = new JPanel();
                JPanel p3 = new JPanel();
                JPanel p4 = new JPanel();
                JTextField idAFDs = new JTextField(3);
                p1.add(l1); p1.add(lista1);
                p3.add(l2); p3.add(idAFDs); p3.add(boton);
                Conversion.add(p1);  Conversion.add(p3);
                //.add(p4);
                Conversion.setLayout(new GridLayout(4,2));
                
                
                
                Object[] colnom = new Object[257];
                
                for(int l=0;l<257;l++) colnom[l] = l;
                
                Object[][] data = new Object[10][257];
                for(int l=0;l<10;l++) for(int k=0;k<257;k++) data[l][k] = k;
                
                //JTable tabla = new JTable(data,colnom);
                
                //Conversion.add(new JScrollPane(tabla));
                
                Conversion.setVisible(true);
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        //Si se presionó el botón, entonces has la transformación
                        for(AFN a: afn.ConjDeAFNs){
                            AFN test = (AFN)lista1.getSelectedItem();
                            if(a.idAFN == test.idAFN){
                                //System.out.println(a);
                                AFD temp2 = a.ConvAFNaAFD();
                                
                                int estaeslaidchida = (idAFDs.getText().isEmpty() ) ? afd.ConjDeAFDs.size()+1 : Integer.parseInt(idAFDs.getText());
                                temp2.setIdAFD(estaeslaidchida);
                                afd.ConjDeAFDs.add(temp2);
                                int cnt = afd.ConjDeAFDs.size();
                        try {
                            PrintWriter out = new PrintWriter("AFD"+ estaeslaidchida+".txt");
                            
                            for (HashMap.Entry<Integer, int[]> set : temp2.getTabla().entrySet()) {
                            int [] temp1 = new int[260];
                            temp1 = set.getValue();
                            out.print(set.getKey()+ " ");
                            for(int i=0;i<256;i++) out.print(temp1[i]+" ");
                            out.print(temp1[256]);
                            out.println("");
                            
                        }
                            out.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        String[] nombre_columnas = new String[258];
                        nombre_columnas[0] = "Id Sj";
                        for(int ii =1;ii<=256;ii++){
                                nombre_columnas[ii] = String.valueOf((char) (ii-1)); 
                        }
                        nombre_columnas[257] = "Token";
                        
                        Object[][] data = new Object[temp2.getTabla().size()][258];
                        
                        
                        int ii = 0;
                        
                        for (HashMap.Entry<Integer, int[]> set : temp2.getTabla().entrySet()) {
                            
                            
                            int [] temp1 = new int[260];
                            temp1 = set.getValue();
                            System.out.print(set.getKey()+ " ");
                            data[ii][0] = set.getKey();
                            for(int i=0;i<257;i++){ 
                                data[ii][i+1] = Integer.toString(temp1[i]);
                                System.out.print(temp1[i]+" ");
                            }
                            ii++;
                            System.out.println("");
                        }
                        
                       JTable tabla = new JTable(data,nombre_columnas);
                       tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                       JScrollPane panelDeslizable = new JScrollPane(tabla);
                       tabla.setDefaultEditor(Object.class, null);
                       panelDeslizable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                       panelDeslizable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                       
                       //p4.add(panelDeslizable);
                       
                       Conversion.remove(p3);
                       Conversion.remove(p1);
                       Conversion.add(panelDeslizable,BorderLayout.CENTER);
                       JButton btn = new JButton("Regresar");
                       p4.add(btn);
                       Conversion.add(p4);
                       btn.addActionListener(new ActionListener(){
                           @Override
                    public void actionPerformed(ActionEvent e){
                                Conversion.dispatchEvent(new WindowEvent(Conversion,WindowEvent.WINDOW_CLOSING));
                            }   
                       });
                       
                       
                                break;
                            }
                        }
                        
                        JOptionPane.showMessageDialog(Conversion, "Conversión AFN->AFD realizada con éxito");
                        Conversion.setVisible(true);
                        //Conversion.dispatchEvent(new WindowEvent(Conversion,WindowEvent.WINDOW_CLOSING));
                    }
                });   
                
            }
        });
        
 //----------------------------------------------------------------------------------------------- 
                //Boton para analizador lexico
        m111.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame AnalLex = new JFrame("Probar analizador léxico");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.9);
                int h = (int)((double)r.width*0.9);
                AnalLex.setSize(w,h);
                AnalLex.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Seleccione un AFD");
                JButton boton_a = new JButton("Cargar AFD desde un archivo");
                JLabel l3 = new JLabel("Ingrese la id del AFD");
                JLabel l4 = new JLabel("Ingrese la cadena");
                JFileChooser archivo_pa_cargar_AFD = new JFileChooser();
                JTextField idAFDs = new JTextField(3);
                JComboBox lista1 = new JComboBox();
                for(AFD a : afd.ConjDeAFDs){ 
                    lista1.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Empezar análisis");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                JPanel p4 = new JPanel();
                JPanel p5 = new JPanel();
                JPanel p6 = new JPanel();
                JTextArea cadena = new JTextArea(5,20);
                cadena.setLineWrap(true);
                JScrollPane panel = new JScrollPane(cadena);
                //cadena.s
                //cadena.setBounds(200, 80, 15, 20);
                //cadena.setPreferredSize(new Dimension(50,50));
                p2.add(l2);
                p1.add(lista1);
                p3.add (l3); p3.add(idAFDs); p3.add(boton_a); p5.add(boton);
                p4.add(l4); p4.add(panel);
                AnalLex.add(p2); AnalLex.add(p1); AnalLex.add(p3);
                AnalLex.add(p4); AnalLex.add(p5);//AnalLex.add(cadena);
                //AnalLex.setLayout(new FlowLayout());
                AnalLex.setLayout(new GridLayout(5,200));
                AnalLex.setVisible(true);
                
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        for(AFD a: afd.ConjDeAFDs){
                            AFD test = (AFD)lista1.getSelectedItem();
                            if(a.getIdAFD() == test.getIdAFD() && !cadena.getText().isEmpty()){

                                AnalizadorLexico analizador = new AnalizadorLexico(a,cadena.getText());
                                String cadena_aux = cadena.getText();
                                while(analizador.yylex() != 0){
                                    analizador.guardarLexemas.add(new Pair<>(analizador.getLexema(),analizador.getToken()));
                                    System.out.println("Lexema: "+analizador.getLexema()+" Token: "+analizador.getToken());
                                }
                                
                                String[] nombre_columnas = new String[2];
                                nombre_columnas[0] = "Lexema"; nombre_columnas[1] = "Token";
                                Object[][] data = new Object[analizador.guardarLexemas.size()][2];
                                int i=0;
                                for(Pair<String,Integer> k : analizador.guardarLexemas){
                                    data[i][0] = k.getL();
                                       data[i][1] = k.getR();
                                       i++;
                            }
                                
                       JTable tabla = new JTable(data,nombre_columnas);
                       TableColumn column = null;
                       column = tabla.getColumnModel().getColumn(0);
                       column.setPreferredWidth(400);
                       tabla.setDefaultEditor(Object.class, null);
                       tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                       JScrollPane panelDeslizable = new JScrollPane(tabla);
                       
                       panelDeslizable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                       panelDeslizable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        AnalLex.remove(p1); AnalLex.remove(p2); AnalLex.remove(p3);
                        AnalLex.remove(p4); AnalLex.remove(p5);
                        AnalLex.setVisible(false);
                        
                        JLabel texto_cadena = new JLabel("Cadena ingresada a analizar:");
                        JPanel panelprueba = new JPanel();
                        panelprueba.add(texto_cadena);
                        JTextArea cadena1 = new JTextArea(5,40);
                        cadena1.setLineWrap(true);
                        cadena1.setText(cadena_aux);
                        JScrollPane panel1 = new JScrollPane(cadena1);
                        panelprueba.add(panel1);
                        cadena1.setEditable(false);
                        AnalLex.add(panelprueba);
                        
                        
                        JLabel texto_fin = new JLabel("Resultado del análisis:");
                        JPanel panelprueba1 = new JPanel();
                        panelprueba1.add(texto_fin);
                         AnalLex.add(panelprueba1);
                         
                        AnalLex.add(panelDeslizable,BorderLayout.CENTER);
                        
                       
                       JButton btn = new JButton("Regresar");
                       p6.add(btn);
                       AnalLex.add(p6);
                       btn.addActionListener(new ActionListener(){
                           @Override
                    public void actionPerformed(ActionEvent e){
                                AnalLex.dispatchEvent(new WindowEvent(AnalLex,WindowEvent.WINDOW_CLOSING));
                            }   
                       });         
                                
                                break;
                            }
                        }

                        AnalLex.setLayout(new GridLayout(4,200));
                        AnalLex.setVisible(true);
                        JOptionPane.showMessageDialog(AnalLex, "Análisis léxico realizado con éxito");
                        
                        //AnalLex.dispatchEvent(new WindowEvent(AnalLex,WindowEvent.WINDOW_CLOSING));
                    }
                });
               //Esta parte es la de agregar un AFD desde un txt
                boton_a.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        AFD nuevo_afd = new AFD();
                            int seleccion = archivo_pa_cargar_AFD.showOpenDialog(AnalLex);
                            if(seleccion == JFileChooser.APPROVE_OPTION){
                                    File fichero = archivo_pa_cargar_AFD.getSelectedFile();
                                    try(FileReader fr = new FileReader(fichero)){
                                        int idSj; int[] arregloX = new int[258];
                                        
                                        String cadena = "";
                                        String buffer = "";
                                        int contador = 0;
                                        int valor = fr.read();
                                        //Scanner scan = new Scanner(System.in);
                                        while(valor != -1){
                                            cadena+= (char) valor;
                                            
                                            
                                            if((char)valor == '\n'){
                                                //System.out.println(buffer);
                                                for(int i =0;i<cadena.length();i++){
                                          
                                                    if(cadena.charAt(i) == ' ' || cadena.charAt(i) == '\n' ||(int) cadena.charAt(i) == 13){
                                                        if(!buffer.equals("")) arregloX[contador] = Integer.parseInt(buffer);
                                                        //System.out.print(i+" ");
                                                        contador++;
                                                        buffer="";
                                                    }
                                                    else if(((cadena.charAt(i)-'0') >= 0) || ((cadena.charAt(i)-'0') <= 9) || (cadena.charAt(i) == '-')) buffer+=cadena.charAt(i);
                                                    
                                                }
                                                nuevo_afd.insertaAFDenTabla(arregloX[0]);
                                                for(int i=1;i<=256;i++) nuevo_afd.insertaAFDenTabla(arregloX[0],i-1,arregloX[i]);
                                                nuevo_afd.setTokenTabla(arregloX[0], arregloX[257]);
                                                //System.out.println(Arrays.toString(arregloX)); System.out.println("Final:  "+arregloX[257]+" ");
                                                cadena="";
                                                contador = 0;
                                                
                                                //System.out.println("HOla");
                                            }
                                            
                                            valor = fr.read();
                                        }
                                        //System.out.println(cadena);
                                        
                                        nuevo_afd.setIdAFD((idAFDs.getText().isEmpty() ) ? afd.ConjDeAFDs.size()+1 : Integer.parseInt(idAFDs.getText()));
                                        afd.ConjDeAFDs.add(nuevo_afd);
                                        
                                            for (HashMap.Entry<Integer, int[]> set : nuevo_afd.getTabla().entrySet()) {
                                                    int [] temp1 = new int[260];
                                                    temp1 = set.getValue();
                                                    System.out.print(set.getKey()+ " ");
                                                    for(int i=0;i<257;i++){ 
                                                        System.out.print(temp1[i]+" ");
                                                    }
                                                    System.out.println("");
                                                }
                                        
                                        p1.remove(lista1);
                                        lista1.removeAllItems();
                                        for(AFD a : afd.ConjDeAFDs){ 
                                            lista1.addItem(a);
                                        }
                                        p1.add(lista1);
                                        AnalLex.setVisible(true);
                                        JOptionPane.showMessageDialog(AnalLex, "AFD agregado con éxito");
                                       //System.out.println(cadena);
                                    } catch(IOException el){
                                        el.printStackTrace();
                                    }
                            }
                    }
                
                });
                
            }
        });
 //----------------------------------------------------------------------------------------------- 
                //Boton para analizador sintáctico
        m122.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame AnalLex = new JFrame("Probar analizador sintáctico");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.9);
                int h = (int)((double)r.width*0.9);
                AnalLex.setSize(w,h);
                AnalLex.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Seleccione un AFD");
                JButton boton_a = new JButton("Cargar AFD desde un archivo");
                JLabel l3 = new JLabel("Ingrese la id del AFD");
                JLabel l4 = new JLabel("Ingrese la cadena");
                JFileChooser archivo_pa_cargar_AFD = new JFileChooser();
                JTextField idAFDs = new JTextField(3);
                JComboBox lista1 = new JComboBox();
                for(AFD a : afd.ConjDeAFDs){ 
                    lista1.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Empezar análisis");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                JPanel p4 = new JPanel();
                JPanel p5 = new JPanel();
                JPanel p6 = new JPanel();
                JTextArea cadena = new JTextArea(5,20);
                cadena.setLineWrap(true);
                JScrollPane panel = new JScrollPane(cadena);
                //cadena.s
                //cadena.setBounds(200, 80, 15, 20);
                //cadena.setPreferredSize(new Dimension(50,50));
                p2.add(l2);
                p1.add(lista1);
                p3.add (l3); p3.add(idAFDs); p3.add(boton_a); p5.add(boton);
                p4.add(l4); p4.add(panel);
                AnalLex.add(p2); AnalLex.add(p1); AnalLex.add(p3);
                AnalLex.add(p4); AnalLex.add(p5);//AnalLex.add(cadena);
                //AnalLex.setLayout(new FlowLayout());
                AnalLex.setLayout(new GridLayout(5,200));
                AnalLex.setVisible(true);
                
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        for(AFD a: afd.ConjDeAFDs){
                            AFD test = (AFD)lista1.getSelectedItem();
                            if(a.getIdAFD() == test.getIdAFD() && !cadena.getText().isEmpty()){
                                
                                //System.out.println(a.getIdAFD());
                                AnalizadorLexico analizador = new AnalizadorLexico(a,cadena.getText());
                                ClaseEvalMatrices prueba = new ClaseEvalMatrices(analizador);
                                //String cadena_aux = cadena.getText();
                                System.out.println(prueba.iniEvalMatrices());
                               
                                break;
                            }
                        }

                        AnalLex.setLayout(new GridLayout(4,200));
                        AnalLex.setVisible(true);
                        JOptionPane.showMessageDialog(AnalLex, "Análisis léxico realizado con éxito");
                        
                        AnalLex.dispatchEvent(new WindowEvent(AnalLex,WindowEvent.WINDOW_CLOSING));
                    }
                });
               //Esta parte es la de agregar un AFD desde un txt
                boton_a.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        AFD nuevo_afd = new AFD();
                            int seleccion = archivo_pa_cargar_AFD.showOpenDialog(AnalLex);
                            if(seleccion == JFileChooser.APPROVE_OPTION){
                                
                                    File fichero = archivo_pa_cargar_AFD.getSelectedFile();
                                    try(FileReader fr = new FileReader(fichero)){
                                        int idSj; int[] arregloX = new int[258];
                                        
                                        String cadena = "";
                                        String buffer = "";
                                        int contador = 0;
                                        int valor = fr.read();
                                        //Scanner scan = new Scanner(System.in);
                                        while(valor != -1){
                                            cadena+= (char) valor;
                                            
                                            
                                            if((char)valor == '\n'){
                                                //System.out.println(buffer);
                                                for(int i =0;i<cadena.length();i++){
                                          
                                                    if(cadena.charAt(i) == ' ' || cadena.charAt(i) == '\n' ||(int) cadena.charAt(i) == 13){
                                                        if(!buffer.equals("")) arregloX[contador] = Integer.parseInt(buffer);
                                                        //System.out.print(i+" ");
                                                        contador++;
                                                        buffer="";
                                                    }
                                                    else if(((cadena.charAt(i)-'0') >= 0) || ((cadena.charAt(i)-'0') <= 9) || (cadena.charAt(i) == '-')) buffer+=cadena.charAt(i);
                                                    
                                                }
                                                nuevo_afd.insertaAFDenTabla(arregloX[0]);
                                                for(int i=1;i<=256;i++) nuevo_afd.insertaAFDenTabla(arregloX[0],i-1,arregloX[i]);
                                                nuevo_afd.setTokenTabla(arregloX[0], arregloX[257]);
                                                //System.out.println(Arrays.toString(arregloX)); System.out.println("Final:  "+arregloX[257]+" ");
                                                cadena="";
                                                contador = 0;
                                                
                                                //System.out.println("HOla");
                                            }
                                            
                                            valor = fr.read();
                                        }
                                        //System.out.println(cadena);
                                        
                                        nuevo_afd.setIdAFD((idAFDs.getText().isEmpty() ) ? afd.ConjDeAFDs.size()+1 : Integer.parseInt(idAFDs.getText()));
                                        afd.ConjDeAFDs.add(nuevo_afd);
                                        
                                            for (HashMap.Entry<Integer, int[]> set : nuevo_afd.getTabla().entrySet()) {
                                                    int [] temp1 = new int[260];
                                                    temp1 = set.getValue();
                                                    System.out.print(set.getKey()+ " ");
                                                    for(int i=0;i<257;i++){ 
                                                        System.out.print(temp1[i]+" ");
                                                    }
                                                    System.out.println("");
                                                }
                                        
                                        p1.remove(lista1);
                                        lista1.removeAllItems();
                                        for(AFD a : afd.ConjDeAFDs){ 
                                            lista1.addItem(a);
                                        }
                                        p1.add(lista1);
                                        AnalLex.setVisible(true);
                                        JOptionPane.showMessageDialog(AnalLex, "AFD agregado con éxito");
                                       //System.out.println(cadena);
                                    } catch(IOException el){
                                        el.printStackTrace();
                                    }
                            }
                    }
                
                });
                
            }
        });
        
   //----------------------------------------------------------------------------------------------- 
                //Boton para analizador sintáctico
        m133.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame AnalLex = new JFrame("Probar analizador sintáctico");
                
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                int w = (int)((double)r.height*0.9);
                int h = (int)((double)r.width*0.9);
                AnalLex.setSize(w,h);
                AnalLex.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Seleccione un AFD");
                JButton boton_a = new JButton("Cargar AFD desde un archivo");
                JLabel l3 = new JLabel("Ingrese la id del AFD");
                JLabel l4 = new JLabel("Ingrese la cadena");
                JFileChooser archivo_pa_cargar_AFD = new JFileChooser();
                JTextField idAFDs = new JTextField(3);
                JComboBox lista1 = new JComboBox();
                for(AFD a : afd.ConjDeAFDs){ 
                    lista1.addItem(a);
                }
                //lista.addItemListener(this);
                JButton boton = new JButton("Empezar análisis");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JPanel p3 = new JPanel();
                JPanel p4 = new JPanel();
                JPanel p5 = new JPanel();
                JPanel p6 = new JPanel();
                JTextArea cadena = new JTextArea(5,20);
                cadena.setLineWrap(true);
                JScrollPane panel = new JScrollPane(cadena);
                //cadena.s
                //cadena.setBounds(200, 80, 15, 20);
                //cadena.setPreferredSize(new Dimension(50,50));
                p2.add(l2);
                p1.add(lista1);
                p3.add (l3); p3.add(idAFDs); p3.add(boton_a); p5.add(boton);
                p4.add(l4); p4.add(panel);
                AnalLex.add(p2); AnalLex.add(p1); AnalLex.add(p3);
                AnalLex.add(p4); AnalLex.add(p5);//AnalLex.add(cadena);
                //AnalLex.setLayout(new FlowLayout());
                AnalLex.setLayout(new GridLayout(5,200));
                AnalLex.setVisible(true);
                
                
                boton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        for(AFD a: afd.ConjDeAFDs){
                            AFD test = (AFD)lista1.getSelectedItem();
                            if(a.getIdAFD() == test.getIdAFD() && !cadena.getText().isEmpty()){
                                
                                //System.out.println(a.getIdAFD());
                                AnalizadorLexico analizador = new AnalizadorLexico(a,cadena.getText());
                                ClaseEvalGramaticas prueba = new ClaseEvalGramaticas(analizador);
                                //LL1Gramaticas prueba1 = new LL1Gramaticas(prueba,cadena.getText());
                                
                                //String cadena_aux = cadena.getText();
                                System.out.println(prueba.iniEvalGramaticas());
                                //prueba1.armarTablaLL1();
                                break;
                            }
                        }

                        AnalLex.setLayout(new GridLayout(4,200));
                        AnalLex.setVisible(true);
                        JOptionPane.showMessageDialog(AnalLex, "Análisis léxico realizado con éxito");
                        
                        AnalLex.dispatchEvent(new WindowEvent(AnalLex,WindowEvent.WINDOW_CLOSING));
                    }
                });
               //Esta parte es la de agregar un AFD desde un txt
                boton_a.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        AFD nuevo_afd = new AFD();
                            int seleccion = archivo_pa_cargar_AFD.showOpenDialog(AnalLex);
                            if(seleccion == JFileChooser.APPROVE_OPTION){
                                
                                    File fichero = archivo_pa_cargar_AFD.getSelectedFile();
                                    try(FileReader fr = new FileReader(fichero)){
                                        int idSj; int[] arregloX = new int[258];
                                        
                                        String cadena = "";
                                        String buffer = "";
                                        int contador = 0;
                                        int valor = fr.read();
                                        //Scanner scan = new Scanner(System.in);
                                        while(valor != -1){
                                            cadena+= (char) valor;
                                            
                                            
                                            if((char)valor == '\n'){
                                                //System.out.println(buffer);
                                                for(int i =0;i<cadena.length();i++){
                                          
                                                    if(cadena.charAt(i) == ' ' || cadena.charAt(i) == '\n' ||(int) cadena.charAt(i) == 13){
                                                        if(!buffer.equals("")) arregloX[contador] = Integer.parseInt(buffer);
                                                        //System.out.print(i+" ");
                                                        contador++;
                                                        buffer="";
                                                    }
                                                    else if(((cadena.charAt(i)-'0') >= 0) || ((cadena.charAt(i)-'0') <= 9) || (cadena.charAt(i) == '-')) buffer+=cadena.charAt(i);
                                                    
                                                }
                                                nuevo_afd.insertaAFDenTabla(arregloX[0]);
                                                for(int i=1;i<=256;i++) nuevo_afd.insertaAFDenTabla(arregloX[0],i-1,arregloX[i]);
                                                nuevo_afd.setTokenTabla(arregloX[0], arregloX[257]);
                                                //System.out.println(Arrays.toString(arregloX)); System.out.println("Final:  "+arregloX[257]+" ");
                                                cadena="";
                                                contador = 0;
                                                
                                                //System.out.println("HOla");
                                            }
                                            
                                            valor = fr.read();
                                        }
                                        //System.out.println(cadena);
                                        
                                        nuevo_afd.setIdAFD((idAFDs.getText().isEmpty() ) ? afd.ConjDeAFDs.size()+1 : Integer.parseInt(idAFDs.getText()));
                                        afd.ConjDeAFDs.add(nuevo_afd);
                                        
                                            for (HashMap.Entry<Integer, int[]> set : nuevo_afd.getTabla().entrySet()) {
                                                    int [] temp1 = new int[260];
                                                    temp1 = set.getValue();
                                                    System.out.print(set.getKey()+ " ");
                                                    for(int i=0;i<257;i++){ 
                                                        System.out.print(temp1[i]+" ");
                                                    }
                                                    System.out.println("");
                                                }
                                        
                                        p1.remove(lista1);
                                        lista1.removeAllItems();
                                        for(AFD a : afd.ConjDeAFDs){ 
                                            lista1.addItem(a);
                                        }
                                        p1.add(lista1);
                                        AnalLex.setVisible(true);
                                        JOptionPane.showMessageDialog(AnalLex, "AFD agregado con éxito");
                                       //System.out.println(cadena);
                                    } catch(IOException el){
                                        el.printStackTrace();
                                    }
                            }
                    }
                
                });
                
            }
        });
        
           //----------------------------------------------------------------------------------------------- 
                //Boton para analizador LL(1)
        m144.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame AnalLex = new JFrame("Probar LL(1)");
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double w = screenSize.getWidth()-50;
                double h = screenSize.getHeight()-50;
                JPanel p = new JPanel();
                Rectangle r = frame.getBounds();
                AnalLex.setSize((int)w,(int)h);
                AnalLex.setLocationRelativeTo(null);
                JLabel l2 = new JLabel("Ingrese la gramática");
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();
                JTextArea cadena1 = new JTextArea(10,100);
                cadena1.setLineWrap(true);
                JScrollPane panel = new JScrollPane(cadena1);
                p1.add(l2); p1.add(panel);
                AnalLex.add(p1);
                AnalLex.setLayout(new FlowLayout());
                
                //AnalLex.setLayout(new GridLayout(3,200));
                JButton boton_gramatica = new JButton("Generar tablas");
                p1.add(boton_gramatica);
                AnalLex.setVisible(true);
                        AFD nuevo_afd = new AFD();
                                String ruta_archivo = System.getProperty("user.dir")+"/AFD_Gramaticas.txt";      //Para que jale en cualquier PC.
                                    File fichero = new File(ruta_archivo);
                                    //File fichero = archivo_pa_cargar_AFD.getSelectedFile();
                                    try(FileReader fr = new FileReader(fichero)){
                                        int[] arregloX = new int[258];
                                        String cadena = "";
                                        String buffer = "";
                                        int contador = 0;
                                        int valor = fr.read();
                                        //Scanner scan = new Scanner(System.in);
                                        while(valor != -1){
                                            cadena+= (char) valor;
                                            
                                            
                                            if((char)valor == '\n'){
                                                //System.out.println(buffer);
                                                for(int i =0;i<cadena.length();i++){
                                          
                                                    if(cadena.charAt(i) == ' ' || cadena.charAt(i) == '\n' ||(int) cadena.charAt(i) == 13){
                                                        if(!buffer.equals("")) arregloX[contador] = Integer.parseInt(buffer);
                                                        //System.out.print(i+" ");
                                                        contador++;
                                                        buffer="";
                                                    }
                                                    else if(((cadena.charAt(i)-'0') >= 0) || ((cadena.charAt(i)-'0') <= 9) || (cadena.charAt(i) == '-')) buffer+=cadena.charAt(i);
                                                    
                                                }
                                                nuevo_afd.insertaAFDenTabla(arregloX[0]);
                                                for(int i=1;i<=256;i++) nuevo_afd.insertaAFDenTabla(arregloX[0],i-1,arregloX[i]);
                                                nuevo_afd.setTokenTabla(arregloX[0], arregloX[257]);
                                                //System.out.println(Arrays.toString(arregloX)); System.out.println("Final:  "+arregloX[257]+" ");
                                                cadena="";
                                                contador = 0;
                                                
                                                //System.out.println("HOla");
                                            }
                                            
                                            valor = fr.read();
                                        }
                                        //System.out.println(cadena);
                                        
                                        nuevo_afd.setIdAFD(999);
                                        afd.ConjDeAFDs.add(nuevo_afd);
                                        
                                            for (HashMap.Entry<Integer, int[]> set : nuevo_afd.getTabla().entrySet()) {
                                                    int [] temp1 = new int[260];
                                                    temp1 = set.getValue();
                                                    System.out.print(set.getKey()+ " ");
                                                    for(int i=0;i<257;i++){ 
                                                        System.out.print(temp1[i]+" ");
                                                    }
                                                    System.out.println("");
                                                }
                                        
                                        AnalLex.setVisible(true);
                                    } catch(IOException el){
                                        el.printStackTrace();
                                    }
                                    
                                    boton_gramatica.addActionListener(new ActionListener(){
                                    @Override
                                        public void actionPerformed(ActionEvent e){
                                            String gramatica="";
                                            gramatica = cadena1.getText();
                                            System.out.println(gramatica);
                                            AnalizadorLexico analizador = new AnalizadorLexico(nuevo_afd,gramatica);
                                            ClaseEvalGramaticas prueba = new ClaseEvalGramaticas(analizador);
                                            System.out.println(prueba.iniEvalGramaticas());
                                            LL1Gramaticas prueba1 = new LL1Gramaticas(prueba);
                                            prueba1.armarTablaLL1();
                                            
                                            
                                            //Empezamos a armar la tabla LL1
                                            Object[] colnom = new Object[prueba.Vt.size()+1];
                                            int i=1;
                                            colnom[0] = "\t"; 
                                            for(String s : prueba.Vt){
                                                colnom[i] = s;
                                                i++;
                                            }
                                            Object[][] data = new Object[prueba.Vn.size()+prueba.Vt.size()][prueba.Vt.size()+2];
                                            //Object [][] data = new Object[20][20];
                                            int j=0,k=0;
                                            for (HashMap.Entry<String, HashMap<String,String[]>> set : prueba1.tablaLL1.entrySet()) {
                                                k=0;
                                                String llave = set.getKey();
                                                data[j][k++]=llave;
                                                HashMap<String,String[]> temp1 = set.getValue();
                                                for(HashMap.Entry<String,String[]> temp2 : temp1.entrySet()){
                                                    String auxiliar="";
                                                    for(String s: temp2.getValue()){
                                                        auxiliar+=s;
                                                        auxiliar+=" ";
                                                    }
                                                    data[j][k++] = auxiliar;
                                                }
                                                j++;
                                            }
                                                            
                                           JTable tabla = new JTable(data,colnom);
                                           tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                           JScrollPane panelDeslizable = new JScrollPane(tabla);
                                           panelDeslizable.setPreferredSize(new Dimension(500, 300));
                                           TableColumnModel columnModel = tabla.getColumnModel();
                                           columnModel.getColumn(0).setPreferredWidth(150);
                                           for(int ii=1;ii<prueba.Vt.size();ii++) columnModel.getColumn(ii).setPreferredWidth(250);
                                           tabla.setDefaultEditor(Object.class, null);
                                           panelDeslizable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                           panelDeslizable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                                           AnalLex.add(panelDeslizable,BorderLayout.CENTER);
                                           
                                            
                                           //Empezamos tabla de Vn
                                           Object[] colnom2 = new Object[2];
                                           colnom2[0] = "\t"; colnom2[1] = "No terminal";
                                           Object[][] data2 = new Object[prueba.Vn.size()][2];
                                           int iterador=0;
                                           for(String s: prueba.Vn){ 
                                               data2[iterador++][0] = " ";
                                               data2[iterador-1][1]= s;
                                           }
                                           
                                           
                                           JTable tabla1 = new JTable(data2,colnom2);
                                           tabla1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                           JScrollPane panelDeslizable1 = new JScrollPane(tabla1);
                                           panelDeslizable1.setPreferredSize(new Dimension(200, 300));
                                           TableColumnModel columnModel1 = tabla1.getColumnModel();
                                           columnModel1.getColumn(0).setPreferredWidth(10);
                                           columnModel1.getColumn(1).setPreferredWidth(150);
                                           tabla1.setDefaultEditor(Object.class, null);
                                           panelDeslizable1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                           panelDeslizable1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                                           AnalLex.add(panelDeslizable1,BorderLayout.CENTER);
                                           
                                           //Empezamos tabla de Vt
                                           
                                           Object[] colnom3 = new Object[2];
                                           colnom3[0] = "Terminal"; colnom3[1] = "Token";
                                           Object[][] data3 = new Object[prueba.Vt.size()][2];
                                           iterador=0;
                                           for(String s: prueba.Vt){
                                               data3[iterador++][1] = "";
                                               data3[iterador-1][0]= s;
                                           }
                                           
                                           
                                           JTable tabla2 = new JTable(data3,colnom3);
                                           tabla2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                           JScrollPane panelDeslizable2 = new JScrollPane(tabla2);
                                           panelDeslizable2.setPreferredSize(new Dimension(320, 300));
                                           TableColumnModel columnModel2 = tabla2.getColumnModel();
                                           columnModel2.getColumn(0).setPreferredWidth(150);
                                           columnModel2.getColumn(1).setPreferredWidth(150);
                                           //tabla2.setDefaultEditor(Object.class, null);
                                           panelDeslizable2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                           panelDeslizable2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                                           AnalLex.add(panelDeslizable2,BorderLayout.CENTER);
                                           
                                           JButton boton_tokens = new JButton("Asignar tokens");
                                           AnalLex.add(boton_tokens);
                                           
                                           JButton add_AFD = new JButton("Seleccionar AFD Léxico");
                                           AnalLex.add(add_AFD);
                                           
                                           

                                           AnalLex.setVisible(true);
                                           
                                           //Para el boton de establecer tokens
                                           boton_tokens.addActionListener(new ActionListener(){
                                           @Override
                                           public void actionPerformed(ActionEvent e){
                                               HashMap<String,Integer> tokens_terminales = new HashMap<>();
                                               for(int i=0;i<prueba.Vt.size();i++){
                                                   String aux=""; String num_aconvertir="";
                                                   aux = (String) tabla2.getValueAt(i, 0);
                                                   num_aconvertir = (String) tabla2.getValueAt(i, 1);
                                                   //System.out.println(aux+" "+num_aconvertir);
                                                   tokens_terminales.put(aux, Integer.parseInt(num_aconvertir));
                                               }
                                                int i;
                                                for(i=0;i<prueba.NumReglas;i++){
                                                    for(NodoArreglo N : prueba.Reglas[i].lista.lista){
                                                        if(N.esTerminal) N.token = tokens_terminales.get(N.c);
                                                        
                                                    }
                                                }
                                                
                                           //para probar que haya funcionado
                                           int iii=0;
                                           for(iii=0;iii<prueba.NumReglas;iii++){
                                                    for(NodoArreglo N : prueba.Reglas[iii].lista.lista){
                                                        if(N.esTerminal) System.out.println(N.c+" "+N.token);
                                                    }
                                                }
                                           
                                           }
                                           });
                                           

                                           
                                           //Para el boton de añadir AFD
                                           add_AFD.addActionListener(new ActionListener(){
                                           @Override
                                           public void actionPerformed(ActionEvent e){
                                               
                                               JFileChooser archivo_pa_cargar_AFD = new JFileChooser();
                                               AFD nuevo_afd = new AFD();
                                                int seleccion = archivo_pa_cargar_AFD.showOpenDialog(AnalLex);
                                                if(seleccion == JFileChooser.APPROVE_OPTION){
                                                        File fichero = archivo_pa_cargar_AFD.getSelectedFile();
                                                        try(FileReader fr = new FileReader(fichero)){
                                                            int idSj; int[] arregloX = new int[258];
                                                            String cadena = "";
                                                            String buffer = "";
                                                            int contador = 0;
                                                            int valor = fr.read();
                                                            //Scanner scan = new Scanner(System.in);
                                                            while(valor != -1){
                                                                cadena+= (char) valor;


                                                                if((char)valor == '\n'){
                                                                    //System.out.println(buffer);
                                                                    for(int i =0;i<cadena.length();i++){

                                                                        if(cadena.charAt(i) == ' ' || cadena.charAt(i) == '\n' ||(int) cadena.charAt(i) == 13){
                                                                            if(!buffer.equals("")) arregloX[contador] = Integer.parseInt(buffer);
                                                                            //System.out.print(i+" ");
                                                                            contador++;
                                                                            buffer="";
                                                                        }
                                                                        else if(((cadena.charAt(i)-'0') >= 0) || ((cadena.charAt(i)-'0') <= 9) || (cadena.charAt(i) == '-')) buffer+=cadena.charAt(i);

                                                                    }
                                                                    nuevo_afd.insertaAFDenTabla(arregloX[0]);
                                                                    for(int i=1;i<=256;i++) nuevo_afd.insertaAFDenTabla(arregloX[0],i-1,arregloX[i]);
                                                                    nuevo_afd.setTokenTabla(arregloX[0], arregloX[257]);
                                                                    //System.out.println(Arrays.toString(arregloX)); System.out.println("Final:  "+arregloX[257]+" ");
                                                                    cadena="";
                                                                    contador = 0;

                                                                    //System.out.println("HOla");
                                                                }

                                                                valor = fr.read();
                                                            }
                                                            //System.out.println(cadena);

                                                            nuevo_afd.setIdAFD(998);
                                                            afd.ConjDeAFDs.add(nuevo_afd);
                                                            prueba1.setAFD(nuevo_afd);
                                                                for (HashMap.Entry<Integer, int[]> set : nuevo_afd.getTabla().entrySet()) {
                                                                        int [] temp1 = new int[260];
                                                                        temp1 = set.getValue();
                                                                        System.out.print(set.getKey()+ " ");
                                                                        for(int i=0;i<257;i++){ 
                                                                            System.out.print(temp1[i]+" ");
                                                                        }
                                                                        System.out.println("");
                                                                    }
                                               
                                               
                                               
                                               
                                           }catch(IOException el){
                                               System.out.println(el);
                                        el.printStackTrace();
                                    }
                                                }
                                           }
                                           });
                                    //Ahora analizador sintactico para el AFD con ID 998
                                    JLabel texto_sigma = new JLabel("Sigma");
                                    JButton boton_analLex = new JButton("Probar léxico");
                                    JTextArea cadena_AFD = new JTextArea(20,20);
                                    cadena_AFD.setLineWrap(true);
                                    JScrollPane panel_cadena = new JScrollPane(cadena_AFD);
                                    AnalLex.add(texto_sigma);
                                    AnalLex.add(panel_cadena);
                                    AnalLex.add(boton_analLex);
                                    
                                    AnalLex.setVisible(true);       
                                    
                                    
                                    boton_analLex.addActionListener(new ActionListener(){
                                           @Override
                                           public void actionPerformed(ActionEvent e){
                                               
                                               //Pongo la cadena
                                               String cadena_por_analizar="";
                                               cadena_por_analizar = cadena_AFD.getText();
                                               prueba1.setSigma(cadena_por_analizar);
                                               prueba1.setAnalizadorLexico();
                                               //Empiezo el analisis
                                               
                                               
                                               while(prueba1.analex.yylex() != 0){
                                                prueba1.analex.guardarLexemas.add(new Pair<>(prueba1.analex.getLexema(),prueba1.analex.getToken()));
                                                //System.out.println("Lexema: "+prueba1.analex.getLexema()+" Token: "+prueba1.analex.getToken());
                                                }
                                               
                                               String[] nombre_columnas = new String[2];
                                                nombre_columnas[0] = "Lexema"; nombre_columnas[1] = "Token";
                                                Object[][] data4 = new Object[prueba1.analex.guardarLexemas.size()][2];
                                                int i=0;
                                                for(Pair<String,Integer> k : prueba1.analex.guardarLexemas){
                                                    data4[i][0] = k.getL();
                                                       data4[i][1] = k.getR();
                                                       i++;
                                                }
                                             
                                           JTable tabla3 = new JTable(data4,nombre_columnas);
                                           tabla3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                           JScrollPane panelDeslizable3 = new JScrollPane(tabla3);
                                           
                                           panelDeslizable3.setPreferredSize(new Dimension(320, 300));
                                           TableColumnModel columnModel3 = tabla3.getColumnModel();
                                           columnModel3.getColumn(0).setPreferredWidth(150);
                                           columnModel3.getColumn(1).setPreferredWidth(150);
                                           tabla3.setDefaultEditor(Object.class, null);
                                           panelDeslizable3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                           panelDeslizable3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                                           AnalLex.remove(panelDeslizable3);AnalLex.remove(panelDeslizable3);AnalLex.remove(panelDeslizable3);
                                           AnalLex.add(panelDeslizable3,BorderLayout.CENTER);
                                           AnalLex.setVisible(true);
                                               
                                           }
                                           });
                                        //Tabla del LL(1)
                                        
                                        JButton analLL1 = new JButton("Analizar con LL1");
                                        DefaultTableModel modeloDeMiJTable;
                                        
                                        modeloDeMiJTable = new DefaultTableModel() {
                                            @Override
                                            public Class getColumnClass(int c) {
                                            return getValueAt(0, c).getClass();
                                            }
                                            @Override
                                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                            return false;
                                            }
                                        };
                                        modeloDeMiJTable.addColumn("Pila");
                                        modeloDeMiJTable.addColumn("Cadena (sigma)");
                                        modeloDeMiJTable.addColumn("Acción");
                                        JTable tablaLL1 = new JTable();
                                        tablaLL1.setModel(modeloDeMiJTable);
                                        tablaLL1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                           JScrollPane panelDeslizableLL1 = new JScrollPane(tablaLL1);
                                           
                                           panelDeslizableLL1.setPreferredSize(new Dimension(1050, 400));
                                           TableColumnModel columnModelLL1 = tablaLL1.getColumnModel();
                                           columnModelLL1.getColumn(0).setPreferredWidth(500);
                                           columnModelLL1.getColumn(1).setPreferredWidth(250);
                                           columnModelLL1.getColumn(2).setPreferredWidth(250);
                                           tablaLL1.setDefaultEditor(Object.class, null);
                                           panelDeslizableLL1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                           panelDeslizableLL1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                                           AnalLex.add(panelDeslizableLL1,BorderLayout.CENTER);
                                        
                                        
                                        analLL1.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                            modeloDeMiJTable.setRowCount(0);
                                            String cadena_por_analizar="";
                                            cadena_por_analizar = cadena_AFD.getText();
                                            prueba1.setSigma(cadena_por_analizar);
                                            prueba1.setAnalizadorLexico();
                                            System.out.println(cadena_por_analizar);
                                            System.out.println(prueba1.AnalisisConLL1());
                                            
                                            for(int l=0;l<prueba1.contador;l++) modeloDeMiJTable.addRow(prueba1.data[l]);
                                            modeloDeMiJTable.fireTableDataChanged();
                                            AnalLex.setVisible(true);
                                            
                                            
                                            		// setup the tree layout configuration
                                        double gapBetweenLevels = 30;
                                        double gapBetweenNodes = 15;
                                        DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<>(gapBetweenLevels, gapBetweenNodes);

                                        // create the NodeExtentProvider for TextInBox nodes
                                        TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

                                        // create the layout
                                        TreeLayout<TextInBox> treeLayout = new TreeLayout<>(prueba1.tree,nodeExtentProvider, configuration);

                                        // Create a panel that draws the nodes and edges and show the panel
                                        TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
                                        panel.setBoxVisible(true);
                                        
                                        JDialog dialog = new JDialog();
                                        JScrollPane panel_overflow = new JScrollPane(panel);
                                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                                        panel_overflow.setPreferredSize(new Dimension((int)(screenSize.getWidth()-50), (int)(screenSize.getHeight()-50)));
                                        Container contentPane = dialog.getContentPane();
                                        ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(
                                                        5, 5, 5, 5));
                                        panel_overflow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                        panel_overflow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                                        contentPane.add(panel_overflow);
                                        dialog.pack();
                                        dialog.setLocationRelativeTo(null);
                                        dialog.setVisible(true);
  
                                            
                                        }
                                    });
                                        
                                        AnalLex.add(analLL1);
                                        AnalLex.setVisible(true);
                                        }
                                    });
            }
                    
                
                
            
        });
        
    }
    
}
