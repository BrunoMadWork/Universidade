public class Equipa{
	private String nome;
	private String treinador;
	private Jogador jogadores[];
	private int pontua��o;
		Equipa(String no, String t,Jogador joga[], int p){
			nome=no;
			treinador=t;
			jogadores=joga;
			pontua��o=p;
			
		}
		public String nome() {
				return nome;
			}
		public String treinador() {
			return treinador;
		}
		///COMO FACO PARA DEVOLVER UM ARRAY?!?!?!?
		public Jogador[] Jogadores() {
			
				return jogadores;
		}
		public void  imprimir_equipa() {
			int n=jogadores.length;
			int i;
			for(i=0;i<n;i++){
				System.out.println(jogadores[i].nome());
				System.out.println(jogadores[i].posi��o());
				System.out.println((jogadores[i].data()).dia());
				System.out.println((jogadores[i].data()).mes());
				System.out.println((jogadores[i].data()).ano());
			}
		}
		public int pontua��o() {
			return pontua��o;
		}
		public void actualizar(int valor) {
			
			pontua��o=pontua��o+valor;
		}
		public void zero() {
			pontua��o=0;
		}
	
}



