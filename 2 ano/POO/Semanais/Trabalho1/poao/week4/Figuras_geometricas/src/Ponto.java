import java.util.*;
public class Ponto extends Figura_geometrica {
			protected int x;
			protected int y;
			Ponto(int a,int b ) {
				super.name="ponto";
				x=a;
				y=b;
			
				
			}
			Ponto() {
				super.name="ponto";
				Scanner sc=new Scanner(System.in);
				System.out.println("Insira a coordenada x(ponto)");
				x=sc.nextInt();
				System.out.println("Insira a coordenada y(ponto)");
				y=sc.nextInt();
			}
			int x() {
				return x;
			}
			int y() {
				return y;
			}
}
