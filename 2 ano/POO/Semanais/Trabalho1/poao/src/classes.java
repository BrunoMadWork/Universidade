import java.util.*;
public class classes {

	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		int i,j;
		Random random=new Random();
		Jogador jogadores[][]=new Jogador[4][16];
		for(i=0;i<4;i++){
			for(j=0;j<16;j++){
				String posi‹o="";
				int a=random.nextInt(4);
				if(a==1)
					posi‹o="defesa";
				if(a==0)
					posi‹o="guarda redes";
				if(a==2)
					posi‹o="medio";
				if(a==3)
					posi‹o="avanado";
				jogadores[i][j]=new Jogador("ZŽ"+i+j,posi‹o ,new Datas(random.nextInt(31),random.nextInt(12),1980+random.nextInt(30)));
			}
		}
		Equipa equipas[]=new Equipa[4];
		
			equipas[0]=new Equipa("Sporting", "Vercautren",jogadores[0], 0);
			equipas[1]=new Equipa("Porto","Victor Pereira" ,jogadores[1], 0);
			equipas[2]=new Equipa("Benfica","Jesus", jogadores[2], 0);
			equipas[3]=new Equipa("AcadŽmica","Pedro Emanuel",jogadores[3], 0 );
			
		Jogo[] jogos=new Jogo[24];
		//pedir ao utilizador 4 jogos
		
		int equipa_casa;
		int equipa_fora;
		int jogos_feitos[][]=new int[24][2];	
		for(i=0;i<4;i++){
			int check=0;
			int gc, gf,passes_casa,passes_sucesso_casa, passes_fora,passes_sucesso_fora,posse_casa,campo;
			do {
				System.out.println("Jogo "+(i+1));
		System.out.println("Insira a equipa de casa\n1-"+equipas[0].nome()+"\n2-"+equipas[1].nome()+"\n3-"+equipas[2].nome()+"\n4-"+equipas[3].nome());
			equipa_casa=sc.nextInt()-1;
		System.out.println("Insira o numero de golos");
			gc=sc.nextInt();
			System.out.println("Insira o numero de passes"); //passes sucesso menor numero de passes
			passes_casa=sc.nextInt();
			System.out.println("Insira o numero de passes com sucesso");
			passes_sucesso_casa=sc.nextInt();
			System.out.println("Insira a posse de bola da equipa de casa");
			posse_casa=sc.nextInt();

		System.out.println("Insira a equipa de fora\n 1-"+equipas[0].nome()+"\n2-"+equipas[1].nome()+"\n3-"+equipas[2].nome()+"\n4-"+equipas[3].nome());
				equipa_fora=sc.nextInt()-1;
				System.out.println("Insira o numero de golos");
				gf=sc.nextInt();
				System.out.println("Insira o numero de passes"); //passes sucesso menor numero de passes
				passes_fora=sc.nextInt();
				System.out.println("Insira o numero de passes com sucesso");
				passes_sucesso_fora=sc.nextInt();
				System.out.println("insira o campo (0-casa /1-fora)");
				campo=sc.nextInt();
				jogos_feitos[i][0]=equipa_casa;
				jogos_feitos[i][1]=equipa_fora;
				if(equipa_casa!=equipa_fora&&equipa_fora>=0&&equipa_fora<4&&equipa_casa>=0&&equipa_fora<4&&gc>-1&&gf>-1&&passes_casa>-1&&passes_fora>-1&&passes_sucesso_casa>-1&&passes_sucesso_fora>0&&passes_sucesso_casa<=passes_casa&&passes_sucesso_fora<=passes_fora&&posse_casa>-1&&posse_casa<=90&&campo>=0&&campo<=1){
					check=1;
				}
					int k;
					for(k=0;k<i;k++){
						if(equipa_casa==jogos_feitos[k][0]&&equipa_fora==jogos_feitos[k][1])
							check=0;
					}
				
			}while(check==0);
			jogos[i]=new Jogo(equipas[equipa_casa],equipas[equipa_fora],posse_casa,90-posse_casa,new Estatistica(equipas[equipa_casa],gc, gf, passes_casa, passes_sucesso_casa ) ,new Estatistica(equipas[equipa_fora],random.nextInt(10), random.nextInt(10), passes_fora, passes_sucesso_fora ) ,campo);
		}
		//GERAR EQUIPAS ALIETORIOS
		for(i=4;i<24;i++){ 
				int check=0;
				do{
				equipa_casa=random.nextInt(4);
				equipa_fora=random.nextInt(4);
				if(equipa_casa!=jogos_feitos[i][0]&&equipa_casa!=jogos_feitos[i][1]){
					check=1;
				}
				}while(check==0);
				int tempo = random.nextInt(90);
				jogos[i]=new Jogo(equipas[equipa_casa],equipas[equipa_fora],tempo,90-tempo,new Estatistica(equipas[equipa_casa],random.nextInt(10), random.nextInt(10), random.nextInt(1000), random.nextInt(1000) ) ,new Estatistica(equipas[equipa_fora],random.nextInt(10), random.nextInt(10), random.nextInt(1000), random.nextInt(1000) ) ,random.nextInt(1));
		
		
	}

	Liga liga=new Liga(jogos,equipas);
	liga.alterar();
	liga.cumulativa();
	System.out.println("TABELA CLASSIFICATIVA");
	for(i=0;i<4;i++){
		System.out.println(liga.tabela()[i].nome());
		System.out.println(liga.tabela()[i].pontua‹o());

	}
}
}
