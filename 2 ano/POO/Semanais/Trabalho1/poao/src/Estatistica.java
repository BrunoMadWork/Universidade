public class Estatistica{
	Equipa equipa;
	int golos_m_equipa;
	int golos_s_equipa;
	int numero_de_passes;
	int numero_de_passes_correctos;
	
		Estatistica(Equipa eq1, int gme1, int gms1,int np,int npc){
			equipa=eq1;
			golos_m_equipa=gme1;
			golos_s_equipa=gms1;
			 numero_de_passes=np;
			numero_de_passes_correctos=npc;
	
}
		
		public Equipa equipa() {

			return equipa;
		}
		
		int golos_m_equipa() {
			return golos_m_equipa;
		}
		int golos_s_equipa() {
			return golos_s_equipa;
		}
	
		public int numero_de_passes(){
			return numero_de_passes;
		}
		public int numero_de_passes_correctos() {
			return numero_de_passes_correctos;
		}
}
