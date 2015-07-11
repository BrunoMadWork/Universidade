import javax.swing.ImageIcon;


public class Tipo4 extends Peças {
	private int power; //se for 0 nao tem, se for 1 tem tipo 1 .....
	
	
	public Tipo4(int p){//inicializar senpre assim
		super.id=4;
		power=p;
                reset();
                desmarcar();
		imagem=new ImageIcon("Netscape.png");
		imagem_pow1= new ImageIcon("1Preto.png");                          //cada metodo tem a sua imagem associada
		imagem_pow2=new ImageIcon("2Preto.png");  
		imagem_pow3=new ImageIcon("3Preto.png");  
		
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
