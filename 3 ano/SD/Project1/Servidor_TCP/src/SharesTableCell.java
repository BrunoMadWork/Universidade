import java.io.Serializable;


public class SharesTableCell implements Serializable {
		private String nome;
		private int idea_key;
		private int ac��es;
		private float pre�o;
		public SharesTableCell(String nome,int ac��es, int idea_key, float pre�o) {
			this.nome = nome;
			this.ac��es=ac��es;
			this.idea_key = idea_key;
			this.pre�o = pre�o;
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
		public float getPre�o() {
			return pre�o;
		}
		public void setPre�o(float pre�o) {
			this.pre�o = pre�o;
		}
		public int getAc��es() {
			return ac��es;
		}
		public void setAc��es(int ac��es) {
			this.ac��es = ac��es;
		}	
}
