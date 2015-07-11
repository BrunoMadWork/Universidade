/*Bruno Madureira
 * Christophe Oliveira
 */

import java.util.*;
public class Trabalho2 {
	public static void main(String[] chars){
	System.out.println("Bem vindo! Insira 10 palavras!!:");
	String[] lista=inserir();
	Scanner sc=new Scanner(System.in);
	System.out.println("Insira a frase");
	String frase=sc.nextLine();
	String corrigida=correc��o(lista,frase);
	System.out.println("A frase final corrigida �:"+corrigida);
	
}
	public static String[] inserir() {
		int i;
		String[] lista=new String[10];
		Scanner sc=new Scanner(System.in);
		
		for(i=0;i<10;i++){
			System.out.println("Insira o elemento "+(i+1)+":");
			lista[i]=sc.nextLine();
			
		}
		return lista;
	}
	

	public static String correc��o(String[] lista, String frase){
		StringTokenizer componente=new StringTokenizer(frase);
		String corrigida="";
		String temp;
		while(componente.hasMoreElements()){
			temp=componente.nextToken();
			corrigida=corrigida.concat(opera��o(temp,lista)+" ");
		}
		return corrigida;
	}
	public static String opera��o(String palavra,String[] lista ){
		Scanner sc=new Scanner(System.in);
		int i,j;
		int check=1;
		for(i=0;i<10;i++){
			if(palavra.length()>lista[i].length()&&(palavra.length()-lista[i].length()<=2)&&palavra.length()>=2){
				int diferen�a=0;
				for(j=2;j<lista[i].length();j++){
					if(lista[i].charAt(j)!=palavra.charAt(j)){
						diferen�a++;
					}
				}
				if(diferen�a<2){
					check=0;
				}
			}
			//
			else if(palavra.length()<lista[i].length()&&lista[i].length()-palavra.length()<=2&&palavra.length()>=2){
				int diferen�a=0;
				for(j=2;j<palavra.length();j++){
					if(lista[i].charAt(j)!=palavra.charAt(j)){
						diferen�a++;
					}
				}
				if(diferen�a<2){
					check=0;
				}
			}
			//else check=0;
			
			
			if(palavra.length()>1&&palavra.charAt(0)==lista[i].charAt(0)&&palavra.charAt(1)==lista[i].charAt(1)&&(check==0||lista[i].length()<=2)){
				System.out.println("sera "+palavra+" ou "+lista[i]+"? (s/n)");
				if(sc.nextLine().equals("s")){
					String nova = lista[i];
					return nova;
				}
			}
			else if(palavra.length()==lista[i].length()&&lista[i].charAt(1)==palavra.charAt(1)){
				System.out.println("sera "+palavra+"ou "+lista[i]+"? (s/n)");
				if(sc.nextLine().equals("s")){
					String nova = lista[i];
					return nova;
				}
			}
				//perguntar ao utilizador
	
		}
		 return palavra;
	}
}