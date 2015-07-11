import java.io.Serializable;


public class Entrada_historico implements Serializable{
	private String vendedor;
	private String comprador;
	private int  ac��es;
	private float preco;
	private int idea_key;
	private int n_compra;
	private String tipo;
	public Entrada_historico(String vendedor, String comprador, int ac��es,	float preco, int idea_key, int n_compra, int t) {
		this.vendedor = vendedor;
		this.comprador = comprador;
		this.ac��es = ac��es;
		this.preco = preco;
		this.idea_key = idea_key;
		this.n_compra = n_compra;
		if(t==1){
			tipo=comprador+" comprou a "+vendedor;
		}
		else{
			tipo=vendedor+" vendeu a "+comprador;
		}	
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getComprador() {
		return comprador;
	}
	public void setComprador(String comprador) {
		this.comprador = comprador;
	}
	public int getAc��es() {
		return ac��es;
	}
	public void setAc��es(int ac��es) {
		this.ac��es = ac��es;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public int getIdea_key() {
		return idea_key;
	}
	public void setIdea_key(int idea_key) {
		this.idea_key = idea_key;
	}
	public int getN_compra() {
		return n_compra;
	}
	public void setN_compra(int n_compra) {
		this.n_compra = n_compra;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String printEntry(){
		String temp=" "+tipo+" "+ac��es+" ac��es que custaram "+preco+"cada \n";
		return temp;
	}
}
