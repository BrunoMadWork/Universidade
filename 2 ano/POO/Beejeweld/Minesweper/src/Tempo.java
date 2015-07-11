import java.util.*;
public class Tempo {
	long tempo;
	
	public Tempo(int t) {
		tempo=t;
	}
	
	public long tempo(){
		return tempo;
		
	}
	public void alterar(int valor){
		tempo=valor;
	}
	public long tempo_actual(){//retunr 1 se tiver na boa 0 se acabar
		return System.currentTimeMillis();
	}
	public boolean time_up() {//return true se perdeu
		if(tempo_actual()>=tempo){
			return true;
		}
		else return false;
		
	}
	public void  diminuir_tempo(int valor){
		tempo=tempo-valor;
	}
	
}
