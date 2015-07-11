import java.io.Serializable;
import java.util.ArrayList;


public class Idea implements Serializable {
	private int acções_totais;
	private int preco;
	private int tipo;
	private String texto;
	private int idea_key;
	public Idea(int acções_totais, int preco, int tipo, String texto,int idea_key) {
		this.acções_totais = acções_totais;
		this.preco = preco;
		this.tipo = tipo;
		this.texto = texto;
		this.idea_key = idea_key;
	}
	public int getAcções_totais() {
		return acções_totais;
	}
	public void setAcções_totais(int acções_totais) {
		this.acções_totais = acções_totais;
	}
	public int getPreco() {
		return preco;
	}
	public void setPreco(int preco) {
		this.preco = preco;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getIdea_key() {
		return idea_key;
	}
	public void setIdea_key(int idea_key) {
		this.idea_key = idea_key;
	}
}
