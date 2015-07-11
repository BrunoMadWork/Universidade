public class Equipa{
	private String nome;
	private String treinador;
	private Jogador jogadores[];
	private int pontuação;
		Equipa(String no, String t,Jogador joga[], int p){
			nome=no;
			treinador=t;
			jogadores=joga;
			pontuação=p;
			
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
				System.out.println(jogadores[i].posição());
				System.out.println((jogadores[i].data()).dia());
				System.out.println((jogadores[i].data()).mes());
				System.out.println((jogadores[i].data()).ano());
			}
		}
		public int pontuação() {
			return pontuação;
		}
		public void actualizar(int valor) {
			
			pontuação=pontuação+valor;
		}
		public void zero() {
			pontuação=0;
		}
	
}



