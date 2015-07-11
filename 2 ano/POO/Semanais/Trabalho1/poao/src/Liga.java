public class Liga {   
		private Jogo jogos[];
		private Equipa tabela[];
		Liga( Jogo j[],Equipa t[]){
			jogos=  j;
			tabela=t;
			}
		
		//funcoes de retornar elementos

	public Jogo[] jogos() {
		
		return jogos;
	}
	
	public Equipa[] tabela() {
		
	
		return tabela;
	}
	/*public void actualização ( Jogo jo1 ,Jogo jo2,Jogo jo3,Jogo jo4){
				jogos[]=[jogos, jo1,jo2,jo3, jo4];
				
	}
 */	public void alterar() {
	  int i;
	 for(i=0;i<24;i++) {
		 if(jogos[i].estatistica_equipa1().golos_m_equipa()>jogos[i].estatistica_equipa2().golos_m_equipa()) {
			 jogos[i].estatistica_equipa1().equipa().actualizar(3);
			 
		 }
	 
	 if(jogos[i].estatistica_equipa1().golos_m_equipa()<jogos[i].estatistica_equipa2().golos_m_equipa()) {
		 jogos[i].estatistica_equipa2().equipa().actualizar(3);
	 
 }
	 if(jogos[i].estatistica_equipa1().golos_m_equipa()==jogos[i].estatistica_equipa2().golos_m_equipa()) {
		 jogos[i].estatistica_equipa1().equipa().actualizar(1);
		 jogos[i].estatistica_equipa2().equipa().actualizar(1);

	 }
	 }
 }
 public Estatistica[] cumulativa() {
		int i,j;
		Estatistica[] cumulativa=new Estatistica[4];
		int golos_m_equipa;
		int golos_s_equipa;
		int numero_de_passes;
		int numero_de_passes_correctos;
			for (i=0;i<4;i++){
				golos_m_equipa=0;
				golos_s_equipa=0;
				numero_de_passes=0;
				numero_de_passes_correctos=0;
				for(j=0;j<24;j++)
				{	
					if(jogos[j].equipa1()==tabela[i]){
					golos_m_equipa=golos_m_equipa+jogos[j].estatistica_equipa1().golos_m_equipa();
					golos_s_equipa=golos_s_equipa+jogos[j].estatistica_equipa1().golos_s_equipa();
					numero_de_passes=numero_de_passes+jogos[j].estatistica_equipa1().numero_de_passes();
					numero_de_passes_correctos=numero_de_passes_correctos+jogos[j].estatistica_equipa1().numero_de_passes_correctos();
				
		}
				if(jogos[j].equipa2()==tabela[i]){
					golos_m_equipa=golos_m_equipa+jogos[j].estatistica_equipa2().golos_m_equipa();
					golos_s_equipa=golos_s_equipa+jogos[j].estatistica_equipa2().golos_s_equipa();
					numero_de_passes=numero_de_passes+jogos[j].estatistica_equipa2().numero_de_passes();
					numero_de_passes_correctos=numero_de_passes_correctos+jogos[j].estatistica_equipa2().numero_de_passes_correctos();
				
		}
				
				
		}		
				cumulativa[i]=new Estatistica(tabela[i],golos_m_equipa,golos_s_equipa,numero_de_passes,numero_de_passes_correctos);
				golos_m_equipa=0;
				golos_s_equipa=0;
				numero_de_passes=0;
				numero_de_passes_correctos=0;
			}
				for(i=0;i<4;i++) {
					System.out.println("Estatistica equipa :   "+cumulativa[i].equipa().nome() );
					System.out.println("Golos marcados:"+ cumulativa[i].golos_m_equipa());
					System.out.println("Golos sofridos:"+cumulativa[i].golos_s_equipa() );
					System.out.println("Numero de passes:"+cumulativa[i].numero_de_passes());
					System.out.println("Numero de passes correctos:"+cumulativa[i].numero_de_passes_correctos() );
					System.out.println("\n------------\n");
				}
				return cumulativa;
				
	}
 
}