import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Unlimited   { //Jframe e a escuta
       private int dimH=1500;
       private int dimV=800;
       private int lado;
       private int score;
       private Peças array_peças[][];
       private int ronda;
       private int score_por_peça;
       private JFrame janela;
       private Container c;
    /**
     * Creates new form Unlimited
     */
    public Unlimited(int n) {
        janela=new JFrame();
        c=new Container();
        janela.setSize(dimH,dimV);
        janela.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        set_score_peça(10);
        reset_ronda();
        set_lado(n);
        inicializa_array();
        init_listeners();
        init_grid(c);
        janela.getContentPane().add(c,"Center");
        janela.setVisible(true);
        
        
        //getContentPane().add(yellowLabel, BorderLayout.CENTER);
        //setLayout(new GridLayout(lado,lado));

    }

    
                  

    public void set_lado(int l){
        lado=l;
    }
    void inicializa_array(){ //inicializa o array de pecas
        int i;
        int j;
        int id_butão=1;
        //aloca espaco;
        array_peças=new Peças[lado][lado];
        for(i=0;i<lado;i++){
            
            for(j=0;j<lado;j++){
                array_peças[i][j]=random_peça();
                array_peças[i][j].setText(Integer.toString(id_butão));
                id_butão++;//usado para identificar o butao

            }
        }
        
    }
    
    public Peças random_peça(){
        //percentagem de pecas especiais=5%;
        Random random=new Random();
        int especial_percentagem=1+random.nextInt(100);
        int tipo=random.nextInt(6)+1;
        int especial=random_power(especial_percentagem);
        if(tipo==1){
           return new Tipo1(especial);

        }
        if(tipo==2){
            return new Tipo2(especial);
        }
        if(tipo==3){
            return new Tipo3(especial);

        }
        if(tipo==4){
           return new Tipo4(especial);

        }
        if(tipo==5){
           return new Tipo5(especial);

        }
        else{
           return new Tipo6(especial);

        }
    }
    public int random_power (int a){
        if(a>15) return 0;
        if(a<6) return 1;
        if(a>5&&a<11) return 2;
        if (a<16&&a>10) return 3;
        else return 0;
    }
    
    
    public void init_grid( Container c){
           
            c.setLayout(new GridLayout(lado,lado));
            
            int i,j;
            for(i=0;i<lado;i++){
                for(j=0;j<lado;j++){
                    
                  
                    c.add( array_peças[i][j]);
                }
            }
        
    }
    //antes de mais marcar a peça!!!!
    
    /*
    public void por_a_escuta(){
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                array_peças[i][j]
            }
        }
    }*/
    
    public void init_listeners(){
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                array_peças[i][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[i][j],ronda,score_por_peça,  c));
            }
        }
    }
    public void reset_ronda(){
        ronda=1;
        score=0;
    }
    
    public int ronda(){
        return ronda;
    }
    public void set_score_peça(int a){
        score_por_peça=a;
    }
    // Variables declaration - do not modify                     
    // End of variables declaration                   
}

