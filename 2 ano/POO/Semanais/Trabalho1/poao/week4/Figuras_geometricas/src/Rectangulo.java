import java.util.*;
public class Rectangulo extends Figura_geometrica {
	protected Segmento largura1;
	protected Segmento largura2;
	protected Segmento comprimento1;
	protected Segmento comprimento2;

	int largura;
	public Rectangulo(int a , int b, int c, int d, int e, int f , int g , int h) { 
																			
		this.name="rectangulo";
		largura1=new Segmento(a,b,c,d);
		largura2=new Segmento(e,f,g,h);
		comprimento1=new Segmento(a,b,e,f);
		comprimento2=new Segmento(c,d,g,h);

	}
	public Rectangulo() {
		float len1;
		float len2;
		float len3;
		float len4;
		int a,b,c,d,e,f,g,h;
		Scanner sc=new Scanner(System.in);
		int [][] array=new int[4][2];
		do{
		System.out.println("Insira a coordenada x do 1 vertice(rectangulo)");
		a=sc.nextInt();
		System.out.println("Insira a coordenada y do 1 vertice (rectangulo)");
		b=sc.nextInt();
		array[0]=new int[] {a,b};
		System.out.println("Insira a coordenada x do 2 vertice(rectangulo)");
		c=sc.nextInt();
		System.out.println("Insira a coordenada y do 2 vertice(rectangulo) ");
		d=sc.nextInt();
		array[1]=new int[] {c,d};
		System.out.println("Insira a coordenada x do 3 vertice(rectangulo)");
		e=sc.nextInt();
		System.out.println("Insira a coordenada y do 3 vertice (rectangulo)");
		f=sc.nextInt();
		array[2]=new int[] {e,f};
		System.out.println("Insira a coordenada x do 4 vertice(rectangulo)");
		g=sc.nextInt();
		System.out.println("Insira a coordenada y do 4 vertice (rectangulo)");
		h=sc.nextInt();
		array[3]=new int[] {g,h};
		len1=(new Segmento(a,b,c,d)).comprimento();
		len2=(new Segmento(e,f,g,h)).comprimento();
		len3=(new Segmento(a,b,e,f)).comprimento();
		len4=(new Segmento(c,d,g,h)).comprimento();
	
		}while((len1!=len2)&&(len3!=len4)&&comparac��o(array)==false);
		
		this.name="rectangulo";
		largura1=new Segmento(a,b,c,d);
		largura2=new Segmento(e,f,g,h);
		comprimento1=new Segmento(a,b,e,f);
		comprimento2=new Segmento(d,e,g,h);

	}
	public double area() {
		return largura1.comprimento()*comprimento1.comprimento();
	}
	public boolean comparac��o(int array[][]){
		int i,j;
		boolean bo=true;
		for(i=0;i<array.length;i++){
			for(j=i+1;j<array.length;j++) {
				if(array[i][0]==array[j][0]||array[i][1]==array[j][1])
					bo=false;
		}
		}
		return bo;
	}
}
