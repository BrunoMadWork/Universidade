import java.io.Serializable;


public class Topico   implements Serializable{
	private String texto;
	private int topic_key;
	public Topico(String texto, int topic_key) {
		super();
		this.texto = texto;
		this.topic_key = topic_key;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getTopic_key() {
		return topic_key;
	}
	public void setTopic_key(int topic_key) {
		this.topic_key = topic_key;
	}
}
