import java.util.*;
public class Segmento extends Figura_geometrica {
	protected Ponto ponto1;
	protected Ponto ponto2;
	protected float comprimento;
	public Segmento(int a,int b,int c,int d) {
		this.name="Segmento";
		ponto1=new Ponto(a,b);
		ponto2=new Ponto(c,d);
		comprimento=distancia(ponto1,ponto2);
	}
	public Segmento() {
		int [][] array=new int[2][2];
		do {
		this.name="segmento";
		Scanner sc=new Scanner(System.in);
		System.out.println("Insira o ponto 1(segmento)");
		System.out.println("Insira a coordenada x(segmento)");
		int x=sc.nextInt();
		System.out.println("Insira a coordenada y(segmento)");
		int y=sc.nextInt();
		ponto1=new Ponto(x,y);
		array[0]=new int[] {x,y};
		System.out.println("Insira o ponto 2(segmento)");
		System.out.println("Insira a coordenada x(segmento)");
		x=sc.nextInt();
		System.out.println("Insira a coordenada y(segmento)");
		y=sc.nextInt();
		ponto2=new Ponto(x,y);
		array[1]=new int[] {x,y};
		comprimento=distancia(ponto1,ponto2);
	}while(comparacção(array)==false);
	}
	float distancia(Ponto ponto1, Ponto ponto2){
		comprimento=(float) Math.sqrt(Math.pow((ponto2.x()-ponto1.x()),2)+Math.pow((ponto2.y()-ponto1.y()),2));
		return comprimento;
	}
	float comprimento(){
		return comprimento;
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
