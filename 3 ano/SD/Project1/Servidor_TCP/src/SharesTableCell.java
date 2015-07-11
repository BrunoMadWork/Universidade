import java.io.Serializable;


public class SharesTableCell implements Serializable {
		private String nome;
		private int idea_key;
		private int acções;
		private float preço;
		public SharesTableCell(String nome,int acções, int idea_key, float preço) {
			this.nome = nome;
			this.acções=acções;
			this.idea_key = idea_key;
			this.preço = preço;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public int getIdea_key() {
			return idea_key;
		}
		public void setIdea_key(int idea_key) {
			this.idea_key = idea_key;
		}
		public float getPreço() {
			return preço;
		}
		public void setPreço(float preço) {
			this.preço = preço;
		}
		public int getAcções() {
			return acções;
		}
		public void setAcções(int acções) {
			this.acções = acções;
		}	
}
