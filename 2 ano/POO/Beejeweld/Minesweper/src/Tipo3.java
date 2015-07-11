import javax.swing.ImageIcon;


public class Tipo3 extends Peças {

	private int power; //se for 0 nao tem, se for 1 tem tipo 1 .....
	
	public Tipo3(int p){//inicializar senpre assim
		super.id=3;
		power=p;
                reset();
                desmarcar();
		imagem=new ImageIcon("Firefox.png");
		imagem_pow1= new ImageIcon("1Laranja.png");                          //cada metodo tem a sua imagem associada
		imagem_pow2=new ImageIcon("2Laranja.png");  
		imagem_pow3=new ImageIcon("3Laranja.png");  
		
                if(p==0){
                            setIcon(imagem);
                        }
                        if(p==1){
                            setIcon(imagem_pow1);
                        }
                        
                        if(p==2){
                            setIcon(imagem_pow2);
                        }
                        
                        if(p==3){
                            setIcon(imagem_pow3);
                        }
		
	}

public int  get_power(){
	return power;
}

}
