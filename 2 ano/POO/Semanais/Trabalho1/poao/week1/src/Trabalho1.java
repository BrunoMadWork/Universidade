/*Bruno Madureira
 * Christophe Oliveira
 */

import java.util.*;
public class Trabalho1 {
	public static void main(String[] args){
		int largura=0,comprimento=0;
		System.out.println("Bem vindo ao MINESWEEPER!");
		largura=inserir_largura();
		comprimento=inserir_comprimento();
		int[][] campo=new int[comprimento][largura];
		
		
		int nminas=(int) (0.15*(largura*comprimento));
		inicializar(campo,comprimento,largura,nminas);
		///agora vai comecar o jogo em si//
		jogo(campo,comprimento,largura,nminas);
		imprimir(campo,comprimento,largura);
	}
	public static int[][] inicializar (int campo[][],int comprimento, int largura, int nminas){
		
		int i=0,j=0;
		for(i=0;i<comprimento;i++){
			for(j=0;j<largura;j++){
				campo[i][j]=0;
			}
		}
		while(nminas!=0){
			Random randomGenerator=new Random();
			do{
			i=randomGenerator.nextInt(comprimento-1);//-1//
			j=randomGenerator.nextInt(largura-1);//-1//
			}while(campo[i][j]==-1);
				campo[i][j]=-1;
				nminas--;
		}
		for(i=0;i<comprimento;i++){
			for(j=0;j<largura;j++){
					if(campo[i][j]==-1){
							//verificacao casa a cima//
						if(i>0&&campo[i-1][j]!=-1)
							campo[i-1][j]++;
						//casa a baixo//
						if(i<comprimento&&campo[i+1][j]!=-1)
							campo[i+1][j]++;
						//verificacao esquerda//
						if(j>0&&campo[i][j-1]!=-1)
							campo[i][j-1]++;
						//verificacao direita
						if(j<largura&&campo[i][j+1]!=-1)
							campo[i][j+1]++;
						//verificacao canto superior esquerdo//
						if(i>0&&j>0&&campo[i-1][j-1]!=-1)
							campo[i-1][j-1]++;
						//verifiacao campo baixo esquerdo//
						if((i<comprimento)&&j>0&&campo[i+1][j-1]!=-1)
							campo[i+1][j-1]++;
						//verificacao campo superior direito//
						if(i>0&&j<largura&&campo[i-1][j+1]!=-1)
							campo[i-1][j+1]++;
						//verificacao campo inferior direito//
						if(i<comprimento&&j<largura&&campo[i+1][j+1]!=-1)
							campo[i+1][j+1]++;
					}
				
		}
		}
		return campo;
	}

	public static int inserir_largura(){
		int largura;
		Scanner sc=new Scanner(System.in);
		do{
			System.out.println("Insira a largura:");
			largura=sc.nextInt();
		}while(largura>20||largura<1);
			return largura;
	}
	public static int inserir_comprimento(){
		int comprimento;
		Scanner sc=new Scanner(System.in);
		do{
			System.out.println("O comprimento:");
			comprimento=sc.nextInt();
		}while(comprimento>20||comprimento<1);
			return comprimento;
	}

	public static void jogo(int campo[][], int comprimento, int largura, int nminas){
		int pontuação=0;
		int [][] jjogado=new int[comprimento][largura];
		jjogado=matriz_jogadas(comprimento,largura);//jjogado matriz que tem as posicoes ja jogadas
		System.out.println("Quantas casas prentende visitar?");
		Scanner sc=new Scanner(System.in);
		int jogadas=sc.nextInt();
		int i;
		int a,b;
		int minas=0;
		int check=0;
		int check2=0;
		for(i=0;i<jogadas;i++){//fazer algo para o utilizador nao escolher as mesmas coordenadas//
			System.out.println("Jogada "+(i+1)+" de "+jogadas+" .Insira as codernadas, primeiro coluna depois linha");
				do{
					check2=0;
				a=sc.nextInt()-1;//a cada posicao e somado -1 ja que o utilizador nao utiliza indices como java
				b=sc.nextInt()-1;
					if((a<0||b<0||a>=comprimento||b>=largura)){
						System.out.println("Posição ilegal! Escolha outra!");
						check=1;}
					if(check==0){	
						if(jjogado[a][b]==-1){
							check2=1;
					System.out.println("Posição já jogada! Escolha outra!");
					}
					}
						check=0;
				}while(check2==1|(a<0||b<0||a>=comprimento||b>=largura));
				jjogado[a][b]=-1;
				if(campo[a][b]!=-1)
					pontuação=pontuação+campo[a][b];
				else
					minas++;
		}
		if(minas!=0)
			pontuação=-1*pontuação;
		System.out.println("O jogo acabou! Acertou em "+minas+"minas  a pontuação é "+ pontuação);
	
		
	}



public static void imprimir(int campo[][],int comprimento,int largura) {
	int i,j;
	for(i=0;i<comprimento;i++){
		for(j=0;j<largura;j++){
			if(campo[i][j]==-1)
				System.out.print("M ");
			else 
				System.out.print(campo[i][j]+" ");
			
			if (j==largura-1)
				System.out.println("");
			
		}
	}
	
}
public static int[][]matriz_jogadas(int comprimento,int largura){
	int [][] matriz=new int [comprimento][largura];
	int i,j;
	for(i=0;i<comprimento;i++){
		
		for(j=0;j<largura;j++){
			
			matriz[i][j]=0;
		}
	}
	return matriz;
}
}
