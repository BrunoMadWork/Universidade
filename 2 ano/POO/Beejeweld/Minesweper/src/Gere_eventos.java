
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brunomadureira
 */
public class Gere_eventos implements ActionListener  {
    
   private JFrame janela; 
   private Peças [][] array_peças;
   private Peças [][] auxiliar;
   private int lado;
   private int score;
   private Peças peça1;
   private int ronda;
   private int score_peça;
   private Container c;
   public Gere_eventos(JFrame jan, Peças[][] pe, int l, int sc ,Peças i ,int r, int sp, Container co){
        
       janela=jan;
       array_peças=pe;
       lado=l;
       score=sc;
       peça1=i;
       ronda=r;
       score_peça=sp;
       auxiliar=new Peças[lado][lado];
       set_piece_score();
       c=co;
       c.setLayout(new GridLayout(lado,lado));
       
   }

   
   public int lado() {
       return lado;
   }
   public int score() {
       return score;
   }
   public Peças [][] array_peças() {
       return array_peças;
   }
   
   
   
    @Override
    public void actionPerformed(ActionEvent ae) {
        desmarcar_se_tiverem_2_marcadas();
        System.out.println("CLICASTE Na peça "+peça1.getText());
        reset_lados();
        
        if(destruição_inicial()==true){
            System.out.println("entrou no 1 if");
            encher_tudo();
            
        }
         
         else if(verificar_se_ronda_acabou()==true){
         System.out.println("entrou no 2 if");

            next_round();
            inicializa_array();
        }
         
        
        else{
            System.out.println("ENTROU NO ELSE");
        //meter algo para acabar a ronda imediatanmente se nao tiverem combinacoes possiveis
        peça1.marcar();
        System.out.println("Foi marcada!");
        //pesquisa se ha outra peca diferente marcada
        if(contar_marcadas()==2){
            System.out.println("Entrou na cena destruição");
            Peças peça2=devolver_outra_peça(peça1);
            System.out.println("Devolveu a peça "+peça2.getText()+"YEAHIIIIIIIII");
         
         

        
       if(is_side_to_side_vertical(peça1,peça2)==true||is_side_to_side_horizontal(peça1,peça2)==true){
           System.out.println("Entrou no if de tarem lado a lado");
           actualizar_auxiliar(peça1,peça2);
           //valores sao referentes ao auxiliar
           reset_lado_auxiliar();
           print_pecas_lado();
           ver_peças_ao_lado_auxiliar();
           print_pecas_lado();
           System.out.println("viu se as peças que tao ao lado");
           if(peça1.n_peças_horizontal()>1&&alguma_lado_3()||peça1.n_peças_vertical()>1&&alguma_lado_3()||peça2.n_peças_vertical()>1&&alguma_lado_3()||peça2.n_peças_horizontal()>1&&alguma_lado_3()){
               System.out.println("viu que tem peças para serem destruidas!");
                 substituir(peça1,peça2);
                 
                System.out.println("Substitui uma pela outra");
           reset_lados();
            ver_peças_ao_lado();
           print_pecas_lado();
            destruir();
            encher_tudo();
            System.out.println("Houce destruição e reencheu se");
           // desmarcar_tudo();
             System.out.println("desmarcou tudo");
           }
           //destruição de pecas
           
           if(verificar_se_ronda_acabou()==true){
            next_round();
            inicializa_array();
           // desmarcar_tudo();
            System.out.println("desmarcou tudo");
        }
           System.out.println("foi tudo desmarcado");
            
           
       }
       //se nao for apenas desmarca tudo e sair
       
       
       
       //no fim
       
       
       //meter algo para terminar a ronda se nao ouverem combinaçoes possiveis
       desmarcar_tudo();
      }
        }  
        
         ////NAO SEI SE TA BEM!
        //janela.setVisible(false);
        remover_peças();
       // janela.dispose();
        c.removeAll();
        janela.getContentPane().removeAll();
        janela.getContentPane().setLayout(null);
        init_grid(c);
        janela.getContentPane().add(c,"Center");
        init_listeners();
        janela.validate();
        janela.repaint();
        janela.setVisible(true);
        System.out.println("Saiu da funcão");
        
    }
    public void advance_round(){
        
        ronda++;
    }
    public Peças devolver_outra_peça(Peças peça){
        
        int i,j;
        for(i=0;i<lado;i++){
            
            for(j=0;j<lado;j++){
                
                if(array_peças[i][j].mark()==1&&array_peças[i][j]!=peça){
                    return array_peças[i][j];
                }
            }
        }
        return null;
    }
    
    
    public void copia_array(Peças[][] original, Peças copia[][]){ //copia uma array para outro provavelmente codigo a mais
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                copia[i][j]=original[i][j];
            }
        }
        
    }
    
    public boolean is_side_to_side_horizontal(Peças a, Peças b){ //verifica se pecas estao lado a lado
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                //verificacoes
                    
                    
                 //esquerda
                if(j-1>-1){
                        
                        if(array_peças[i][j]==a&&array_peças[i][j-1]==b||array_peças[i][j]==b&&array_peças[i][j-1]==a)
                    return true;
                        }
                //direita
                if(j+1<lado){
                    if(array_peças[i][j]==a&&array_peças[i][j+1]==b||array_peças[i][j]==b&&array_peças[i][j+1]==a)
                    return true;
            }
        }
        }
        return false;
    }
    public boolean is_side_to_side_vertical(Peças a, Peças b){
        
         int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                //verificacoes
                
                //cima
                if(i-1>-1){
                if(array_peças[i][j]==a&&array_peças[i-1][j]==b||array_peças[i][j]==b&&array_peças[i-1][j]==a)
                    return true;
                }
                //baixo
                if(i+1<lado) {
                    if(array_peças[i][j]==a&&array_peças[i+1][j]==b||array_peças[i][j]==b&&array_peças[i+1][j]==a)
                        return true;
                    }
                
            }}
        return  false;
    }
    public int contar_marcadas(){
        int i,j,count=0;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                if(array_peças[i][j].mark()==1){
                    count++;
                }
                    
            }
        }
    
    return count;
}
    public void desmarcar_tudo() {
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                array_peças[i][j].desmarcar();
            }
        }
        
    }
    public void substituir(Peças a, Peças b){ //verificar se isto mda
        
        Peças temp1=a;
        Peças temp2=b;
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                if(a==array_peças[i][j]){
                    array_peças[i][j]=temp2;
                }
                if(b==array_peças[i][j]){
                    array_peças[i][j]=temp1;
                }
            }
        }
    }
    
    public void gravidade(){
		
	////aplica as leis da gravidade depois de verificar pecas suspensas no ar
		int i, j;
		for(j=0;j<lado;j++){
			for(i=0;i<lado-1;i++){
				if(array_peças[i][j]!=null&&array_peças[i+1][j]==null){
					array_peças[i+1][j]=array_peças[i][j];
                                        //faltava isto
                                        array_peças[i][j]=null;
				}
			}
		}
	}
    //alterar teste
    public void put_on_top() {
                System.out.println("Vai por peças em cima!!");
		int j;
		Random random=new Random();
		while(top_is_full()==false ){
			for(j=0;j<lado;j++){
				if(array_peças[0][j]==null){
                                              int especial_percentagem=1+random.nextInt(100);
                                             int tipo=random.nextInt(6)+1;
                                             int especial=random_power(especial_percentagem);
                                              if(tipo==1){
                                                  Peças peça=new Tipo1(especial);
                                                  array_peças[0][j]=peça;
                                                  array_peças[0][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[0][j],ronda,score_peça, c));
                                                  array_peças[0][j].setText("NOVA!");

                                                  
                                                               

                                                         }
                                                 if(tipo==2){
                                                        Peças peça=new Tipo2(especial);
                                                        array_peças[0][j]=peça;
                                                        array_peças[0][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[0][j],ronda,score_peça,c));
                                                        array_peças[0][j].setText("NOVA!");
                                                         
                                                         }
                                                  if(tipo==3){
                                                         Peças peça=new Tipo3(especial);
                                                         array_peças[0][j]=peça;
                                                         array_peças[0][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[0][j],ronda,score_peça,c));
                                                         array_peças[0][j].setText("NOVA!");
                                                         

                                                                 }
                                                     if(tipo==4){
                                                         Peças peça=new Tipo4(especial);
                                                         array_peças[0][j]=peça;
                                                         array_peças[0][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[0][j],ronda,score_peça,c));
                                                         array_peças[0][j].setText("NOVA!");
                                                    

                                                                     }
                                                         if(tipo==5){
                                                          Peças peça=new Tipo5(especial);
                                                          array_peças[0][j]=peça;
                                                          array_peças[0][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[0][j],ronda,score_peça,c));
                                                          array_peças[0][j].setText("NOVA!");
                                                         

                                                                  }
                                                        else{
                                                                 Peças peça=new Tipo6(especial);
                                                                 array_peças[0][j]=peça;
                                                                 array_peças[0][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[0][j],ronda,score_peça,c));
                                                                 array_peças[0][j].setText("NOVA!");
                                                         

        }

			}
		}
		}
                System.out.println("Poz la pecas !!!");
    }
		
                public boolean top_is_full(){
		int j;
		boolean bool=true;
		for(j=0;j<lado;j++){
			if(array_peças[0][j]==null){
				bool=false;
			}
		}
		return bool;
	}
	
        public void encher_tudo() {
        //aplicar gravidade e pecas em cima e depois gravidade, ate o topo estar cheio.
            while(free_of_nulls()==false){
                
                gravidade();
                put_on_top();
                gravidade();
            }
}

        
public int random_power (int a){
        if(a>15) return 0;
        if(a<6) return 1;
        if(a>5&&a<11) return 2;
        if (a<16&&a>10) return 3;
        else return 0;
    }

public void ver_peças_ao_lado(){
    int i,j;
    for(i=0;i<lado;i++){
        
        for(j=0;j<lado;j++){
            
            if(i-1>-1){
                if(array_peças[i-1][j].id()==array_peças[i][j].id()){ //ver peca acima
                array_peças[i][j].adicionar_vertical();
                }
            }
            //Ver peça abaixo
            if(i+1<lado){
                if(array_peças[i+1][j].id()==array_peças[i][j].id()){
                    array_peças[i][j].adicionar_vertical();
                }
            //ver peça a esquerda
            }
            if(j-1>-1){
               if(array_peças[i][j-1].id()==array_peças[i][j].id()){
                   array_peças[i][j].adicionar_horizontal();
               }
            }
            
            if(j+1<lado){
                if(array_peças[i][j+1].id()==array_peças[i][j].id()){
                    array_peças[i][j].adicionar_horizontal();
                }
            }
        }
    }
}
 public void actualizar_auxiliar(Peças a, Peças b){ ////TUDO MAL!!
     
     int i,j;
     Peças temp1;
     Peças temp2;
     for(i=0;i<lado;i++){
         
         for(j=0;j<lado;j++){
             
             auxiliar[i][j]=array_peças[i][j];
             
             
         }
     }
     temp1=a;
     temp2=b;
     for(i=0;i<lado;i++){
         
         for(j=0;j<lado;j++){
             
             if(array_peças[i][j]==b){
                 auxiliar[i][j]=temp1;
                 
             }
             if(array_peças[i][j]==a)
                     auxiliar[i][j]=temp2;
         }
     }
 }
public void ver_peças_ao_lado_auxiliar() {
    
    int i,j;
    for(i=0;i<lado;i++){
        
        for(j=0;j<lado;j++){
            
            if(i-1>-1){
                if(auxiliar[i-1][j].id()==auxiliar[i][j].id()){ //ver peca acima
                auxiliar[i][j].adicionar_vertical();
                }
            }
            //Ver peça abaixo
            if(i+1<lado){
                if(auxiliar[i+1][j].id()==auxiliar[i][j].id()){
                    auxiliar[i][j].adicionar_vertical();
                }
            //ver peça a esquerda
            }
            if(j-1>-1){
               if(auxiliar[i][j-1].id()==auxiliar[i][j].id()){
                   auxiliar[i][j].adicionar_horizontal();
               }
            }
            
            if(j+1<lado){
                if(auxiliar[i][j+1].id()==auxiliar[i][j].id()){
                    auxiliar[i][j].adicionar_horizontal();
                }
            }
        }
    }
}
    public void destruir(){
        int i, j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                if(array_peças[i][j]!=null){
                if(array_peças[i][j].n_peças_vertical()==3){
                        fez_pontos();
                        System.out.println("Destrui se a peça "+array_peças[i][j].id());
                        array_peças[i][j].removeActionListener(this);
                        array_peças[i][j]=null;
                        


                        //rever isto
                if(i-1>-1){
                      if(array_peças[i-1][j]!=null){
                            fez_pontos();
                            System.out.println("Destrui se a peça "+array_peças[i-1][j].id());
                            array_peças[i-1][j].removeActionListener(this);
                            array_peças[i-1][j]=null;
                            


                        }
                }
                if(i+1<lado){
                        if(array_peças[i+1][j]!=null){
                            fez_pontos();
                            System.out.println("Destrui se a peça "+array_peças[i+1][j].id());
                            array_peças[i+1][j].removeActionListener(this);
                            array_peças[i+1][j]=null;
                            

                        }

                }
                }
                }
                if(array_peças[i][j]!=null){
                if(array_peças[i][j].n_peças_horizontal==3){
                    if(j-1>-1){
                        if(array_peças[i][j-1]!=null){
                        fez_pontos();
                        System.out.println("Destrui se a peça "+array_peças[i][j-1].id());
                        array_peças[i][j-1].removeActionListener(this);
                        array_peças[i][j-1]=null;
                        

                        }

                    
                }
                    if(j+1<lado){
                        if(array_peças[i][j+1]!=null){
                        fez_pontos();
                        System.out.println("Destrui se a peça "+array_peças[i][j+1].id());
                        array_peças[i][j+1].removeActionListener(this);
                        array_peças[i][j+1]=null;
                        

                        }

                }
                    
                }
                }
          }
        }
    }
    
     void inicializa_array(){ //inicializa o array de pecas
        int i;
        int j;
        int id_butão=0;
        //aloca espaco;
        //array_peças=new Peças[lado][lado];
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
           Peças peça= new Tipo1(especial);
           peça.addActionListener(new Gere_eventos(janela,array_peças,lado,score,peça,ronda,score_peça,c));
           peça.setText("NOVA!");
           return peça;
        }
        if(tipo==2){
            Peças peça= new Tipo2(especial);
            peça.addActionListener(new Gere_eventos(janela,array_peças,lado,score,peça,ronda,score_peça,c));
            peça.setText("NOVA!");
            return peça;
            
        }
        if(tipo==3){
          Peças peça= new Tipo3(especial);
          peça.addActionListener(new Gere_eventos(janela,array_peças,lado,score,peça,ronda,score_peça,c));
          peça.setText("NOVA!");
          return peça;
        }
        if(tipo==4){
         
           Peças peça= new Tipo4(especial);
           peça.addActionListener(new Gere_eventos(janela,array_peças,lado,score,peça,ronda,score_peça,c));
           peça.setText("NOVA!");
           return peça;
        }
        if(tipo==5){
          Peças peça= new Tipo5(especial);
          peça.addActionListener(new Gere_eventos(janela,array_peças,lado,score,peça,ronda,score_peça,c));
          peça.setText("NOVA!");
          return peça;

        }
        else{
            Peças peça= new Tipo6(especial);
            peça.addActionListener(new Gere_eventos(janela,array_peças,lado,score,peça,ronda,score_peça,c));
            peça.setText("NOVA!");
            return peça;
           

        }
    }
     
    public boolean verificar_se_ronda_acabou(){ //alterei
        int i,j;
        boolean bool=false;
         for(i=0;i<lado;i++){
             if(ha_combinações_horizontal(i)==false)
                 bool= true;
             
             
         }
         for(j=0;j<lado;j++){
             if(ha_combinações_vertical(j)==false)
                 bool= true;
             
         }
        
        return bool;
       
     }
    
    public boolean ha_combinações_horizontal(int a){
        int j;
        int count;
        for(j=0;j<lado-3;j++){ //nao sei se é 4 ou é tres!
             count=1; ///TAVA 0 antes!!
            if(array_peças[a][j].id()==array_peças[a][j+1].id()){
                count++;
            }
            if(array_peças[a][j].id()==array_peças[a][j+2].id()){
                count++;
            }
            if(array_peças[a][j].id()==array_peças[a][j+3].id()){
                count++;
            }
            if (count>2){
                return true;
            }
                
        }
        return false;
        
    }
    public boolean ha_combinações_vertical(int a){
        int i;
        int count;
        for(i=0;i<lado-3;i++){ //nao sei se é 4 ou é tres!
             count=1;  //tava 0 antes!!
            if(array_peças[i][a].id()==array_peças[i+1][a].id()){
                count++;
            }
            if(array_peças[i][a].id()==array_peças[i+2][a].id()){
                count++;
            }
            if(array_peças[i][a].id()==array_peças[i+3][a].id()){
                count++;
            }
            if (count>2){
                return true;
            }
                
        }
        return false;
        
    }
    public void next_round(){
        ronda++;
        inicializa_array();
        System.out.println("--------RONDA: "+ronda+" ----------");
        
    }
    public boolean destruição_inicial(){
        boolean bool=false;
        ver_peças_ao_lado();
        
        int i,j;
        for(i=0;i<lado;i++){
            
            for(j=0;j<lado;j++){
                if(array_peças[i][j]!=null){
                if(array_peças[i][j].n_peças_vertical()==3){
                        bool=true;
                        fez_pontos();
                        System.out.println("Destrui se a peça "+array_peças[i][j].id());
                        array_peças[i][j].removeActionListener(this);
                        array_peças[i][j]=null;
                        
                        //rever isto
                if(i-1>-1){
                        if(array_peças[i-1][j]!=null){
                             bool=true;
                             fez_pontos();
                             System.out.println("Destrui se a peça "+array_peças[i-1][j].id());
                             array_peças[i-1][j].removeActionListener(this);
                             array_peças[i-1][j]=null;

                        }
                    
                }
                if(i+1<lado){
                    if(array_peças[i+1][j]!=null){
                        bool=true;
                        fez_pontos();
                        System.out.println("Destrui se a peça "+array_peças[i+1][j].id());
                        array_peças[i+1][j].removeActionListener(this);
                        array_peças[i+1][j]=null;
                        

                    }

                }
                }
                }
                if(array_peças[i][j]!=null){
                if(array_peças[i][j].n_peças_horizontal()==3){
                    if(j-1>-1){
                        if(array_peças[i][j-1]!=null){
                            bool=true;   
                            fez_pontos();
                            System.out.println("Destrui se a peça "+array_peças[i][j-1].id());
                            array_peças[i][j-1].removeActionListener(this);
                            array_peças[i][j-1]=null;
                            

                        }
                    
                }
                    if(j+1<lado){
                    if(array_peças[i][j+1]!=null){
                        bool=true;
                        fez_pontos();
                        System.out.println("Destrui se a peça "+array_peças[i][j+1].id());
                        array_peças[i][j+1].removeActionListener(this);
                        array_peças[i][j+1]=null;
                        

                    }

                }
                    
                }
                }
                
            }
        }
        
        return bool;
    }
    public void reset_lados(){
        
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                array_peças[i][j].reset();
            }
        }
    }
    public void reset_lado_auxiliar(){
        
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                auxiliar[i][j].reset();
            }
        }
    }
    
   public void fez_pontos(){
       
      score=score+score_peça*ronda;
      System.out.println(score);
   }
   public boolean free_of_nulls(){
       int i,j, count=0;
       
       for(i=0;i<lado;i++){
           for(j=0;j<lado;j++){
                     if(array_peças[i][j]==null)
                           count++;
           
           }
       }
       if(count==0)
       {
           return true;
       }
       else{
           return false;
       }
   }
   private void print_pecas_lado(){
       
       int i, j;
       for(i=0;i<lado;i++){
           
           for(j=0;j<lado;j++){
               
               System.out.println("Peça:"+array_peças[i][j].getText()+"vertical: "+array_peças[i][j].n_peças_vertical()+" horizontal "+array_peças[i][j].n_peças_horizontal());
           }
           
       }
       System.out.println();
   }
   private boolean alguma_lado_3(){
       boolean bool=false;
       int i,j;
       for(i=0;i<lado;i++){
           
           for(j=0;j<lado;j++){
               if(array_peças[i][j].n_peças_horizontal()==3||array_peças[i][j].n_peças_vertical()==3){
                   bool=true;
               }
               
           }
       }
       return bool;
       
       
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
   public void init_listeners(){
        int i,j;
        for(i=0;i<lado;i++){
            for(j=0;j<lado;j++){
                array_peças[i][j].addActionListener(new Gere_eventos(janela,array_peças,lado,score,array_peças[i][j],ronda,score_peça,c));
            }
        }
    }
   public void remover_peças(){
       
       int i,j;
       for(i=0;i<lado;i++){
           
           for(j=0;j<lado;j++){
               janela.remove(array_peças[i][j]);
               
               
           }
       }
   }
  public void desmarcar_se_tiverem_2_marcadas(){
       int count=0;
       int i,j;
       for(i=0;i<lado;i++){
           for(j=0;j<lado;j++){
               if(array_peças[i][j].mark()==1){
                   System.out.println("tinha a peça "+array_peças[i][j].getText());
                   count++;
               }
           }
       }
       if (count>1){
           System.out.println("Tinha mais de duas marcadas!");
           for(i=0;i<lado;i++){
               for(j=0;j<lado;j++){
                   array_peças[i][j].desmarcar();
               }
           }
       }
   }
  public void set_piece_score(){
       score_peça=10;
  }
}


