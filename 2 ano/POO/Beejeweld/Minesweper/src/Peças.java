import javax.swing.*;
import java.awt.*;


public class Peças extends JButton{
	//// temos as formas
	
	protected  int id;
	protected int n_peças_horizontal;
	protected int n_peças_vertical;
	 protected ImageIcon imagem;
	 protected ImageIcon imagem_pow1;//cada metodo tem a sua imagem associada
	 protected ImageIcon imagem_pow2;
	 protected ImageIcon imagem_pow3;
         protected int mark=0;
	 
	public int id(){
		return id;
	}


	public int getId() {
		return id;
	}
	
	public void reset(){
		n_peças_horizontal=1;
		n_peças_vertical=1;
	}
	public int n_peças_horizontal(){
		return n_peças_horizontal;
	}
	public int n_peças_vertical(){
		return n_peças_vertical;
	}
	public void adicionar_vertical(){
		n_peças_vertical++;
}
	public void adicionar_horizontal(){
		n_peças_horizontal++;
}
	
	public ImageIcon imagem(){
		return imagem;
	}
	
	public ImageIcon imagem_pow1(){
		return imagem;
	}
	
	public ImageIcon imagem_pow2(){
		return imagem;
	}
	
	public ImageIcon imagem_pow3(){
		return imagem;
	}
        public int  mark(){ //se mark for um, trocam as pecas com mark=1
            return mark;
        }
        public void marcar(){
            mark=1;
        }
        public void desmarcar(){
            mark=0;
        }
        
        
       

    
}