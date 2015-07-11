
public class Dados_ficheiro {
	private String nome;
	private int score;
	
	

	public String get_nome(){//devolve nome
		return nome;
	}
	
	public int get_score(){ //devolve a pontuacao
		return score;
	}
	public void new_nome(String n){ //devolve o nome
		nome=n;
	}
	public void new_score(int sc){ //devolve o score
		score=sc;
	}
}
