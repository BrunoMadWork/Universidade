import java.io.Serializable;

public class Valor  implements Serializable {
	private String password;
	private int n_shares;
	public Valor(String password, int n_shares) {
		this.password = password;
		this.n_shares = n_shares;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getN_shares() {
		return n_shares;
	}
	public void setN_shares(int n_shares) {
		this.n_shares = n_shares;
	}
}
