 public class Jogador{
	private String nome;
	private String posi��o;
	private Datas data;
	Jogador(String n,String p,Datas d) {
		nome=n;
		posi��o=p;
		data=d;
	}
	public String nome() {
		return nome;
	}
	
	public String posi��o() {
		return posi��o;
	}
	public Datas data() {
		return data;
	}
	
}