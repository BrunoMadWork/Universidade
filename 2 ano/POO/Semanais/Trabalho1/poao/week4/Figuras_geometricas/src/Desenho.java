//Bruno Madureira 2011161942
//Christophe Oliveira 2011154902
import java.util.*;
public class Desenho {
	static int count;
	double area_total;

	public Desenho () {
		int n_pontos;
		int n_linhas;
		int n_circulos;
		int n_rectangulos;
		int n_quadrados;
		Scanner sc=new Scanner(System.in);
		System.out.println("Bem vindo ao programa de desenho:");
		System.out.println("Quantos pontos quer?");
		n_pontos=sc.nextInt();
		System.out.println("Quantas linhas quer?");
		n_linhas=sc.nextInt();
		System.out.println("Quantos circulos quer?");
		n_circulos=sc.nextInt();
		System.out.println("Quantos rectangulos quer?");
		n_rectangulos=sc.nextInt();
		System.out.println("Quantos quadrados quer?");
		n_quadrados=sc.nextInt();
		Ponto[] pontos =new Ponto [n_pontos];
		Segmento[] segmentos =new Segmento [n_linhas];
		Circulo[] circulo =new Circulo [n_circulos];
		Rectangulo[] rectangulo =new Rectangulo [n_rectangulos];
		Quadrado[] quadrado =new Quadrado [n_quadrados];



		// meter figuras!!! chamar inicializadores e calcular as areas!
		int i;
		for(i=0;i<n_pontos;i++){
			pontos[i]=new Ponto();
			
			count();
		}
		for(i=0;i<n_linhas;i++){
			segmentos[i]=new Segmento();
			count();
		}
		for(i=0;i<n_circulos;i++){
			circulo[i]=new Circulo();
			count();
			area_total=area_total+circulo[i].area();
		}
		for(i=0;i<n_rectangulos;i++){
			rectangulo[i]=new Rectangulo();
			count();
			area_total=area_total+rectangulo[i].area();
		}
		for(i=0;i<n_quadrados;i++){
			quadrado[i]=new Quadrado();
			count();
			area_total=area_total+quadrado[i].area();
		}
		
		System.out.println("Numero de pontos: "+n_pontos+"\nNumeros de linhas: "+ n_linhas+"\nNumero de circulos: "+n_circulos+"\nNUmero de rectangulos:"+n_rectangulos+"\nNumero de quadrados:"+n_quadrados );
		System.out.println("A area Ž: "+area_total+" est‹o usados "+count );
	}
	public void count(){
		count++;
	}
	
}