 public class Jogador{
	private String nome;
	private String posição;
	private Datas data;
	Jogador(String n,String p,Datas d) {
		nome=n;
		posição=p;
		data=d;
	}
	public String nome() {
		return nome;
	}
	
	public String posição() {
		return posição;
	}
	public Datas data() {
		return data;
	}
	
}