public class Jogo{
	private Equipa equipa1;
	private Equipa equipa2;
	private int posse_equipa1;
	private int posse_equipa2;
	private Estatistica estatistica_equipa1;
	private Estatistica estatistica_equipa2;
	private int campo; //1 equipa 1, 2 equipa 2
		Jogo(Equipa e1, Equipa e2, int p1,int  p2,Estatistica es1, Estatistica es2, int cam){
			equipa1=e1;
			equipa2=e2;
			posse_equipa1=p1;
			 posse_equipa2=p2;
			 estatistica_equipa1=es1;
			 estatistica_equipa2=es2;
			 campo=cam;
}
		public Equipa equipa1() {
			
			return equipa1;
		}
		public Equipa equipa2() {
			return equipa2;
		}
		public int posse_equipa1() {
			return posse_equipa1;
		}
		public int posse_equipa2() {
			return posse_equipa2;
		}
		public Estatistica estatistica_equipa1() {
			return estatistica_equipa1;
		}
		public Estatistica estatistica_equipa2(){
			return estatistica_equipa2;
		}
		public int campo() {
			return campo;
		}
}