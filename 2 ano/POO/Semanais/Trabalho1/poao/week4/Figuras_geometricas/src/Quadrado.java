import java.util.*;
public class Quadrado extends Figura_geometrica {
	protected Segmento lado1;
	protected Segmento lado2;
	protected Segmento lado3;
	protected Segmento lado4;
	public Quadrado(int a,int b,int c, int d,int e, int f,int g, int h) {
		this.name="Quadrado";
		lado1=new Segmento(a,b,c,d);
		lado2=new Segmento(e,f,g,h);
		lado3=new Segmento(a,b,e,f);
		lado4=new Segmento(c,d,g,h);
	}
	public Quadrado() {
		int [][] array=new int[4][2];
		this.name="Quadrado";
		float len1;
		float len2;
		float len3;
		float len4;
		int a,b,c,d,e,f,g,h;
		Scanner sc=new Scanner(System.in);
		do{

		System.out.println("Insira a coordenada x do 1 vertice(quadrado)");
		a=sc.nextInt();
		System.out.println("Insira a coordenada y do 1 vertice (quadrado)");
		b=sc.nextInt();
		array[0]=new int[] {a,b};
		System.out.println("Insira a coordenada x do 2 vertice(quadrado)");
		c=sc.nextInt();
		System.out.println("Insira a coordenada y do 2 vertice (quadrado)");
		d=sc.nextInt();
		array[1]=new int[] {c,d};
		System.out.println("Insira a coordenada x do 3 vertice(quadrado)");
		e=sc.nextInt();
		System.out.println("Insira a coordenada y do 3 vertice(quadrado) ");
		f=sc.nextInt();
		array[2]=new int[] {e,f};
		System.out.println("Insira a coordenada x do 4 vertice(quadrado)");
		g=sc.nextInt();
		System.out.println("Insira a coordenada y do 4 vertice (quadrado)");
		h=sc.nextInt();
		array[3]=new int[] {c,d};
		len1=(new Segmento(a,b,c,d)).comprimento();
		len2=(new Segmento(e,f,g,h)).comprimento();
		len3=(new Segmento(a,b,e,f)).comprimento();
		len4=(new Segmento(c,d,g,h)).comprimento();
	
		}while((len1!=len2)&&(len3!=len4)&&comparacção(array)==false);
		lado1=new Segmento(a,b,c,d);
		lado2=new Segmento(e,f,g,h);
		lado3=new Segmento(a,b,e,f);
		lado4=new Segmento(c,d,g,h);;

	}
	public double area() {
		float a=lado1.comprimento()*lado1.comprimento();
		return a;
	}
	public boolean comparacção(int array[][]){
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
