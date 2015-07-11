import javax.swing.ImageIcon;


public class Tipo1 extends Peças {
	private int power;//se for 0 nao tem, se for 1 tem tipo 1 .....
	
	
	
	public Tipo1(int p){//inicializar senpre assim
			super.id=1;
			power=p;
                        reset(); //poe a 1 o numero de peças
                        desmarcar();
			imagem=new ImageIcon("Chrome.png");
			imagem_pow1= new ImageIcon("1verde.png");                          //cada metodo tem a sua imagem associada
			imagem_pow2=new ImageIcon("2verde.png");  
			imagem_pow3=new ImageIcon("3verde.png");  
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
