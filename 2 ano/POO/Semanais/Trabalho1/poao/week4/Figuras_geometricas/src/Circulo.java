import java.util.*;
public class Circulo extends Figura_geometrica {

	protected Ponto centro;
	protected int raio;
	Circulo(int a , int b) {
		Ponto centro=new Ponto(a,b);
		this.name="Circulo";
	}
	Circulo() {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Insira o centro (circulo)");
		System.out.println("Insira a coordenada x(circulo)");
		int a=sc.nextInt();
		System.out.println("Insira a coordenada y(circulo)");
		int b=sc.nextInt();
		System.out.println("Insira o raio(circulo)");
		raio=sc.nextInt();
		Ponto centro=new Ponto(a,b);
		this.name="Circulo";
		
	}
		
		
	public double area() {
		double area= Math.PI*(raio)*(raio);
		return area;
	}
}